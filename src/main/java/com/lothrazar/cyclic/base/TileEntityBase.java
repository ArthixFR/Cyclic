package com.lothrazar.cyclic.base;

import com.lothrazar.cyclic.ModCyclic;
import com.lothrazar.cyclic.block.breaker.BlockBreaker;
import com.lothrazar.cyclic.block.cable.energy.TileCableEnergy;
import com.lothrazar.cyclic.capability.CustomEnergyStorage;
import com.lothrazar.cyclic.item.datacard.filter.FilterCardItem;
import com.lothrazar.cyclic.net.PacketEnergySync;
import com.lothrazar.cyclic.registry.PacketRegistry;
import com.lothrazar.cyclic.util.UtilDirection;
import com.lothrazar.cyclic.util.UtilEntity;
import com.lothrazar.cyclic.util.UtilFakePlayer;
import com.lothrazar.cyclic.util.UtilFluid;
import com.lothrazar.cyclic.util.UtilItemStack;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.client.CPlayerDiggingPacket.Action;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

public abstract class TileEntityBase extends TileEntity implements IInventory {

  public static final String NBTINV = "inv";
  public static final String NBTFLUID = "fluid";
  public static final String NBTENERGY = "energy";
  public static final int MENERGY = 64 * 1000;
  protected int flowing = 1;
  protected int needsRedstone = 1;
  protected int render = 0; // default to do not render
  protected int timer = 0;

  public TileEntityBase(TileEntityType<?> tileEntityTypeIn) {
    super(tileEntityTypeIn);
  }

  public int getTimer() {
    return timer;
  }

  protected PlayerEntity getLookingPlayer(int maxRange, boolean mustCrouch) {
    List<PlayerEntity> players = world.getEntitiesWithinAABB(PlayerEntity.class, new AxisAlignedBB(
        this.pos.getX() - maxRange, this.pos.getY() - maxRange, this.pos.getZ() - maxRange, this.pos.getX() + maxRange, this.pos.getY() + maxRange, this.pos.getZ() + maxRange));
    for (PlayerEntity player : players) {
      if (mustCrouch && !player.isCrouching()) {
        continue; //check the next one
      }
      //am i looking
      Vector3d positionEyes = player.getEyePosition(1F);
      Vector3d look = player.getLook(1F);
      //take the player eye position. draw a vector from the eyes, in the direction they are looking
      //of LENGTH equal to the range
      Vector3d visionWithLength = positionEyes.add(look.x * maxRange, look.y * maxRange, look.z * maxRange);
      //ray trayce from eyes, along the vision vec
      BlockRayTraceResult rayTrace = this.world.rayTraceBlocks(new RayTraceContext(positionEyes, visionWithLength, RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.NONE, player));
      if (this.pos.equals(rayTrace.getPos())) {
        //at least one is enough, stop looping
        return player;
      }
    }
    return null;
  }

  public void tryDumpFakePlayerInvo(WeakReference<FakePlayer> fp, boolean includeMainHand) {
    int start = (includeMainHand) ? 0 : 1;
    ArrayList<ItemStack> toDrop = new ArrayList<ItemStack>();
    for (int i = start; i < fp.get().inventory.mainInventory.size(); i++) {
      ItemStack s = fp.get().inventory.mainInventory.get(i);
      if (s.isEmpty() == false) {
        toDrop.add(s.copy());
        fp.get().inventory.mainInventory.set(i, ItemStack.EMPTY);
      }
    }
    UtilItemStack.drop(this.world, this.pos.up(), toDrop);
  }

  public static void tryEquipItem(ItemStack item, WeakReference<FakePlayer> fp, Hand hand) {
    if (fp == null) {
      return;
    }
    fp.get().setHeldItem(hand, item);
  }

  public static void syncEquippedItem(LazyOptional<IItemHandler> i, WeakReference<FakePlayer> fp, int slot, Hand hand) {
    if (fp == null) {
      return;
    }
    i.ifPresent(inv -> {
      inv.extractItem(slot, 64, false); //delete and overwrite
      inv.insertItem(slot, fp.get().getHeldItem(hand), false);
    });
  }

