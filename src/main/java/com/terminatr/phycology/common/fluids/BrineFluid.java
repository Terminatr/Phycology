package com.terminatr.phycology.common.fluids;

import net.minecraft.block.Block;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.RegistryObject;

import static com.terminatr.phycology.common.core.PhycologyFluids.*;


public class BrineFluid {

    public static final ResourceLocation FLUID_STILL = new ResourceLocation("minecraft:block/brown_mushroom_block");
    public static final ResourceLocation FLUID_FLOWING = new ResourceLocation("minecraft:block/mushroom_stem");

    public static RegistryObject<FlowingFluid> BRINE = FLUIDS.register("brine", () -> new ForgeFlowingFluid.Source(BrineFluid.brineProperties));
    public static RegistryObject<FlowingFluid> BRINE_FLOWING = FLUIDS.register("brine_flowing", () -> new ForgeFlowingFluid.Flowing(BrineFluid.brineProperties));

    public static RegistryObject<FlowingFluidBlock> BRINE_BLOCK = BLOCKS.register("brine_block", () -> new FlowingFluidBlock(
            BRINE,
            Block.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100.0F).noDrops()));

    public static RegistryObject<Item> BRINE_BUCKET = ITEMS.register("brine_bucket", () -> new BucketItem(
            BRINE,
            new Item.Properties().containerItem(Items.BUCKET).maxStackSize(1).group(ItemGroup.MISC)));

    public static final ForgeFlowingFluid.Properties brineProperties =
            new ForgeFlowingFluid.Properties(
                    BRINE,
                    BRINE_FLOWING,
                    FluidAttributes.builder(FLUID_STILL, FLUID_FLOWING).color(0xAACC9933)).bucket(BRINE_BUCKET).block(BRINE_BLOCK);

    public BrineFluid() {

    }
}
