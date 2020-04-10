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
package com.lothrazar.cyclicmagic.block.workbench;

import java.util.HashSet;
import java.util.Set;
import com.lothrazar.cyclicmagic.block.core.TileEntityBaseMachineInvo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityWorkbench extends TileEntityBaseMachineInvo {

  public static final int ROWS = 3;
  public static final int COLS = 3;
  public static final int SIZE_GRID = 3 * 3;
  //for multiplayer, keep a list of inventories being interacted with by the players
  private Set<InventoryWorkbench> inventoriesInUse = new HashSet<>();

  public void addInvo(InventoryWorkbench inv) {
    this.inventoriesInUse.add(inv);
  }

  public void removeInvo(InventoryWorkbench inv) {
    this.inventoriesInUse.remove(inv);
  }

  public TileEntityWorkbench() {
    super(SIZE_GRID);//left and right side both have a tall rectangle. then 3x3 crafting 
    this.setSlotsForBoth();
  }

  @Override
  public void readFromNBT(NBTTagCompound tagCompound) {
    super.readFromNBT(tagCompound);
    //trigger updates for anyone using it 
    for (InventoryWorkbench invo : this.inventoriesInUse) {
      invo.onCraftMatrixChanged();
    }
  }

  @Override
  public boolean isUsableByPlayer(EntityPlayer player) {
    return getWorld().getTileEntity(pos) == this
        && player.getDistanceSq(pos.getX(), pos.getY(), pos.getZ()) <= 32;
  }

  @Override
  public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
    return super.writeToNBT(tagCompound);
  }
}
