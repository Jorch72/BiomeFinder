package de.sanandrew.mods.biomefinder.client.handler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import de.sanandrew.mods.biomefinder.tileentity.TileEntityBiomeFinder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

/**
 * ****************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 * *****************************************************************************************************************
 */
public class RenderOverlayEventHandler
{
    private FontRenderer font;

    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent.Post event) {
        Minecraft mc = Minecraft.getMinecraft();

        if( font == null ) {
            font = mc.fontRenderer;
        }

        if( event.type == RenderGameOverlayEvent.ElementType.ALL ) {
            if( mc.objectMouseOver != null && mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK ) {
                TileEntity tile = mc.theWorld.getTileEntity(mc.objectMouseOver.blockX, mc.objectMouseOver.blockY, mc.objectMouseOver.blockZ);
                if( tile instanceof TileEntityBiomeFinder ) {
                    TileEntityBiomeFinder biomeFinder = (TileEntityBiomeFinder) tile;
                    font.drawString(String.format("currX: %d", biomeFinder.blockX), 0, 0, 0xFFFFFF);
                    font.drawString(String.format("currZ: %d", biomeFinder.blockZ), 0, 10, 0xFFFFFF);
                    font.drawString(String.format("currRad: %f", biomeFinder.rad), 0, 20, 0xFFFFFF);
                }
            }
        }
    }
}
