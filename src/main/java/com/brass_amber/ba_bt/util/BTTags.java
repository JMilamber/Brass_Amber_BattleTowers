package com.brass_amber.ba_bt.util;

import com.brass_amber.ba_bt.BABTMain;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.Structure;

public class BTTags {

    public static class Structures {

        public static final TagKey<Structure> LAND = createBT("structure/land_tower_avoid_structures");
        public static final TagKey<Structure> OCEAN = createBT("structure/ocean_tower_avoid_structures");

        private static TagKey<Structure> createBT(String name) {
            return TagKey.create(Registries.STRUCTURE, new ResourceLocation(BABTMain.MODID, name));
        }
    }

    public static class Blocks {


        public static TagKey<Block> BASE_PROTECTED_TAG = createBT("blocks/tower_base_cannot_replace");

        private static TagKey<Block> createBT(String name) {
            return TagKey.create(Registries.BLOCK, new ResourceLocation(BABTMain.MODID, name));
        }
    }
}
