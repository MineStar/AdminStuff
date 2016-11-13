/*
 * Copyright (C) 2011 MineStar.de 
 * 
 * This file is part of 'AdminStuff'.
 * 
 * 'AdminStuff' is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3 of the License.
 * 
 * 'AdminStuff' is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with 'AdminStuff'.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * AUTHOR: GeMoschen
 * 
 */

package de.minestar.AdminStuff.commands;

import java.util.HashSet;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.minestar.AdminStuff.Core;
import de.minestar.AdminStuff.data.ItemType;
import de.minestar.minestarlibrary.commands.AbstractCommand;
import de.minestar.minestarlibrary.utils.ChatUtils;
import de.minestar.minestarlibrary.utils.ConsoleUtils;
import de.minestar.minestarlibrary.utils.PlayerUtils;

public class cmdStack extends AbstractCommand {

    public cmdStack(String syntax, String arguments, String node) {
        super(Core.NAME, syntax, arguments, node);
    }

    @Override
    /**
     * Representing the command <br>
     * /stack <br>
     * Stack your inventory
     * 
     * @param player
     *            Called the command
     * @param split
     */
    public void execute(String[] args, Player player) {
        if (args.length == 0)
            stack(player, player);
        else
            stack(args[0], player);
    }

    @Override
    public void execute(String[] args, ConsoleCommandSender console) {
        if (args.length == 0)
            ConsoleUtils.printError(pluginName, "You don't even have an inventory, so how can you stack it?");
        else
            stack(args[0], console);
    }

    private void stack(String targetName, CommandSender sender) {
        Player target = PlayerUtils.getOnlinePlayer(targetName);
        if (target == null)
            ChatUtils.writeError(sender, pluginName, "Spieler '" + targetName + "' wurde nicht gefunden!");
        else if (target.isDead() || !target.isOnline())
            ChatUtils.writeError(sender, pluginName, "Spieler '" + target.getName() + "' ist nicht online!");
        else
            stack(target, sender);

    }

