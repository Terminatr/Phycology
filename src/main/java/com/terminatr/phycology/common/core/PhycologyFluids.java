package com.terminatr.phycology.common.core;

import com.terminatr.phycology.Phycology;
import com.terminatr.phycology.common.fluids.BrineFluid;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;


public class PhycologyFluids {

    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, Phycology.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, Phycology.MOD_ID);
    public static final DeferredRegister<Fluid> FLUIDS = new DeferredRegister<>(ForgeRegistries.FLUIDS, Phycology.MOD_ID);

    public static BrineFluid brine_fluid = new BrineFluid();

    public PhycologyFluids() {

    }

    @Mod.EventBusSubscriber(modid = Phycology.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class Registration {

        @SubscribeEvent
        public static void registerFluids(RegistryEvent.Register<Fluid> event) {

            IForgeRegistry<Fluid> fluidRegistry = event.getRegistry();
        }
    }
}
