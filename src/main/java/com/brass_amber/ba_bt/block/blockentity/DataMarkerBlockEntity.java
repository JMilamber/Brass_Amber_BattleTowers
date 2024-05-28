package com.brass_amber.ba_bt.block.blockentity;

import com.brass_amber.ba_bt.init.BTBlockEntityType;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import static com.brass_amber.ba_bt.util.BTStatics.*;

public class DataMarkerBlockEntity extends BlockEntity {

    // Container type can be any type listed in containerTypes in
    public String containerType = "Invalid";
    public String lootType = "Invalid";
    public int rarity = 0;

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
            containerType = "Invalid";
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
        compoundTag.putInt("Rarity", rarity);
    }

    protected Block getContainerType() {
        // Already protected from errors by check in load() function
        return containerBlocks.get(containerTypes.indexOf(containerType));

    }

    protected String getLootType() {
        // Already protected from errors by check in load() function
        return lootType;
    }

}


