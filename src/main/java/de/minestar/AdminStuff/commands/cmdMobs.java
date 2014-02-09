/*
 * Copyright (C) 2012 MineStar.de 
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

import org.bukkit.entity.Animals;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;

import de.minestar.AdminStuff.Core;
import de.minestar.minestarlibrary.commands.AbstractCommand;
import de.minestar.minestarlibrary.utils.PlayerUtils;

public class cmdMobs extends AbstractCommand {

    public cmdMobs(String syntax, String arguments, String node) {
        super(Core.NAME, syntax, arguments, node);
        this.description = "Monster zählen";
    }

    @Override
    public void execute(String[] args, Player player) {
        int animalCount = player.getWorld().getEntitiesByClass(Animals.class).size();
        int mobCount = player.getWorld().getEntitiesByClass(Monster.class).size();
        PlayerUtils.sendInfo(player, pluginName, "Mobs in world '" + player.getWorld().getName() + "'");
        PlayerUtils.sendInfo(player, "Friendly: " + animalCount);
        PlayerUtils.sendInfo(player, "Hostile: " + mobCount);
    }
}
