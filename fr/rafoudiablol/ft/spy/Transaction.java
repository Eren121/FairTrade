package fr.rafoudiablol.ft.spy;

import fr.rafoudiablol.ft.utils.YamlUtils;
import org.bukkit.inventory.ItemStack;

public class Transaction
{
    public final String requesterName;
    public final String accepterName;
    public final ItemStack[] whatRequesterGives;
    public final ItemStack[] whatAccepterGives;
    public final String date;

    public Transaction(String requesterName, String accepterName, String whatRequesterGives, String whatAccepterGives, String date) {

        this.requesterName = requesterName;
        this.accepterName = accepterName;
        this.whatRequesterGives = YamlUtils.toItems(whatRequesterGives);
        this.whatAccepterGives = YamlUtils.toItems(whatAccepterGives);
        this.date = date;
    }

    public String getTitle() {

        return requesterName + "/" + accepterName;
    }
}
