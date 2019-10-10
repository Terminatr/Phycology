package com.terminatr.phycology.common.core;

import com.terminatr.phycology.Phycology;
import com.terminatr.phycology.common.fluids.BrineFluid;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;


public class PhycologyFluids {

    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, Phycology.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, Phycology.MOD_ID);
    public static final DeferredRegister<Fluid> FLUIDS = new DeferredRegister<>(ForgeRegistries.FLUIDS, Phycology.MOD_ID);

    public static final BrineFluid brine_fluid = new BrineFluid();

    public PhycologyFluids() {

    }
}
