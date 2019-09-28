package com.terminatr.phycology.init;

import com.terminatr.phycology.Phycology;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(modid = Phycology.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(Phycology.MOD_ID)
public class ModItems {

    public static final Item algae_item = null;

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {

        event.getRegistry().registerAll(

                new Item(new Item.Properties()).setRegistryName(Phycology.MOD_ID, "algae_item"),
                new BlockItem(

                        ModBlocks.algae_block,
                        new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)
                ).setRegistryName(ModBlocks.algae_block.getRegistryName())
        );
    }
}
