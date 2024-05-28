package com.brass_amber.ba_bt.init;

import com.brass_amber.ba_bt.BABTMain;
import com.brass_amber.ba_bt.block.blockentity.DataMarkerBlockEntity;
import com.brass_amber.ba_bt.block.blockentity.spawner.*;
import com.brass_amber.ba_bt.block.blockentity.GolemChestBlockEntity;
import com.brass_amber.ba_bt.block.blockentity.TowerChestBlockEntity;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;


public class BTBlockEntityType {
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, BABTMain.MODID);

	public static final RegistryObject<BlockEntityType<GolemChestBlockEntity>> LAND_GOLEM_CHEST = BLOCK_ENTITY_TYPES.register("land_golem_chest", () -> BlockEntityType.Builder.of(GolemChestBlockEntity::new, BTBlocks.LAND_GOLEM_CHEST.get()).build(null));
	public static final RegistryObject<BlockEntityType<TowerChestBlockEntity>> LAND_CHEST = BLOCK_ENTITY_TYPES.register("land_chest", () -> BlockEntityType.Builder.of(TowerChestBlockEntity::new, BTBlocks.LAND_CHEST.get()).build(null));
	public static final RegistryObject<BlockEntityType<GolemChestBlockEntity>> OCEAN_GOLEM_CHEST = BLOCK_ENTITY_TYPES.register("ocean_golem_chest", () -> BlockEntityType.Builder.of(GolemChestBlockEntity::new, BTBlocks.OCEAN_GOLEM_CHEST.get()).build(null));
	public static final RegistryObject<BlockEntityType<TowerChestBlockEntity>> OCEAN_CHEST = BLOCK_ENTITY_TYPES.register("ocean_chest", () -> BlockEntityType.Builder.of(TowerChestBlockEntity::new, BTBlocks.OCEAN_CHEST.get()).build(null));
	public static final RegistryObject<BlockEntityType<GolemChestBlockEntity>> CORE_GOLEM_CHEST = BLOCK_ENTITY_TYPES.register("core_golem_chest", () -> BlockEntityType.Builder.of(GolemChestBlockEntity::new, BTBlocks.CORE_GOLEM_CHEST.get()).build(null));
	public static final RegistryObject<BlockEntityType<TowerChestBlockEntity>> CORE_CHEST = BLOCK_ENTITY_TYPES.register("core_chest", () -> BlockEntityType.Builder.of(TowerChestBlockEntity::new, BTBlocks.CORE_CHEST.get()).build(null));
	public static final RegistryObject<BlockEntityType<GolemChestBlockEntity>> NETHER_GOLEM_CHEST = BLOCK_ENTITY_TYPES.register("nether_golem_chest", () -> BlockEntityType.Builder.of(GolemChestBlockEntity::new, BTBlocks.NETHER_GOLEM_CHEST.get()).build(null));
	public static final RegistryObject<BlockEntityType<TowerChestBlockEntity>> NETHER_CHEST = BLOCK_ENTITY_TYPES.register("nether_chest", () -> BlockEntityType.Builder.of(TowerChestBlockEntity::new, BTBlocks.NETHER_CHEST.get()).build(null));
	public static final RegistryObject<BlockEntityType<GolemChestBlockEntity>> END_GOLEM_CHEST = BLOCK_ENTITY_TYPES.register("end_golem_chest", () -> BlockEntityType.Builder.of(GolemChestBlockEntity::new, BTBlocks.END_GOLEM_CHEST.get()).build(null));
	public static final RegistryObject<BlockEntityType<TowerChestBlockEntity>> END_CHEST = BLOCK_ENTITY_TYPES.register("end_chest", () -> BlockEntityType.Builder.of(TowerChestBlockEntity::new, BTBlocks.END_CHEST.get()).build(null));
	public static final RegistryObject<BlockEntityType<GolemChestBlockEntity>> SKY_GOLEM_CHEST = BLOCK_ENTITY_TYPES.register("sky_golem_chest", () -> BlockEntityType.Builder.of(GolemChestBlockEntity::new, BTBlocks.SKY_GOLEM_CHEST.get()).build(null));
	public static final RegistryObject<BlockEntityType<TowerChestBlockEntity>> SKY_CHEST = BLOCK_ENTITY_TYPES.register("sky_chest", () -> BlockEntityType.Builder.of(TowerChestBlockEntity::new, BTBlocks.SKY_CHEST.get()).build(null));

	public static final RegistryObject<BlockEntityType<BTLandSpawnerEntity>> LAND_MOB_SPAWNER = BLOCK_ENTITY_TYPES.register("l_spawner", () -> BlockEntityType.Builder.of(BTLandSpawnerEntity::new, BTBlocks.LAND_SPAWNER.get()).build(null));
	public static final RegistryObject<BlockEntityType<BTOceanSpawnerEntity>> OCEAN_MOB_SPAWNER = BLOCK_ENTITY_TYPES.register("o_spawner", () -> BlockEntityType.Builder.of(BTOceanSpawnerEntity::new, BTBlocks.OCEAN_SPAWNER.get()).build(null));
	public static final RegistryObject<BlockEntityType<BTCoreSpawnerEntity>> CORE_MOB_SPAWNER = BLOCK_ENTITY_TYPES.register("c_spawner", () -> BlockEntityType.Builder.of(BTCoreSpawnerEntity::new, BTBlocks.CORE_SPAWNER.get()).build(null));
	public static final RegistryObject<BlockEntityType<BTNetherSpawnerEntity>> NETHER_MOB_SPAWNER = BLOCK_ENTITY_TYPES.register("n_spawner", () -> BlockEntityType.Builder.of(BTNetherSpawnerEntity::new, BTBlocks.NETHER_SPAWNER.get()).build(null));
	public static final RegistryObject<BlockEntityType<BTEndSpawnerEntity>> END_MOB_SPAWNER = BLOCK_ENTITY_TYPES.register("e_spawner", () -> BlockEntityType.Builder.of(BTEndSpawnerEntity::new, BTBlocks.END_SPAWNER.get()).build(null));
	public static final RegistryObject<BlockEntityType<BTSkySpawnerEntity>> SKY_MOB_SPAWNER = BLOCK_ENTITY_TYPES.register("s_spawner", () -> BlockEntityType.Builder.of(BTSkySpawnerEntity::new, BTBlocks.SKY_SPAWNER.get()).build(null));

	public static final RegistryObject<BlockEntityType<DataMarkerBlockEntity>> DATA_MARKER = BLOCK_ENTITY_TYPES.register("data_marker", () -> BlockEntityType.Builder.of(DataMarkerBlockEntity::new).build(null));

	public static void register(IEventBus eventBus) {
		BLOCK_ENTITY_TYPES.register(eventBus);
	}

}
