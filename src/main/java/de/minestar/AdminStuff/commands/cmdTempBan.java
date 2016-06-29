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

import java.util.Arrays;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

import net.minecraft.server.v1_9_R2.GameProfileBanEntry;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.craftbukkit.v1_9_R2.CraftServer;
import org.bukkit.entity.Player;

import com.mojang.authlib.GameProfile;

import de.minestar.AdminStuff.Core;
import de.minestar.minestarlibrary.commands.AbstractExtendedCommand;
import de.minestar.minestarlibrary.utils.ChatUtils;
import de.minestar.minestarlibrary.utils.PlayerUtils;

public class cmdTempBan extends AbstractExtendedCommand {

    private static final String[] examples = { "1d30h40m", "1d30h", "1d40m", "30h40m", "1d", "30h", "40m" };

    public cmdTempBan(String syntax, String arguments, String node) {
        super(Core.NAME, syntax, arguments, node);
    }

    @Override
    public void execute(String[] args, Player player) {
        tempBann(args, player);
    }

    @Override
    public void execute(String[] args, ConsoleCommandSender console) {
        tempBann(args, console);
    }

    private void tempBann(String[] args, CommandSender sender) {
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
            else
                ChatUtils.writeError(sender, pluginName, "Spieler '" + playerName + "' ist nicht online, wird dennoch gebannt!");
        }

        int[] dates = parseString(args[1].toLowerCase(), sender);
        // an error occured
        if (dates == null)
            return;

        // end time = current time + day in milliseconds + hours in milliseconds
        // + mins in milliseconds
        long time = System.currentTimeMillis() + TimeUnit.DAYS.toMillis(dates[0]) + TimeUnit.HOURS.toMillis(dates[1]) + TimeUnit.MINUTES.toMillis(dates[2]);
        String message = "gebannt fuer " + dates[0] + " Tage, " + dates[1] + " Stunden, " + dates[2] + " Minuten!";

        String reason = null;
        if (args.length > 2)
            reason = ChatUtils.getMessage(args, 2);
        else
            reason = "Temporärer Bann";

        if (target != null) {
            CraftServer cServer = (CraftServer) Bukkit.getServer();
            GameProfile[] profileList = cServer.getHandle().g();
            for (GameProfile profile : profileList) {
                if (profile.getName().equalsIgnoreCase(playerName)) {
                    ban(sender, profile, time, reason);

                    if (target != null) {
                        target.kickPlayer(reason);
                    }

                    ChatUtils.writeSuccess(sender, pluginName, "Spieler '" + playerName + "' wurde gebannt!");
                }
            }

        }

        ChatUtils.writeSuccess(sender, pluginName, "Spieler '" + playerName + "' ist " + message);
    }

    private void ban(CommandSender sender, GameProfile profile, long time, String reason) {
        // MINECRAFT HACK

        // CREATE BAN ENTRY
        GameProfileBanEntry banEntry = new GameProfileBanEntry(profile, new Date(), sender.getName(), new Date(time), reason);
        ((CraftServer) Bukkit.getServer()).getHandle().getProfileBans().add(banEntry);
    }

    private int[] parseString(String date, CommandSender sender) {
        // days, hours, minutes
        int[] result = new int[3];
        try {
            // split the string at 'h' OR 'm' OR 'd' and remaine the delimiter
            StringTokenizer st = new StringTokenizer(date, "[d,h,m]", true);
            // parsed integer
            int i = 0;
            // date identifier
            char c = 0;
            // parse string
            while (st.hasMoreTokens()) {
                i = Integer.parseInt(st.nextToken());
                c = st.nextToken().charAt(0);
                // assign date
                fillDates(result, c, i);
            }

        } catch (Exception e) {
            ChatUtils.writeError(sender, pluginName, "Falsche Syntax! Beispiele: ");
            showExamples(sender);
            return null;
        }
        // when all numbers are zero or negative
        if (result[0] < 1 && result[1] < 1 && result[2] < 1) {
            ChatUtils.writeError(sender, pluginName, Arrays.toString(result) + "sind ungueltige Eingabe! Eine Zahl muss wenigstens positiv ungleich null sein!");
            showExamples(sender);
            return null;
        }
        return result;
    }

    private void fillDates(int[] result, char c, int i) {

        switch (c) {
        case 'd':
            result[0] = i;
            break;
        case 'h':
            result[1] = i;
            break;
        case 'm':
            result[2] = i;
            break;
        }
    }

    private void showExamples(CommandSender sender) {
        for (String example : examples)
            ChatUtils.writeInfo(sender, example);
    }
}
