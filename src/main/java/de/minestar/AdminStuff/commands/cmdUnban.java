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

import net.minecraft.server.v1_10_R1.GameProfileBanList;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.craftbukkit.v1_10_R1.CraftServer;
import org.bukkit.entity.Player;

import com.mojang.authlib.GameProfile;

import de.minestar.AdminStuff.Core;
import de.minestar.minestarlibrary.commands.AbstractCommand;
import de.minestar.minestarlibrary.utils.ChatUtils;
import de.minestar.minestarlibrary.utils.PlayerUtils;

public class cmdUnban extends AbstractCommand {

    public cmdUnban(String syntax, String arguments, String node) {
        super(Core.NAME, syntax, arguments, node);
    }

    @Override
    public void execute(String[] args, Player player) {
        unban(args, player);
    }

    @Override
    public void execute(String[] args, ConsoleCommandSender console) {
        unban(args, console);
    }

    private void unban(String[] args, CommandSender sender) {
        String targetName = PlayerUtils.getCorrectPlayerName(args[0]);
        if (targetName == null) {
            ChatUtils.writeError(sender, pluginName, "Spieler '" + args[0] + "' existiert nicht!");
        } else {
            // MINECRAFT HACK
            CraftServer cServer = (CraftServer) Bukkit.getServer();
            GameProfileBanList banList = cServer.getHandle().getProfileBans();
            GameProfile bannedProfile = banList.a(targetName);
            if (bannedProfile != null) {
                ChatUtils.writeSuccess(sender, pluginName, "Spieler '" + targetName + "' wurde entbannt!");
                banList.remove(bannedProfile);
                return;
            }
            ChatUtils.writeError(sender, pluginName, "Spieler '" + targetName + " war nicht gebannt!");
        }
    }

}
