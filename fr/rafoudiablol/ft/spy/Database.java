package fr.rafoudiablol.ft.spy;

import fr.rafoudiablol.ft.config.EnumI18n;
import fr.rafoudiablol.ft.events.FinalizeTransactionEvent;
import fr.rafoudiablol.ft.utils.YamlBuilder;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;

import static fr.rafoudiablol.ft.main.FairTrade.getFt;
import static fr.rafoudiablol.ft.spy.Queries.*;

public class Database implements IDatabase, Listener
{
    private Connection connection;
    private Statement statement;
    private Logger log;

    public Database(Logger log)
    {
        this.log = log;
    }

    @Override
    public void connect(String path)
    {
        String conPath = "jdbc:sqlite:" + path;

        log.info("Connect to database '" + conPath + "'");

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

        int id = registerTransaction(e.getPlayer(), e.getOther(), YamlBuilder.toString(e.getPlayerGift()), YamlBuilder.toString(e.getOtherGift()));
        e.forEach(p -> getFt().sendMessage(EnumI18n.FINALIZED.localize(id), p));
    }

    /**
     *
     * @param requester the player who /request
     * @param accepter the player who /accept
     * @param requesterGive what requester gives to accepter
     * @param accepterGive what accepter gives to requester
     * @return ID of the transaction
     */
    private int registerTransaction(HumanEntity requester, HumanEntity accepter, String requesterGive, String accepterGive)
    {
        int ret = -1;

        try {

            PreparedStatement st = connection.prepareStatement(Queries.InsertTransaction.query);
            st.setString(1, requester.getName());
            st.setString(2, accepter.getName());
            st.setString(3, requesterGive);
            st.setString(4, accepterGive);
            st.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            ResultSet res = query("SELECT MAX(id) FROM notary");
            res.next();
            ret = res.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ret;
    }

    public Transaction getTransactionFromID(int id)
    {
        ResultSet res = query("SELECT * FROM notary WHERE id = " + id);
        Transaction ret = null;

        if(res != null)
        {
            try {

                //Check ID was found
                if(res.next()) {
                    ret = new Transaction(
                            res.getString(Requester.query),
                            res.getString(Accepter.query),
                            res.getString(WhatRequestGive.query),
                            res.getString(WhatAccepterGive.query),
                            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(res.getString(At.query)).toString());
                }

            } catch (SQLException | ParseException e) {
                e.printStackTrace();
            }
        }

        return ret;
    }
}