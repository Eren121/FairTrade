package fr.rafoudiablol.ft.container;

import fr.rafoudiablol.ft.main.FairTrade;

public enum Locations {

    Empty(0),
    Owner(1),
    Remote(2),
    Confirm(3),
    OwnerConfirm(7),
    RemoteConfirm(8);

    // Aliases
    public static final Locations Left = Owner;
    public static final Locations Right = Remote;

    public final int id;

    Locations(int id) {
        this.id = id;
    }

    public static Locations valueOf(int id) {

        for(Locations loc : values()) {

            if(loc.id == id) {

                return loc;
            }
        }

        FairTrade.getFt().w("not found slot ID " + id);
        return null;
    }
}
