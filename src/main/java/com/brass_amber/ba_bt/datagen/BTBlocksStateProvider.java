package com.brass_amber.ba_bt.datagen;

import com.brass_amber.ba_bt.BABTMain;
import com.brass_amber.ba_bt.init.BTBlocks;
import net.minecraft.client.model.ModelUtils;
import net.minecraft.data.PackOutput;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import org.apache.commons.lang3.ObjectUtils;

public class BTBlocksStateProvider extends BlockStateProvider {
    public BTBlocksStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, BABTMain.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        chestBlock(BTBlocks.LAND_CHEST);
        chestBlock(BTBlocks.LAND_GOLEM_CHEST);
        chestBlock(BTBlocks.OCEAN_CHEST);
        chestBlock(BTBlocks.OCEAN_GOLEM_CHEST);
        chestBlock(BTBlocks.CORE_CHEST);
        chestBlock(BTBlocks.CORE_GOLEM_CHEST);
        chestBlock(BTBlocks.NETHER_CHEST);
        chestBlock(BTBlocks.NETHER_GOLEM_CHEST);
        chestBlock(BTBlocks.END_CHEST);
        chestBlock(BTBlocks.END_GOLEM_CHEST);
        chestBlock(BTBlocks.SKY_CHEST);
        chestBlock(BTBlocks.SKY_GOLEM_CHEST);

        spawnerBlock(BTBlocks.LAND_SPAWNER);
        spawnerBlock(BTBlocks.OCEAN_SPAWNER);
        spawnerBlock(BTBlocks.CORE_SPAWNER);
        spawnerBlock(BTBlocks.NETHER_SPAWNER);
        spawnerBlock(BTBlocks.END_SPAWNER);
        spawnerBlock(BTBlocks.SKY_SPAWNER);
        spawnerBlock(BTBlocks.SPAWNER_MARKER);

        simpleBlock(BTBlocks.AIR_FILL.get(), models().getBuilder(BTBlocks.AIR_FILL.getId().getPath()));
        simpleBlockWithItem(BTBlocks.DATA_MARKER.get(), models().cubeAll(BTBlocks.DATA_MARKER.getId().getPath(), new ResourceLocation(BABTMain.MODID,"block/" + BTBlocks.DATA_MARKER.getId().getPath())));
    }


    private void chestBlock(RegistryObject<Block> block) {
        simpleBlock(
                block.get(),
                models().getBuilder(block.getId().getPath())
                        .texture("particle", new ResourceLocation("minecraft:block/stone"))
        );
    }

    private void spawnerBlock(RegistryObject<Block> block) {
        simpleBlockWithItem(
                block.get(),
                models().cubeAll(block.getId().getPath(), new ResourceLocation(BABTMain.MODID,"block/spawner/" + block.getId().getPath()))
        );
    }
}
