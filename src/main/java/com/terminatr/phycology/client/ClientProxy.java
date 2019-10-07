package com.terminatr.phycology.client;

import com.terminatr.phycology.common.ServerProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;

public class ClientProxy extends ServerProxy {

    public ClientProxy() {

    }

    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void preInit() {

        super.preInit();
    }

    @Override
    public World getClientWorld() {

        return Minecraft.getInstance().world;
    }
}
