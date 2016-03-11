package de.sanandrew.mods.biomefinder.client;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import de.sanandrew.mods.biomefinder.CommonProxy;
import de.sanandrew.mods.biomefinder.client.handler.RenderOverlayEventHandler;
import net.minecraftforge.common.MinecraftForge;

/**
 * ****************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 * *****************************************************************************************************************
 */
public class ClientProxy
    extends CommonProxy
{
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);

        MinecraftForge.EVENT_BUS.register(new RenderOverlayEventHandler());
    }
}
