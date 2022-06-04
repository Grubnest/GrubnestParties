package com.grubnest.party.other;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class PartyManager implements IPartyManager{
    ArrayList<Party> allParties;
    HashMap<UUID, UUID> invites;

    public PartyManager(){
        allParties = new ArrayList<>();
        invites = new HashMap<>();
    }

    @Override
    public void addParty(Party party) {
        if(!allParties.contains(party)){
            allParties.add(party);
        }
    }

    @Override
    public void removeParty(Party party) {
        party.clearMembers();
        allParties.remove(party);
    }

    @Override
    public Party getParty(Player player) {
        for(Party party : allParties){
            if(party.isMember(player)){
                return party;
            }
        }
        return null;
    }

    @Override
    public void addInvite(Player sender, Player receiver) {
        UUID senderUUID = sender.getUniqueId();
        UUID receiverUUID = receiver.getUniqueId();
        if(invites.containsKey(senderUUID)){
            sender.sendMessage("You have already invited someone recently.");
            return;
        }
        invites.put(senderUUID, receiverUUID);
    }

    @Override
    public void removeInvite(Player player) {
        UUID uuid = player.getUniqueId();
        if(invites.containsKey(uuid)){
            invites.remove(player.getUniqueId());
        }
    }

    @Override
    public boolean getInvite(Player sender, Player receiver) {
        UUID senderUUID = sender.getUniqueId();
        UUID receiverUUID = receiver.getUniqueId();
        if(invites.containsKey(senderUUID)){
            return invites.get(senderUUID).equals(receiverUUID);
        }
        return false;
    }
}
