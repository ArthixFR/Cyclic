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
package com.lothrazar.cyclicmagic.block.exppylon;

import com.lothrazar.cyclicmagic.ModCyclic;
import com.lothrazar.cyclicmagic.util.UtilChat;
import com.lothrazar.cyclicmagic.util.UtilExperience;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketTilePylon implements IMessage, IMessageHandler<PacketTilePylon, IMessage> {

  private BlockPos pos;
  private int value;
  private TileEntityXpPylon.Fields type;

  public PacketTilePylon() {}

  public PacketTilePylon(BlockPos p, int s, TileEntityXpPylon.Fields spr) {
    pos = p;
    value = s;
    type = spr;
  }

  @Override
  public void fromBytes(ByteBuf buf) {
    NBTTagCompound tags = ByteBufUtils.readTag(buf);
    int x = tags.getInteger("x");
    int y = tags.getInteger("y");
    int z = tags.getInteger("z");
    pos = new BlockPos(x, y, z);
    value = tags.getInteger("value");
    type = TileEntityXpPylon.Fields.values()[tags.getInteger("t")];
  }

  @Override
  public void toBytes(ByteBuf buf) {
    NBTTagCompound tags = new NBTTagCompound();
    tags.setInteger("x", pos.getX());
    tags.setInteger("y", pos.getY());
    tags.setInteger("z", pos.getZ());
    tags.setInteger("value", value);
    tags.setInteger("t", type.ordinal());
    ByteBufUtils.writeTag(buf, tags);
  }

  @Override
  public IMessage onMessage(PacketTilePylon message, MessageContext ctx) {
    EntityPlayerMP player = ctx.getServerHandler().player;
    TileEntityXpPylon tile = (TileEntityXpPylon) player.getEntityWorld().getTileEntity(message.pos);
    if (tile != null) {
      int fluidPylonHasFluid = tile.getField(message.type.ordinal());
      int pylonSpaceFluid = TileEntityXpPylon.TANK_FULL - fluidPylonHasFluid;
      if (message.type.ordinal() == TileEntityXpPylon.Fields.EXP.ordinal()) { //actually this is a deposit from the player
        int playerHasExp = (int) Math.floor(UtilExperience.getExpTotal(player));
        int playerHasFluid = playerHasExp * TileEntityXpPylon.FLUID_PER_EXP;
        if (message.value >= 0) { // deposit, not a drain
          int expToDrainFluid = 0;
          if (message.value == 0) {
            //deposit all FROM player TO tile
            expToDrainFluid = Math.min(playerHasFluid, pylonSpaceFluid);
          }
          else {//try deposit specified amt
            expToDrainFluid = Math.min(message.value * TileEntityXpPylon.FLUID_PER_EXP, pylonSpaceFluid);
          }
          int expToDrain = expToDrainFluid / TileEntityXpPylon.FLUID_PER_EXP;
          // if I have 5 exp, then we get 5*20 fluid units
          int fluidToDeposit = expToDrainFluid;
          if (fluidPylonHasFluid + expToDrainFluid <= TileEntityXpPylon.TANK_FULL) {//is it full
            if (UtilExperience.drainExp(player, expToDrain)) {//does player have enough
              //then deposit that much into it if drain worked
              tile.setField(message.type.ordinal(), fluidPylonHasFluid + fluidToDeposit);
            }
            else { //  not enouh
              UtilChat.addChatMessage(player, "tile.exp_pylon.notenough");
            }
          }
          else { //  full
            UtilChat.addChatMessage(player, "tile.exp_pylon.full");
          }
        }
        else { // so message.value < 0
          // so DRAIN FROM PYLON, add to PLAYER. BUT only if PYLON has enough
          int expToAdd = message.value * -1;
          int fluidToDrain = expToAdd * TileEntityXpPylon.FLUID_PER_EXP;
          if (fluidToDrain > fluidPylonHasFluid && fluidPylonHasFluid > 0) {
            ModCyclic.logger.info("pulling out more than is there " + fluidToDrain);
            ModCyclic.logger.info("fluidPylonHasFluidt" + fluidPylonHasFluid);
            fluidToDrain = fluidPylonHasFluid;
            expToAdd = fluidToDrain / TileEntityXpPylon.FLUID_PER_EXP;
          }
          if (fluidPylonHasFluid >= fluidToDrain) {
            //if I have 40 exp, that is 2 fluid units
            tile.setField(message.type.ordinal(), fluidPylonHasFluid - fluidToDrain);
            UtilExperience.incrementExp(player, expToAdd);
          }
        }
      }
      else {//NON EXP  field toggle/value will be + or 1 something so increment by that
        tile.setField(message.type.ordinal(), fluidPylonHasFluid + message.value);
      }
      tile.markDirty();
      if (player.openContainer != null) {
        player.openContainer.detectAndSendChanges();
        player.sendAllWindowProperties(player.openContainer, tile);
      }
    }
    return null;
  }
}
