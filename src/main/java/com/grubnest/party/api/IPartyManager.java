package com.grubnest.party.api;


import com.grubnest.party.other.Party;
import org.bukkit.entity.Player;

public interface IPartyManager {
    /*
    This interface is designed to manage the Party objects, and handle if a player invites to a party.
    06/04/2022 - joannamusing
     */

    /*
    This method will add a party to our list of parties, often done on the creation of a new party.
     */
    void addParty(Party party);
    /*
    This method will remove a party from the list, often done upon disbanding.
     */
    void removeParty(Party party);
    /*
    A super important method, we always need to check if the player is in a party, and return it if so.
     */
    Party getParty(Player player);
    /*
    When a player is invited to a party, we put them in a hashMap, so they can't be spammed, hopefully.
     */
    void addInvite(Player sender, Player receiver);
    /*
    When a player accepts the invite or the invite times out, we remove them from the hashMap.
     */
    void removeInvite(Player player);
    /*
    We check before they join a party to see if they were indeed invited to that party.
     */
    boolean getInvite(Player sender, Player receiver);
}
