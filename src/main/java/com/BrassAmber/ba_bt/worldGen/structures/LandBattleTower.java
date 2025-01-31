package com.BrassAmber.ba_bt.worldGen.structures;

import com.BrassAmber.ba_bt.BattleTowersConfig;
import com.BrassAmber.ba_bt.BrassAmberBattleTowers;
import com.BrassAmber.ba_bt.worldGen.BTLandJigsawPlacement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.*;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.BuiltinStructureSets;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGenerator;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;
import net.minecraft.world.level.levelgen.structure.pieces.PiecesContainer;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static com.BrassAmber.ba_bt.BattleTowersConfig.*;
import static com.BrassAmber.ba_bt.BrassAmberBattleTowers.SAVETOWERS;
import static com.BrassAmber.ba_bt.BrassAmberBattleTowers.locate;
import static com.BrassAmber.ba_bt.util.BTStatics.*;
import static com.BrassAmber.ba_bt.util.BTTags.Biomes.*;
import static com.BrassAmber.ba_bt.util.BTUtil.chunkDistanceTo;
import static com.BrassAmber.ba_bt.util.SaveTowers.towers;


public class LandBattleTower extends StructureFeature<JigsawConfiguration> {

    private static boolean watered;

    public static final Codec<JigsawConfiguration> CODEC = RecordCodecBuilder.create((codec) -> codec.group(StructureTemplatePool.CODEC.fieldOf("start_pool").forGetter(JigsawConfiguration::startPool),
            Codec.intRange(0, 40).fieldOf("size").forGetter(JigsawConfiguration::maxDepth)
    ).apply(codec, JigsawConfiguration::new));

    public LandBattleTower() {
        super(CODEC, LandBattleTower::createPiecesGenerator, LandBattleTower::afterPlace);
    }

    @Override
    public GenerationStep.@NotNull Decoration step() {
        return GenerationStep.Decoration.SURFACE_STRUCTURES;
    }


