package com.terminatr.phycology.common.core;

import com.terminatr.phycology.Phycology;
import com.terminatr.phycology.client.inventory.FilterMachineScreen;
import com.terminatr.phycology.common.blocks.FilterMachineBlock;
import com.terminatr.phycology.common.inventory.FilterMachineContainer;
import com.terminatr.phycology.common.tileentity.FilterMachineTileEntity;
import com.terminatr.phycology.common.util.BlockNames;
import com.terminatr.phycology.common.util.ContainerNames;
import com.terminatr.phycology.common.util.TileEntityNames;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import java.util.function.Supplier;


public class PhycologyBlocks {

    public static Item.Properties itemProperties;

    @ObjectHolder(BlockNames.FILTER_MACHINE)
    public static FilterMachineBlock filtermachine_block;

    @ObjectHolder(TileEntityNames.FILTER_MACHINE)
    public static TileEntityType<?> filtermachine_tileentity;

    @ObjectHolder(ContainerNames.FILTER_MACHINE)
    public static ContainerType<FilterMachineContainer> filtermachine_container;

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

            itemProperties = (new Item.Properties().group(ItemGroup.BUILDING_BLOCKS));

            itemRegistry.register(new BlockItem(filtermachine_block, itemProperties
                    ).setRegistryName(filtermachine_block.getRegistryName())
            );
        }

        @SubscribeEvent
        public static void registerTileEntities(RegistryEvent.Register<TileEntityType<?>> event) {

            IForgeRegistry<TileEntityType<?>> tileEntityRegistry = event.getRegistry();

            tileEntityRegistry.register(TileEntityType.Builder.create((Supplier<TileEntity>) FilterMachineTileEntity::new,
                    PhycologyBlocks.filtermachine_block).build(null).setRegistryName(TileEntityNames.FILTER_MACHINE));
        }

        @SubscribeEvent
        public static void registerContainers(RegistryEvent.Register<ContainerType<?>> event) {

            IForgeRegistry<ContainerType<?>> containerRegistry = event.getRegistry();

            containerRegistry.register(new ContainerType<>(FilterMachineContainer::createFilterMachineContainer
            ).setRegistryName(ContainerNames.FILTER_MACHINE));
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerScreenFactories() {

        ScreenManager.registerFactory(filtermachine_container, FilterMachineScreen::new);
    }
}
