package com.grubnest.party.events;

import com.grubnest.party.Main;
import com.grubnest.party.other.Party;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncChat implements Listener {
    private final Main plugin = Main.getInstance();

    @EventHandler
    public void onEvent(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();
        Party p = plugin.getPartyManager().getParty(player);
        if (message.startsWith("@")) {
            if (p != null) {
                if (p.getChatVisibility(player)) {
                    p.sendMessage(player, message);
                    event.setCancelled(true);
                }
            } else {
                player.sendMessage("You are not in a party!");
            }
        }
    }
}
