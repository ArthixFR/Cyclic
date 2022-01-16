package com.lothrazar.cyclic.item.inventorycake;

import com.lothrazar.cyclic.base.ContainerBase;
import com.lothrazar.cyclic.data.Const;
import com.lothrazar.cyclic.data.CyclicFile;
import com.lothrazar.cyclic.event.PlayerDataEvents;
import com.lothrazar.cyclic.registry.ContainerScreenRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerCake extends ContainerBase {

  private CyclicFile datFile;
  private ItemStackHandler mirror;

  public ContainerCake(int id, PlayerInventory playerInventory, PlayerEntity player) {
    super(ContainerScreenRegistry.inventory_cake, id);
    this.playerEntity = player;
    this.playerInventory = playerInventory;
    this.datFile = PlayerDataEvents.getOrCreate(player);
    this.endInv = 3 * 9;
    //copy to this MIRROR inventory
    mirror = new ItemStackHandler(endInv);
    for (int j = 0; j < endInv; j++) {
      mirror.setStackInSlot(j, datFile.inventory.getStackInSlot(j));
      int row = j / 9;
      int col = j % 9;
      int xPos = 8 + col * Const.SQ;
      int yPos = 17 + row * Const.SQ;
      this.addSlot(new SlotItemHandler(mirror, j, xPos, yPos) {

        @Override
        public void onSlotChanged() {
          super.onSlotChanged();
          //sync it up with file system vers
          datFile.inventory.setStackInSlot(this.getSlotIndex(), this.getStack());
        }
      });
    }
    layoutPlayerInventorySlots(8, 84);
  }

  @Override
  public boolean canInteractWith(PlayerEntity playerIn) {
    return true;
  }
}
