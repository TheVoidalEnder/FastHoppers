package com.example.fasterhoppers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Hopper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public final class FasterHoppers extends JavaPlugin implements CommandExecutor, Listener {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        this.getCommand("hopperadmin").setExecutor(this);
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can open the setup engine configuration.");
            return true;
        }
        
        // Check for the base GUI permission node
        if (!player.hasPermission("fasthopper.gui") && !player.hasPermission("fasthopper.admin")) {
            player.sendMessage(ChatColor.RED + "You do not have permission to view hopper configurations.");
            return true;
        }
        
        openAdminGUI(player);
        return true;
    }

    public void openAdminGUI(Player player) {
        Inventory gui = Bukkit.createInventory(null, 27, ChatColor.DARK_GRAY + "Global Hopper Settings");

        ItemStack backgroundPane = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta paneMeta = backgroundPane.getItemMeta();
        paneMeta.setDisplayName(" ");
        backgroundPane.setItemMeta(paneMeta);
        for (int i = 0; i < 27; i++) gui.setItem(i, backgroundPane);

        int amount = getConfig().getInt("transfer-amount", 1);
        int cooldown = getConfig().getInt("transfer-cooldown", 8);

        // Dynamic Lore changes based on admin status
        String clickAmountText = player.hasPermission("fasthopper.admin") ? "&aLeft-Click &7(+1) | &cRight-Click &7(-1)" : "&cViewing Only";
        String clickSpeedText = player.hasPermission("fasthopper.admin") ? "&aLeft-Click &7(Faster) | &cRight-Click &7(Slower)" : "&cViewing Only";

        gui.setItem(11, buildItem(Material.CHEST, "&b&lItem Transfer Amount", 
                "&7Current: &e" + amount + " items", "", clickAmountText));

        gui.setItem(15, buildItem(Material.CLOCK, "&e&lHopper Cooldown Speed", 
                "&7Current: &e" + cooldown + " ticks", "&8(Vanilla is 8. Lower = Faster)", "", clickSpeedText));

        player.openInventory(gui);
    }

    private ItemStack buildItem(Material material, String title, String... context) {
        ItemStack stack = new ItemStack(material);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', title));
        meta.setLore(Arrays.stream(context).map(line -> ChatColor.translateAlternateColorCodes('&', line)).toList());
        stack.setItemMeta(meta);
        return stack;
    }

    @EventHandler
    public void onGuiClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().contains("Global Hopper Settings")) return;
        event.setCancelled(true); // Always lock items in slots

        if (event.getCurrentItem() == null || !(event.getWhoClicked() instanceof Player player)) return;

        // Kick out players who don't have the admin node but try to click items
        if (!player.hasPermission("fasthopper.admin")) {
            player.sendMessage(ChatColor.RED + "You need fasthopper.admin to modify settings.");
            return;
        }

        int targetAmt = getConfig().getInt("transfer-amount", 1);
        int targetCd = getConfig().getInt("transfer-cooldown", 8);
        boolean processingDelta = false;

        if (event.getSlot() == 11) {
            if (event.getClick().isLeftClick() && targetAmt < 64) { targetAmt++; processingDelta = true; }
            else if (event.getClick().isRightClick() && targetAmt > 1) { targetAmt--; processingDelta = true; }
        } else if (event.getSlot() == 15) {
            if (event.getClick().isLeftClick() && targetCd > 1) { targetCd--; processingDelta = true; }
            else if (event.getClick().isRightClick() && targetCd < 200) { targetCd++; processingDelta = true; }
        }

        if (processingDelta) {
            getConfig().set("transfer-amount", targetAmt);
            getConfig().set("transfer-cooldown", targetCd);
            saveConfig();
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1f, 1f);
            openAdminGUI(player); 
        }
    }

    @EventHandler
    public void onGlobalHopperMove(InventoryMoveItemEvent event) {
        if (event.getSource().getType() != InventoryType.HOPPER) return;

        if (event.getSource().getHolder() instanceof Hopper targetedHopper) {
            targetedHopper.setTransferCooldown(getConfig().getInt("transfer-cooldown", 8));
        }

        int structuralCap = getConfig().getInt("transfer-amount", 1);
        ItemStack shiftingCargo = event.getItem();
        if (structuralCap > 1 && shiftingCargo.getAmount() == 1) {
            shiftingCargo.setAmount(Math.min(shiftingCargo.getMaxStackSize(), structuralCap));
        }
    }
}
