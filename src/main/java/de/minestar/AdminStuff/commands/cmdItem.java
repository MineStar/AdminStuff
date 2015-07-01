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

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.minestar.AdminStuff.Core;
import de.minestar.minestarlibrary.commands.AbstractExtendedCommand;

public class cmdItem extends AbstractExtendedCommand {

    public cmdItem(String syntax, String arguments, String node) {
        super(Core.NAME, syntax, arguments, node);
    }

    @Override
    /**
     * Representing the command <br>
     * /i <ItemID or Name>[:SubID] <Amount> <br>
     * This gives the player a specified itemstack
     * 
     * @param player
     *            Called the command
     * @param split
     *            split[0] is the item name
     *            split[1] is the itemamount
     */
    public void execute(String[] args, Player player) {
        String command = "/give";
        for (String arg : args) {
            command += ' ' + arg;
        }
        Bukkit.getServer().dispatchCommand(player, command);
    }
}
