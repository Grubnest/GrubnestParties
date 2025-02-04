package com.grubnest.party.gui;

import com.grubnest.party.other.Party;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.*;

public class PartyGUI {
    private final Player player;
    private final Party p;
    private final List<Inventory> inventories;
    private static HashMap<UUID, PartyGUI> openInventories;
    private final int currentPage = 0;
    private final ChatColor color1;
    private final ChatColor color2;
    public final String nextPage;
    public final String previousPage;

    public PartyGUI(Player player, Party p){
        this.player = player;
        this.p = p;
        this.color1 = p.getColor1();
        this.color2 = p.getColor2();
        this.nextPage = color2 + "Next Page";
        this.previousPage = color2 + "Previous Page";
        this.inventories = new ArrayList<>();
        openInventories = new HashMap<>();
    }
    public void openPartyGUI(Player player, String string, ArrayList<ItemStack> skulls){
        Inventory newPage = getBlankPage(string);
        for (ItemStack skull : skulls) {
            //If the first slot that is empty is our slot after Previous Page button.
            if (newPage.firstEmpty() == 46) {
                inventories.add(newPage);
                newPage = getBlankPage(string);
                newPage.addItem(skull);
            } else {

                newPage.addItem(skull);
            }
        }
        inventories.add(newPage);
        openInventories.put(player.getUniqueId(), this);
        player.openInventory(inventories.get(currentPage));
    }
    public Inventory getBlankPage(String string){
        Inventory blankPage = Bukkit.createInventory(null, (9 * 6), string);

        ItemStack nextPageIcon = new ItemStack(Material.PAPER, 1);
        ItemMeta nextPageMeta = nextPageIcon.getItemMeta();
        if(nextPageMeta != null) {
            nextPageMeta.setDisplayName(nextPage);
            nextPageIcon.setItemMeta(nextPageMeta);
        }
        ItemStack previousPageIcon = new ItemStack(Material.PAPER, 1);
        ItemMeta previousPageMeta = nextPageIcon.getItemMeta();
        if(previousPageMeta != null) {
            previousPageMeta.setDisplayName(previousPage);
            previousPageIcon.setItemMeta(previousPageMeta);
        }
        blankPage.setItem(48, createItem(Material.OAK_SIGN, "Toggle Party Chat", "Click this to toggle party chat."));
        blankPage.setItem(50, createItem(Material.OAK_SIGN, "Disband Party", "Click this to disband the party."));
        blankPage.setItem(45, previousPageIcon);
        blankPage.setItem(53, nextPageIcon);
        return blankPage;
    }
    /*
    This will create all the player heads for the PartyGUI.
    If the user is the leader of the party, they will also show the shift-click options.
     */
    public ArrayList<ItemStack> generateSkulls(Player user, ArrayList<UUID> players){
        ArrayList<ItemStack> skulls = new ArrayList<>();
        ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();
        if(skullMeta != null) {
            for (UUID uuid : players) {
                Player player = Bukkit.getPlayer(uuid);
                if (player == null) {
                    break;
                }
                if (p.getUUID().equals(uuid)) {
                    skullMeta.setDisplayName(color1 + "" + ChatColor.ITALIC + player.getName());
                } else {
                    skullMeta.setDisplayName(color2 + "" + player.getName());
                }
                if(user.getUniqueId().equals(p.getUUID())) {
                    lore.add(color1 + "Shift + Left Click to promote.");
                    lore.add(color1 + "Shift + Right Click to kick.");
                }
                skullMeta.setOwnerProfile(Objects.requireNonNull(Bukkit.getPlayer(uuid)).getPlayerProfile());
                skullMeta.setLore(lore);
                itemStack.setItemMeta(skullMeta);
                skulls.add(itemStack);
            }
        }
        return skulls;
    }
    public ItemStack createItem(Material material, String name, String lore){
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        if(itemMeta != null) {
            itemMeta.setDisplayName(color1 + name);

            ArrayList<String> l = new ArrayList<>();
            l.add(color2 + lore);
            itemMeta.setLore(l);

            itemStack.setItemMeta(itemMeta);
        }
        return itemStack;
    }
    public List<Inventory> getInventories(){
        return inventories;
    }
    public static HashMap<UUID, PartyGUI> getOpenInventories(){
        return openInventories;
    }
    public int getCurrentPage(){
        return currentPage;
    }
}
