package com.terminatr.phycology.common.tileentity;

import com.terminatr.phycology.common.core.PhycologyBlocks;
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

    public enum FilterMachineData {

        PROCESSING_TIME,
        PROCESSING_TIME_TOTAL,

        WATER_LEVEL_MIN,
        WATER_LEVEL_MAX,
        WATER_LEVEL,

        BRINE_LEVEL_MIN,
        BRINE_LEVEL_MAX,
        BRINE_LEVEL
    }

    private int processingTime = 0;
    private final int processingTimeTotal = 200;

    private final int waterLevelMin = 0;
    private final int waterLevelMax = 10;
    private int waterLevel = waterLevelMin;

    private final int brineLevelMin = 0;
    private final int brineLevelMax = 10;
    private int brineLevel = brineLevelMin;

    protected final IIntArray filtermachineData = new IIntArray() {

        public int get(int index) {

            FilterMachineData[] dataValues = FilterMachineData.values();

            FilterMachineData data = dataValues[index];

            switch (data) {

                case PROCESSING_TIME:
                    return FilterMachineTileEntity.this.processingTime;

                case PROCESSING_TIME_TOTAL:
                    return FilterMachineTileEntity.this.processingTimeTotal;

                case WATER_LEVEL:
                    return FilterMachineTileEntity.this.waterLevel;

                case WATER_LEVEL_MAX:
                    return FilterMachineTileEntity.this.waterLevelMax;

                case BRINE_LEVEL:
                    return FilterMachineTileEntity.this.brineLevel;

                default:
                    return -1;
            }
        }

        public void set(int index, int value) {

            /*switch (index) {

                case 0:
                    FilterMachineTileEntity.this.processingTime = value;
                    break;

                case 1:
                    FilterMachineTileEntity.this.processingTimeTotal = value;
                    break;

                case 2:
                    FilterMachineTileEntity.this.waterLevel = value;
            }*/

        }

        public int size() { return FilterMachineData.values().length; }
    };

    protected FilterMachineTileEntity(TileEntityType<?> typeIn) {

        super(typeIn);

        this.machineContents = NonNullList.<ItemStack>withSize(FilterMachineContainer.FilterMachineSlots.values().length, ItemStack.EMPTY);
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

        this.machineContents = NonNullList.<ItemStack>withSize(FilterMachineContainer.FilterMachineSlots.values().length, ItemStack.EMPTY);

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
            waterLevel = compound.getInt("water");
            brineLevel = compound.getInt("brine");
        }
    }

    @Override
    public CompoundNBT write(CompoundNBT compound)
    {
        super.write(compound);

        if (!this.checkLootAndWrite(compound))
        {
            ItemStackHelper.saveAllItems(compound, this.machineContents);
            compound.putInt("water", waterLevel);
            compound.putInt("brine", brineLevel);
        }

        return compound;
    }

    @Override
    public void tick() {

        if (!this.world.isRemote) {

            ItemStack itemstack = this.machineContents.get(FilterMachineContainer.FilterMachineSlots.WATER_INPUT.ordinal());

            if (!itemstack.isEmpty() && itemstack.isItemEqual(new ItemStack(Items.WATER_BUCKET)) && canAddWater()) {

                this.waterLevel++;

                this.machineContents.set(FilterMachineContainer.FilterMachineSlots.WATER_INPUT.ordinal(), ItemStack.EMPTY);
                this.machineContents.set(FilterMachineContainer.FilterMachineSlots.WATER_OUTPUT.ordinal(), new ItemStack(Items.BUCKET));

                /*this.processingTime++;

                if (this.processingTime >= this.processingTimeTotal) {

                    this.machineContents.set(1, new ItemStack(BrineFluid.BRINE_BUCKET.get()));
                    this.machineContents.set(0, ItemStack.EMPTY);
                    this.processingTime = 0;
                }*/
            } else if (!itemstack.isEmpty() && itemstack.isItemEqual(new ItemStack(Items.BUCKET)) && canRemoveWater()) {

                this.waterLevel--;

                this.machineContents.set(FilterMachineContainer.FilterMachineSlots.WATER_INPUT.ordinal(), ItemStack.EMPTY);
                this.machineContents.set(FilterMachineContainer.FilterMachineSlots.WATER_OUTPUT.ordinal(), new ItemStack(Items.WATER_BUCKET));
            } else {

                this.processingTime = 0;
            }
        }
    }

    private boolean canAddWater() {

        if (this.waterLevel >= this.waterLevelMax) {

            this.waterLevel = this.waterLevelMax;
            return false;
        } else {

            return true;
        }
    }

    private boolean canRemoveWater() {

        if (this.waterLevel <= this.waterLevelMin) {

            this.waterLevel = this.waterLevelMin;
            return false;
        } else {

            return true;
        }
    }

    private boolean canAddBrine() {

        if (this.brineLevel >= this.brineLevelMax) {

            this.brineLevel = this.brineLevelMax;
            return false;
        } else {

            return true;
        }
    }

    private boolean canRemoveBrine() {

        if (this.brineLevel <= this.brineLevelMin) {

            this.brineLevel = this.brineLevelMin;
            return false;
        } else {

            return true;
        }
    }
}
