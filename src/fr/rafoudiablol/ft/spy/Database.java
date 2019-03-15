package fr.rafoudiablol.ft.spy;

import com.mojang.authlib.yggdrasil.response.User;
import fr.rafoudiablol.ft.config.EnumI18n;
import fr.rafoudiablol.ft.events.FinalizeTransactionEvent;
import fr.rafoudiablol.ft.main.FairTrade;
import fr.rafoudiablol.ft.trade.Offer;
import fr.rafoudiablol.ft.trade.OfflineOffer;
import fr.rafoudiablol.ft.trade.OfflineTrade;
import fr.rafoudiablol.ft.utils.YamlUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.Query;
import org.jdbi.v3.core.statement.Script;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import static fr.rafoudiablol.ft.main.FairTrade.getFt;

public class Database implements Listener
{
    private Logger log;
    private String insertStatement;
    private Jdbi jdbi;
    private Handle handle;

    public Database(Logger log)
    {
        this.log = log;
    }

    public void connect(String path) {
        String conPath = "jdbc:sqlite:" + path;

        try {

            Class.forName("org.sqlite.JDBC");
            jdbi = Jdbi.create(conPath);
            handle = jdbi.open();
            log.info("Successfully connected to " + conPath);
        }
        catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        handle.close();
    }

    protected Query query(String request) {
        return handle.createQuery(request);
    }

    public void update(String request) throws IllegalArgumentException{

        jdbi.useHandle(handle -> {
           handle.createScript(request).execute();
        });
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void event(FinalizeTransactionEvent e) {

        int id = registerTransaction(new Offer[] {e.getTrade().getOffer(0), e.getTrade().getOffer(1)});
        e.forEach(p -> getFt().sendMessage(EnumI18n.FINALIZED.localize(id), p));
    }

    private int registerTransaction(Offer offers[]) {

        Handle transaction = handle.begin();
        transaction.createUpdate("INSERT INTO Trade VALUES(NULL, DATETIME('now'))").execute();

        for(int i = 0; i < 2; i++) {

            transaction.createUpdate("INSERT INTO Offer VALUES(NULL, :uuid, :items, :money)")
                    .bind(Columns.UUID, offers[i].getPlayer().getUniqueId())
                    .bind(Columns.ITEMSTACKS, YamlUtils.toString(offers[i].getItems()))
                    .bind(Columns.MONEY, offers[i].getMoney())
                    .execute();

            transaction.createUpdate("INSERT INTO Link VALUES((SELECT MAX(id) FROM Trade), (SELECT MAX(id) FROM Offer))")
                    .execute();
        }

        transaction.commit();
        return handle.createQuery("SELECT MAX(id) FROM Trade").mapTo(int.class).findOnly();
    }

    public OfflineTrade getTradeFromID(int id)
    {
        OfflineTrade ret = null;
        List<Map<String, Object>> list = handle.createQuery("SELECT date_recorded, uuid, items, money FROM Offer, Trade, Link" +
                " WHERE tradeID = :id AND tradeID = Trade.id AND offerID = Offer.id").bind("id", id).mapToMap().list();

        if(list.size() == 2) {

            ret = new OfflineTrade();
            OfflineOffer o = new OfflineOffer();
            ret.setDate((String)list.get(0).get(Columns.DATE_RECORDED));

            for(int i = 0; i < 2; i++) {

                Map<String, Object> map = list.get(i);
                o.setName(Bukkit.getOfflinePlayer(UUID.fromString((String)map.get(Columns.UUID))).getName());
                o.setItems(YamlUtils.toItems((String)map.get(Columns.ITEMSTACKS)));
                o.setMoney((Integer)map.get(Columns.MONEY));
                ret.setOffer(i, o);
            }

        }

        return ret;
    }

    public void setInsertStatement(String insert) {
        this.insertStatement = insert;
    }
}