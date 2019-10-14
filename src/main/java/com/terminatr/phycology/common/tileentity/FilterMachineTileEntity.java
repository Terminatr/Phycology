package com.terminatr.phycology.common.tileentity;

import com.terminatr.phycology.common.core.PhycologyBlocks;
import com.terminatr.phycology.common.fluids.BrineFluid;
import com.terminatr.phycology.common.inventory.FilterMachineContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.IIntArray;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;


public class FilterMachineTileEntity extends LockableLootTileEntity implements INamedContainerProvider, ITickableTileEntity {

    private NonNullList<ItemStack> machineContents;
    private int processingTime = 0;
    private int processingTimeTotal = 200;

    protected final IIntArray filtermachineData = new IIntArray() {

        public int get(int index) {

            switch (index) {
                case 0:
                    return FilterMachineTileEntity.this.processingTime;

                case 1:
                    return FilterMachineTileEntity.this.processingTimeTotal;

                default:
                    return 0;
            }
        }

        public void set(int index, int value) {

            switch (index) {

                case 0:
                    FilterMachineTileEntity.this.processingTime = value;
                    break;

                case 1:
                    FilterMachineTileEntity.this.processingTimeTotal = value;
                    break;
            }

        }

        public int size() { return 2; }
    };

    protected FilterMachineTileEntity(TileEntityType<?> typeIn) {

        super(typeIn);

        this.machineContents = NonNullList.<ItemStack>withSize(2, ItemStack.EMPTY);
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

        return FilterMachineContainer.createFilterMachineContainer(windowId, playerInventory, this, this.filtermachineData);
    }

    @Override
    public NonNullList<ItemStack> getItems() {

        return this.machineContents;
    }

    @Override
    public void setItems(NonNullList<ItemStack> itemsIn) {

        this.machineContents = NonNullList.<ItemStack>withSize(2, ItemStack.EMPTY);

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

    @Override
    public void tick() {

        if (!this.world.isRemote) {

            ItemStack itemstack = this.machineContents.get(0);

            if (!itemstack.isEmpty()) {

                this.processingTime++;
            } else {

                this.processingTime = 0;
            }

            if (itemstack.isItemEqual(new ItemStack(Items.WATER_BUCKET))) {

                if (this.processingTime >= this.processingTimeTotal) {

                    this.machineContents.set(1, new ItemStack(BrineFluid.BRINE_BUCKET.get()));
                    this.machineContents.set(0, ItemStack.EMPTY);
                    this.processingTime = 0;
                }
            }
        }
    }
}
