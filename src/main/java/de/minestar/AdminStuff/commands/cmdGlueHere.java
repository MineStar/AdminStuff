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

import java.util.List;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import de.minestar.AdminStuff.Core;
import de.minestar.core.MinestarCore;
import de.minestar.core.units.MinestarPlayer;
import de.minestar.minestarlibrary.commands.AbstractExtendedCommand;
import de.minestar.minestarlibrary.utils.ChatUtils;
import de.minestar.minestarlibrary.utils.PlayerUtils;

public class cmdGlueHere extends AbstractExtendedCommand {

    public cmdGlueHere(String syntax, String arguments, String node) {
        super(Core.NAME, syntax, arguments, node);
    }

    @Override
    /**
     * Representing the command <br>
     * /gluehere <Player><br>
     * Glues the player at the position the command  caller is looking
     * 
     * @param player
     *            Called the command
     * @param split
     *            split[0] is the targets name
     */
    public void execute(String[] args, Player player) {
        Player target = null;
        MinestarPlayer mPlayer = null;
        boolean forceGlue = false;
        
        for (String targetName : args) 
            if (targetName.equalsIgnoreCase("-force")) forceGlue = true;
        
        for (String targetName : args) {
            if (targetName.equalsIgnoreCase("-force")) continue;
            target = PlayerUtils.getOnlinePlayer(targetName);
            if (target == null)
                PlayerUtils.sendError(player, pluginName, "Spieler '" + targetName + "' wurde nicht gefunden!");
            else if (target.isDead() || !target.isOnline())
                PlayerUtils.sendError(player, pluginName, "Spieler '" + targetName + "' ist tot oder offline!");
            else {
                mPlayer = MinestarCore.getPlayer(target);
                Location loc = mPlayer.getLocation("adminstuff.glue");
                if (loc == null) {
                    List<Block> lastBlocks = player.getLastTwoTargetBlocks((Set<Material>) null, 50);
                    BlockFace face = lastBlocks.get(1).getFace(lastBlocks.get(0));
                    Location glueLoc = lastBlocks.get(0).getLocation();
                    
                    glueLoc.setX(glueLoc.getX() + 0.5);
                    glueLoc.setZ(glueLoc.getZ() + 0.5);
                    if (face == BlockFace.DOWN){
                        glueLoc.setY(glueLoc.getY() - 1.0);
                        if(!lastBlocks.get(0).getRelative(BlockFace.DOWN).getType().equals(Material.AIR) && forceGlue == false) {
                            ChatUtils.writeError(player, pluginName, "Kann Spieler '" + target.getDisplayName() + "' nicht an diese Stelle kleben!");
                            continue;
                        }
                    }
                    if (face == BlockFace.UP){
                        if(!lastBlocks.get(0).getRelative(BlockFace.UP).getType().equals(Material.AIR) && forceGlue == false) {
                            ChatUtils.writeError(player, pluginName, "Kann Spieler '" + target.getDisplayName() + "' nicht an diese Stelle kleben!");
                            continue;
                        }
                    }
                    
                    mPlayer.setLocation("adminstuff.glue", glueLoc);
                    PlayerUtils.sendSuccess(player, pluginName, "Spieler '" + target.getName() + "' ist festgeklebt!");
                    PlayerUtils.sendInfo(target, pluginName, "Du bist festgeklebt!");
                } else {
                    mPlayer.removeValue("adminstuff.glue", Location.class);
                    PlayerUtils.sendSuccess(player, pluginName, "Spieler '" + target.getName() + "' ist wieder frei!");
                    PlayerUtils.sendInfo(target, pluginName, "Du bist wieder frei!");
                }
            }
        }
    }
}
