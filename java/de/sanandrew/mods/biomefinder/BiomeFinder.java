/**
 * ****************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 * *****************************************************************************************************************
 */
package de.sanandrew.mods.biomefinder;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import de.sanandrew.mods.biomefinder.block.BlockBiomeFinder;
import de.sanandrew.mods.biomefinder.packet.PacketSendBFData;
import de.sanandrew.mods.biomefinder.tileentity.TileEntityBiomeFinder;
import net.darkhax.bookshelf.lib.util.Utilities;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;

@Mod(modid = BiomeFinder.MOD_ID, version = BiomeFinder.VERSION, name = "Biome Finder", dependencies = "required-after:bookshelf@[1.0.4.178,)")
public class BiomeFinder
{
    public static final String MOD_ID = "biomefinder";
    public static final String VERSION = "1.0.0";
    public static final String MOD_CHANNEL = "BiomeFinderNWCH";
    public static SimpleNetworkWrapper network;

    private static final String MOD_PROXY_CLIENT = "de.sanandrew.mods.biomefinder.client.ClientProxy";
    private static final String MOD_PROXY_COMMON = "de.sanandrew.mods.biomefinder.CommonProxy";

    @Mod.Instance(BiomeFinder.MOD_ID)
    public static BiomeFinder instance;

    public static Block bFinder;

    @SidedProxy(modId = BiomeFinder.MOD_ID, clientSide = BiomeFinder.MOD_PROXY_CLIENT, serverSide = BiomeFinder.MOD_PROXY_COMMON)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        bFinder = new BlockBiomeFinder();
        bFinder.setCreativeTab(CreativeTabs.tabRedstone);
        bFinder.setBlockName(BiomeFinder.MOD_ID + ":biomeFinder");
        GameRegistry.registerBlock(bFinder, "tile.biomeFinder");
        GameRegistry.registerTileEntity(TileEntityBiomeFinder.class, BiomeFinder.MOD_ID + ":tileEntity.biomeFinder");

        network = NetworkRegistry.INSTANCE.newSimpleChannel(MOD_CHANNEL);

        Utilities.registerMessage(network, PacketSendBFData.class, 0, Side.CLIENT);

        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
    }
}
