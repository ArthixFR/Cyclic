package com.lothrazar.cyclic.block.creativebattery;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.annotation.Nonnull;
import com.lothrazar.cyclic.base.TileEntityBase;
import com.lothrazar.cyclic.capability.CustomEnergyStorage;
import com.lothrazar.cyclic.registry.BlockRegistry;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class TileBatteryInfinite extends TileEntityBase implements ITickableTileEntity {

  static final int MAX = 6400000;

  static enum Fields {
    N, E, S, W, U, D;
  }

  private Map<Direction, Boolean> poweredSides;
  private LazyOptional<IEnergyStorage> energy = LazyOptional.of(this::createEnergy);

  public TileBatteryInfinite() {
    super(BlockRegistry.Tiles.battery_infinite);
    poweredSides = new HashMap<Direction, Boolean>();
    for (Direction f : Direction.values()) {
      poweredSides.put(f, true);
    }
  }

  public boolean getSideHasPower(Direction side) {
    return this.poweredSides.get(side);
  }

  public int getSideField(Direction side) {
    return this.getSideHasPower(side) ? 1 : 0;
  }

  public void setSideField(Direction side, int pow) {
    this.poweredSides.put(side, (pow == 1));
  }

  private IEnergyStorage createEnergy() {
    return new CustomEnergyStorage(MAX, MAX / 4);
  }

  @Override
  public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side) {
    if (cap == CapabilityEnergy.ENERGY) {
      return energy.cast();
    }
    return super.getCapability(cap, side);
  }

  @Override
  public void read(CompoundNBT tag) {
    for (Direction f : Direction.values()) {
      poweredSides.put(f, tag.getBoolean("flow_" + f.getName2()));
    }
    energy.ifPresent(h -> ((INBTSerializable<CompoundNBT>) h).deserializeNBT(tag.getCompound("energy")));
    super.read(tag);
  }

  @Override
  public CompoundNBT write(CompoundNBT tag) {
    for (Direction f : Direction.values()) {
      tag.putBoolean("flow_" + f.getName2(), poweredSides.get(f));
    }
    energy.ifPresent(h -> {
      CompoundNBT compound = ((INBTSerializable<CompoundNBT>) h).serializeNBT();
      tag.put("energy", compound);
    });
    return super.write(tag);
  }

  //  @Override
  public ITextComponent getDisplayName() {
    return new StringTextComponent(getType().getRegistryName().getPath());
  }

  @Override
  public void tick() {
    //recharge
    IEnergyStorage handlerHere = this.getCapability(CapabilityEnergy.ENERGY, null).orElse(null);
    if (handlerHere == null) {
      return;
    }
    handlerHere.receiveEnergy(MAX / 4, false);
    //now go
    this.tickCableFlow();
  }

  private List<Integer> rawList = IntStream.rangeClosed(
      0, 5).boxed().collect(Collectors.toList());

  private void tickCableFlow() {
    Collections.shuffle(rawList);
    for (Integer i : rawList) {
      Direction exportToSide = Direction.values()[i];
      if (this.poweredSides.get(exportToSide))
        moveEnergy(exportToSide, MAX / 4);
    }
  }

  @Override
  public int getField(int id) {
    switch (Fields.values()[id]) {
      case D:
        return this.getSideField(Direction.DOWN);
      case E:
        return this.getSideField(Direction.EAST);
      case N:
        return this.getSideField(Direction.NORTH);
      case S:
        return this.getSideField(Direction.SOUTH);
      case U:
        return this.getSideField(Direction.UP);
      case W:
        return this.getSideField(Direction.WEST);
    }
    return -1;
  }

  @Override
  public void setField(int field, int value) {
    switch (Fields.values()[field]) {
      case D:
        this.setSideField(Direction.DOWN, value % 2);
      break;
      case E:
        this.setSideField(Direction.EAST, value % 2);
      break;
      case N:
        this.setSideField(Direction.NORTH, value % 2);
      break;
      case S:
        this.setSideField(Direction.SOUTH, value % 2);
      break;
      case U:
        this.setSideField(Direction.UP, value % 2);
      break;
      case W:
        this.setSideField(Direction.WEST, value % 2);
      break;
    }
  }
}