    public static BlockPos isSpawnableChunk(PieceGeneratorSupplier.Context<JigsawConfiguration> context, int biomeType,
                                            WorldgenRandom worldgenRandom, ChunkPos chunkPos, ChunkGenerator chunkGen) {

        List<ResourceKey<StructureSet>> vanillaStructures = new ArrayList<>();
        vanillaStructures.add(BuiltinStructureSets.VILLAGES);
        vanillaStructures.add(BuiltinStructureSets.DESERT_PYRAMIDS);
        vanillaStructures.add(BuiltinStructureSets.IGLOOS);
        vanillaStructures.add(BuiltinStructureSets.JUNGLE_TEMPLES);
        vanillaStructures.add(BuiltinStructureSets.SWAMP_HUTS);
        vanillaStructures.add(BuiltinStructureSets.PILLAGER_OUTPOSTS);
        vanillaStructures.add(BuiltinStructureSets.WOODLAND_MANSIONS);
        vanillaStructures.add(BuiltinStructureSets.RUINED_PORTALS);
        vanillaStructures.add(BuiltinStructureSets.SHIPWRECKS);


        for (ResourceKey<StructureSet> set : vanillaStructures) {
            // BrassAmberBattleTowers.LOGGER.info(context.chunkGenerator().hasFeatureChunkInRange(set, context.seed(), chunkPos.x, chunkPos.z, 3));

            if (chunkGen.hasFeatureChunkInRange(set, context.seed(), chunkPos.x, chunkPos.z, 3)) {
                // BrassAmberBattleTowers.LOGGER.info("Has " + set + " Feature in range");
                return BlockPos.ZERO;
            }
        }

        // Test/Check 3 by 3 square of chunks for possible spawns
        List<ChunkPos> testables = new ArrayList<>(
                List.of(
                        chunkPos,
                        new ChunkPos(chunkPos.x, chunkPos.z + 1),
                        new ChunkPos(chunkPos.x + 1, chunkPos.z),
                        new ChunkPos(chunkPos.x, chunkPos.z - 1),
                        new ChunkPos(chunkPos.x - 1, chunkPos.z)
                )
        );

        List<ChunkPos> usablePositions =  new ArrayList<>();
        ArrayList<Integer> usableHeights = new ArrayList<>();
        ArrayList<Boolean> hasWater = new ArrayList<>();
        int newLandHeight;
        int lowestY;
        int highestY;
        int minX;
        int minZ;
        int newX;
        int newZ;

        for (ChunkPos pos : testables) {
            // BrassAmberBattleTowers.LOGGER.info("Land tower testing at " + pos);
            int middleHieght = chunkGen.getFirstOccupiedHeight(pos.getMiddleBlockX(), pos.getMiddleBlockZ(), Heightmap.Types.WORLD_SURFACE_WG, context.heightAccessor());
            Holder<Biome> biome = chunkGen.getNoiseBiome(QuartPos.fromBlock(pos.getMiddleBlockX()), QuartPos.fromBlock(middleHieght), QuartPos.fromBlock(pos.getMiddleBlockZ()));
            lowestY = 215;
            highestY = 0;
            hasWater.clear();
            minX = pos.getMinBlockX();
            minZ = pos.getMinBlockZ();

            for (int x = 0; x < 6; x++) {
                for (int z = 0; z < 6; z++) {
                    newX = minX + (x*3);
                    newZ = minZ + (z*3);
                    newLandHeight = chunkGen.getFirstOccupiedHeight(newX, newZ, Heightmap.Types.WORLD_SURFACE_WG, context.heightAccessor());

                    lowestY = Math.min(newLandHeight, lowestY);
                    highestY = Math.max(newLandHeight, highestY);

                    // get column of blocks at blockpos.
                    NoiseColumn columnOfBlocks = chunkGen.getBaseColumn(newX, newZ, context.heightAccessor());
                    // combine the column of blocks with land height and you get the top block itself which you can test.
                    BlockState topBlock = columnOfBlocks.getBlock(newLandHeight);
                    // check whether the topBlock is a source block of water.
                    if (topBlock == Blocks.WATER.defaultBlockState()) {
                        hasWater.add(Boolean.TRUE);
                    }
                }
            }

            if (highestY > 215) {
                BrassAmberBattleTowers.LOGGER.info("Terrain to high for Land Tower");
                return  BlockPos.ZERO;
            }

            // 12 Blocks seem to work well with allowing a good number of small cliff spawns, while removing the mountainside spawns
            boolean isFlat = highestY - lowestY <= 12;

            // 256 blocks in one layer of a chunk, if more than 1/16 is water, avoid.
            watered = hasWater.size() >= 16;
            if (watered && biomeType != 1) {
                return BlockPos.ZERO;
            }
            int usableHeight = lowestY + ((highestY - lowestY)/4);

            boolean acceptableBiome = acceptableBiome(biome, biomeType);

            // BrassAmberBattleTowers.LOGGER.info("flat?: " + isFlat + " water?: " + watered + " usable height: " + usableHeight + " acceptable biome? " + acceptableBiome);

            if (isFlat && acceptableBiome) {
                // BrassAmberBattleTowers.LOGGER.info("Usable position at: " + pos + " " + usableHeight);
                usablePositions.add(pos);
                usableHeights.add(usableHeight);
            }

        }

        if (usablePositions.size() > 0) {
            int index = worldgenRandom.nextInt(usablePositions.size());
            int landHeight = usableHeights.get(index);
            // BrassAmberBattleTowers.LOGGER.info("Position chosen: " + usablePositions.get(index).getMiddleBlockPosition(landHeight));
            return usablePositions.get(index).getMiddleBlockPosition(landHeight);
        }

        return BlockPos.ZERO;
    }

