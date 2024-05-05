package com.brass_amber.ba_bt.client.renderer.chest;

import com.brass_amber.ba_bt.util.GolemType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class SkyGolemChestRenderer extends AbstractBTChestRenderer{
    public SkyGolemChestRenderer(BlockEntityRendererProvider.Context context) {
        super(context, GolemType.SKY, true);
    }
}
