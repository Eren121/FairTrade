package fr.rafoudiablol.ft.db;

public enum Queries {

    Requester("requester"),
    Accepter("accepter"),
    WhatRequestGive("what_requester_give"),
    WhatAccepterGive("what_accepter_give"),
    At("at"),
    InsertTransaction("INSERT INTO notary VALUES(NULL, ?, ?, ?, ?, DATETIME('now'));"),
    CreateTable(
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

    Queries(String str)
    {
        query = str;
    }
}