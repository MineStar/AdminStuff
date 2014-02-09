/*
 * Copyright (C) 2013 MineStar.de 
 * 
 * This file is part of AdminStuff.
 * 
 * AdminStuff is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3 of the License.
 * 
 * AdminStuff is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with AdminStuff.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.minestar.AdminStuff.commands;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Horse.Variant;
import org.bukkit.entity.Player;
import org.bukkit.inventory.HorseInventory;
import org.bukkit.inventory.ItemStack;

import de.minestar.AdminStuff.Core;
import de.minestar.minestarlibrary.commands.AbstractExtendedCommand;

public class cmdRide extends AbstractExtendedCommand {

    private final Map<String, Variant> variants;

    public cmdRide(String syntax, String arguments, String node) {
        super(Core.NAME, syntax, arguments, node);
        this.variants = new HashMap<String, Variant>();
        fillVariantsMap();
    }

    private void fillVariantsMap() {
        variants.put("undead", Variant.UNDEAD_HORSE);
        variants.put("skelet", Variant.SKELETON_HORSE);
        variants.put("donkey", Variant.DONKEY);
        variants.put("mule", Variant.MULE);
    }

    @Override
    public void execute(String[] args, Player player) {
        // Spawn horse
        Horse horse = (Horse) player.getWorld().spawnEntity(player.getLocation(), EntityType.HORSE);
        Variant var = Variant.HORSE;
        // Possible variant
        if (args.length == 1) {
            var = variants.get(args[0].toLowerCase());
            if (var == null)
                var = Variant.HORSE;
        }
        HorseInventory inv = horse.getInventory();
        inv.setSaddle(new ItemStack(Material.SADDLE));
        horse.setVariant(var);
        horse.setOwner(player);
        horse.setPassenger(player);
    }

}
