/**
 * This class was created by <Surseance> as a part of the
 * EnderGloves mod for Minecraft. 
 *
 * This mod is registered under the WTFPL v2.0. Please read the
 * COPYING.WTFPL file for more details.
 *
 * File created @[May 14, 2014, 8:21:56 PM] 
 */
package endergloves.common.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * @author Surseance (Johnny Eatmon)
 * <jmaeatmon@gmail.com>
 *
 */
public class BlockEnderTotem extends BlockContainer
{
	public BlockEnderTotem()
	{
		super(Material.rock);
		this.setHardness(3.5F);
		this.setResistance(6.0F);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata)
	{
		return null;
	}
}
