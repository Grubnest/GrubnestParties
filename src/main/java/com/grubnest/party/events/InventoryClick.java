package com.grubnest.party.events;

import com.grubnest.party.Main;
import com.grubnest.party.gui.PartyGUI;
import com.grubnest.party.other.Party;
import com.grubnest.party.other.PartyManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class InventoryClick implements Listener {
    private final Main plugin = Main.getInstance();

    PartyManager pm = plugin.getPartyManager();

    /*
    This class is for handling interactions in the Party GUI. They should be able to toggle party chat,
    kick or promote players (if leader). Interfacing for secondary "page" is planned for parties over 35 members.
     */
    @EventHandler
    public void onEvent(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player player) {
            Party party = pm.getParty(player);
            //This block will return the inventory the player is in, if it is one of the party GUIs.
            if (PartyGUI.getOpenInventories() != null) {
                if (PartyGUI.getOpenInventories().containsKey(player.getUniqueId())) {
                    PartyGUI partyGui = PartyGUI.getOpenInventories().get(player.getUniqueId());
                    for (Inventory i : partyGui.getInventories()) {
                        if (event.getInventory().equals(i)) {
                            event.setCancelled(true);
                        }
                        if (party != null) {
                            //player.sendMessage(String.valueOf(event.getSlot()));
                            int currentPage = partyGui.getCurrentPage();
                            int totalPages = partyGui.getInventories().size();
                            switch (event.getSlot()) {
                                case (45):
                                    if(currentPage > totalPages){
                                        currentPage -= 1;
                                        player.openInventory(partyGui.getInventories().get(currentPage));
                                    }else{
                                        player.sendMessage("No previous page.");
                                    }
                                    break;
                                case (48):
                                    party.togglePartyChat(player);
                                    break;
                                case (50):
                                    Bukkit.dispatchCommand(player, "party disband");
                                case (53):
                                    if(!(currentPage >= totalPages - 1)){
                                        currentPage += 1;
                                        player.openInventory(partyGui.getInventories().get(currentPage));
                                    }else{
                                        player.sendMessage("No next page.");
                                    }
                                    break;
                            }
                            if (event.getSlot() != 45 && event.getSlot() != 48 && event.getSlot() != 50 && event.getSlot() != 53) {
                                if (event.getCurrentItem() != null) {
                                    for (Player target : Bukkit.getOnlinePlayers()) {
                                        ItemStack itemStack = event.getCurrentItem();
                                        if (itemStack.getType().equals(Material.PLAYER_HEAD)) {
                                            SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
                                            assert skullMeta != null;
                                            if (skullMeta.hasOwner() && skullMeta.getOwningPlayer() != null) {
                                                Player t = skullMeta.getOwningPlayer().getPlayer();
                                                if (t != null) {
                                                    if (target.getName().equalsIgnoreCase(t.getName())) {
                                                        if (event.isShiftClick() && event.isRightClick()) {
                                                            Bukkit.dispatchCommand(player, "party kick " + target.getName());
                                                        } else if (event.isShiftClick() && event.isLeftClick()) {
                                                            Bukkit.dispatchCommand(player, "party promote " + target.getName());
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
