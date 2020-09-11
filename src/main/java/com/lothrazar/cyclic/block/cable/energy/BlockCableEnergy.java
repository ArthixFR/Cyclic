package com.lothrazar.cyclic.block.cable.energy;

import javax.annotation.Nullable;
import com.lothrazar.cyclic.block.cable.CableBase;
import com.lothrazar.cyclic.block.cable.EnumConnectType;
import com.lothrazar.cyclic.block.cable.ShapeCache;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class BlockCableEnergy extends CableBase {

  public BlockCableEnergy(Properties properties) {
    super(properties.hardnessAndResistance(0.5F));
  }

  @Override
  public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
    return ShapeCache.getOrCreate(state, CableBase::createShape);
  }

  @Override
  public boolean hasTileEntity(BlockState state) {
    return true;
  }

  @Nullable
  @Override
  public TileEntity createTileEntity(BlockState state, IBlockReader world) {
    return new TileCableEnergy();
  }

  @Override
  protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
    super.fillStateContainer(builder);
    builder.add(UP, DOWN, NORTH, EAST, SOUTH, WEST);
  }

  @Override
  public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState stateIn, @Nullable LivingEntity placer, ItemStack stack) {
    for (Direction d : Direction.values()) {
      TileEntity facingTile = worldIn.getTileEntity(pos.offset(d));
      IEnergyStorage energy = facingTile == null ? null : facingTile.getCapability(CapabilityEnergy.ENERGY, d.getOpposite()).orElse(null);
      if (energy != null) {
        stateIn = stateIn.with(FACING_TO_PROPERTY_MAP.get(d), EnumConnectType.INVENTORY);
        worldIn.setBlockState(pos, stateIn);
      }
    }
  }

  @Override
  public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
    EnumProperty<EnumConnectType> property = FACING_TO_PROPERTY_MAP.get(facing);
    if (stateIn.get(property).isBlocked()) {
      return stateIn;
    }
    if (isEnergy(stateIn, facing, facingState, world, currentPos, facingPos)) {
      return stateIn.with(property, EnumConnectType.INVENTORY);
    }
    else {
      return stateIn.with(property, EnumConnectType.NONE);
    }
  }
}
