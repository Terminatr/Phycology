package com.terminatr.phycology.common.blocks;

import com.terminatr.phycology.common.tileentity.FilterMachineTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.ContainerBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public class FilterMachineBlock extends Block {

    public FilterMachineBlock(Block.Properties builder) {

        super(builder);
    }

    public TileEntity createNewTileEntity(IBlockReader worldIn) {

        return new FilterMachineTileEntity();
    }
}
