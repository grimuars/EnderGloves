/**
 * This class was created by <Surseance> as a part of the
 * EnderGloves mod for Minecraft. 
 *
 * This mod is registered under the WTFPL v2.0. Please read the
 * COPYING.WTFPL file for more details.
 *
 * File created @[May 25, 2014, 3:09:52 PM] 
 */
package enderglove.client.renderers.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAnvil;
import net.minecraft.block.BlockDragonEgg;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import enderglove.common.entity.EntityMinedBlock;

/**
 * @author Surseance (Johnny Eatmon) 
 * Email: surseance@autistici.org
 * 
 */
@SideOnly(Side.CLIENT)
public class RenderMinedBlock extends Render
{
	private RenderBlocks blockRenderer = new RenderBlocks();

	public RenderMinedBlock()
	{
		shadowSize = 0.0F;
	}

	public void doRender(EntityMinedBlock entBlock, double posX, double posY, double posZ, float f, float renderTick)
	{
		World world = entBlock.getWorldObj();
		Block block = entBlock.getBlock();

		int x = MathHelper.floor_double(entBlock.posX);
		int y = MathHelper.floor_double(entBlock.posY);
		int z = MathHelper.floor_double(entBlock.posZ);
		
		GL11.glDisable(GL11.GL_LIGHTING);

		GL11.glPushMatrix();
		GL11.glTranslatef((float)posX, (float)posY, (float)posZ);
		this.bindEntityTexture(entBlock);
		
		float scale = entBlock.scale;
		float rot = world.getWorldTime() * 17.8F;
		float rf = 1 - world.rand.nextFloat();
			
		GL11.glScalef(scale, scale, scale);
		GL11.glRotatef(rot, rf, rf, rf);
		
		Tessellator tessellator = Tessellator.instance;

		this.blockRenderer.blockAccess = entBlock.worldObj;
		this.blockRenderer.useInventoryTint = true;

		if (block instanceof BlockAnvil)
		{
			blockRenderer.blockAccess = world;
			tessellator.startDrawingQuads();
			tessellator.setTranslation((-x) - 0.5F, (-y) - 0.5F, (-z) - 0.5F);
			blockRenderer.renderBlockAnvilMetadata((BlockAnvil) block, x, y, z, entBlock.metadata);
			tessellator.setTranslation(0.0D, 0.0D, 0.0D);
			tessellator.draw();
		}
		else if (block instanceof BlockDragonEgg)
		{
			blockRenderer.blockAccess = world;
			tessellator.startDrawingQuads();
			tessellator.setTranslation((-x) - 0.5F, (-y) - 0.5F, (-z) - 0.5F);
			blockRenderer.renderBlockDragonEgg((BlockDragonEgg) block, x, y, z);
			tessellator.setTranslation(0.0D, 0.0D, 0.0D);
			tessellator.draw();
		}
		else
		{
			blockRenderer.setRenderBoundsFromBlock(block);
			blockRenderer.renderBlockSandFalling(block, world, x, y, z, entBlock.metadata);
		}

		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glPopMatrix();
	}

	protected ResourceLocation getEntityTexture(EntityMinedBlock entBlock)
	{
		return TextureMap.locationBlocksTexture;
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return this.getEntityTexture((EntityMinedBlock) entity);
	}

	@Override
	public void doRender(Entity entity, double posX, double posY, double posZ, float par8, float brightness)
	{
		this.doRender((EntityMinedBlock) entity, posX, posY, posZ, par8, brightness);
	}
}
