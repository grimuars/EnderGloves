/**
 * This class was created by BrassGoggledCoders modding team.
 * This class is available as part of the EnderGloves Mod for Minecraft.
 *
 * EnderGloves is open-source and is distributed under the MMPL v1.0 License.
 * (http://www.mod-buildcraft.com/MMPL-1.0.txt)
 *
 */
package enderglove.common.lib.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;

/**
 * @author Surseance
 * 
 */
public class EnchantmentSpelunker extends Enchantment
{
	public EnchantmentSpelunker(int id, int rarity)
	{
		super(id, rarity, EnumEnchantmentType.all);
		this.setName("spelunker");
	}
}
