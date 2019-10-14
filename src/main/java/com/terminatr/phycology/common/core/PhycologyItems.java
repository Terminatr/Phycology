package com.terminatr.phycology.common.core;

import com.terminatr.phycology.Phycology;
import net.minecraft.item.*;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;


public class PhycologyItems {

    public PhycologyItems() {

    }

    @Mod.EventBusSubscriber(modid = Phycology.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class Registration {

        @SubscribeEvent
        public static void registerItems(RegistryEvent.Register<Item> event) {

            IForgeRegistry<Item> itemRegistry = event.getRegistry();
        }
    }
}