    public static @NotNull Optional<PieceGenerator<JigsawConfiguration>> createPiecesGenerator(PieceGeneratorSupplier.Context<JigsawConfiguration> context) {
        // Check if the spot is valid for our structure. This is just as another method for cleanness.
        // Returning an empty optional tells the game to skip this spot as it will not generate the structure. -- TelepathicGrunt

        Optional<PieceGenerator<JigsawConfiguration>> piecesGenerator;
        int firstTowerDistance = BattleTowersConfig.firstTowerDistance.get();
        int minimumSeparation = BattleTowersConfig.landMinimumSeperation.get();
        int seperationRange = BattleTowersConfig.landAverageSeperationModifier.get();

        ChunkPos chunkPos = context.chunkPos();
        ChunkGenerator chunkGen = context.chunkGenerator();

        boolean firstTowerDistanceCheck = chunkDistanceTo(ChunkPos.ZERO, chunkPos) < firstTowerDistance;
        if (firstTowerDistanceCheck) {
            return Optional.empty();
        }
        // BrassAmberBattleTowers.LOGGER.info("current distance " + (int) Mth.absMax(chunkPos.x, chunkPos.z) + "  config distance " + BattleTowersConfig.firstTowerDistance.get());

        WorldgenRandom worldgenRandom = new WorldgenRandom(new LegacyRandomSource(chunkPos.x));
        worldgenRandom.setLargeFeatureSeed(context.seed(), chunkPos.x, chunkPos.z);

        int nextSeperation =  minimumSeparation + worldgenRandom.nextInt(seperationRange * 2);
        int closestDistance = 2000;

        if (!towers.get(0).isEmpty()) {
            for (ChunkPos towerPos: towers.get(0)) {
                int distance = chunkDistanceTo(chunkPos, towerPos);
                closestDistance = Math.min(closestDistance, distance);
                // BrassAmberBattleTowers.LOGGER.info("Tower distance from generation try:" + distance);
            }
        }

        if (closestDistance <= nextSeperation) {
            // BrassAmberBattleTowers.LOGGER.info("Land not outside tower separation " + nextSeperation);
            return Optional.empty();
        }

        BlockPos chunkCenter = chunkPos.getMiddleBlockPosition(0);
        int x = chunkCenter.getX();
        int z = chunkCenter.getZ();
        int y =  chunkGen.getFirstFreeHeight(chunkCenter.getX(), chunkCenter.getZ(), Heightmap.Types.WORLD_SURFACE_WG, context.heightAccessor());

        Holder<Biome> biome = chunkGen.getNoiseBiome(QuartPos.fromBlock(x), QuartPos.fromBlock(y), QuartPos.fromBlock(z));
        BlockPos spawnPos;
        ResourceLocation location = context.config().startPool().value().getName();

        // 0 = Normal, 1 = Overgrown, 2 = Sandy
        int towerType = 0;
        towerType = location.getPath().equals(locate("land_tower/start_pool_overgrown").getPath()) ? 1 : towerType;
        towerType = location.getPath().equals(locate("land_tower/start_pool_sandy").getPath()) ? 2 : towerType;

        // Moved Biome check from JigsawPlacement to here
        boolean acceptableBiome = acceptableBiome(biome, towerType);

        if (!acceptableBiome) {
            // BrassAmberBattleTowers.LOGGER.info("Bad " + landTowerNames.get(towerType) + " Land Biome " + " : " + biome.unwrapKey());
            return Optional.empty();
        }



        // BrassAmberBattleTowers.LOGGER.info(typeNames.get(towerType) + " Land Biome " + " : " + biome.unwrapKey());
        spawnPos = isSpawnableChunk(context, towerType, worldgenRandom, chunkPos, chunkGen);
        // BrassAmberBattleTowers.LOGGER.info("Land closest position: " + closestDistance);

        boolean sandy = towerType == 2;

        if (spawnPos.getY() != 0) {
            piecesGenerator =
                    BTLandJigsawPlacement.addPieces(
                            context, // Used for JigsawPlacement to get all the proper behaviors done.
                            PoolElementStructurePiece::new, // Needed in order to create a list of jigsaw pieces when making the structure's layout.
                            spawnPos, // Position of the structure. Y value is ignored if last parameter is set to true. --TelepathicGrunt
                            watered,
                            sandy
                    );

            if (piecesGenerator.isPresent()) {
                // I use to debug and quickly find out if the structure is spawning or not and where it is.
                // This is returning the coordinates of the center starting piece.
                BrassAmberBattleTowers.LOGGER.info(landTowerNames.get(towerType) + " Tower at " + spawnPos);
                SAVETOWERS.addTower(chunkPos, "Land_Towers");
            }
            // Return the pieces generator that is now set up so that the game runs it when it needs to create the layout of structure pieces
            return piecesGenerator;
            // BrassAmberBattleTowers.LOGGER.info(landTowerNames.get(towerType) + "incorrect biome? " + biome);
        }
        return Optional.empty();
    }

