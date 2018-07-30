package fr.rafoudiablol.ft.spy;

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
                    Requester.query + " varchar(50)," +
                    Accepter.query + " VARCHAR(50)," +
                    WhatRequestGive.query + " text," +
                    WhatAccepterGive.query + " text," +
                    At + " datetime" +
            ");");


    public final String query;
    Queries(String str)
    {
        query = str;
    }
}
