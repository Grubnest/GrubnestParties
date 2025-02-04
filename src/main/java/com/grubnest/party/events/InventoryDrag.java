package com.grubnest.party.events;

import com.grubnest.party.gui.PartyGUI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;

public class InventoryDrag implements Listener {
    @EventHandler
    public void onEvent(InventoryDragEvent event){
        if(event.getWhoClicked() instanceof Player player) {
            if (PartyGUI.getOpenInventories() != null) {
                if (PartyGUI.getOpenInventories().containsKey(player.getUniqueId())) {
                    PartyGUI partyGui = PartyGUI.getOpenInventories().get(player.getUniqueId());
                    for (Inventory i : partyGui.getInventories()) {
                        if (event.getInventory().equals(i)) {
                            event.setCancelled(true);
                        }
                    }
                }
            }
        }
    }
}
