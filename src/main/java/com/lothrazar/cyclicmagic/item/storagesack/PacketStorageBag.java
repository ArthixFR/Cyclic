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
package com.lothrazar.cyclicmagic.item.storagesack;

import com.lothrazar.cyclicmagic.util.UtilChat;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketStorageBag implements IMessage, IMessageHandler<PacketStorageBag, IMessage> {

  private int type;

  public PacketStorageBag() {}

  public PacketStorageBag(int type) {
    this.type = type;
  }

  @Override
  public void fromBytes(ByteBuf buf) {
    NBTTagCompound tags = ByteBufUtils.readTag(buf);
    type = tags.getInteger("type");
  }

  @Override
  public void toBytes(ByteBuf buf) {
    NBTTagCompound tags = new NBTTagCompound();
    tags.setInteger("type", type);
    ByteBufUtils.writeTag(buf, tags);
  }

  @Override
  public IMessage onMessage(PacketStorageBag message, MessageContext ctx) {
    EntityPlayer player = ctx.getServerHandler().player;
    ItemStack stack = player.getHeldItemMainhand();
    if (!stack.isEmpty() && stack.getItem() instanceof ItemStorageBag) {
      if (message.type == 0) {
        StorageActionType.toggle(stack);
        UtilChat.addChatMessage(player, UtilChat.lang(StorageActionType.getName(stack)));
      }
      else {
        StoragePickupType.toggle(stack);
        UtilChat.addChatMessage(player, UtilChat.lang(StoragePickupType.getName(stack)));
      }
    }
    return null;
  }
}