  public static void tryEquipItem(LazyOptional<IItemHandler> i, WeakReference<FakePlayer> fp, int slot, Hand hand) {
    if (fp == null) {
      return;
    }
    i.ifPresent(inv -> {
      ItemStack maybeTool = inv.getStackInSlot(0);
      if (!maybeTool.isEmpty()) {
        if (maybeTool.getCount() <= 0) {
          maybeTool = ItemStack.EMPTY;
        }
      }
      if (!maybeTool.equals(fp.get().getHeldItem(hand))) {
        fp.get().setHeldItem(hand, maybeTool);
      }
    });
  }

  public static ActionResultType leftClickBlock(final WeakReference<FakePlayer> fakePlayerWeakReference,
                                                final BlockPos targetPos, final Direction facing) {
    final FakePlayer fakePlayer = fakePlayerWeakReference.get();
    if (fakePlayer == null) {
      return ActionResultType.FAIL;
    }
    final Direction placementOn = (facing == null) ? fakePlayer.getAdjustedHorizontalFacing() : facing;
    final BlockRayTraceResult blockRayTraceResult = new BlockRayTraceResult(fakePlayer.getLookVec(), placementOn, targetPos, true);
    try {
      fakePlayer.interactionManager.func_225416_a(blockRayTraceResult.getPos(), Action.START_DESTROY_BLOCK, facing, 0);
      return ActionResultType.SUCCESS;
    }
    catch (Exception e) {
      return ActionResultType.FAIL;
    }
  }

  public static ActionResultType rightClickBlock(WeakReference<FakePlayer> fakePlayer,
      World world, BlockPos targetPos, Hand hand, Direction facing) throws Exception {
    if (fakePlayer == null) {
      return ActionResultType.FAIL;
    }
    Direction placementOn = (facing == null) ? fakePlayer.get().getAdjustedHorizontalFacing() : facing;
    BlockRayTraceResult blockraytraceresult = new BlockRayTraceResult(
        fakePlayer.get().getLookVec(), placementOn,
        targetPos, true);
    //processRightClick
    ActionResultType result = fakePlayer.get().interactionManager.func_219441_a(fakePlayer.get(), world,
        fakePlayer.get().getHeldItem(hand), hand, blockraytraceresult);
    //it becomes CONSUME result 1 bucket. then later i guess it doesnt save, and then its water_bucket again
    return result;
  }

  public static boolean tryHarvestBlock(WeakReference<FakePlayer> fakePlayer, World world, BlockPos targetPos) {
    if (fakePlayer == null) {
      return false;
    }
    return fakePlayer.get().interactionManager.tryHarvestBlock(targetPos);
  }

  public WeakReference<FakePlayer> setupBeforeTrigger(ServerWorld sw, String name, UUID uuid) {
    WeakReference<FakePlayer> fakePlayer = UtilFakePlayer.initFakePlayer(sw, uuid, name);
    if (fakePlayer == null) {
      ModCyclic.LOGGER.error("Fake player failed to init " + name + " " + uuid);
      return null;
    }
    //fake player facing the same direction as tile. for throwables
    fakePlayer.get().setPosition(this.getPos().getX(), this.getPos().getY(), this.getPos().getZ()); //seems to help interact() mob drops like milk
    fakePlayer.get().rotationYaw = UtilEntity.getYawFromFacing(this.getCurrentFacing());
    return fakePlayer;
  }

  public WeakReference<FakePlayer> setupBeforeTrigger(ServerWorld sw, String name) {
    return setupBeforeTrigger(sw, name, UUID.randomUUID());
  }

  public void setLitProperty(boolean lit) {
    BlockState st = this.getBlockState();
    if (!st.hasProperty(BlockBase.LIT)) {
      return;
    }
    boolean previous = st.get(BlockBreaker.LIT);
    if (previous != lit) {
      this.world.setBlockState(pos, st.with(BlockBreaker.LIT, lit));
    }
  }

