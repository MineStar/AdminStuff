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

import java.util.Date;

import net.minecraft.server.v1_8_R3.GameProfileBanEntry;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.entity.Player;

import com.mojang.authlib.GameProfile;

import de.minestar.AdminStuff.Core;
import de.minestar.minestarlibrary.commands.AbstractExtendedCommand;
import de.minestar.minestarlibrary.utils.ChatUtils;
import de.minestar.minestarlibrary.utils.PlayerUtils;

public class cmdBan extends AbstractExtendedCommand {

    public cmdBan(String syntax, String arguments, String node) {
        super(Core.NAME, syntax, arguments, node);
    }

    @Override
    public void execute(String[] args, Player player) {
        banPlayer(args, player);
    }

    @Override
    public void execute(String[] args, ConsoleCommandSender console) {
        banPlayer(args, console);
    }

    private void banPlayer(String[] args, CommandSender sender) {
        String playerName = args[0];
        Player target = PlayerUtils.getOnlinePlayer(playerName);
        // player is online
        if (target != null)
            playerName = target.getName();
        else {
            // player is maybe offline?
            playerName = PlayerUtils.getOfflinePlayerName(playerName);
            // player was never on the server
            if (playerName == null) {
                playerName = args[0];
                ChatUtils.writeError(sender, pluginName, "Spieler '" + playerName + "' existiert nicht, wird dennoch praeventiv gebannt!");
            }
            // player is offline
            ChatUtils.writeInfo(sender, pluginName, "Spieler '" + playerName + "' ist nicht online, wird dennoch gebannt!");
        }

        String reason = null;
        if (args.length > 1)
            reason = ChatUtils.getMessage(args, 1);
        else
            reason = "Permanenter Bann";

        CraftServer cServer = (CraftServer) Bukkit.getServer();
        GameProfile[] profileList = cServer.getHandle().g();
        for (GameProfile profile : profileList) {
            if (profile.getName().equalsIgnoreCase(playerName)) {
                ban(sender, profile, reason);

                if (target != null) {
                    target.kickPlayer(reason);
                }

                ChatUtils.writeSuccess(sender, pluginName, "Spieler '" + playerName + "' wurde gebannt!");
            }
        }
    }

    private void ban(CommandSender sender, GameProfile profile, String reason) {
        // MINECRAFT HACK

        // CREATE BAN ENTRY
        GameProfileBanEntry banEntry = new GameProfileBanEntry(profile, new Date(), sender.getName(), null, reason);
        ((CraftServer) Bukkit.getServer()).getHandle().getProfileBans().add(banEntry);
    }
}
