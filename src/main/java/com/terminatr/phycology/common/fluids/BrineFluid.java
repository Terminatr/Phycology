package com.terminatr.phycology.common.fluids;

import com.terminatr.phycology.common.core.PhycologyFluids;
import net.minecraft.block.Block;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.*;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.RegistryObject;


public class BrineFluid {

    public static final ResourceLocation FLUIDSTILL = new ResourceLocation("minecraft:block/brown_mushroom_block");
    public static final ResourceLocation FLUIDFLOWING = new ResourceLocation("minecraft:block/mushroom_stem");

    public static RegistryObject<FlowingFluid> brine = PhycologyFluids.FLUIDS.register("brine", () -> new ForgeFlowingFluid.Source(BrineFluid.brineProperties));;
    public static RegistryObject<FlowingFluid> brine_flowing = PhycologyFluids.FLUIDS.register("brine_flowing", () -> new ForgeFlowingFluid.Flowing(BrineFluid.brineProperties));;

    public static RegistryObject<FlowingFluidBlock> brine_block = PhycologyFluids.BLOCKS.register("brine_block", () -> new FlowingFluidBlock(brine, BrineFluid.blockProperties));

    public static RegistryObject<Item> BRINE_BUCKET = PhycologyFluids.ITEMS.register("brine_bucket", () -> new BucketItem(brine, BrineFluid.bucketProperties));

    public static final ForgeFlowingFluid.Properties brineProperties =
            new ForgeFlowingFluid.Properties(
                    brine,
                    brine_flowing,
                    FluidAttributes.builder(FLUIDSTILL, FLUIDFLOWING).color(0xAACC9933)).bucket(BRINE_BUCKET).block(brine_block);

    public static final Item.Properties bucketProperties = new Item.Properties().containerItem(Items.BUCKET).maxStackSize(1).group(ItemGroup.MISC);

    public static final Block.Properties blockProperties = Block.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100.0F).noDrops();

    public BrineFluid() {

    }
}
