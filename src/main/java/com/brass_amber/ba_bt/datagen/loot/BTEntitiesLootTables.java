package com.brass_amber.ba_bt.datagen.loot;

import com.brass_amber.ba_bt.entity.hostile.golem.BTAbstractGolem;
import com.brass_amber.ba_bt.entity.hostile.golem.BTLandGolem;
import com.brass_amber.ba_bt.init.BTEntityType;
import com.brass_amber.ba_bt.util.GolemType;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.Objects;

public class BTEntitiesLootTables extends EntityLootSubProvider {
    public BTEntitiesLootTables() {
        super(FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    public void generate() {
        this.add(BTEntityType.LAND_GOLEM.get(), getGolemLootTable(GolemType.LAND));
        this.add(BTEntityType.OCEAN_GOLEM.get(), getGolemLootTable(GolemType.OCEAN));
        this.add(BTEntityType.CORE_GOLEM.get(), getGolemLootTable(GolemType.CORE));
        this.add(BTEntityType.NETHER_GOLEM.get(), getGolemLootTable(GolemType.NETHER));
        this.add(BTEntityType.END_GOLEM.get(), getGolemLootTable(GolemType.END));
        this.add(BTEntityType.SKY_GOLEM.get(), getGolemLootTable(GolemType.SKY));
        
    }

    public static LootTable.Builder getGolemLootTable(GolemType type) {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Objects.requireNonNull(GolemType.getEyeFor(type))))
        );
    }
}
