package com.grubnest.party.commands;

import com.grubnest.party.Main;
import com.grubnest.party.gui.PartyGUI;
import com.grubnest.party.other.Party;
import com.grubnest.party.other.PartyManager;
import com.grubnest.party.util.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CParty implements CommandExecutor {
    private final Main plugin = Main.getInstance();

    PartyManager pm = plugin.getPartyManager();

    //TODO: Add in the "/party set" branch to allow for leader and name to be changed.
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (player.hasPermission("kazanjima.commands.party")) {
                Party p = pm.getParty(player);
                if (args.length > 0) {
                    for (Player target : Bukkit.getOnlinePlayers()) {
                        if (args[0].equalsIgnoreCase(target.getName())) {
                            /*
                            Creates a party if they are not in one, then invites the target player.
                            /party <Player>
                            */
                            if (p != null) {
                                Bukkit.dispatchCommand(player, "party invite " + target.getName());
                            } else {
                                Bukkit.dispatchCommand(player, "party create " + player.getName());
                                Bukkit.dispatchCommand(player, "party invite " + target.getName());
                            }
                        }
                    }
                    //If there is some kind of argument after party, we go to the switch. "/party <argument>"
                    switch (args[0]) {
                        /*
                        Toggles the party chat on or off.
                        /party chat
                        */
                        case ("chat"):
                            if (p != null) {
                                p.togglePartyChat(player);
                            }
                            break;
                        /*
                        Creates a new party with the specified name.
                        /party create <String>
                        */
                        case ("create"):
                            if (p == null) {
                                if (args.length == 2) {
                                    p = new Party(player, args[1]);
                                    pm.addParty(p);
                                    player.sendMessage("You have created a party!");
                                } else {
                                    player.sendMessage("/party create <String>");
                                }
                            } else {
                                player.sendMessage("You are already in a party.");
                            }
                            break;
                        /*
                        This will let the party leader disband the party.
                        /party disband
                        */
                        case ("disband"):
                            if (p != null) {
                                if (p.isLeader(player)) {
                                    pm.removeParty(p);
                                    player.sendMessage("Party disbanded.");
                                }
                            }
                            break;
                        /*
                        Gives the user a list of party commands.
                        /party help
                        */
                        case ("help"):
                            ArrayList<String> strings = new ArrayList<>();
                            strings.add("/party - Check your party status.");
                            strings.add("/party <Player> - Invites another player to join your party.");
                            strings.add("/party chat - Toggle party chat on/off.");
                            strings.add("/party create <String> - Creates a party with the name of the string.");
                            strings.add("/party disband - Disbands your party.");
                            strings.add("/party help - I wonder what this does.");
                            strings.add("/party kick <Player> - Kicks the selected member if you are the leader.");
                            strings.add("/party invite <Player> - Invites a player to join your party.");
                            strings.add("/party join <Player>- Accepts an invite from the player.");
                            strings.add("/party leave - Leaves your current party.");
                            strings.add("/party promote <Player> - Promote a member to party leader.");
                            for (String s : strings) {
                                player.sendMessage(s);
                            }
                            break;
                        case ("invite"):
                            if (args.length != 2) {
                                player.sendMessage("/party invite <Player>");
                            }
                            if (p != null) {
                                for (Player target : Bukkit.getOnlinePlayers()) {
                                    if (args[1].equalsIgnoreCase(target.getName())) {
                                        Party t = pm.getParty(target);
                                        if(t == null){
                                            pm.addInvite(player, target);
                                            String message = "You have been invited to a party by " + player.getName() + "!";
                                            String cmd = "party join " + player.getName();
                                            Messages.createClickableCommand(target, message, cmd);
                                        }else{
                                            player.sendMessage("That player is already in a party.");
                                        }
                                    }
                                }
                            }
                            break;
                        case ("join"):
                            if (args.length != 2) {
                                player.sendMessage("/party join <Player>");
                            }
                            for (Player target : Bukkit.getOnlinePlayers()) {
                                if (args[1].equalsIgnoreCase(target.getName())) {
                                    p = pm.getParty(target);
                                    if (pm.getInvite(target, player)) {
                                        p.addMember(player);
                                        pm.removeInvite(target);
                                        player.sendMessage("You have joined the party!");
                                    }
                                }
                            }
                            break;
                        case ("kick"):
                            if (args.length != 2) {
                                player.sendMessage("/party kick <Player>");
                            }
                            if (p != null) {
                                for (Player target : Bukkit.getOnlinePlayers()) {
                                    if (args[1].equalsIgnoreCase(target.getName())) {
                                        if (p.isLeader(player)) {
                                            if (p.getMembers().contains(target.getUniqueId())) {
                                                if(p.isLeader(target)){
                                                    player.sendMessage("You are the leader, please promote someone first.");
                                                    break;
                                                }
                                                p.removeMember(target);
                                                player.sendMessage(target.getName() + " has been kicked from the party.");
                                            }
                                        }
                                    }
                                }
                            }
                            break;
                        case ("leave"):
                            if (p != null) {
                                p.removeMember(player);
                            }
                            break;
                        case ("promote"):
                            if(args.length != 2){
                                player.sendMessage("/party promote <Player>");
                            }
                            if(p != null){
                                if(p.isLeader(player)){
                                    for(Player target : Bukkit.getOnlinePlayers()){
                                        if(target.getName().equalsIgnoreCase(args[1])){
                                            if(p.isLeader(target)){
                                                player.sendMessage("You are already the leader.");
                                                break;
                                            }
                                            p.promote(player, target);
                                            player.sendMessage(target.getName() + " is now the leader.");
                                        }
                                    }
                                }
                            }
                            break;
                    }
                /*
                This is the general party command. It will open the party GUI if they are in a party, otherwise return a message.
                /party
                */
                } else {
                    if (p != null) {
                        PartyGUI pgui = new PartyGUI(player, p);
                        pgui.openPartyGUI(player, p.getName(), pgui.generateSkulls(player, p.getMembers()));
                    } else {
                        player.sendMessage("You are not in a party. Try \"/party help\".");
                    }
                }
            }
        }
        return true;
    }
}
