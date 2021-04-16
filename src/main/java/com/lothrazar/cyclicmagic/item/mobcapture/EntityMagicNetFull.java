/*******************************************************************************
 * The MIT License (MIT)
 * 
 * Copyright (C) 2014-2018 Sam Bassett (aka Lothrazar)
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/
package com.lothrazar.cyclicmagic.item.mobcapture;

import com.lothrazar.cyclicmagic.entity.EntityThrowableDispensable;
import com.lothrazar.cyclicmagic.entity.RenderBall;
import com.lothrazar.cyclicmagic.registry.SoundRegistry;
import com.lothrazar.cyclicmagic.util.UtilItemStack;
import com.lothrazar.cyclicmagic.util.UtilSound;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class EntityMagicNetFull extends EntityThrowableDispensable {

  public static class FactoryBall implements IRenderFactory<EntityMagicNetFull> {

    @Override
    public Render<? super EntityMagicNetFull> createRenderFor(RenderManager rm) {
      return new RenderBall<EntityMagicNetFull>(rm, "net");
    }
  }

  private ItemStack captured;

  public EntityMagicNetFull(World worldIn) {
    super(worldIn);
  }

  public EntityMagicNetFull(World worldIn, double x, double y, double z) {
    super(worldIn, x, y, z);
  }

  public EntityMagicNetFull(World worldIn, EntityLivingBase ent, ItemStack c) {
    super(worldIn, ent);
    this.setCaptured(c);
  }

  @Override
  protected void processImpact(RayTraceResult mop) {
    if (getCaptured() == null || getCaptured().hasTagCompound() == false) {
      //client desync maybe
      return;
    }
    Entity spawnEntity = EntityList.createEntityFromNBT(getCaptured().getTagCompound(), this.getEntityWorld());
    if (spawnEntity != null) {
      spawnEntity.readFromNBT(getCaptured().getTagCompound());
      spawnEntity.setLocationAndAngles(this.posX, this.posY + 1.1F, this.posZ, this.rotationYaw, 0.0F);
      this.getEntityWorld().spawnEntity(spawnEntity);
      if (spawnEntity instanceof EntityLivingBase) {
        UtilSound.playSound((EntityLivingBase) spawnEntity, SoundRegistry.monster_ball_release);
        UtilItemStack.dropItemStackInWorld(this.getEntityWorld(), this.getPosition(), new ItemStack(getCaptured().getItem()));
      }
    }
    this.setDead();
  }

  public ItemStack getCaptured() {
    return captured;
  }

  public void setCaptured(ItemStack captured) {
    this.captured = captured;
  }
}
