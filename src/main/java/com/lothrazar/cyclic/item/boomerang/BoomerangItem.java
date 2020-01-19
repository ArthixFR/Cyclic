package com.lothrazar.cyclic.item.boomerang;

import com.lothrazar.cyclic.base.ItemBase;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class BoomerangItem extends ItemBase {

  private static final float INACCURACY_DEFAULT = 1.0F;
  private static final float PITCHOFFSET = 0.0F;
  private static final float VELOCITY_MAX = 1.5F;
  protected static final float MAX_CHARGE = 9.7F;
  private static final int TICKS_USING = 93000;

  public BoomerangItem(Properties properties) {
    super(properties);
  }

  @Override
  public UseAction getUseAction(ItemStack stack) {
    return UseAction.BOW;
  }

  @Override
  public int getUseDuration(ItemStack stack) {
    return TICKS_USING;//bow has 72000
  }

  @Override
  public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
    ItemStack itemstack = playerIn.getHeldItem(handIn);
    playerIn.setActiveHand(handIn);
    return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
  }

  @Override
  public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entity, int chargeTimer) {
    //
    int charge = this.getUseDuration(stack) - chargeTimer;
    //    System.out.println(" " + charge);
    float percentageCharged = BowItem.getArrowVelocity(charge);//never zero, its from [0.03,1];
    if (percentageCharged < 0.1) {
      return;//not enough force to go with any realistic path 
    }
    float amountCharged = percentageCharged * MAX_CHARGE;
    float velocityFactor = percentageCharged * 1.5F;
    if (entity instanceof PlayerEntity == false) {
      return;
    }
    PlayerEntity player = (PlayerEntity) entity;
    //    BoomerangEntity e = new BoomerangEntity(CyclicRegistry.Entities.boomerang, worldIn);
    //    e.shoot(entity, player.rotationPitch, player.rotationYaw, PITCHOFFSET, velocityFactor * VELOCITY_MAX, INACCURACY_DEFAULT);
    //    worldIn.addEntity(e);
    //    e.setOwner(player);
  }
}