    @SuppressWarnings("deprecation")
    private void stack(Player target, CommandSender sender) {
        ItemStack[] items = target.getInventory().getContents();
        int len = items.length;

        int affected = 0;

        HashSet<Integer> ignoreMaxStacks = new HashSet<Integer>();
        // signs
        ignoreMaxStacks.add(Material.SIGN.getId());
        ignoreMaxStacks.add(Material.WALL_SIGN.getId());
        ignoreMaxStacks.add(Material.SIGN_POST.getId());
        // doors
        ignoreMaxStacks.add(Material.WOOD_DOOR.getId());
        ignoreMaxStacks.add(Material.IRON_DOOR.getId());
        // common
        ignoreMaxStacks.add(Material.BOAT.getId());
        ignoreMaxStacks.add(Material.EGG.getId());
        ignoreMaxStacks.add(Material.CAKE.getId());
        ignoreMaxStacks.add(Material.CAKE_BLOCK.getId());
        ignoreMaxStacks.add(Material.SKULL_ITEM.getId());
        ignoreMaxStacks.add(Material.SKULL.getId());
        ignoreMaxStacks.add(Material.BUCKET.getId());
        ignoreMaxStacks.add(Material.BED.getId());
        ignoreMaxStacks.add(Material.SHEARS.getId());
        // records
        ignoreMaxStacks.add(Material.GOLD_RECORD.getId());
        ignoreMaxStacks.add(Material.GREEN_RECORD.getId());
        ignoreMaxStacks.add(Material.RECORD_3.getId());
        ignoreMaxStacks.add(Material.RECORD_4.getId());
        ignoreMaxStacks.add(Material.RECORD_5.getId());
        ignoreMaxStacks.add(Material.RECORD_6.getId());
        ignoreMaxStacks.add(Material.RECORD_7.getId());
        ignoreMaxStacks.add(Material.RECORD_8.getId());
        ignoreMaxStacks.add(Material.RECORD_9.getId());
        ignoreMaxStacks.add(Material.RECORD_10.getId());
        ignoreMaxStacks.add(Material.RECORD_11.getId());
        ignoreMaxStacks.add(Material.RECORD_12.getId());
        // minecarts
        ignoreMaxStacks.add(Material.MINECART.getId());
        ignoreMaxStacks.add(Material.STORAGE_MINECART.getId());
        ignoreMaxStacks.add(Material.POWERED_MINECART.getId());
        ignoreMaxStacks.add(Material.EXPLOSIVE_MINECART.getId());
        ignoreMaxStacks.add(Material.HOPPER_MINECART.getId());
        // plates, leggings, helmets, boots
        ignoreMaxStacks.add(Material.IRON_BOOTS.getId());
        ignoreMaxStacks.add(Material.IRON_LEGGINGS.getId());
        ignoreMaxStacks.add(Material.IRON_CHESTPLATE.getId());
        ignoreMaxStacks.add(Material.IRON_HELMET.getId());
        ignoreMaxStacks.add(Material.GOLD_BOOTS.getId());
        ignoreMaxStacks.add(Material.GOLD_LEGGINGS.getId());
        ignoreMaxStacks.add(Material.GOLD_CHESTPLATE.getId());
        ignoreMaxStacks.add(Material.GOLD_HELMET.getId());
        ignoreMaxStacks.add(Material.DIAMOND_BOOTS.getId());
        ignoreMaxStacks.add(Material.DIAMOND_LEGGINGS.getId());
        ignoreMaxStacks.add(Material.DIAMOND_CHESTPLATE.getId());
        ignoreMaxStacks.add(Material.DIAMOND_HELMET.getId());
        ignoreMaxStacks.add(Material.LEATHER_BOOTS.getId());
        ignoreMaxStacks.add(Material.LEATHER_LEGGINGS.getId());
        ignoreMaxStacks.add(Material.LEATHER_CHESTPLATE.getId());
        ignoreMaxStacks.add(Material.LEATHER_HELMET.getId());
        // horse
        ignoreMaxStacks.add(Material.SADDLE.getId());
        ignoreMaxStacks.add(Material.IRON_BARDING.getId());
        ignoreMaxStacks.add(Material.GOLD_BARDING.getId());
        ignoreMaxStacks.add(Material.DIAMOND_BARDING.getId());
        // weapons
        ignoreMaxStacks.add(Material.BOW.getId());
        // sword
        ignoreMaxStacks.add(Material.DIAMOND_SWORD.getId());
        ignoreMaxStacks.add(Material.GOLD_SWORD.getId());
        ignoreMaxStacks.add(Material.IRON_SWORD.getId());
        ignoreMaxStacks.add(Material.WOOD_SWORD.getId());
        // axe
        ignoreMaxStacks.add(Material.DIAMOND_AXE.getId());
        ignoreMaxStacks.add(Material.GOLD_AXE.getId());
        ignoreMaxStacks.add(Material.IRON_AXE.getId());
        ignoreMaxStacks.add(Material.WOOD_AXE.getId());
        // spade
        ignoreMaxStacks.add(Material.DIAMOND_SPADE.getId());
        ignoreMaxStacks.add(Material.GOLD_SPADE.getId());
        ignoreMaxStacks.add(Material.IRON_SPADE.getId());
        ignoreMaxStacks.add(Material.WOOD_SPADE.getId());
        // pickaxe
        ignoreMaxStacks.add(Material.DIAMOND_PICKAXE.getId());
        ignoreMaxStacks.add(Material.GOLD_PICKAXE.getId());
        ignoreMaxStacks.add(Material.IRON_PICKAXE.getId());
        ignoreMaxStacks.add(Material.WOOD_PICKAXE.getId());
        // hoe
        ignoreMaxStacks.add(Material.DIAMOND_HOE.getId());
        ignoreMaxStacks.add(Material.GOLD_HOE.getId());
        ignoreMaxStacks.add(Material.IRON_HOE.getId());
        ignoreMaxStacks.add(Material.WOOD_HOE.getId());

        boolean ignoreMax = false, ignoreDamaged = false;
        for (int i = 0; i < len; i++) {
            ItemStack item = items[i];

            if (item == null) {
                continue;
            }

            ignoreMax = ignoreMaxStacks.contains(item.getTypeId());

            // Avoid infinite stacks and stacks with durability
            if (item.getAmount() <= 0 || (!ignoreMax && item.getMaxStackSize() == 1)) {
                continue;
            }

            int max = ignoreMax ? 64 : item.getMaxStackSize();

            if (item.getAmount() < max) {
                int needed = max - item.getAmount(); // Number of needed items
                                                     // until max

                // Find another stack of the same type
                for (int j = i + 1; j < len; j++) {
                    ItemStack item2 = items[j];

                    // Avoid infinite stacks and stacks with durability
                    if (item2 == null || item2.getAmount() <= 0 || (!ignoreMax && item.getMaxStackSize() == 1)) {
                        continue;
                    }

                    // Same type?
                    // Blocks store their color in the damage value
                    if (item2.getTypeId() == item.getTypeId() && ((!ItemType.usesDamageValue(item.getTypeId()) && ignoreDamaged) || item.getDurability() == item2.getDurability()) && ((item.getItemMeta() == null && item2.getItemMeta() == null) || (item.getItemMeta() != null && item.getItemMeta().equals(item2.getItemMeta())))) {
                        // This stack won't fit in the parent stack
                        if (item2.getAmount() > needed) {
                            item.setAmount(max);
                            item2.setAmount(item2.getAmount() - needed);
                            break;
                        } else {
                            items[j] = null;
                            item.setAmount(item.getAmount() + item2.getAmount());
                            needed = max - item.getAmount();
                        }

                        affected++;
                    }
                }
            }
        }

        if (affected > 0) {
            target.getInventory().setContents(items);
        }

        target.updateInventory();

        PlayerUtils.sendSuccess(target, pluginName, "Items gestackt!");
        if (!sender.getName().equalsIgnoreCase(target.getName())) {
            ChatUtils.writeSuccess(sender, pluginName, "Items des Spielers '" + target.getName() + "' wurden gestackt!");
        }
    }
}
