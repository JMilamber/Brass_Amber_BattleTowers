package com.brass_amber.ba_bt.worldGen.structures;

import com.google.common.collect.ImmutableList;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;

import java.util.List;

import static com.brass_amber.ba_bt.util.BTTags.Blocks.BASE_PROTECTED_TAG;

public class TowerProcessors {

    static final StructureProcessor BASE_PROTECTED;

    static final StructureProcessor NORMAL_LAND;
    static final StructureProcessor NORMAL_FLOOR_LAND;
    static final StructureProcessor NORMAL_STAIRS_LAND;

    static final StructureProcessor CARPET_PLACER;

    static {

        BASE_PROTECTED = new ProtectedBlockProcessor(BASE_PROTECTED_TAG);

        NORMAL_LAND = new RuleProcessor(ImmutableList.of(
                new ProcessorRule(
                        new RandomBlockMatchTest(Blocks.STONE_BRICKS, 0.33F),
                        AlwaysTrueTest.INSTANCE,
                        Blocks.MOSSY_STONE_BRICKS.defaultBlockState()
                ),
                new ProcessorRule(
                        new RandomBlockMatchTest(Blocks.STONE_BRICKS, 0.22F),
                        AlwaysTrueTest.INSTANCE,
                        Blocks.CRACKED_STONE_BRICKS.defaultBlockState()
                ),
                new ProcessorRule(
                        new RandomBlockMatchTest(Blocks.STONE_BRICKS, 0.08F),
                        AlwaysTrueTest.INSTANCE,
                        Blocks.CHISELED_STONE_BRICKS.defaultBlockState()
                ),
                new ProcessorRule(
                        new RandomBlockMatchTest(Blocks.STONE_BRICK_STAIRS, 0.22F),
                        AlwaysTrueTest.INSTANCE,
                        Blocks.MOSSY_STONE_BRICK_STAIRS.defaultBlockState()
                ),
                new ProcessorRule(
                        new RandomBlockMatchTest(Blocks.STONE_BRICK_SLAB, 0.11F),
                        AlwaysTrueTest.INSTANCE,
                        Blocks.MOSSY_STONE_BRICK_SLAB.defaultBlockState()
                )

        ));
        NORMAL_STAIRS_LAND = new RuleProcessor(ImmutableList.of(
                new ProcessorRule(
                        new RandomBlockMatchTest(Blocks.PURPUR_SLAB, 0.4F),
                        AlwaysTrueTest.INSTANCE,
                        Blocks.COBBLESTONE_SLAB.defaultBlockState()
                ),
                new ProcessorRule(
                        new RandomBlockMatchTest(Blocks.PURPUR_SLAB, 0.6F),
                        AlwaysTrueTest.INSTANCE,
                        Blocks.MOSSY_COBBLESTONE_SLAB.defaultBlockState()
                ),
                new ProcessorRule(
                        new RandomBlockMatchTest(Blocks.PURPUR_SLAB, 0.6F),
                        AlwaysTrueTest.INSTANCE,
                        Blocks.STONE_BRICK_SLAB.defaultBlockState()
                ),
                new ProcessorRule(
                        new BlockMatchTest(Blocks.PURPUR_SLAB),
                        AlwaysTrueTest.INSTANCE,
                        Blocks.MOSSY_STONE_BRICK_SLAB.defaultBlockState()
                ),
                new ProcessorRule(
                        new RandomBlockMatchTest(Blocks.END_STONE_BRICK_SLAB, 0.4F),
                        AlwaysTrueTest.INSTANCE,
                        Blocks.COBBLESTONE_SLAB.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.TOP)
                ),
                new ProcessorRule(
                        new RandomBlockMatchTest(Blocks.END_STONE_BRICK_SLAB, 0.6F),
                        AlwaysTrueTest.INSTANCE,
                        Blocks.MOSSY_COBBLESTONE_SLAB.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.TOP)
                ),
                new ProcessorRule(
                        new RandomBlockMatchTest(Blocks.END_STONE_BRICK_SLAB, 0.6F),
                        AlwaysTrueTest.INSTANCE,
                        Blocks.STONE_BRICK_SLAB.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.TOP)
                ),
                new ProcessorRule(
                        new BlockMatchTest(Blocks.END_STONE_BRICK_SLAB),
                        AlwaysTrueTest.INSTANCE,
                        Blocks.MOSSY_STONE_BRICK_SLAB.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.TOP)
                ),
                new ProcessorRule(
                        new RandomBlockMatchTest(Blocks.PRISMARINE_SLAB, 0.65F),
                        AlwaysTrueTest.INSTANCE,
                        Blocks.MOSSY_COBBLESTONE_SLAB.defaultBlockState()
                        ),
                new ProcessorRule(
                        new BlockMatchTest(Blocks.PRISMARINE_SLAB),
                        AlwaysTrueTest.INSTANCE,
                        Blocks.COBBLESTONE_SLAB.defaultBlockState()
                ),
                new ProcessorRule(
                        new RandomBlockMatchTest(Blocks.PURPUR_BLOCK, 0.3F),
                        AlwaysTrueTest.INSTANCE,
                        Blocks.COBBLESTONE.defaultBlockState()
                ),
                new ProcessorRule(
                        new RandomBlockMatchTest(Blocks.PURPUR_BLOCK, 0.3F),
                        AlwaysTrueTest.INSTANCE,
                        Blocks.STONE_BRICKS.defaultBlockState()
                ),
                new ProcessorRule(
                        new RandomBlockMatchTest(Blocks.PURPUR_BLOCK, 0.5F),
                        AlwaysTrueTest.INSTANCE,
                        Blocks.MOSSY_COBBLESTONE.defaultBlockState()
                ),
                new ProcessorRule(
                        new RandomBlockMatchTest(Blocks.PURPUR_BLOCK, 0.5F),
                        AlwaysTrueTest.INSTANCE,
                        Blocks.MOSSY_STONE_BRICKS.defaultBlockState()
                ),
                new ProcessorRule(
                        new RandomBlockMatchTest(Blocks.PURPUR_BLOCK, 0.7F),
                        AlwaysTrueTest.INSTANCE,
                        Blocks.CRACKED_STONE_BRICKS.defaultBlockState()
                ),
                new ProcessorRule(
                        new BlockMatchTest(Blocks.PURPUR_BLOCK),
                        AlwaysTrueTest.INSTANCE,
                        Blocks.POLISHED_GRANITE.defaultBlockState()
                )
        ));
        NORMAL_FLOOR_LAND = new RuleProcessor(ImmutableList.of(
                new ProcessorRule(
                        new RandomBlockMatchTest(Blocks.END_STONE_BRICKS, 0.33F),
                        AlwaysTrueTest.INSTANCE,
                        Blocks.MOSSY_STONE_BRICKS.defaultBlockState()
                ),
                new ProcessorRule(
                        new RandomBlockMatchTest(Blocks.END_STONE_BRICKS, 0.22F),
                        AlwaysTrueTest.INSTANCE,
                        Blocks.CRACKED_STONE_BRICKS.defaultBlockState()
                ),
                new ProcessorRule(
                        new RandomBlockMatchTest(Blocks.END_STONE_BRICKS, 0.08F),
                        AlwaysTrueTest.INSTANCE,
                        Blocks.CHISELED_STONE_BRICKS.defaultBlockState()
                ),
                new ProcessorRule(
                        new RandomBlockMatchTest(Blocks.END_STONE_BRICKS, 0.2F),
                        AlwaysTrueTest.INSTANCE,
                        Blocks.SMOOTH_STONE.defaultBlockState()
                ),
                new ProcessorRule(
                        new BlockMatchTest(Blocks.END_STONE_BRICKS),
                        AlwaysTrueTest.INSTANCE,
                        Blocks.STONE_BRICKS.defaultBlockState()
                )
        ));
        CARPET_PLACER = new RuleProcessor(ImmutableList.of(
                new ProcessorRule(
                        new BlockMatchTest(Blocks.RED_WOOL),
                        AlwaysTrueTest.INSTANCE,
                        Blocks.RED_CARPET.defaultBlockState()
                ),
                new ProcessorRule(
                        new BlockMatchTest(Blocks.PURPLE_WOOL),
                        AlwaysTrueTest.INSTANCE,
                        Blocks.PURPLE_CARPET.defaultBlockState()
                )

        ));
    }
}
