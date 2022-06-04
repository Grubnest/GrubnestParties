package com.grubnest.party.util;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;

import org.bukkit.entity.Player;

public class messages {
    public static void createClickableCommand(Player player, String message, String command){
        //Creates a new text component that will translate color code hopefully of our message.
        TextComponent component = new TextComponent(TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', message)));
        component.setColor(ChatColor.GREEN);
        //Making our invite message hover on the screen.
        component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(message)));
        //Adding a click event to our amazing component. Command would be "party join" for our purposes.
        component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/" + command));
        player.spigot().sendMessage(component);
    }
}