    public static void afterPlace(WorldGenLevel worldGenLevel, StructureFeatureManager featureManager, ChunkGenerator chunkGenerator, Random random, BoundingBox boundingBox, ChunkPos chunkPos, PiecesContainer piecesContainer) {
        BoundingBox boundingbox = piecesContainer.calculateBoundingBox();
        int bbYStart = boundingbox.minY();
        boundingbox.getCenter();

        BlockPos chunckCenter = chunkPos.getMiddleBlockPosition(bbYStart);

        // BrassAmberBattleTowers.LOGGER.info("Post Processing: In chunk: " + chunkPos + " " + chunckCenter);

        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
        blockpos$mutableblockpos.setY(bbYStart);
        // get start and end postions for x/z, using min/max to account for the MinBlock being -25 and the MaxBlock being -27
        int startX = chunckCenter.getX() - 8;
        int endX = chunckCenter.getX() + 8;
        // BrassAmberBattleTowers.LOGGER.info("X start: " + startX + " end: " + endX);

        int startZ = chunckCenter.getZ() - 8;
        int endZ = chunckCenter.getZ() + 8;
        // BrassAmberBattleTowers.LOGGER.info("X start: " + startZ + " end: " + endZ);

        ArrayList<BlockPos> startPositions = new ArrayList<>();
        List<BlockState> acceptableBlocks = List.of(
                Blocks.STONE_BRICKS.defaultBlockState(), Blocks.CRACKED_STONE_BRICKS.defaultBlockState(),
                Blocks.MOSSY_STONE_BRICKS.defaultBlockState(), Blocks.CHISELED_STONE_BRICKS.defaultBlockState()
        );

        for (int x = startX; x <= endX; x++) {
            for (int z = startZ; z <= endZ; z++) {
                blockpos$mutableblockpos.set(x, bbYStart, z);
                // BrassAmberBattleTowers.LOGGER.info("Block at: " + blockpos$mutableblockpos + " is: " + worldGenLevel.getBlockState(blockpos$mutableblockpos));
                if ( acceptableBlocks.contains(worldGenLevel.getBlockState(blockpos$mutableblockpos))) {
                    // BrassAmberBattleTowers.LOGGER.info("Block is acceptable: " + blockpos$mutableblockpos + " "+ worldGenLevel.getBlockState(blockpos$mutableblockpos));
                    startPositions.add(new BlockPos(x, bbYStart - 1, z));
                }
            }
        }

        for (BlockPos startPos: startPositions) {
            for (int y = startPos.getY(); y > worldGenLevel.getMinBuildHeight() ; y--) {
                blockpos$mutableblockpos.set(startPos.getX(), y, startPos.getZ());
                // BrassAmberBattleTowers.LOGGER.info("Block to check: " + blockpos$mutableblockpos + " is: " + worldGenLevel.getBlockState(blockpos$mutableblockpos));
                if (worldGenLevel.isEmptyBlock(blockpos$mutableblockpos) || worldGenLevel.isWaterAt(blockpos$mutableblockpos)
                        || worldGenLevel.getBlockState(blockpos$mutableblockpos).getBlock() instanceof TallGrassBlock
                        || worldGenLevel.getBlockState(blockpos$mutableblockpos).getBlock() instanceof FlowerBlock
                        || worldGenLevel.getBlockState(blockpos$mutableblockpos).getBlock() instanceof DeadBushBlock) {
                    worldGenLevel.setBlock(blockpos$mutableblockpos, Blocks.STONE_BRICKS.defaultBlockState(), 2);
                } else {
                    // Add two blocks into this ground level as well.
                    worldGenLevel.setBlock(blockpos$mutableblockpos, Blocks.STONE_BRICKS.defaultBlockState(), 2);
                    worldGenLevel.setBlock(blockpos$mutableblockpos.below(), Blocks.STONE_BRICKS.defaultBlockState(), 2);
                    break;
                }
            }
        }
    }

    public static boolean acceptableBiome(Holder<Biome> biome, int towerType) {
        boolean acceptable = false;

        // Test base minecraft acceptable biomes
        if (towerType == 0) {
            acceptable = LAND.test(biome);
        } else if (towerType == 1) {
            acceptable = LAND_OVERGROWN.test(biome);
        } else if (towerType == 2) {
            acceptable = LAND_SANDY.test(biome);
        }

        // Test Terralith Mod acceptable biomes
        if (terralithBiomeSpawning.get() && !acceptable) {
            if (towerType == 0) {
                acceptable = TERRA_LAND.test(biome);
            } else if (towerType == 1) {
                acceptable = TERRA_LAND_OVERGROWN.test(biome);
            } else if (towerType == 2) {
                acceptable = TERRA_LAND_SANDY.test(biome);
            }
        }

        // Test Biomes Of Plenty Mod acceptable biomes
        if (biomesOfPlentyBiomeSpawning.get() && !acceptable) {
            if (towerType == 0) {
                acceptable = BOP_LAND.test(biome);
            } else if (towerType == 1) {
                acceptable = BOP_LAND_OVERGROWN.test(biome);
            } else if (towerType == 2) {
                acceptable = BOP_LAND_SANDY.test(biome);
            }
        }

        // Test Oh The Biomes You'll Go mod acceptable biomes
        if (biomesYoullGoBiomeSpawning.get() && !acceptable) {
            if (towerType == 0) {
                acceptable = BYG_LAND.test(biome);
            } else if (towerType == 1) {
                acceptable = BYG_LAND_OVERGROWN.test(biome);
            } else if (towerType == 2) {
                acceptable = BYG_LAND_SANDY.test(biome);
            }
        }

        return acceptable;
    }
}
