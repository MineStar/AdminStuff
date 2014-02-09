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

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import de.minestar.AdminStuff.Core;
import de.minestar.minestarlibrary.commands.AbstractExtendedCommand;
import de.minestar.minestarlibrary.utils.ChatUtils;
import de.minestar.minestarlibrary.utils.PlayerUtils;

public class cmdKick extends AbstractExtendedCommand {

	public cmdKick(String syntax, String arguments, String node) {
		super(Core.NAME, syntax, arguments, node);
	}

	@Override
	public void execute(String[] args, Player player) {
		kickPlayer(args, player);
	}

	@Override
	public void execute(String[] args, ConsoleCommandSender console) {
		kickPlayer(args, console);
	}

	private void kickPlayer(String[] args, CommandSender sender) {
		String targetName = args[0];
		String msg = null;
		if (args.length == 1)
			msg = "Du wurdest gekickt!";
		else
			msg = ChatUtils.getMessage(args, 1);

		Player target = PlayerUtils.getOnlinePlayer(targetName);
		if (target == null)
			ChatUtils.writeError(sender, pluginName, "Spieler '" + targetName
					+ "' wurde nicht gefunden!");
		else if (!target.isOnline())
			ChatUtils.writeError(sender, pluginName, "Spieler '" + targetName
					+ "' ist nicht online!");
		else {
			target.kickPlayer(msg);
			ChatUtils.writeSuccess(sender, pluginName, "Spieler '" + targetName
					+ "' wurde gekickt!");
		}
	}
}
