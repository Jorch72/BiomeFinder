package de.sanandrew.mods.biomefinder.tileentity;

import cpw.mods.fml.common.network.NetworkRegistry;
import de.sanandrew.mods.biomefinder.BiomeFinder;
import de.sanandrew.mods.biomefinder.packet.PacketSendBFData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.biome.BiomeGenBase;

/**
 * ****************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 * *****************************************************************************************************************
 */
public class TileEntityBiomeFinder
        extends TileEntity
{
    private static final int MAX_SRC_DEPTH = 50;

    protected BiomeGenBase biome = BiomeGenBase.mushroomIsland;

    public static final double MAX_RAD = Math.PI / 4 * 4096 * 4;

    public double rad = 0.0D;
    private int divisor = 4;
    public int blockX;
    public int blockZ;
    private boolean done = false;

    public TileEntityBiomeFinder() {}

    @Override
    public boolean canUpdate() {
        return true;
    }

    @Override
    public void updateEntity() {
        if( !this.worldObj.isRemote ) {
            if( !this.done ) {
                for( int i = 0; i < MAX_SRC_DEPTH; i++ ) {
                    if( this.rad >= MAX_RAD ) {
                        this.worldObj.getClosestPlayer(this.xCoord, this.yCoord, this.zCoord, 64D).addChatMessage(
                                new ChatComponentText(String.format("NO MESA FOUND! LAST COORDS: X:%d  Z:%d", blockX, blockZ)));
                        this.done = true;
                        break;
                    } else {
                        double x = Math.sin(this.rad) * this.rad;
                        double z = Math.cos(this.rad) * this.rad;

                        this.blockX = (int) x + TileEntityBiomeFinder.this.xCoord;
                        this.blockZ = (int) z + TileEntityBiomeFinder.this.zCoord;

                        if( this.worldObj.getBiomeGenForCoords(blockX, blockZ) == this.biome ) {
                            this.worldObj.getClosestPlayer(this.xCoord, this.yCoord, this.zCoord, 64D).addChatMessage(
                                    new ChatComponentText(String.format("MESA FOUND AT X:%d  Z:%d", blockX, blockZ)));
                            done = true;
                            break;
                        }

                        this.rad += Math.PI / this.divisor;

                        this.divisor = 4 + (int) (this.rad / Math.PI);
                    }
                }

                BiomeFinder.network.sendToAllAround(new PacketSendBFData(this), new NetworkRegistry.TargetPoint(this.worldObj.provider.dimensionId, this.xCoord, this.yCoord, this.zCoord, 64.0D));
            }
        }

        super.updateEntity();
    }
}
