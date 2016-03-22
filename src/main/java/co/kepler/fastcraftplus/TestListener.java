package co.kepler.fastcraftplus;

import co.kepler.fastcraftplus.craftgui.GUIFastCraft;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Just a class for all things testing related.
 */
public class TestListener implements Listener {

    public TestListener() {

    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        e.getPlayer().getInventory().setItem(0, new ItemStack(Material.GOLD_INGOT, 64));

        GUIFastCraft fcGUI = new GUIFastCraft(e.getPlayer(), null);
        fcGUI.show();
    }
}
