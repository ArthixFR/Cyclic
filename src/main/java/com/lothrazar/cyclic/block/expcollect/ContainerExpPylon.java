package com.lothrazar.cyclic.block.expcollect;

import com.lothrazar.cyclic.base.ContainerBase;
import com.lothrazar.cyclic.registry.BlockRegistry;
import com.lothrazar.cyclic.registry.ContainerScreenRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ContainerExpPylon extends ContainerBase {

  TileExpPylon tile;

  public ContainerExpPylon(int windowId, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity player) {
    super(ContainerScreenRegistry.EXPERIENCE_PYLON, windowId);
    tile = (TileExpPylon) world.getTileEntity(pos);
    this.playerEntity = player;
    this.playerInventory = playerInventory;
    layoutPlayerInventorySlots(8, 84);
    this.trackAllIntFields(tile, TileExpPylon.Fields.values().length);
  }

  @Override
  public boolean canInteractWith(PlayerEntity playerIn) {
    return isWithinUsableDistance(IWorldPosCallable.of(tile.getWorld(), tile.getPos()), playerEntity, BlockRegistry.experience_pylon);
  }
}
