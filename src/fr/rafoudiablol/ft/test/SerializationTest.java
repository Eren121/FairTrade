package fr.rafoudiablol.ft.test;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.logging.Logger;

public class SerializationTest {

    public SerializationTest(Logger logger) {

        ItemStack itemstack = new ItemStack(Material.ACACIA_BOAT);
        Map<String, Object> map = itemstack.serialize();

        map.forEach((string, object) -> {
            logger.info(string + ":" + object);
        });
    }
}
