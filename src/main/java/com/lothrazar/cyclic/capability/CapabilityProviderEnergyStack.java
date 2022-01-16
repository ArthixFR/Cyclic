package com.lothrazar.cyclic.capability;

import com.lothrazar.cyclic.base.TileEntityBase;
import com.lothrazar.cyclic.block.battery.TileBattery;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class CapabilityProviderEnergyStack implements ICapabilitySerializable<CompoundNBT> {

  CustomEnergyStorage energy = new CustomEnergyStorage(TileBattery.MAX, TileBattery.MAX);
  private LazyOptional<IEnergyStorage> energyCap = LazyOptional.of(() -> energy);

  public CapabilityProviderEnergyStack(int max) {
    energy = new CustomEnergyStorage(max, max);
    energyCap = LazyOptional.of(() -> energy);
  }

  @Override
  public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
    if (cap == CapabilityEnergy.ENERGY) {
      return energyCap.cast();
    }
    return LazyOptional.empty();
  }

  @Override
  public CompoundNBT serializeNBT() {
    CompoundNBT tag = new CompoundNBT();
    tag.put(TileEntityBase.NBTENERGY, energy.serializeNBT());
    return tag;
  }

  @Override
  public void deserializeNBT(CompoundNBT nbt) {
    energy.deserializeNBT(nbt.getCompound(TileEntityBase.NBTENERGY));
  }
}
