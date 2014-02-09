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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import de.minestar.AdminStuff.Core;
import de.minestar.minestarlibrary.commands.AbstractExtendedCommand;
import de.minestar.minestarlibrary.utils.PlayerUtils;

public class cmdButcher extends AbstractExtendedCommand {

	private Map<Player, Long> recentUser = new HashMap<Player, Long>();

	public cmdButcher(String syntax, String arguments, String node) {
		super(Core.NAME, syntax, arguments, node);
		this.description = "Toetet Monster im Umkreis";
	}

	@Override
	public void execute(String[] args, Player player) {
		if (args.length == 0) {
			Long lastTime = recentUser.get(player);
			if (lastTime == null
					|| (System.currentTimeMillis() - lastTime >= TimeUnit.MINUTES
							.toMillis(5))) {
				PlayerUtils.sendMessage(player, ChatColor.MAGIC,
						"-------------------------------------------");
				PlayerUtils
						.sendError(player, pluginName,
								"Wenn du wirklich alle Tiere über den Jordan schicken willst,");
				PlayerUtils.sendError(player, pluginName, "(Das sind ca. "
						+ countMobs(player.getWorld())
						+ " Tiere! Denk mal an deren Kinder!)");
				PlayerUtils
						.sendError(player, pluginName,
								"Dann gebe bitte den Befehl ein 2. Mal ein und lebe mit den Folgen!");
				PlayerUtils.sendMessage(player, ChatColor.MAGIC,
						"-------------------------------------------");
				recentUser.put(player, System.currentTimeMillis());
			} else {
				killAll(player);
				recentUser.remove(player);
			}
		} else if (args.length == 1) {
			int radius = 0;
			try {
				radius = Integer.parseInt(args[0]);
			} catch (Exception e) {
				PlayerUtils.sendError(player, pluginName, args[0]
						+ " ist keine Zahl!");
				return;
			}
			killRadius(player, radius);
		} else
			PlayerUtils.sendError(player, pluginName, getHelpMessage());
	}

	private void killRadius(Player player, int radius) {
		int chunkRadius = (radius >> 4) + 1;
		int tempRadius = radius * radius;

		Chunk chunk = player.getLocation().getChunk();
		Location pLoc = player.getLocation();
		World world = player.getWorld();

		int startX = chunk.getX() - chunkRadius;
		int startZ = chunk.getZ() - chunkRadius;

		int endX = chunk.getX() + chunkRadius;
		int endZ = chunk.getZ() + chunkRadius;

		int counter = 0;
		for (int x = startX; x <= endX; ++x) {
			for (int z = startZ; z <= endZ; ++z) {
				chunk = world.getChunkAt(x, z);
				if (chunk == null)
					continue;

				for (Entity entity : chunk.getEntities()) {
					if (entity instanceof Player
							|| (!(entity instanceof LivingEntity)))
						continue;
					if (isInRange(tempRadius, pLoc, entity.getLocation())) {
						entity.remove();
						++counter;
					}
				}
			}
		}
		PlayerUtils.sendSuccess(player, pluginName, "Es wurden im Umkreis von "
				+ radius + " Metern " + counter + " Monster entfernt.");
	}

	private boolean isInRange(int r, Location pLoc, Location mLoc) {
		return r >= (Math.pow((pLoc.getBlockX() - mLoc.getBlockX()), 2)
				+ Math.pow((pLoc.getBlockY() - mLoc.getBlockY()), 2) + Math
					.pow((pLoc.getBlockZ() - mLoc.getBlockZ()), 2));
	}

	private void killAll(Player player) {
		Collection<LivingEntity> entities = player.getWorld()
				.getEntitiesByClass(LivingEntity.class);
		int counter = 0;
		for (LivingEntity entity : entities) {
			if (entity instanceof Player)
				continue;
			entity.remove();
			++counter;
		}

		PlayerUtils.sendSuccess(player, pluginName, "Es wurden " + counter
				+ " Monster entfernt.");
	}

	private int countMobs(World world) {
		Collection<LivingEntity> entities = world
				.getEntitiesByClass(LivingEntity.class);
		int counter = 0;
		for (LivingEntity entity : entities) {
			if (entity instanceof Player)
				continue;
			++counter;
		}

		return counter;
	}
}
