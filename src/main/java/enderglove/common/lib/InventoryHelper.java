/**
 * This class was created by <Surseance> as a part of the
 * EnderGlove mod for Minecraft.
 *
 * This mod is registered under the WTFPL v2.0. Please read the
 * COPYING.WTFPL file for more details.
 *
 * File created @[May 14, 2014, 9:08:06 PM]
 */
package enderglove.common.lib;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryEnderChest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * @author Surseance (Johnny Eatmon) <jmaeatmon@gmail.com>
 * 
 */
public class InventoryHelper
{
	/**
	 * Grabs the Ender Chest inventory relative to a given player.
	 * 
	 * @param p
	 *            - the player that owns the Ender Inventory
	 * @return - an instance of the InventoryEnderChest
	 */
	public static InventoryEnderChest getPlayerEnderChest(final EntityPlayer p)
	{
		return p.getInventoryEnderChest();
	}

	/**
	 * @author MightyPork, from PowerCraft
	 * 
	 * @param inv
	 * @param is
	 * @return
	 */
	public static boolean addItemStackToInventory(final IInventory inv,
			final ItemStack is)
	{
		if (!is.isItemDamaged())
		{
			int stackSize;
			do
			{
				stackSize = is.stackSize;
				is.stackSize = storePartially(inv, is);
			} while (is.stackSize > 0 && is.stackSize < stackSize);

			return is.stackSize < stackSize;
		}

		final int slot = getFirstEmptySlot(inv, is);

		if (slot >= 0)
		{
			inv.setInventorySlotContents(slot, ItemStack.copyItemStack(is));
			is.stackSize = 0;
			return true;
		}

		return false;
	}

	/**
	 * @author MightyPork, from PowerCraft
	 * 
	 * @param inv
	 * @param is
	 * @return
	 */
	public static int storePartially(final IInventory inv, final ItemStack is)
	{
		final Item item = is.getItem();
		int size = is.stackSize;

		if (is.getMaxStackSize() == 1) // Not stackable
		{
			final int freeSlot = getFirstEmptySlot(inv, is);

			if (freeSlot < 0)
			{
				return size;
			}

			if (inv.getStackInSlot(freeSlot) == null)
			{
				inv.setInventorySlotContents(freeSlot,
						ItemStack.copyItemStack(is));
			}

			return 0;
		}

		int freeSlot = getNonFilledStack(inv, is);

		if (freeSlot < 0)
		{
			freeSlot = getFirstEmptySlot(inv, is);
		}
		if (freeSlot < 0)
		{
			return size;
		}

		if (inv.getStackInSlot(freeSlot) == null)
		{
			inv.setInventorySlotContents(freeSlot,
					new ItemStack(item, 0, is.getItemDamage()));
		}

		int canStore = size;

		if (canStore > inv.getStackInSlot(freeSlot).getMaxStackSize()
				- inv.getStackInSlot(freeSlot).stackSize)
		{
			canStore = inv.getStackInSlot(freeSlot).getMaxStackSize()
					- inv.getStackInSlot(freeSlot).stackSize;
		}
		if (canStore > inv.getInventoryStackLimit()
				- inv.getStackInSlot(freeSlot).stackSize)
		{
			canStore = inv.getInventoryStackLimit()
					- inv.getStackInSlot(freeSlot).stackSize;
		}

		if (canStore == 0)
		{
			return size;
		}
		else
		{
			size -= canStore;
			inv.getStackInSlot(freeSlot).stackSize += canStore;
			return size;
		}
	}

	/**
	 * @author MightyPork, from PowerCraft
	 * 
	 * @param inv
	 * @param is
	 * @return
	 */
	public static int getNonFilledStack(final IInventory inv, final ItemStack is)
	{
		for (int slot = 0; slot < inv.getSizeInventory(); slot++)
		{
			final ItemStack stackInSlot = inv.getStackInSlot(slot);

			if (stackInSlot != null
					&& stackInSlot.getItem() == is.getItem()
					&& stackInSlot.isStackable()
					&& stackInSlot.stackSize < stackInSlot.getMaxStackSize()
					&& stackInSlot.stackSize < inv.getInventoryStackLimit()
					&& (!stackInSlot.getHasSubtypes() || stackInSlot
							.getItemDamage() == is.getItemDamage()))
			{
				return slot;
			}
		}

		return -1;
	}

	/**
	 * Grabs the first empty slot that the item stack can be placed into.
	 * 
	 * @param inv
	 *            - the inventory to check
	 * @param is
	 *            - the item stack to put in the inventory
	 * @return -1 if there is not slot available
	 */
	public static int getFirstEmptySlot(final IInventory inv, final ItemStack is)
	{
		for (int slot = 0; slot < inv.getSizeInventory(); slot++)
		{
			if (inv.getStackInSlot(slot) == null)
			{
				return slot;
			}
		}

		return -1;
	}

	/**
	 * Determines if a specific item is in the player's inventory.
	 * 
	 * @param player
	 *            - the player with the inventory to check
	 * @param item
	 *            - the item stack to look for
	 * @return - the slot in which the specified item sits
	 */
	public static int isInPlayerInventory(final EntityPlayer player,
			final Item item)
	{
		for (int slot = 0; slot < player.inventory.mainInventory.length; slot++)
		{
			if (player.inventory.mainInventory[slot] != null
					&& player.inventory.mainInventory[slot].getItem() == item)
			{
				return slot;
			}
		}

		return -1;
	}

	/**
	 * Determines if the given inventory is empty by checking if it can add the
	 * given item stack to the inventory.
	 * 
	 * @param inv
	 *            - the inventory to check
	 * @param is
	 *            - the item stack to put in the inventory
	 * @return true if there's room for the item stack
	 */
	public static boolean isInvEmpty(final IInventory inv, final ItemStack is)
	{
		if (!addItemStackToInventory(inv, is))
		{
			return false;
		}

		return true;
	}
}