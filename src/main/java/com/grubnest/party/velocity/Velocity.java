package com.grubnest.party.velocity;

import com.google.inject.Inject;
import com.grubnest.party.events.ServerHop;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;

import java.util.logging.Logger;

@Plugin(id = "party", name = "Party", version = "1.0.0", url = "https://github.com/Grubnest/GrubnestParties",
        description = "[DESCRIPTION]", authors = {"joannamusing"})
public class Velocity {
    private final ProxyServer proxyServer;
    private final Logger logger;
    @Inject
    public Velocity(ProxyServer proxyServer, Logger logger) {
        this.proxyServer = proxyServer;
        this.logger = logger;

        logger.info("[GrubnestParties]: Enabled Velocity.");
    }

    @Subscribe
    public void onInitialization(ProxyInitializeEvent event) {
        CommandManager commandManager = proxyServer.getCommandManager();
        logger.info("[GrubnestParties]: Registered commands.");

        //proxyServer.getEventManager().register(this, new ServerHop());
        logger.info("[GrubnestParties]: Registered events.");
    }
}