package com.grubnest.party.other;

import org.bukkit.entity.Player;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.UUID;

public interface IParty {
    /*
    This interface is designed for the party system, and holds the methods that we use to interact with our Party objects.
    06/04/2022 - joannamusing
     */

    /*
    This method is to get the party UUID.
     */
    UUID getUUID();
    /*
    This method is to set the party UUID.
     */
    void setUUID(UUID uuid);
    /*
    Returns the current name of the party.
     */
    String getName();
    /*
    This method will let you change the current party name, using the player to find the current party.
     */
    void setName(String string);
    /*
    This will return how many players are in the party.
     */
    int getSize();
    /*
    Method to add a player to the party.
     */
    void addMember(Player player);
    /*
    Method to remove a player from the party.
     */
    void removeMember(Player player);
    /*
    Returns a list of all the party members.
     */
    ArrayList<UUID> getMembers();
    /*
    Removes all the party members from the party; used on disbanding.
     */
    void clearMembers();
    /*
    This will check if the player is a member.
     */
    boolean isMember(Player player);
    /*
    This will check if the player is a leader.
     */
    boolean isLeader(Player player);
    /*
    This method will let you promote a member to leader, removing the old one.
     */
    void promote(Player sender, Player receiver);
    /*
    Returns first and second color for our party channel stuff.
     */
    ChatColor getColor1();
    ChatColor getColor2();
    /*
    This method will let you toggle the visibility of party chat for a player.
     */
    void togglePartyChat(Player player);
    /*
    This method is to check if a player has party chat toggled on or off.
     */
    boolean getChatVisibility(Player player);
    /*
    This message will send a "party" channel message to the player; used in conjunction with the AsyncChatEvent.
     */
    void sendMessage(Player player, String string);
}
