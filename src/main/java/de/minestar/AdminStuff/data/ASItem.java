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

package de.minestar.AdminStuff.data;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.server.v1_7_R2.Block;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_7_R2.util.CraftMagicNumbers;
import org.bukkit.inventory.ItemStack;

import com.bukkit.gemo.utils.BlockUtils;

public class ASItem {
    public static Map<String, Integer> matList = null;
    private int TypeID = 0;
    private byte Data = (byte) 0;

    public ASItem(int TypeID) {
        this.TypeID = TypeID;
        initMatList();
    }

    public ASItem(int TypeID, byte Data) {
        this.TypeID = TypeID;
        this.Data = Data;
        initMatList();
    }

    public static void initMatList() {
        if (matList == null) {
            matList = new HashMap<String, Integer>();
            for (Material mat : Material.values()) {
                matList.put(mat.name().replace("_", "").replace(" ", "").toLowerCase(), mat.getId());
            }
        }
    }

    public int getTypeID() {
        return TypeID;
    }

    public byte getData() {
        return Data;
    }

    /**
     * 
     * STATIC FUNCTIONS FOR RETURNING ITEMS
     * 
     */

    public static int returnItemID(String itemName) {
        return BlockUtils.getItemIDFromName(itemName);
    }

    public static ItemStack getItemStack(int TypeID, int amount) {
        return getItemStack(TypeID, (byte) 0, amount);
    }

    public static ItemStack getItemStack(int TypeID, byte Data, int amount) {
        if (!isValid(TypeID, Data))
            return null;

        return new ItemStack(Material.getMaterial(Integer.toString(TypeID)), amount, Data);
    }

    public static ItemStack getItemStack(String name, int amount) {
        return getItemStack(name, (byte) 0, amount);
    }

    public static ItemStack getItemStack(String name, byte Data, int amount) {
        if (!isValid(name, Data))
            return null;

        if (!isInteger(name))
            return new ItemStack(matList.get(name.toLowerCase()), amount, Data);
        else
            return new ItemStack(Material.matchMaterial(name), amount, Data);
    }

    /**
     * 
     * STATIC FUNCTIONS FOR ITEM VALIDATION
     * 
     */

    public static boolean isValid(int TypeID) {
        return isValid(TypeID, (byte) 0);
    }

    public static String getIDPart(String input) {
        if (input.split(":").length >= 1)
            return input.split(":")[0];
        else
            return input;
    }

    public static byte getDataPart(String input) {
        try {
            return Byte.valueOf(input.split(":")[1]);
        } catch (Exception e) {
            return 0;
        }
    }

    public static boolean isInteger(String input) {
        try {
            Integer.parseInt(getIDPart(input));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isValid(String name) {
        return isValid(name, (byte) 0);
    }

    public static boolean isValid(String name, byte Data) {
        if (isInteger(name))
            return isValid(Integer.valueOf(name), Data);

        initMatList();
        if (!matList.containsKey(name.toLowerCase()))
            return false;

        return isValid(matList.get(name.toLowerCase()), Data);
    }

    public static boolean isValid(int TypeID, byte Data) {
        initMatList();
        Material mat = Material.matchMaterial(Integer.toString(TypeID));
        if (mat == null) {
            mat = null;
            return false;
        }

        if (Data < 0 || Data > 15)
            return false;

        switch (mat) {
        case WOOL:
        case INK_SACK:
            return true;
        case LOG:
        case MONSTER_EGGS:
        case JUKEBOX:
        case LONG_GRASS:
        case DEAD_BUSH:
            return Data <= 2;
        case DOUBLE_STEP:
        case STEP:
            return Data <= 6;
        case CROPS:
            return Data <= 7;
        case COAL:
            return Data <= 1;
        case SAPLING:
        case LEAVES:
            return Data <= 4;
        case SMOOTH_BRICK:
            return Data <= 3;
        default:
            return Data == 0;
        }
    }

    public static int getMaxStackSize(Material mat) {
        switch (mat) {
        case CAKE:
        case LAVA_BUCKET:
        case WATER_BUCKET:
        case MILK_BUCKET:
        case BUCKET:
        case MAP:
            return 1;
        default:
            // do nothing
        }
        // tools
        if ((mat.getId() >= 267 && mat.getId() <= 286) || (mat.getId() >= 290 && mat.getId() <= 294) || (mat.getId() >= 298 && mat.getId() <= 317))
            return 1;
        else
            return 64;
    }
}
