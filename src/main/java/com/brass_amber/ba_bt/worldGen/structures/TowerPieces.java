package com.brass_amber.ba_bt.worldGen.structures;

import com.brass_amber.ba_bt.BABTMain;
import com.brass_amber.ba_bt.BattleTowersConfig;
import com.brass_amber.ba_bt.init.BTStructurePieces;
import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;
import net.minecraftforge.common.world.PieceBeardifierModifier;
import org.slf4j.Logger;

import java.util.*;

import static com.brass_amber.ba_bt.worldGen.structures.TowerProcessors.*;


public class TowerPieces {
    static final Logger LOGGER = LogUtils.getLogger();

    public static void generateTower(StructureTemplateManager templateManager, BlockPos blockPos, Rotation rotation, List<TowerPiece> towerPieces, RandomSource randomSource, String towerName, String variant) {
        LOGGER.info("Tower name: " + towerName + " Variant: " + variant);
        TowerGenInfo towerGenInfo = TowerGenInfo.getTypeForName(towerName);

        // decide on wall/stair processors (different for each variant)
        List<StructureProcessor> shellProcessors = TowerGenInfo.getShellProcessors(towerGenInfo, variant);
        List<StructureProcessor> variantProcessors = TowerGenInfo.getVariantProcessors(towerGenInfo, variant);

        // Add tower shell to list first so it is generated first
        towerPieces.add(new BasePiece(templateManager, "base", towerName, blockPos, rotation, ""));
        int floorHeight = BattleTowersConfig.landFloorHeight.get();
        int doubledFloorHeight = floorHeight * 2;

        BlockPos rotated;
        // get blockpos for rotated pieces
        rotated = switch(rotation) {
            case NONE -> blockPos.east(28).south(28);
            case CLOCKWISE_90 -> blockPos.south(28).west(28);
            case CLOCKWISE_180 -> blockPos.west(28).north(28);
            case COUNTERCLOCKWISE_90 -> blockPos.north(28).east(28);
        };


        // Move base pos to account for tower base height
        rotated = rotated.above(towerPieces.get(0).getHeight());
        blockPos = blockPos.above(towerPieces.get(0).getHeight());



        for (int i = 0; i < 4; i++) {
            towerPieces.add(new ShellPiece(templateManager, "shell", towerName, rotated.above(i*doubledFloorHeight), rotation.getRotated(Rotation.CLOCKWISE_180), "", shellProcessors, variantProcessors));
            towerPieces.add(new ShellPiece(templateManager, "shell", towerName, blockPos.above(floorHeight + i*doubledFloorHeight), rotation, "", shellProcessors, variantProcessors));
        }
        towerPieces.add(new TowerPiece(templateManager, "top", towerName, blockPos.above(floorHeight*8), rotation, ""));
        // Add shell variant changes (if variant)
        if (!variant.equals("normal")) {
            // TODO add shell variation
        }

        String roomName;
        Map<String, Integer> usedRooms = new HashMap<>();
        Rotation roomRotation;
        BlockPos roomPos;
        int failSafe;

        towerPieces.add(new RoomPiece(templateManager, "entrance", towerName, blockPos, rotation, List.of(NORMAL_FLOOR_LAND),0 ));

        // Add random internal rooms (skipping entry floor)
        for (int i = 1; i < 7; i++) {
            failSafe = 0;
            // Get random room
            do {
                roomName = TowerGenInfo.getRandomRoom(towerGenInfo, randomSource);
                failSafe ++;
                if (failSafe > 80) {
                    roomName = TowerGenInfo.LAND.getRooms().get(0);
                }
                if (usedRooms.isEmpty()) {
                    usedRooms.put(roomName, 1);
                }
            }
            while (usedRooms.getOrDefault(roomName, 0) == 2);

            if (usedRooms.size() < 2) {
                usedRooms.put(roomName, usedRooms.getOrDefault(roomName, 0)+1);
            }

            if ((i & 1) == 0) {
                // even
                roomRotation = rotation.getRotated(Rotation.CLOCKWISE_180);
                roomPos = rotated;
            } else {
                // odd
                roomRotation = rotation;
                roomPos = blockPos;
            }

            towerPieces.add(new RoomPiece(
                    templateManager, roomName, towerName,
                    roomPos.above(i*floorHeight), roomRotation,
                    TowerGenInfo.getRoomProcessors(towerGenInfo, roomName), i
            ));

        }

        towerPieces.add(new RoomPiece(templateManager, "top_floor", towerName, blockPos.above(floorHeight*7), rotation, List.of(CARPET_PLACER), 8));

    }

