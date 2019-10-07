package com.terminatr.phycology.common.blocks;

import com.terminatr.phycology.common.tileentity.FilterMachineTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;


public class FilterMachineBlock extends Block {

    public FilterMachineBlock(Block.Properties properties) {

        super(properties);
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader worldIn) {

        return new FilterMachineTileEntity();
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {

        if (worldIn.isRemote) {

            return true;
        } else {

            INamedContainerProvider inamedcontainerprovider = this.getContainer(state, worldIn, pos);

            if (inamedcontainerprovider != null) {

                player.openContainer(inamedcontainerprovider);
                player.addStat(this.getOpenStat());
            }

            return true;
        }
    }

    protected Stat<ResourceLocation> getOpenStat() {

        return Stats.CUSTOM.get(Stats.OPEN_CHEST);
    }

    @SuppressWarnings("deprecation")
    @Override
    @Nullable
    public INamedContainerProvider getContainer(BlockState state, World world, BlockPos pos)
    {
        TileEntity tileentity = world.getTileEntity(pos);
        return tileentity instanceof INamedContainerProvider ? (INamedContainerProvider) tileentity : null;
    }

    @Override
    public boolean hasTileEntity(BlockState state)
    {
        return true;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean eventReceived(BlockState state, World worldIn, BlockPos pos, int id, int param)
    {
        super.eventReceived(state, worldIn, pos, id, param);
        TileEntity tileentity = worldIn.getTileEntity(pos);
        return tileentity == null ? false : tileentity.receiveClientEvent(id, param);
    }
}
