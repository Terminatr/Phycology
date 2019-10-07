package com.terminatr.phycology.common.tileentity;

import com.terminatr.phycology.common.core.PhycologyBlocks;
import com.terminatr.phycology.common.inventory.FilterMachineContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;


public class FilterMachineTileEntity extends LockableLootTileEntity implements INamedContainerProvider {

    private NonNullList<ItemStack> machineContents;

    protected FilterMachineTileEntity(TileEntityType<?> typeIn) {

        super(typeIn);

        this.machineContents = NonNullList.<ItemStack>withSize(54, ItemStack.EMPTY);
    }

    public FilterMachineTileEntity() {

        this(PhycologyBlocks.filtermachine_tileentity);
    }

    @Override
    public int getSizeInventory() {

        return this.getItems().size();
    }

    @Override
    public boolean isEmpty() {

        for (ItemStack itemstack : this.machineContents) {

            if (!itemstack.isEmpty()) {

                return false;
            }
        }

        return true;
    }

    @Override
    public ITextComponent getDisplayName() {

        return new StringTextComponent("FM");
    }

    @Override
    public ITextComponent getDefaultName() {

        return new TranslationTextComponent("container.chest");
    }

    @Override
    public Container createMenu(int windowId, PlayerInventory playerInventory) {

        return FilterMachineContainer.createFilterMachineContainer(windowId, playerInventory, this);
    }

    @Override
    public NonNullList<ItemStack> getItems() {

        return this.machineContents;
    }

    @Override
    public void setItems(NonNullList<ItemStack> itemsIn) {

        System.out.println("setItems");
        System.out.println(this.machineContents);

        this.machineContents = NonNullList.<ItemStack>withSize(54, ItemStack.EMPTY);

        for (int i = 0; i < itemsIn.size(); i++) {

            if (i < this.machineContents.size()) {

                this.getItems().set(i, itemsIn.get(i));
            }
        }
    }

    @Override
    public void read(CompoundNBT compound)
    {
        super.read(compound);

        this.machineContents = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);

        if (!this.checkLootAndRead(compound))
        {
            ItemStackHelper.loadAllItems(compound, this.machineContents);
        }
    }

    @Override
    public CompoundNBT write(CompoundNBT compound)
    {
        super.write(compound);

        if (!this.checkLootAndWrite(compound))
        {
            ItemStackHelper.saveAllItems(compound, this.machineContents);
        }

        return compound;
    }
}