  public Direction getCurrentFacing() {
    if (this.getBlockState().hasProperty(BlockStateProperties.FACING)) {
      return this.getBlockState().get(BlockStateProperties.FACING);
    }
    if (this.getBlockState().hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {
      return this.getBlockState().get(BlockStateProperties.HORIZONTAL_FACING);
    }
    return null;
  }

  @Override
  public CompoundNBT getUpdateTag() {
    //thanks http://www.minecraftforge.net/forum/index.php?topic=39162.0
    CompoundNBT syncData = new CompoundNBT();
    this.write(syncData); //this calls writeInternal
    return syncData;
  }

  protected BlockPos getCurrentFacingPos(int distance) {
    Direction f = this.getCurrentFacing();
    if (f != null) {
      return this.pos.offset(f, distance);
    }
    return this.pos;
  }

  protected BlockPos getCurrentFacingPos() {
    return getCurrentFacingPos(1);
  }

  @Override
  public void onDataPacket(net.minecraft.network.NetworkManager net, net.minecraft.network.play.server.SUpdateTileEntityPacket pkt) {
    this.read(this.getBlockState(), pkt.getNbtCompound());
    super.onDataPacket(net, pkt);
  }

  @Override
  public SUpdateTileEntityPacket getUpdatePacket() {
    return new SUpdateTileEntityPacket(this.pos, 1, getUpdateTag());
  }

  public boolean isPowered() {
    return this.getWorld().isBlockPowered(this.getPos());
  }

  public int getRedstonePower() {
    return this.getWorld().getRedstonePowerFromNeighbors(this.getPos());
  }

  public boolean requiresRedstone() {
    return this.needsRedstone == 1;
  }

  public void moveFluids(Direction myFacingDir, BlockPos posTarget, int toFlow, IFluidHandler tank) {
    if (tank == null || tank.getFluidInTank(0).isEmpty()) {
      return;
    }
    final Direction themFacingMe = myFacingDir.getOpposite();
    UtilFluid.tryFillPositionFromTank(world, posTarget, themFacingMe, tank, toFlow);
  }

  public void tryExtract(IItemHandler myself, Direction extractSide, int qty, ItemStackHandler nullableFilter) {
    if (qty <= 0) {
      return;
    }
    final ItemStack stackInSlot = myself.getStackInSlot(0);
    if (!stackInSlot.isEmpty()) {
      return;
    }
    if (extractSide == null) {
      return;
    }
    final BlockPos posTarget = pos.offset(extractSide);
    final TileEntity tile = world.getTileEntity(posTarget);
    if (tile == null) {
      return;
    }
    final IItemHandler itemHandlerFrom = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, extractSide.getOpposite()).orElse(null);
    if (itemHandlerFrom == null) {
      return;
    }
    final int slotLimit = Math.min(myself.getSlotLimit(0), qty);
    for (int slot = 0; slot < itemHandlerFrom.getSlots(); slot++) {
      final ItemStack itemStackToExtract = itemHandlerFrom.getStackInSlot(slot);
      if (itemStackToExtract.isEmpty()) {
        continue;
      }
      if (nullableFilter != null &&
          !FilterCardItem.filterAllowsExtract(nullableFilter.getStackInSlot(0), itemStackToExtract)) {
        continue;
      }
      //find the theoretical maximum we can extract
      int limit = Math.min(itemStackToExtract.getCount(), itemStackToExtract.getMaxStackSize());
      limit = Math.min(limit, slotLimit);
      //check there is anything to extract
      if (limit <= 0) {
        continue;
      }
      final ItemStack extractedItemStack = itemHandlerFrom.extractItem(slot, limit, false);
      if (extractedItemStack.isEmpty()) {
        continue;
      }
      ItemStack remainderItemStack = myself.insertItem(0, extractedItemStack, false);
      //sanity check
      if (!remainderItemStack.isEmpty()) {
        ModCyclic.LOGGER.error("Incorrect number of items extracted, have to re-insert " + remainderItemStack);
        remainderItemStack = itemHandlerFrom.insertItem(slot, remainderItemStack, false);
        if (!remainderItemStack.isEmpty()) {
          ModCyclic.LOGGER.error("Incorrect number of items extracted and now unable to re-insert, " + remainderItemStack + " have been lost");
        }
      }
      return;
    }
  }

  public boolean moveItems(Direction myFacingDir, int max, IItemHandler handlerHere) {
    return moveItems(myFacingDir, pos.offset(myFacingDir), max, handlerHere, 0);
  }

