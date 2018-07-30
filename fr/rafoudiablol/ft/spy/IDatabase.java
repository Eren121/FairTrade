package fr.rafoudiablol.ft.spy;

import java.sql.ResultSet;

public interface IDatabase {

    void close();
    void connect(String path);
    ResultSet query(String request);
    void update(String request);
}