    public static class TowerPiece extends TemplateStructurePiece {
        protected String towerName;  // Used to find the piece for the correct tower type (Land, Ocean, Etc)
        protected String variant;  // Used to find the pieces for the tower variant. If no variant this will be ""
        public TowerPiece(StructureTemplateManager templateManager, String templateName, String towerName, BlockPos blockPos, Rotation rotation, String variant) {
            super(BTStructurePieces.TOWER_PIECE.get(), 0, templateManager, makeLocation(towerName, templateName, variant), templateName, makeSettings(rotation), blockPos);
            this.towerName = towerName;
            this.variant = variant;
        }

        public TowerPiece(StructureTemplateManager templateManager, CompoundTag compoundTag) {
            super(BTStructurePieces.TOWER_PIECE.get(), compoundTag, templateManager, (resourceLocation) -> {
                return makeSettings(Rotation.valueOf(compoundTag.getString("Rotation")));
            });
            this.towerName = compoundTag.getString("TowerName");
            this.variant = compoundTag.getString("Variant");

        }

        public int getHeight() {
            return this.template.getBoundingBox(this.placeSettings(), this.templatePosition).getYSpan();
        }

        protected ResourceLocation makeTemplateLocation() {
            return makeLocation(this.towerName, this.templateName, this.variant);
        }

        protected static ResourceLocation makeLocation(String towerName, String templateName, String variant) {
            // Example: land_tower/normal/entry
            if (variant == null || variant.isEmpty()) {
                return new ResourceLocation(BABTMain.MODID, towerName + "/" + templateName);
            }
            return new ResourceLocation(BABTMain.MODID, towerName + "/" + variant  + "/" + templateName);
        }


        protected static StructurePlaceSettings makeSettings(Rotation rotation) {
            return (new StructurePlaceSettings()).setIgnoreEntities(true).setRotation(rotation).setMirror(Mirror.NONE).addProcessor(BlockIgnoreProcessor.STRUCTURE_BLOCK);
        }

        protected void addAdditionalSaveData(StructurePieceSerializationContext serializationContext, CompoundTag compoundTag) {
            super.addAdditionalSaveData(serializationContext, compoundTag);
            compoundTag.putString("Rotation", this.placeSettings.getRotation().name());
            compoundTag.putString("TowerName", this.towerName);
            compoundTag.putString("Variant", this.variant);
        }

        @Override
        protected void handleDataMarker(String string, BlockPos blockPos, ServerLevelAccessor serverLevelAccessor, RandomSource randomSource, BoundingBox boundingBox) {

        }
    }

    public static class ShellPiece extends TowerPiece {

        public ShellPiece(
                StructureTemplateManager templateManager, String templateName, String towerName,
                BlockPos blockPos, Rotation rotation, String variant,
                List<StructureProcessor> shellProcessors, List<StructureProcessor> variantProcessors
        ) {
            super(templateManager, templateName, towerName, blockPos, rotation, variant);

            for (StructureProcessor processor : shellProcessors) {
                this.placeSettings.addProcessor(processor);
            }
            for (StructureProcessor processor : variantProcessors) {
                this.placeSettings.addProcessor(processor);
            }
        }

    }
    public static class BasePiece extends TowerPiece implements PieceBeardifierModifier {

        public BasePiece(
                StructureTemplateManager templateManager, String templateName, String towerName,
                BlockPos blockPos, Rotation rotation, String variant
        ) {
            super(templateManager, templateName, towerName, blockPos, rotation, variant);

            this.placeSettings.addProcessor(BASE_PROTECTED);
            this.placeSettings.addProcessor(NORMAL_LAND);
        }

        @Override
        public BoundingBox getBeardifierBox() {
            return this.getBoundingBox().inflatedBy(1);
        }

        @Override
        public TerrainAdjustment getTerrainAdjustment() {
            return TerrainAdjustment.BURY;
        }

        @Override
        public int getGroundLevelDelta() {
            return 3;
        }
    }


    public static class RoomPiece extends TowerPiece {
        public int towerlayer;
        public RoomPiece(StructureTemplateManager templateManager, String templateName, String towerName, BlockPos blockPos, Rotation rotation, List<StructureProcessor> proccessors, int towerLayer) {
            super(templateManager, templateName, towerName, blockPos, rotation, "normal");
            for (StructureProcessor processor : proccessors) {
                this.placeSettings.addProcessor(processor);
            }
            this.towerlayer = towerLayer;
        }

        public RoomPiece(StructureTemplateManager templateManager, CompoundTag compoundTag) {
            super(templateManager, compoundTag);
            this.towerlayer = compoundTag.getInt("TowerLayer");
        }


    }
}
