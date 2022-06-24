package com.grubnest.party.other;

import com.grubnest.party.api.IParty;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.UUID;

public class Party implements IParty {
    UUID uuid;
    String name;
    ArrayList<UUID> members = new ArrayList<>();
    ArrayList<UUID> inChat = new ArrayList<>();
    ChatColor color1 = ChatColor.GREEN;
    ChatColor color2 = ChatColor.WHITE;
    public final String TAG = color2 + "[" + color1 + "Party" + color2 + "]";

    public Party(Player player, String string){
        uuid = player.getUniqueId();
        name = string;
        members.add(player.getUniqueId());
        inChat.add(player.getUniqueId());
    }
    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String string) {
        name = string;
    }

    @Override
    public int getSize() {
        return members.size();
    }

    @Override
    public void addMember(Player player) {
        UUID uuid = player.getUniqueId();
        if(!members.contains(uuid)){
            members.add(uuid);
        }
    }

    @Override
    public void removeMember(Player player) {
        members.remove(player.getUniqueId());
    }

    @Override
    public ArrayList<UUID> getMembers() {
        return members;
    }

    @Override
    public void clearMembers() {
        members.clear();
    }

    @Override
    public boolean isMember(Player player) {
        return members.contains(player.getUniqueId());
    }

    @Override
    public boolean isLeader(Player player) {
        return uuid.equals(player.getUniqueId());
    }

    @Override
    public void promote(Player sender, Player receiver) {
        if(!isLeader(sender)){
            if(!uuid.equals(receiver.getUniqueId())){
                setUUID(receiver.getUniqueId());
            }
        }
    }

    @Override
    public ChatColor getColor1() {
        return color1;
    }

    @Override
    public ChatColor getColor2() {
        return color2;
    }

    @Override
    public void togglePartyChat(Player player) {
        UUID uuid = player.getUniqueId();
        if(inChat.contains(uuid)){
            inChat.remove(uuid);
            player.sendMessage("Party chat toggled off.");
        }else{
            inChat.add(uuid);
            player.sendMessage("Party chat toggled on.");
        }
    }

    @Override
    public boolean getChatVisibility(Player player) {
        return inChat.contains(player.getUniqueId());
    }

    @Override
    public void sendMessage(Player player, String string) {
        string = string.replace('@', ' ');
        string = color1 + " " + player.getName() + color2 + ":" + string;
        for(UUID uuid : getMembers()) {
            Player receiver = Bukkit.getServer().getPlayer(uuid);
            if(receiver != null){
                receiver.sendMessage(TAG + string);
            }
        }
    }
}
