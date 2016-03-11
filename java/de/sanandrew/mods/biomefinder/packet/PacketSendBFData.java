package de.sanandrew.mods.biomefinder.packet;

import de.sanandrew.mods.biomefinder.tileentity.TileEntityBiomeFinder;
import io.netty.buffer.ByteBuf;
import net.darkhax.bookshelf.common.network.AbstractMessage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

/**
 * ****************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 * *****************************************************************************************************************
 */
public class PacketSendBFData
        extends AbstractMessage<PacketSendBFData>
{
    private int xCoord;
    private int yCoord;
    private int zCoord;
    private int checkedBlockX;
    private int checkedBlockZ;
    private float currRad;

    public PacketSendBFData() {}

    public PacketSendBFData(TileEntityBiomeFinder tileBiomeFinder) {
        this.xCoord = tileBiomeFinder.xCoord;
        this.yCoord = tileBiomeFinder.yCoord;
        this.zCoord = tileBiomeFinder.zCoord;
        this.checkedBlockX = tileBiomeFinder.blockX;
        this.checkedBlockZ = tileBiomeFinder.blockZ;
        this.currRad = (float) tileBiomeFinder.rad;
    }

    @Override
    public void handleClientMessage(PacketSendBFData packet, EntityPlayer player) {
        TileEntity tile = player.worldObj.getTileEntity(packet.xCoord, packet.yCoord, packet.zCoord);
        if( tile instanceof TileEntityBiomeFinder ) {
            TileEntityBiomeFinder biomeFinder = (TileEntityBiomeFinder) tile;
            biomeFinder.blockX = packet.checkedBlockX;
            biomeFinder.blockZ = packet.checkedBlockZ;
            biomeFinder.rad = packet.currRad;
        }
    }

    @Override
    public void handleServerMessage(PacketSendBFData packet, EntityPlayer player) {

    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.xCoord = buf.readInt();
        this.yCoord = buf.readInt();
        this.zCoord = buf.readInt();
        this.checkedBlockX = buf.readInt();
        this.checkedBlockZ = buf.readInt();
        this.currRad = buf.readFloat();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.xCoord);
        buf.writeInt(this.yCoord);
        buf.writeInt(this.zCoord);
        buf.writeInt(this.checkedBlockX);
        buf.writeInt(this.checkedBlockZ);
        buf.writeFloat(this.currRad);
    }
}
