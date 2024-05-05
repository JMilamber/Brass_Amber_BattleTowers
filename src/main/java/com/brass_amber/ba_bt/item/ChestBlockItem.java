package com.brass_amber.ba_bt.item;

import com.brass_amber.ba_bt.BABTMain;
import com.brass_amber.ba_bt.block.block.TowerChestBlock;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nullable;
import java.util.List;


public class ChestBlockItem extends BlockItem {
    public ChestBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {

        if (Screen.hasShiftDown()) {
            if (this.getBlock() instanceof TowerChestBlock) {
                tooltip.add(Component.translatable("tooltip.ba_bt.tower_chest").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
            } else {
                tooltip.add(Component.translatable("tooltip.ba_bt.golem_chest").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
            }
        } else {
            tooltip.add(BABTMain.HOLD_SHIFT_TOOLTIP);
        }
    }
}

