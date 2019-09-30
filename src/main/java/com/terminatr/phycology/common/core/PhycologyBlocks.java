package com.terminatr.phycology.common.core;

import com.terminatr.phycology.Phycology;
import com.terminatr.phycology.common.blocks.FilterMachineBlock;
import com.terminatr.phycology.common.util.BlockNames;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;


public class PhycologyBlocks {

    public static Item.Properties itemBuilder;

    @ObjectHolder(BlockNames.FILTER_MACHINE)
    public static FilterMachineBlock filtermachine_block;

    public PhycologyBlocks() {

    }

    @Mod.EventBusSubscriber(modid = Phycology.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class Registration {

        @SubscribeEvent
        public static void registerBlocks(RegistryEvent.Register<Block> event) {

            IForgeRegistry<Block> blockRegistry = event.getRegistry();

            blockRegistry.register(new FilterMachineBlock(Block.Properties
                    .create(Material.IRON).hardnessAndResistance(5)
                    .harvestLevel(2)
                    .harvestTool(ToolType.PICKAXE)
                    ).setRegistryName(BlockNames.FILTER_MACHINE)
            );
        }

        @SubscribeEvent
        public static void registerItems(RegistryEvent.Register<Item> event) {

            IForgeRegistry<Item> itemRegistry = event.getRegistry();

            itemBuilder = (new Item.Properties().group(ItemGroup.BUILDING_BLOCKS));

            itemRegistry.register(new BlockItem(filtermachine_block, itemBuilder
                    ).setRegistryName(filtermachine_block.getRegistryName())
            );
        }
    }
}
