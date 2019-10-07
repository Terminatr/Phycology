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

public class FilterMachineContainer extends Container {

    private final IInventory inventory;

    private FilterMachineContainer(ContainerType<?> containerType, int windowId, PlayerInventory playerInventory) {

        this(containerType, windowId, playerInventory, new Inventory(54));
    }

    public static FilterMachineContainer createFilterMachineContainer(int windowId, PlayerInventory playerInventory) {

        return new FilterMachineContainer(
                PhycologyBlocks.filtermachine_container,
                windowId,
                playerInventory,
                new Inventory(54));
    }

    public static FilterMachineContainer createFilterMachineContainer(int windowId, PlayerInventory playerInventory, IInventory inventory)
    {
        return new FilterMachineContainer(
                PhycologyBlocks.filtermachine_container,
                windowId,
                playerInventory,
                inventory);
    }

    public FilterMachineContainer(ContainerType<?> containerType, int windowId, PlayerInventory playerInventory, IInventory inventory) {

        super(containerType, windowId);
        assertInventorySize(inventory, 1);

        this.inventory = inventory;

        inventory.openInventory(playerInventory.player);

        for (int chestRow = 0; chestRow < 6; chestRow++)
        {
            for (int chestCol = 0; chestCol < 9; chestCol++)
            {
                this.addSlot(new Slot(inventory, chestCol + chestRow * 9, 12 + chestCol * 18, 18 + chestRow * 18));
            }
        }

        for (int i = 0; i < 36; i++) {

            this.addSlot(new Slot(playerInventory, i, 12 + (i % 9) * 18, 140 + (i / 9) * 18));
        }
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

            if (index < 54)
            {
                if (!this.mergeItemStack(itemstack1, 54, this.inventorySlots.size(), true))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 0, 54, false))
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
