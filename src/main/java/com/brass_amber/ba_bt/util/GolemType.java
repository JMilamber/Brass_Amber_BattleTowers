package com.brass_amber.ba_bt.util;

import javax.annotation.Nullable;

import com.brass_amber.ba_bt.client.BTChestTextures;
import com.brass_amber.ba_bt.entity.LandDestructionEntity;
import com.brass_amber.ba_bt.entity.OceanDestructionEntity;
import com.brass_amber.ba_bt.entity.block.BTAbstractObelisk;
import com.brass_amber.ba_bt.entity.block.BTMonolith;
import com.brass_amber.ba_bt.entity.hostile.BTCultist;
import com.brass_amber.ba_bt.init.BTBlockEntityType;
import com.brass_amber.ba_bt.init.BTEntityType;
import com.brass_amber.ba_bt.init.BTItems;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public enum GolemType implements StringRepresentable {
	EMPTY("empty", Component.literal("Empty")),
	LAND("land", Component.translatable("entity.ba_bt.land_golem")),
	OCEAN("ocean", Component.translatable("entity.ba_bt.ocean_golem")),
	CORE("core", Component.translatable("entity.ba_bt.core_golem")),
	NETHER("nether", Component.translatable("entity.ba_bt.nether_golem")),
	END("end", Component.translatable("entity.ba_bt.end_golem")),
	SKY("sky", Component.translatable("entity.ba_bt.sky_golem")),
	CITY("city", Component.literal("~"));

	private final String name;
	private final Component displayName;

	GolemType(String name, Component displayName) {
		this.name = name;
		this.displayName = displayName;
	}

	/*********************************************************** Monolith Spawning ********************************************************/

	/**
	 * Get the correct Monolith key for the Correct Monolith Entity
	 */
	public static @NotNull EntityType<?> getGolemFor(GolemType golemType) {
		return switch (golemType) {
			case EMPTY, CITY, LAND -> BTEntityType.LAND_GOLEM.get();
			case OCEAN -> BTEntityType.OCEAN_GOLEM.get();
			case CORE -> BTEntityType.CORE_GOLEM.get();
			case NETHER -> BTEntityType.NETHER_GOLEM.get();
			case END -> BTEntityType.END_GOLEM.get();
			case SKY -> BTEntityType.SKY_GOLEM.get();
		};
	}

	/**
	 * Get the correct Monolith key for the Correct Monolith Entity
	 */
	public static @NotNull EntityType<BTMonolith> getMonolithFor(GolemType golemType) {
		return switch (golemType) {
			case EMPTY, CITY, LAND -> BTEntityType.LAND_MONOLITH.get();
			case OCEAN -> BTEntityType.OCEAN_MONOLITH.get();
			case CORE -> BTEntityType.CORE_MONOLITH.get();
			case NETHER -> BTEntityType.NETHER_MONOLITH.get();
			case END -> BTEntityType.END_MONOLITH.get();
			case SKY -> BTEntityType.SKY_MONOLITH.get();
		};
	}

	/*********************************************************** Obelisk ********************************************************/

	/**
	 * Get the correct Obelisk for the Golem Type.
	 */
	@NotNull
	public static EntityType<BTAbstractObelisk> getObeliskFor(GolemType golemType) {
		return switch (golemType) {
			case EMPTY, CITY, LAND -> BTEntityType.LAND_OBELISK.get();
			case OCEAN -> BTEntityType.OCEAN_OBELISK.get();
			case CORE -> BTEntityType.CORE_OBELISK.get();
			case NETHER -> BTEntityType.NETHER_OBELISK.get();
			case END -> BTEntityType.END_OBELISK.get();
			case SKY -> BTEntityType.SKY_OBELISK.get();
		};
	}

	@NotNull
	public static GolemType getTypeForObelisk(BTAbstractObelisk entity) {
		EntityType<?> entityType = entity.getType();
        if (entityType.equals(BTEntityType.LAND_OBELISK.get())) {
            return LAND;
        } else if (entityType.equals(BTEntityType.OCEAN_OBELISK.get())) {
            return OCEAN;
        } else if (entityType.equals(BTEntityType.CORE_OBELISK.get())) {
            return CORE;
        } else if (entityType.equals(BTEntityType.NETHER_OBELISK.get())) {
            return NETHER;
        } else if (entityType.equals(BTEntityType.END_OBELISK.get())) {
            return END;
        } else if (entityType.equals(BTEntityType.SKY_OBELISK.get())) {
            return SKY;
        }
		return EMPTY;
    }


	/*********************************************************** Monolith ********************************************************/

	/**
	 * Get the correct Monolith Item for the Correct Monolith Entity.
	 */

	public static Item getMonolithItemFor(GolemType golemType) {
		return switch (golemType) {
			default -> BTItems.LAND_MONOLITH.get();
			case OCEAN -> BTItems.OCEAN_MONOLITH.get();
			case CORE -> BTItems.CORE_MONOLITH.get();
			case NETHER -> BTItems.NETHER_MONOLITH.get();
			case END -> BTItems.END_MONOLITH.get();
			case SKY -> BTItems.SKY_MONOLITH.get();
		};
	}

	/**
	 * Return the correct GolemType for each Monolith Entity.
	 */
	public static GolemType getTypeForMonolith(BTMonolith BTMonolithEntity) {
		EntityType<?> entityType = BTMonolithEntity.getMonolithType();
		if (entityType != null) {
			if (entityType.equals(BTEntityType.LAND_MONOLITH.get())) {
				return LAND;
			} else if (entityType.equals(BTEntityType.OCEAN_MONOLITH.get())) {
				return OCEAN;
			} else if (entityType.equals(BTEntityType.CORE_MONOLITH.get())) {
				return CORE;
			}else if (entityType.equals(BTEntityType.NETHER_MONOLITH.get())) {
				return NETHER;
			}  else if (entityType.equals(BTEntityType.END_MONOLITH.get())) {
				return END;
			} else if (entityType.equals(BTEntityType.SKY_MONOLITH.get())) {
				return SKY;
			}
		}

		// Couldn't get EntityType
		return EMPTY;
	}

	/*********************************************************** Eyes ********************************************************/

	/**
	 * Return the matching Guardian Eye for each GolemType.
	 */
	@Nullable
	public static Item getEyeFor(GolemType golemType) {
		return switch (golemType) {
			case EMPTY, CITY -> null;
			case LAND -> BTItems.LAND_GUARDIAN_EYE.get();
			case OCEAN -> BTItems.OCEAN_GUARDIAN_EYE.get();
			case CORE -> BTItems.CORE_GUARDIAN_EYE.get();
			case NETHER -> BTItems.NETHER_GUARDIAN_EYE.get();
			case END -> BTItems.END_GUARDIAN_EYE.get();
			case SKY -> BTItems.SKY_GUARDIAN_EYE.get();
		};
	}

	/**
	 * Return the previous GolemType in fighting order.
	 */

	public static GolemType getPreviousGolemType(GolemType golemType) {
		return switch (golemType) {
			default -> EMPTY;
			case OCEAN -> LAND;
			case CORE -> OCEAN;
			case NETHER -> CORE;
			case END -> NETHER;
			case SKY -> END;
		};
	}

	/*********************************************************** Keys ********************************************************/

	@Nullable
	public static Item getKeyFor(GolemType golemType) {
		return switch (golemType) {
			default -> null;
			case LAND -> BTItems.LAND_MONOLITH_KEY.get();
			case OCEAN -> BTItems.OCEAN_MONOLITH_KEY.get();
			case CORE -> BTItems.CORE_MONOLITH_KEY.get();
			case NETHER -> BTItems.NETHER_MONOLITH_KEY.get();
			case END -> BTItems.END_MONOLITH_KEY.get();
			case SKY -> BTItems.SKY_MONOLITH_KEY.get();
		};
	}

	/*********************************************************** Extra ********************************************************/

	public static GolemType getTypeForName(String name) {
		return switch (name) {
			default -> null;
			case "land" -> LAND;
			case "ocean" -> OCEAN;
			case "core" -> CORE;
			case "nether" -> NETHER;
			case "end" -> END;
			case "sky" -> SKY;
			case "city" -> CITY;
		};
	}

	public static Entity getDestructionEntity(GolemType golemType, Level level, BlockPos blockPos) {
		Entity destruction = null;
		switch (golemType) {
			default -> {
			}
			case LAND -> {
				destruction = new LandDestructionEntity(blockPos, level);
				blockPos = blockPos.above(6);
			}
			case OCEAN -> {
				destruction = new OceanDestructionEntity(level);
				blockPos = blockPos.atY(level.getSeaLevel() - 88);
			}
		}
		if (destruction != null) {
			destruction.setPos(blockPos.getX() + .25D, blockPos.getY(), blockPos.getZ() + .25D);
		}

		return destruction;
	}

	public static Entity getSpecialEnemy(GolemType golemType, ServerLevel serverLevel) {
		return switch (golemType) {
			default -> null;
			case LAND -> BTEntityType.BT_CULTIST.get().create(serverLevel);
			case OCEAN -> EntityType.GUARDIAN.create(serverLevel);
			case CORE -> EntityType.MAGMA_CUBE.create(serverLevel);
			case NETHER -> EntityType.WITHER_SKELETON.create(serverLevel);
			case END -> EntityType.ENDERMAN.create(serverLevel);
			case SKY -> EntityType.SKELETON.create(serverLevel);
		};
	}

	public static EntityType<?> getSpecialEnemyType(GolemType golemType) {
		return switch (golemType) {
			default -> null;
			case LAND -> BTEntityType.BT_CULTIST.get();
			case OCEAN -> EntityType.GUARDIAN;
			case CORE -> EntityType.MAGMA_CUBE;
			case NETHER -> EntityType.WITHER_SKELETON;
			case END -> EntityType.ENDERMAN;
			case SKY -> EntityType.SKELETON;
		};
	}

	public static Class<? extends Entity> getSpecialEnemyClass(GolemType golemType) {
		return switch (golemType) {
			default -> null;
			case LAND -> BTCultist.class;
			case OCEAN -> Guardian.class;
			case CORE -> MagmaCube.class;
			case NETHER -> WitherSkeleton.class;
			case END -> EnderMan.class;
			case SKY -> Skeleton.class;
		};
	}

	public static Integer getNumForType(GolemType golemType) {
		return switch (golemType) {
			default -> null;
			case LAND -> 0;
			case OCEAN -> 1;
			case CORE -> 2;
			case NETHER -> 3;
			case END -> 4;
			case SKY -> 5;
			case CITY -> 6;
		};
	}
	public static Item getResonanceCrystalForType(GolemType golemType) {
		return switch (golemType) {
			default -> null;
			case LAND -> BTItems.LAND_RESONANCE_CRYSTAL.get();
			case OCEAN -> BTItems.OCEAN_RESONANCE_CRYSTAL.get();
			case CORE -> BTItems.CORE_RESONANCE_CRYSTAL.get();
			case NETHER -> BTItems.NETHER_RESONANCE_CRYSTAL.get();
			case END -> BTItems.END_RESONANCE_CRYSTAL.get();
			case SKY -> BTItems.SKY_RESONANCE_CRYSTAL.get();
			case CITY -> BTItems.CITY_RESONANCE_CRYSTAL.get();
		};
	}

	@Override
	public @NotNull String getSerializedName() {
		return this.name;
	}

	public Component getDisplayName() {
		return this.displayName;
	}

    public static Supplier<BlockEntityType<? extends ChestBlockEntity>> getGolemChestType(GolemType golemType) {
		return switch (golemType) {
			default -> BTBlockEntityType.LAND_GOLEM_CHEST::get;
			case OCEAN -> BTBlockEntityType.OCEAN_GOLEM_CHEST::get;
			case CORE -> BTBlockEntityType.CORE_GOLEM_CHEST::get;
			case NETHER -> BTBlockEntityType.NETHER_GOLEM_CHEST::get;
			case END -> BTBlockEntityType.END_GOLEM_CHEST::get;
			case SKY -> BTBlockEntityType.SKY_GOLEM_CHEST::get;
		};
    }

	public static Supplier<BlockEntityType<? extends ChestBlockEntity>> getChestType(GolemType golemType) {
		return switch (golemType) {
			default  -> BTBlockEntityType.LAND_CHEST::get;
			case OCEAN -> BTBlockEntityType.OCEAN_CHEST::get;
			case CORE -> BTBlockEntityType.CORE_CHEST::get;
			case NETHER -> BTBlockEntityType.NETHER_CHEST::get;
			case END -> BTBlockEntityType.END_CHEST::get;
			case SKY -> BTBlockEntityType.SKY_CHEST::get;
		};
	}

	public static ResourceLocation[] getGolemChestTextures(GolemType golemType) {
		return switch (golemType) {
			default  -> BTChestTextures.LAND_CHEST_TEXTURES;
			case OCEAN -> BTChestTextures.OCEAN_CHEST_TEXTURES;
			case CORE -> BTChestTextures.CORE_CHEST_TEXTURES;
			case NETHER -> BTChestTextures.NETHER_CHEST_TEXTURES;
			case END -> BTChestTextures.END_CHEST_TEXTURES;
			case SKY -> BTChestTextures.SKY_CHEST_TEXTURES;
		};
	}

	public static ResourceLocation[] getChestTextures(GolemType golemType) {
		return switch (golemType) {
			default  -> BTChestTextures.LAND_GOLEM_CHEST_TEXTURES;
			case OCEAN -> BTChestTextures.OCEAN_GOLEM_CHEST_TEXTURES;
			case CORE -> BTChestTextures.CORE_GOLEM_CHEST_TEXTURES;
			case NETHER -> BTChestTextures.NETHER_GOLEM_CHEST_TEXTURES;
			case END -> BTChestTextures.END_GOLEM_CHEST_TEXTURES;
			case SKY -> BTChestTextures.SKY_GOLEM_CHEST_TEXTURES;
		};
	}
}
