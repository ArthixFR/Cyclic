package com.lothrazar.cyclic.block.battery;

import com.lothrazar.cyclic.base.ContainerBase;
import com.lothrazar.cyclic.registry.BlockRegistry;
import com.lothrazar.cyclic.registry.ContainerScreenRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntReferenceHolder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.wrapper.InvWrapper;

public class ContainerBattery extends ContainerBase {

  TileBattery tile;

  public ContainerBattery(int windowId, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity player) {
    super(ContainerScreenRegistry.batteryCont, windowId);
    tile = (TileBattery) world.getTileEntity(pos);
    this.playerEntity = player;
    this.playerInventory = new InvWrapper(playerInventory);
    layoutPlayerInventorySlots(8, 84);
    trackInt(new IntReferenceHolder() {

      @Override
      public int get() {
        return getEnergy();
      }

      @Override
      public void set(int value) {
        //        tile.getCapability(CapabilityEnergy.ENERGY).ifPresent(h -> ((CustomEnergyStorage) h).setEnergy(value));
      }
    });
    trackInt(new IntReferenceHolder() {

      @Override
      public int get() {
        return tile.getFlowing();
      }

      @Override
      public void set(int value) {
        tile.setFlowing(value);
      }
    });
  }

  public int getEnergy() {
    return tile.getCapability(CapabilityEnergy.ENERGY).map(IEnergyStorage::getEnergyStored).orElse(0);
  }

  @Override
  public boolean canInteractWith(PlayerEntity playerIn) {
    return isWithinUsableDistance(IWorldPosCallable.of(tile.getWorld(), tile.getPos()), playerEntity, BlockRegistry.battery);
  }
}