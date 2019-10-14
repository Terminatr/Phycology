package com.terminatr.phycology;

import com.terminatr.phycology.client.ClientProxy;
import com.terminatr.phycology.common.ServerProxy;
import com.terminatr.phycology.common.core.PhycologyBlocks;
import com.terminatr.phycology.common.core.PhycologyFluids;
import com.terminatr.phycology.common.fluids.BrineFluid;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;


import static com.terminatr.phycology.Phycology.MOD_ID;

@Mod(MOD_ID)
public class Phycology {

    public static final String MOD_ID = "phycology";

    public static Phycology instance;

    public static ServerProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> ServerProxy::new);

    public Phycology() {

        instance = this;

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::preInit);

        PhycologyFluids.BLOCKS.register(modEventBus);
        PhycologyFluids.ITEMS.register(modEventBus);
        PhycologyFluids.FLUIDS.register(modEventBus);
    }

    private void preInit(final FMLCommonSetupEvent event) {

        proxy.preInit();

        DistExecutor.runWhenOn(Dist.CLIENT, () -> PhycologyBlocks::registerScreenFactories);

        //PacketHandler.register();
    }
}
