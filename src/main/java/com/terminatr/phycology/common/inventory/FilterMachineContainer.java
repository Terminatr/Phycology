package com.terminatr.phycology.common.inventory;

import com.terminatr.phycology.common.core.PhycologyBlocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


public class FilterMachineContainer extends Container {

    private final IInventory inventory;
    private final IIntArray filtermachineData;

    private final int inputSlot = 0;
    private final int outputSlot = 1;

    private final int playerInventorySize = 27;
    private final int playerHotbarSize = 9;

    /*private FilterMachineContainer(ContainerType<?> containerType, int windowId, PlayerInventory playerInventory) {

        this(containerType, windowId, playerInventory, new Inventory(2));
    }*/

    public static FilterMachineContainer createFilterMachineContainer(int windowId, PlayerInventory playerInventory) {

        return new FilterMachineContainer(
                PhycologyBlocks.filtermachine_container,
                windowId,
                playerInventory,
                new Inventory(2),
                new IntArray(2));
    }

    public static FilterMachineContainer createFilterMachineContainer(int windowId, PlayerInventory playerInventory, IInventory inventory, IIntArray filtermachineData)
    {
        return new FilterMachineContainer(
                PhycologyBlocks.filtermachine_container,
                windowId,
                playerInventory,
                inventory,
                filtermachineData);
    }

    public FilterMachineContainer(ContainerType<?> containerType, int windowId, PlayerInventory playerInventory, IInventory inventory, IIntArray filtermachineData) {

        super(containerType, windowId);
        assertInventorySize(inventory, 0);

        this.inventory = inventory;
        this.filtermachineData = filtermachineData;

        inventory.openInventory(playerInventory.player);

        this.addSlot(new Slot(inventory, inputSlot, 57, 73));
        this.addSlot(new Slot(inventory, outputSlot, 111, 73));

        for (int player_hotbar = 0; player_hotbar < playerHotbarSize; player_hotbar++) {

            this.addSlot(new Slot(playerInventory, player_hotbar, 12 + (player_hotbar % 9) * 18, 198 + (player_hotbar / 9) * 18));
        }

        for (int player_inventory = 0; player_inventory < playerInventorySize; player_inventory++) {

            this.addSlot(new Slot(playerInventory, player_inventory + playerHotbarSize, 12 + (player_inventory % 9) * 18, 140 + (player_inventory / 9) * 18));
        }

        this.trackIntArray(filtermachineData);
    }

    @OnlyIn(Dist.CLIENT)
    public int getProcessingProgressScaled() {

        int progress = this.filtermachineData.get(0);
        int progressTotal = this.filtermachineData.get(1);

        return progressTotal != 0 && progress != 0 ? progress * 17 / progressTotal : 0;
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {

        return this.inventory.isUsableByPlayer(playerIn);
    }

    @Override
    public void onContainerClosed(PlayerEntity playerIn)
    {
        super.onContainerClosed(playerIn);
        this.inventory.closeInventory(playerIn);
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index)
    {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index < 2)
            {
                if (!this.mergeItemStack(itemstack1, 2, this.inventorySlots.size(), false))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 0, 2, false))
            {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty())
            {
                slot.putStack(ItemStack.EMPTY);
            }
            else
            {
                slot.onSlotChanged();
            }
        }

        return itemstack;
    }
}
