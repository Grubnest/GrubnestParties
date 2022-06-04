package com.grubnest.party;

import com.grubnest.party.commands.CParty;
import com.grubnest.party.events.AsyncChat;
import com.grubnest.party.events.InventoryClick;
import com.grubnest.party.events.InventoryDrag;
import com.grubnest.party.other.PartyManager;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class Main extends JavaPlugin {
    public static Main instance;
    public static Logger logger;
    public PartyManager partyManager;
    @Override
    public void onEnable() {
        instance = this;
        logger = getLogger();
        partyManager = new PartyManager();
    }

    @Override
    public void onDisable() {

    }
    public void registerCommands(){
        this.getCommand("party").setExecutor(new CParty());
    }
    public void registerEvents(){
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new AsyncChat(), this);
        pm.registerEvents(new InventoryClick(), this);
        pm.registerEvents(new InventoryDrag(), this);
    }
    public static Main getInstance(){
        return instance;
    }
    public PartyManager getPartyManager(){
        return partyManager;
    }
}
