package fr.rafoudiablol.ft.spy;

public class Query {

    public static final Query Requester = new Query("requester");
    public static final Query Accepter = new Query("accepter");
    public static final Query WhatRequestGive = new Query("what_requester_give");
    public static final Query WhatAccepterGive = new Query("what_accepter_give");
    public static final Query At = new Query("at");
    public static final Query InsertTransaction = new Query("INSERT INTO notary VALUES(NULL, ?, ?, ?, ?, DATETIME('now'));");


    public static final Query CreateTable = new Query(
            "create table if not exists notary(" +
            "  id INTEGER PRIMARY KEY," +
                    Requester + " VARCHAR(50)," +
                    Accepter + " VARCHAR(50)," +
                    WhatRequestGive + " TEXT," +
                    WhatAccepterGive + " TEXT," +
                    At + " DATETIME);");


    public final String query;

    @Override
    public String toString() { return query; }

    Query(String str)
    {
        query = str;
    }
}