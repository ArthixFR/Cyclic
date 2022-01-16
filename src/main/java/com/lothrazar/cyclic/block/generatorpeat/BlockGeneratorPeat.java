package com.lothrazar.cyclic.block.generatorpeat;

import com.lothrazar.cyclic.base.BlockBase;
import com.lothrazar.cyclic.registry.ContainerScreenRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public class BlockGeneratorPeat extends BlockBase {

  public BlockGeneratorPeat(Properties properties) {
    super(properties.hardnessAndResistance(1.8F));
    this.setHasGui();
  }

  @Override
  public boolean hasTileEntity(BlockState state) {
    return true;
  }

  @Override
  public TileEntity createTileEntity(BlockState state, IBlockReader world) {
    return new TileGeneratorPeat();
  }

  @Override
  public void registerClient() {
    ScreenManager.registerFactory(ContainerScreenRegistry.generatorCont, ScreenGeneratorPeat::new);
  }

  @Override
  protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
    builder.add(LIT);
  }
}