  public boolean moveItems(Direction myFacingDir, BlockPos posTarget, int max, IItemHandler handlerHere, int theslot) {
    if (max <= 0) {
      return false;
    }
    if (this.world.isRemote()) {
      return false;
    }
    if (handlerHere == null) {
      return false;
    }
    //first get the original ItemStack as creating new ones is expensive
    final ItemStack originalItemStack = handlerHere.getStackInSlot(theslot);
    if (originalItemStack.isEmpty()) {
      return false;
    }
    final Direction themFacingMe = myFacingDir.getOpposite();
    final TileEntity tileTarget = world.getTileEntity(posTarget);
    if (tileTarget == null) {
      return false;
    }
    final IItemHandler handlerOutput = tileTarget
        .getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, themFacingMe)
        .orElse(null);
    if (handlerOutput == null) {
      return false;
    }
    final int maxStackSize = originalItemStack.getMaxStackSize();
    final int originalCount = originalItemStack.getCount();
    int remainingItemCount = originalCount;
    ItemStack itemStackToInsert = null;
    //attempt to push into output
    for (int slot = 0; slot < handlerOutput.getSlots(); slot++) {
      //find the theoretical maximum we can insert
      int limit = Math.min(handlerOutput.getSlotLimit(slot), maxStackSize);
      int amountInOutputSlot = 0;
      //reduce limit by amount already in the output slot
      final ItemStack stackInOutputSlot = handlerOutput.getStackInSlot(slot);
      if (!stackInOutputSlot.isEmpty()) {
        //if the output slot is not compatible then skip this slot
        if (!ItemHandlerHelper.canItemStacksStack(originalItemStack, stackInOutputSlot)) {
          continue;
        }
        amountInOutputSlot = stackInOutputSlot.getCount();
        limit -= amountInOutputSlot;
      }
      limit = Math.min(limit, remainingItemCount);
      limit = Math.min(limit, max);
      //skip this output slot if we cannot insert more
      if (limit <= 0) {
        continue;
      }
      //lazily create an ItemStack for inserting and re-use it for future inserts
      if (itemStackToInsert == null) {
        itemStackToInsert = originalItemStack.copy();
      }
      itemStackToInsert.setCount(limit);
      //only perform an insert (even simulated) if required as it creates at least one new ItemStack in the process
      final ItemStack remainderItemStack = handlerOutput.insertItem(slot, itemStackToInsert, false);
      //check the remainder to calculate how much was actually inserted
      remainingItemCount -= (limit - remainderItemStack.getCount());
      if (remainingItemCount <= 0) {
        break;
      }
    }
    final int insertedItemCount = originalCount - remainingItemCount;
    final boolean didInsertItems = insertedItemCount > 0;
    if (didInsertItems) {
      //only perform an extraction if required as it creates a new ItemStack in the process
      final ItemStack extractedItemStack = handlerHere.extractItem(theslot, insertedItemCount, false);
      //sanity check
      if (extractedItemStack.getCount() != insertedItemCount) {
        ModCyclic.LOGGER.error("Imbalance moving items, extracted " + extractedItemStack + " inserted " + insertedItemCount);
      }
    }
    return didInsertItems;
  }

  protected boolean moveEnergy(Direction myFacingDir, int quantity) {
    return moveEnergy(myFacingDir, pos.offset(myFacingDir), quantity);
  }

  protected boolean moveEnergy(final Direction myFacingDir, final BlockPos posTarget, final int quantity) {
    if (quantity <= 0) {
      return false;
    }
    if (this.world.isRemote) {
      return false; //important to not desync cables 
    }
    final IEnergyStorage handlerHere = this.getCapability(CapabilityEnergy.ENERGY, myFacingDir).orElse(null);
    if (handlerHere == null) {
      return false;
    }
    final Direction themFacingMe = myFacingDir.getOpposite();
    final TileEntity tileTarget = world.getTileEntity(posTarget);
    if (tileTarget == null) {
      return false;
    }
    final IEnergyStorage handlerOutput = tileTarget.getCapability(CapabilityEnergy.ENERGY, themFacingMe).orElse(null);
    if (handlerOutput == null) {
      return false;
    }
    final int capacity = handlerOutput.getMaxEnergyStored() - handlerOutput.getEnergyStored();
    if (capacity <= 0) {
      return false;
    }
    //first simulate
    final int drain = handlerHere.extractEnergy(Math.min(quantity, capacity), true);
    if (drain <= 0) {
      return false;
    }
    //now push it into output, but find out what was ACTUALLY taken
    final int filled = handlerOutput.receiveEnergy(drain, false);
    if (filled <= 0) {
      return false;
    }
    //now actually drain that much from here
    final int drained = handlerHere.extractEnergy(filled, false);
    //sanity check
    if (drained != filled) {
      ModCyclic.LOGGER.error("Imbalance moving energy, extracted " + drained + " received " + filled);
    }
    if (tileTarget instanceof TileCableEnergy) {
      // not so compatible with other fluid systems. it will do i guess
      TileCableEnergy cable = (TileCableEnergy) tileTarget;
      cable.updateIncomingEnergyFace(themFacingMe);
    }
    return true;
  }

  @Override
  public void read(BlockState bs, CompoundNBT tag) {
    flowing = tag.getInt("flowing");
    needsRedstone = tag.getInt("needsRedstone");
    render = tag.getInt("renderParticles");
    timer = tag.getInt("timer");
    super.read(bs, tag);
  }

  @Override
  public CompoundNBT write(CompoundNBT tag) {
    tag.putInt("flowing", flowing);
    tag.putInt("needsRedstone", needsRedstone);
    tag.putInt("renderParticles", render);
    tag.putInt("timer", timer);
    return super.write(tag);
  }

  public abstract void setField(int field, int value);

  public abstract int getField(int field);

  public void setNeedsRedstone(int value) {
    this.needsRedstone = value % 2;
  }

  public FluidStack getFluid() {
    return FluidStack.EMPTY;
  }

  public void setFluid(FluidStack fluid) {}

  /************************** IInventory needed for IRecipe **********************************/
  @Deprecated
  @Override
  public int getSizeInventory() {
    return 0;
  }

  @Deprecated
  @Override
  public boolean isEmpty() {
    return true;
  }

  @Deprecated
  @Override
  public ItemStack getStackInSlot(int index) {
    return ItemStack.EMPTY;
  }

  @Deprecated
  @Override
  public ItemStack decrStackSize(int index, int count) {
    return ItemStack.EMPTY;
  }

  @Deprecated
  @Override
  public ItemStack removeStackFromSlot(int index) {
    return ItemStack.EMPTY;
  }

  @Deprecated
  @Override
  public void setInventorySlotContents(int index, ItemStack stack) {}

  @Deprecated
  @Override
  public boolean isUsableByPlayer(PlayerEntity player) {
    return false;
  }

  @Deprecated
  @Override
  public void clear() {}

  public void setFieldString(int field, String value) {
    //for string field  
  }

  public String getFieldString(int field) {
    //for string field  
    return null;
  }

  public int getEnergy() {
    return this.getCapability(CapabilityEnergy.ENERGY).map(IEnergyStorage::getEnergyStored).orElse(0);
  }

  public void setEnergy(int value) {
    IEnergyStorage energy = this.getCapability(CapabilityEnergy.ENERGY).orElse(null);
    if (energy != null && energy instanceof CustomEnergyStorage) {
      ((CustomEnergyStorage) energy).setEnergy(value);
    }
  }

  //fluid tanks have 'onchanged', energy caps do not
  protected void syncEnergy() {
    //skip if clientside
    if (world.isRemote || world.getGameTime() % 20 != 0) {
      return;
    }
    final IEnergyStorage energy = this.getCapability(CapabilityEnergy.ENERGY).orElse(null);
    if (energy == null) {
      return;
    }
    final PacketEnergySync packetEnergySync = new PacketEnergySync(this.getPos(), energy.getEnergyStored());
    PacketRegistry.sendToAllClients(world, packetEnergySync);
  }

  public void exportEnergyAllSides() {
    for (final Direction exportToSide : UtilDirection.getAllInDifferentOrder()) {
      moveEnergy(exportToSide, MENERGY / 2);
    }
  }
}
