package com.terminatr.phycology.common.inventory;

import com.terminatr.phycology.common.core.PhycologyBlocks;
import com.terminatr.phycology.common.tileentity.FilterMachineTileEntity;
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

    public enum FilterMachineSlots {

        WATER_INPUT(36, 44),
        WATER_OUTPUT(36, 76),

        BRINE_INPUT(132, 44),
        BRINE_OUTPUT(132, 76),

        CATALYST(84, 44),
        MACHINE_OUTPUT(84, 76);

        private int xPosition;
        private int yPosition;

        FilterMachineSlots(int x, int y) {

            this.xPosition = x;
            this.yPosition = y;
        }
    }

    private final int playerHotbarSize = 9;
    private final int playerInventorySize = 27;
    private final int playerHotbarStartIndex = FilterMachineSlots.values().length;
    private final int playerInventoryStartIndex = playerHotbarStartIndex + playerHotbarSize;

    public static FilterMachineContainer createFilterMachineContainer(int windowId, PlayerInventory playerInventory) {

        return new FilterMachineContainer(
                PhycologyBlocks.filtermachine_container,
                windowId,
                playerInventory,
                new Inventory(FilterMachineSlots.values().length),
                new IntArray(FilterMachineTileEntity.FilterMachineData.values().length));
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

    private FilterMachineContainer(ContainerType<?> containerType, int windowId, PlayerInventory playerInventory, IInventory inventory, IIntArray filtermachineData) {

        super(containerType, windowId);
        assertInventorySize(inventory, FilterMachineSlots.values().length);
        assertIntArraySize(filtermachineData, FilterMachineTileEntity.FilterMachineData.values().length);

        this.inventory = inventory;
        this.filtermachineData = filtermachineData;

        inventory.openInventory(playerInventory.player);

        for (FilterMachineSlots slot : FilterMachineSlots.values()) {

            this.addSlot(new Slot(inventory, slot.ordinal(), slot.xPosition, slot.yPosition));
        }

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

        int progress = this.filtermachineData.get(FilterMachineTileEntity.FilterMachineData.PROCESSING_TIME.ordinal());
        int progressTotal = this.filtermachineData.get(FilterMachineTileEntity.FilterMachineData.PROCESSING_TIME_TOTAL.ordinal());

        return progressTotal != 0 && progress != 0 ? progress * 17 / progressTotal : 0;
    }

    @OnlyIn(Dist.CLIENT)
    public int getWaterLevelScaled() {

        int waterLevel = this.filtermachineData.get(FilterMachineTileEntity.FilterMachineData.WATER_LEVEL.ordinal());
        int waterLevelMax = this.filtermachineData.get(FilterMachineTileEntity.FilterMachineData.WATER_LEVEL_MAX.ordinal());

        return waterLevelMax != 0 && waterLevel != 0 ? waterLevel * 62 / waterLevelMax : 0;
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
        Slot activeSlot = this.inventorySlots.get(index);

        if (activeSlot != null && activeSlot.getHasStack())
        {
            ItemStack itemstack1 = activeSlot.getStack();
            itemstack = itemstack1.copy();

            if (index < playerHotbarStartIndex) {

                if (!this.mergeItemStack(itemstack1, playerHotbarStartIndex, this.inventorySlots.size(), false)) {

                    return ItemStack.EMPTY;
                }

                activeSlot.onSlotChange(itemstack1, itemstack);
            } else if (index >= playerHotbarStartIndex) {

                if (!this.mergeItemStack(itemstack1, 0, playerHotbarStartIndex, false)) {

                    return ItemStack.EMPTY;
                }
            }

            if (itemstack1.isEmpty()) {

                activeSlot.putStack(ItemStack.EMPTY);
            } else {

                activeSlot.onSlotChanged();
            }
        }

        return itemstack;
    }
}
