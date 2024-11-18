package com.brass_amber.ba_bt.worldGen.structures;


import java.util.List;
import java.util.Optional;

import com.brass_amber.ba_bt.BABTMain;
import com.brass_amber.ba_bt.BattleTowersConfig;
import com.brass_amber.ba_bt.util.SaveTowers;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.*;
import net.minecraft.world.level.levelgen.structure.pieces.PiecesContainer;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;

import static com.brass_amber.ba_bt.BABTMain.SAVE_TOWERS;
import static com.brass_amber.ba_bt.BattleTowersConfig.*;
import static com.brass_amber.ba_bt.util.BTUtil.chunkDistanceTo;

public abstract class TowerStructure extends Structure {

    protected final TowerStructure.BTStructureSettings extraSettings;
    protected String towerName;

    protected final List<Integer> minimumSeperations;
    protected final List<Integer> averageSeperations;
    protected int towerType = 0;
    protected int towerId = -1; // Tower number (Land = 0, Ocean = 1, etc. )
    protected String[] towerTypeConversion;

    protected final Boolean buryTower = false;
    protected final Boolean randomBuryDepth = false;

    protected TowerStructure(StructureSettings structureSettings, BTStructureSettings extraSettings) {
        super(structureSettings);
        this.extraSettings = extraSettings;
        this.minimumSeperations = List.of(landMinimumSeperation.get(), oceanMinimumSeperation.get());
        this.averageSeperations = List.of(landAverageSeperationModifier.get(), oceanAverageSeperationModifier.get());
    }

    public static <S extends TowerStructure> RecordCodecBuilder<S, TowerStructure.BTStructureSettings> extraSettingsCodec() {
        return TowerStructure.BTStructureSettings.CODEC.forGetter((object) -> new BTStructureSettings(null, null, null, null));
    }

    @Override
    public void afterPlace(WorldGenLevel p_226560_, StructureManager p_226561_, ChunkGenerator p_226562_, RandomSource p_226563_, BoundingBox p_226564_, ChunkPos p_226565_, PiecesContainer p_226566_) {
        super.afterPlace(p_226560_, p_226561_, p_226562_, p_226563_, p_226564_, p_226565_, p_226566_);
    }

    @Override
    protected Optional<Structure.GenerationStub> findGenerationPoint(GenerationContext generationContext) {

        ChunkPos chunkPos = generationContext.chunkPos();
        // BABTMain.LOGGER.info("Attempting Land Tower Spawn at " + chunkPos.x + " " + chunkPos.z);

        // Ensure tower chunk is outside initial player requested spawn range
        if (chunkDistanceTo(ChunkPos.ZERO, chunkPos) < BattleTowersConfig.firstTowerDistance.get()) {
            return Optional.empty();
        }

        ChunkGenerator chunkGen = generationContext.chunkGenerator();
        int minimumSeparation = this.minimumSeperations.get(this.towerId);
        int seperationRange = this.averageSeperations.get(this.towerId);

        //
        int nextSeperation =  minimumSeparation + generationContext.random().nextInt(seperationRange * 2);
        int closestDistance = 2000;

        if (!SaveTowers.towers.get(this.towerId).isEmpty()) {
            for (ChunkPos towerPos: SaveTowers.towers.get(this.towerId)) {
                closestDistance = Math.min(closestDistance, chunkDistanceTo(chunkPos, towerPos));
                // BABTMain.LOGGER.info("Tower distance from generation try:" + closestDistance);
            }
        }

        if (closestDistance <= nextSeperation) {
            // BABTMain.LOGGER.info("Land not outside tower separation " + nextSeperation);
            return Optional.empty();
        }

        Pair<Boolean, BlockPos> canSpawn = isSpawnableChunk(generationContext);
        Rotation rotation = Rotation.getRandom(generationContext.random());

        if (canSpawn.getFirst()) {
            saveTower(canSpawn.getSecond(), chunkPos);
            return Optional.of(new Structure.GenerationStub(canSpawn.getSecond(), (piecesBuilder) -> {
                this.generatePieces(piecesBuilder, generationContext, canSpawn.getSecond(), rotation);
            }));
        }

        return Optional.empty();
    }

    protected void generatePieces(StructurePiecesBuilder piecesBuilder, Structure.GenerationContext generationContext, BlockPos blockPos, Rotation rotation) {
        List<TowerPieces.TowerPiece> list = Lists.newLinkedList();
        String variant;
        try {
            variant = this.towerTypeConversion[this.towerType];
        } catch (IndexOutOfBoundsException e) {
            variant = "normal";
        }
        TowerPieces.generateTower(generationContext.structureTemplateManager(), blockPos, rotation, list, generationContext.random(), this.towerName, variant);
        list.forEach(piecesBuilder::addPiece);
    }


    protected abstract Pair<Boolean, BlockPos> isSpawnableChunk(GenerationContext generationContext);

    public record BTStructureSettings(HolderSet<Structure> avoidStructures, HolderSet<Biome> biomesTerra,
                                      HolderSet<Biome> biomesBOP, HolderSet<Biome> biomesBYG) {
        public static final MapCodec<TowerStructure.BTStructureSettings> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
                RegistryCodecs.homogeneousList(Registries.STRUCTURE).fieldOf("avoid_structures").forGetter(btStructureSettings -> btStructureSettings.avoidStructures),
                RegistryCodecs.homogeneousList(Registries.BIOME).fieldOf("biomes_terra").forGetter(structure -> structure.biomesTerra),
                RegistryCodecs.homogeneousList(Registries.BIOME).fieldOf("biomes_bop").forGetter(structure -> structure.biomesBOP),
                RegistryCodecs.homogeneousList(Registries.BIOME).fieldOf("biomes_byg").forGetter(structure -> structure.biomesBYG)
        ).apply(instance, BTStructureSettings::new));
    }
    // Used for tower saving and logging of tower positions
    public void saveTower(BlockPos spawnPos, ChunkPos chunkPos) {
        BABTMain.LOGGER.info(this.towerName + " Tower at " + spawnPos + " " + chunkPos);
        SAVE_TOWERS.addTower(chunkPos, this.towerName);
    }

    public boolean acceptableBiome(StructureSettings settings, BTStructureSettings extraSettings, Holder<Biome> biome) {
        boolean acceptable = false;

        // Test base minecraft acceptable biomes
        acceptable = settings.biomes().contains(biome);

        // Test Terralith Mod acceptable biomes
        if (terralithBiomeSpawning.get()) {
            acceptable = extraSettings.biomesTerra().contains(biome);
        }

        // Test Biomes Of Plenty Mod acceptable biomes
        if (biomesOfPlentyBiomeSpawning.get()) {
            acceptable = extraSettings.biomesBOP().contains(biome);
        }

        if (biomesYoullGoBiomeSpawning.get()) {
            acceptable = extraSettings.biomesBYG().contains(biome);
        }

        return acceptable;
    }
}


