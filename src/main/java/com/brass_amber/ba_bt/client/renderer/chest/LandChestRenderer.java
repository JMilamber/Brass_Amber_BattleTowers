package com.brass_amber.ba_bt.client.renderer.chest;

import com.brass_amber.ba_bt.util.GolemType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class LandChestRenderer extends AbstractBTChestRenderer{
    public LandChestRenderer(BlockEntityRendererProvider.Context context) {
        super(context, GolemType.LAND, false);
    }
}
