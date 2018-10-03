package fr.rafoudiablol.ft.spy;

import fr.rafoudiablol.ft.config.EnumI18n;
import fr.rafoudiablol.ft.events.FinalizeTransactionEvent;
import fr.rafoudiablol.ft.trade.Offer;
import fr.rafoudiablol.ft.trade.OfflineOffer;
import fr.rafoudiablol.ft.trade.OfflineTrade;
import fr.rafoudiablol.ft.trade.Trade;
import fr.rafoudiablol.ft.utils.YamlUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.UUID;
import java.util.logging.Logger;

import static fr.rafoudiablol.ft.main.FairTrade.getFt;

public class Database implements IDatabase, Listener
{
    private Connection connection;
    private Statement statement;
    private Logger log;
    private String insertStatement;

    public Database(Logger log)
    {
        this.log = log;
    }

    @Override
    public void connect(String path)
    {
        String conPath = "jdbc:sqlite:" + path;

        try {

            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(conPath);
            statement = connection.createStatement();

            log.info("Successfully connected to " + conPath);
        }
        catch(ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close()
    {
        try {

            if(statement != null) statement.close();
            if(connection != null) connection.close();
        }
        catch(SQLException e) {}
    }

    @Override
    public ResultSet query(String request)
    {
        ResultSet ret = null;

        try {
            ret = statement.executeQuery(request);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ret;
    }

    @Override
    public void update(String request) {

        try {
            statement.executeUpdate(request);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void event(FinalizeTransactionEvent e) {

        int id = registerTransaction(new Offer[] {e.getTrade().getOffer(0), e.getTrade().getOffer(1)});
        e.forEach(p -> getFt().sendMessage(EnumI18n.FINALIZED.localize(id), p));
    }

    private int registerTransaction(Offer offers[]) {

        int ret = -1;

        try {

            for(String prepared : insertStatement.split(";")) {

                for (int i = 0; i <= 1; ++i) {
                    prepared = prepared.replace(":name" + i, '\'' + offers[i].getPlayer().getName() + '\'');
                    prepared = prepared.replace(":uuid" + i, '\'' + offers[i].getPlayer().getUniqueId().toString() + '\'');
                    prepared = prepared.replace(":items" + i, '\'' + YamlUtils.toString(offers[i].getItems()) + '\'');
                    prepared = prepared.replace(":money" + i, String.valueOf(offers[i].getMoney()));
                }

                PreparedStatement st = connection.prepareStatement(prepared);
                st.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            ResultSet res = query("SELECT MAX(tradeID) FROM Trades");
            res.next();
            ret = res.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ret;
    }

    public OfflineTrade getTradeFromID(int id)
    {
        ResultSet res = query("SELECT tradeDate, playerUUID, offerItems, offerMoney FROM Trades, Offers " +
                "WHERE tradeID = " + id + " AND (Trades.offerID = Offers.offerID OR Trades.offerID + 1 = Offers.offerID)");

        if(res == null)
            return null;

        try {

            if(!res.next()) {
                return null;
            }

            OfflineTrade t = new OfflineTrade();
            OfflineOffer o = new OfflineOffer();
            t.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(res.getString(1)).toString());
            o.setName(Bukkit.getOfflinePlayer(UUID.fromString(res.getString(2))).getName());
            o.setItems(YamlUtils.toItems(res.getString(3)));
            o.setMoney(res.getDouble(4));
            t.setOffer(0, o);

            if(!res.next()) {
                return null;
            }

            o = new OfflineOffer();
            o.setName(Bukkit.getOfflinePlayer(UUID.fromString(res.getString(2))).getName());
            o.setItems(YamlUtils.toItems(res.getString(3)));
            o.setMoney(res.getDouble(4));
            t.setOffer(1, o);

            return t;

        } catch (SQLException | ParseException e) {

            e.printStackTrace();
            return null;
        }
    }

    public void setInsertStatement(String insert) {
        this.insertStatement = insert;
    }
}