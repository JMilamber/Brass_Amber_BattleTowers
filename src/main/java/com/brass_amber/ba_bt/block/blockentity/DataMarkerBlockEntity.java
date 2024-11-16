package com.brass_amber.ba_bt.block.blockentity;

import com.brass_amber.ba_bt.block.block.DataMarkerBlock;
import com.brass_amber.ba_bt.init.BTBlockEntityType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;

import static com.brass_amber.ba_bt.util.BTStatics.*;

public class DataMarkerBlockEntity extends BlockEntity {

    // Container type can be any type listed in containerTypes in
    public String containerType = "Invalid";
    public String lootType = "Invalid";
    public String lootType2 = "Invalid";
    public int rarity = -1; // -1 == set by room height. 1-4 == set by data


    public DataMarkerBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(BTBlockEntityType.DATA_MARKER.get(), blockPos, blockState);
    }



    @Override
    public void load(CompoundTag compoundTag) {
        super.load(compoundTag);
        try {
            containerType = compoundTag.getString("Container");
        } catch (Exception ignored) {
            containerType = "Invalid";
        }

        if (!containerTypes.contains(containerType)) {
            containerType = "Invalid";
        }

        try {
            lootType = compoundTag.getString("Loot");
        } catch (Exception ignored) {
            lootType = "Invalid";
        }
        if (!lootTypes.contains(lootType)) {
            lootType = "Invalid";
        }

        try {
            lootType2 = compoundTag.getString("Loot2");
        } catch (Exception ignored) {
            lootType2 = "Invalid";
        }
        if (!lootTypes.contains(lootType2)) {
            lootType2 = "Invalid";
        }

        try {
            rarity = compoundTag.getInt("Rarity");
        } catch (Exception ignored) {
            containerType = "Invalid";
        }
        if (rarity <= -1 || rarity >= 5) {
            rarity = -1;
        }
    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag) {
        super.saveAdditional(compoundTag);

        compoundTag.putString("Container", containerType);
        compoundTag.putString("Loot", lootType);
        compoundTag.putString("Loot2", lootType2);
        compoundTag.putInt("Rarity", rarity);
        compoundTag.putString("Facing", this.getBlockState().getValue(DataMarkerBlock.FACING).toString());
    }

    protected Block getContainerType() {
        // Already protected from errors by check in load() function
        return containerBlocks.get(containerTypes.indexOf(containerType));

    }

    protected String getLootType() {
        // Already protected from errors by check in load() function
        return lootType;
    }
    protected String getLootType2() {
        // Already protected from errors by check in load() function
        return lootType;
    }

}


