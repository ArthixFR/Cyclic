package com.lothrazar.cyclic.base;

import com.lothrazar.cyclic.registry.ItemRegistry;
import com.lothrazar.cyclic.util.UtilItemStack;
import java.util.List;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ItemBase extends Item {

  public static final float INACCURACY_DEFAULT = 1.0F;
  public static final float VELOCITY_MAX = 1.5F;

  public ItemBase(Properties properties) {
    super(properties);
    ItemRegistry.items.add(this);
  }

  protected void shootMe(World world, PlayerEntity shooter, ProjectileItemEntity ball, float pitch, float velocityFactor) {
    if (world.isRemote) {
      return;
    }
    Vector3d vector3d1 = shooter.getUpVector(1.0F);
    // pitch is degrees so can be -10, +10, etc
    Quaternion quaternion = new Quaternion(new Vector3f(vector3d1), pitch, true);
    Vector3d vector3d = shooter.getLook(1.0F);
    Vector3f vector3f = new Vector3f(vector3d);
    vector3f.transform(quaternion);
    ball.shoot(vector3f.getX(), vector3f.getY(), vector3f.getZ(), velocityFactor * VELOCITY_MAX, INACCURACY_DEFAULT);
    //    worldIn.playSound(null, player.getPosX(), player.getPosY(), player.getPosZ(),
    //        SoundEvents.ENTITY_ENDER_PEARL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
    world.addEntity(ball);
  }

  protected ItemStack findAmmo(PlayerEntity player, Item item) {
    for (int i = 0; i < player.inventory.getSizeInventory(); ++i) {
      ItemStack itemstack = player.inventory.getStackInSlot(i);
      if (itemstack.getItem() == item) {
        return itemstack;
      }
    }
    return ItemStack.EMPTY;
  }

  public void tryRepairWith(ItemStack stackToRepair, PlayerEntity player, Item target) {
    if (stackToRepair.isDamaged()) {
      ItemStack torches = this.findAmmo(player, target);
      if (!torches.isEmpty()) {
        torches.shrink(1);
        UtilItemStack.repairItem(stackToRepair);
      }
    }
  }

  public float getChargedPercent(ItemStack stack, int chargeTimer) {
    return BowItem.getArrowVelocity(this.getUseDuration(stack) - chargeTimer);
  }

  @Override
  @OnlyIn(Dist.CLIENT)
  public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
    tooltip.add(new TranslationTextComponent(getTranslationKey() + ".tooltip").mergeStyle(TextFormatting.GRAY));
  }

  @OnlyIn(Dist.CLIENT)
  public void registerClient() {}
}
