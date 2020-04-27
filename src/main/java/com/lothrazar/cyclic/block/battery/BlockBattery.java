package com.lothrazar.cyclic.block.battery;

import java.util.Map;
import javax.annotation.Nullable;
import com.lothrazar.cyclic.base.BlockBase;
import com.lothrazar.cyclic.base.CustomEnergyStorage;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.network.NetworkHooks;

public class BlockBattery extends BlockBase {

  public BlockBattery(Properties properties) {
    super(properties.hardnessAndResistance(1.8F));
  }

  @Override
  public void harvestBlock(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity ent, ItemStack stack) {
    super.harvestBlock(world, player, pos, state, ent, stack);
    //    worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());//is this needed???
    ItemStack battery = new ItemStack(this);
    if (ent != null) {
      IEnergyStorage handlerHere = ent.getCapability(CapabilityEnergy.ENERGY, null).orElse(null);
      IEnergyStorage storage = battery.getCapability(CapabilityEnergy.ENERGY, null).orElse(null);
      if (storage != null) {
        ((CustomEnergyStorage) storage).setEnergy(handlerHere.getEnergyStored());
      }
    }
    //even if energy fails 
    if (world.isRemote == false)
      world.addEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), battery));
    //    player.dropItem(battery, true);
  }

  @Override
  public boolean hasTileEntity(BlockState state) {
    return true;
  }

  @Override
  public TileEntity createTileEntity(BlockState state, IBlockReader world) {
    return new TileBattery();
  }

  @Override
  public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult result) {
    if (!world.isRemote) {
      TileEntity tileEntity = world.getTileEntity(pos);
      if (tileEntity instanceof INamedContainerProvider) {
        NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) tileEntity, tileEntity.getPos());
      }
      else {
        throw new IllegalStateException("Our named container provider is missing!");
      }
      return true;
    }
    return super.onBlockActivated(state, world, pos, player, hand, result);
  }

  @Override
  public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
    try {
      IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null).orElse(null);
      if (storage != null) {
        TileBattery container = (TileBattery) world.getTileEntity(pos);
        CustomEnergyStorage storageTile = (CustomEnergyStorage) container.getCapability(CapabilityEnergy.ENERGY, null).orElse(null);
        if (storageTile != null) {
          storageTile.setEnergy(storage.getEnergyStored());
        }
      }
    }
    catch (Exception e) {
      //
    }
  }
}
