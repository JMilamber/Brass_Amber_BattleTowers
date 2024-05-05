package com.brass_amber.ba_bt.datagen;

import com.brass_amber.ba_bt.datagen.loot.BTBlockLootTables;
import com.brass_amber.ba_bt.datagen.loot.BTEntitiesLootTables;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;

public class BTLootTableProvider {
    public static LootTableProvider create(PackOutput packOutput) {
        return new LootTableProvider(packOutput, Set.of(),
                List.of(
                        new LootTableProvider.SubProviderEntry(BTBlockLootTables::new, LootContextParamSets.BLOCK),
                        new LootTableProvider.SubProviderEntry(BTEntitiesLootTables::new, LootContextParamSets.ENTITY)
                )
        );
    }
}
