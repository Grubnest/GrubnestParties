package com.grubnest.party.events;

import com.grubnest.party.Main;
import com.grubnest.party.data.Exchange;
import com.grubnest.party.other.Party;
import com.grubnest.party.other.PartyManager;
import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import com.velocitypowered.api.proxy.Player;
import org.bukkit.Bukkit;
import org.bukkit.Server;

import java.util.UUID;

public class ServerHop {
    private final Main plugin = Main.getInstance();
    private final PartyManager pm = plugin.getPartyManager();

    @Subscribe(order = PostOrder.NORMAL)
    public void onEvent(ServerConnectedEvent event){
        Player player = event.getPlayer();
        Party p = pm.getParty((org.bukkit.entity.Player) player);
        if(p != null){
            Server server = (Server) event.getServer();
            int maximumPlayers = server.getMaxPlayers();
            int currentPlayers = server.getOnlinePlayers().size();
            int partySize = p.getSize();
            if(currentPlayers <= maximumPlayers - partySize){
                //Then we have enough space for the party members.
                for(UUID uuid : p.getMembers()){
                    Player target = (Player) Bukkit.getPlayer(uuid);
                    try{
                        assert target != null;
                        Exchange.connectPlayer((org.bukkit.entity.Player) target, server.getName());
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
