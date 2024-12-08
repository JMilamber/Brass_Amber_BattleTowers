package com.brass_amber.ba_bt.block.blockentity;

import com.brass_amber.ba_bt.BABTMain;
import com.brass_amber.ba_bt.block.block.DataMarkerBlock;
import com.brass_amber.ba_bt.init.BTBlockEntityType;
import com.brass_amber.ba_bt.util.BTStatics;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.brass_amber.ba_bt.util.BTStatics.*;
import static com.brass_amber.ba_bt.util.BTUtil.listFromTag;
import static com.brass_amber.ba_bt.util.BTUtil.newStringList;

public class DataMarkerBlockEntity extends BlockEntity {

    // Container type can be any type listed in containerTypes in
    public String containerType = "Invalid";
    public List<String> lootTypes = List.of("Invalid");
    public int rarity = -1; // -1 == set by room height. 1-4 == set by data


    public DataMarkerBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(BTBlockEntityType.DATA_MARKER.get(), blockPos, blockState);
    }



    @Override
    public void load(CompoundTag compoundTag) {
        super.load(compoundTag);
        // BABTMain.LOGGER.info("Loading Marker Data");
        try {
            this.containerType = compoundTag.getString("Container");
        } catch (Exception ignored) {
            this.containerType = "Invalid";
        }

        if (!containerTypes.contains(containerType)) {
            this.containerType = "Invalid";
        }

        this.lootTypes = listFromTag(compoundTag.getCompound("Loot"), Optional.of(lootMap.keySet().stream().toList()));

        try {
            this.rarity = compoundTag.getInt("Rarity");
        } catch (Exception ignored) {
            containerType = "Invalid";
        }
        if (this.rarity <= -1 || this.rarity >= 5) {
            this.rarity = -1;
        }
    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag) {
        super.saveAdditional(compoundTag);

        compoundTag.putString("Container", containerType);
        compoundTag.put("Loot", newStringList(this.lootTypes));
        compoundTag.putInt("Rarity", rarity);
        compoundTag.putString("Facing", this.getBlockState().getValue(DataMarkerBlock.FACING).toString());
    }

    public Block getContainer() {
        // Already protected from errors by check in load() function
        return containerBlocks.get(containerTypes.indexOf(containerType));
    }

    public BlockState getContainerState() {
        // Already protected from errors by check in load() function
        return containerBlocks.get(containerTypes.indexOf(containerType)).defaultBlockState();
    }

    public List<String> getLootTypes() {
        // Already protected from errors by check in load() function
        return lootTypes;
    }

    public int getRarity() {
        return rarity;
    }
}


