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

    private void stack(Player target, CommandSender sender) {
        ItemStack[] items = target.getInventory().getContents();
        int len = items.length;

        int affected = 0;

        HashSet<Material> ignoreMaxStacks = new HashSet<Material>();
        // signs
        ignoreMaxStacks.add(Material.SIGN);
        ignoreMaxStacks.add(Material.WALL_SIGN);
        ignoreMaxStacks.add(Material.SIGN_POST);
        // doors
        ignoreMaxStacks.add(Material.WOOD_DOOR);
        ignoreMaxStacks.add(Material.ACACIA_DOOR_ITEM);
        ignoreMaxStacks.add(Material.BIRCH_DOOR_ITEM);
        ignoreMaxStacks.add(Material.DARK_OAK_DOOR_ITEM);
        ignoreMaxStacks.add(Material.JUNGLE_DOOR_ITEM);
        ignoreMaxStacks.add(Material.SPRUCE_DOOR_ITEM);
        ignoreMaxStacks.add(Material.IRON_DOOR);
        // boats
        ignoreMaxStacks.add(Material.BOAT);
        ignoreMaxStacks.add(Material.BOAT_ACACIA);
        ignoreMaxStacks.add(Material.BOAT_BIRCH);
        ignoreMaxStacks.add(Material.BOAT_DARK_OAK);
        ignoreMaxStacks.add(Material.BOAT_JUNGLE);
        ignoreMaxStacks.add(Material.BOAT_SPRUCE);
        // common
        ignoreMaxStacks.add(Material.EGG);
        ignoreMaxStacks.add(Material.CAKE);
        ignoreMaxStacks.add(Material.CAKE_BLOCK);
        ignoreMaxStacks.add(Material.SKULL_ITEM);
        ignoreMaxStacks.add(Material.SKULL);
        ignoreMaxStacks.add(Material.BUCKET);
        ignoreMaxStacks.add(Material.BED);
        ignoreMaxStacks.add(Material.SHEARS);
        // records
        ignoreMaxStacks.add(Material.GOLD_RECORD);
        ignoreMaxStacks.add(Material.GREEN_RECORD);
        ignoreMaxStacks.add(Material.RECORD_3);
        ignoreMaxStacks.add(Material.RECORD_4);
        ignoreMaxStacks.add(Material.RECORD_5);
        ignoreMaxStacks.add(Material.RECORD_6);
        ignoreMaxStacks.add(Material.RECORD_7);
        ignoreMaxStacks.add(Material.RECORD_8);
        ignoreMaxStacks.add(Material.RECORD_9);
        ignoreMaxStacks.add(Material.RECORD_10);
        ignoreMaxStacks.add(Material.RECORD_11);
        ignoreMaxStacks.add(Material.RECORD_12);
        // minecarts
        ignoreMaxStacks.add(Material.MINECART);
        ignoreMaxStacks.add(Material.STORAGE_MINECART);
        ignoreMaxStacks.add(Material.POWERED_MINECART);
        ignoreMaxStacks.add(Material.EXPLOSIVE_MINECART);
        ignoreMaxStacks.add(Material.HOPPER_MINECART);
        // plates, leggings, helmets, boots
        ignoreMaxStacks.add(Material.IRON_BOOTS);
        ignoreMaxStacks.add(Material.IRON_LEGGINGS);
        ignoreMaxStacks.add(Material.IRON_CHESTPLATE);
        ignoreMaxStacks.add(Material.IRON_HELMET);
        ignoreMaxStacks.add(Material.GOLD_BOOTS);
        ignoreMaxStacks.add(Material.GOLD_LEGGINGS);
        ignoreMaxStacks.add(Material.GOLD_CHESTPLATE);
        ignoreMaxStacks.add(Material.GOLD_HELMET);
        ignoreMaxStacks.add(Material.DIAMOND_BOOTS);
        ignoreMaxStacks.add(Material.DIAMOND_LEGGINGS);
        ignoreMaxStacks.add(Material.DIAMOND_CHESTPLATE);
        ignoreMaxStacks.add(Material.DIAMOND_HELMET);
        ignoreMaxStacks.add(Material.LEATHER_BOOTS);
        ignoreMaxStacks.add(Material.LEATHER_LEGGINGS);
        ignoreMaxStacks.add(Material.LEATHER_CHESTPLATE);
        ignoreMaxStacks.add(Material.LEATHER_HELMET);
        // horse
        ignoreMaxStacks.add(Material.SADDLE);
        ignoreMaxStacks.add(Material.IRON_BARDING);
        ignoreMaxStacks.add(Material.GOLD_BARDING);
        ignoreMaxStacks.add(Material.DIAMOND_BARDING);
        // weapons
        ignoreMaxStacks.add(Material.BOW);
        // sword
        ignoreMaxStacks.add(Material.DIAMOND_SWORD);
        ignoreMaxStacks.add(Material.GOLD_SWORD);
        ignoreMaxStacks.add(Material.IRON_SWORD);
        ignoreMaxStacks.add(Material.WOOD_SWORD);
        // axe
        ignoreMaxStacks.add(Material.DIAMOND_AXE);
        ignoreMaxStacks.add(Material.GOLD_AXE);
        ignoreMaxStacks.add(Material.IRON_AXE);
        ignoreMaxStacks.add(Material.WOOD_AXE);
        // spade
        ignoreMaxStacks.add(Material.DIAMOND_SPADE);
        ignoreMaxStacks.add(Material.GOLD_SPADE);
        ignoreMaxStacks.add(Material.IRON_SPADE);
        ignoreMaxStacks.add(Material.WOOD_SPADE);
        // pickaxe
        ignoreMaxStacks.add(Material.DIAMOND_PICKAXE);
        ignoreMaxStacks.add(Material.GOLD_PICKAXE);
        ignoreMaxStacks.add(Material.IRON_PICKAXE);
        ignoreMaxStacks.add(Material.WOOD_PICKAXE);
        // hoe
        ignoreMaxStacks.add(Material.DIAMOND_HOE);
        ignoreMaxStacks.add(Material.GOLD_HOE);
        ignoreMaxStacks.add(Material.IRON_HOE);
        ignoreMaxStacks.add(Material.WOOD_HOE);
        // shield
        ignoreMaxStacks.add(Material.SHIELD);

        boolean ignoreMax = false, ignoreDamaged = false;
        for (int i = 0; i < len; i++) {
            ItemStack item = items[i];

            if (item == null) {
                continue;
            }

            ignoreMax = ignoreMaxStacks.contains(item.getType());

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
