package fr.rafoudiablol.ft.spy;

import fr.rafoudiablol.ft.utils.YamlUtils;
import org.bukkit.inventory.ItemStack;

public class Transaction
{
    public String requesterName;
    public String accepterName;
    public ItemStack[] whatRequesterGives;
    public ItemStack[] whatAccepterGives;
    public String date;

    public Transaction() {
    }

    public String getTitle() {

        return requesterName + "/" + accepterName;
    }
}
