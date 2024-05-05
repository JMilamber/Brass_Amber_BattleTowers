package com.brass_amber.ba_bt.init;

import com.brass_amber.ba_bt.BABTMain;
import com.brass_amber.ba_bt.item.item.*;
import com.brass_amber.ba_bt.util.GolemType;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;


public class BTItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, BABTMain.MODID);

	public static final RegistryObject<Item> TAB_ICON = ITEMS.register("tab_icon",() -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistryObject<Item> LAND_MONOLITH_KEY = ITEMS.register("land_monolith_key", () -> new MonolithKeyItem(new Item.Properties().stacksTo(16).rarity(Rarity.RARE)));
	public static final RegistryObject<Item> OCEAN_MONOLITH_KEY = ITEMS.register("ocean_monolith_key", () -> new MonolithKeyItem(new Item.Properties().stacksTo(16).rarity(Rarity.RARE)));
	public static final RegistryObject<Item> CORE_MONOLITH_KEY = ITEMS.register("core_monolith_key", () -> new MonolithKeyItem(new Item.Properties().stacksTo(16).rarity(Rarity.RARE)));
	public static final RegistryObject<Item> NETHER_MONOLITH_KEY = ITEMS.register("nether_monolith_key", () -> new MonolithKeyItem(new Item.Properties().stacksTo(16).rarity(Rarity.RARE)));
	public static final RegistryObject<Item> END_MONOLITH_KEY = ITEMS.register("end_monolith_key", () -> new MonolithKeyItem(new Item.Properties().stacksTo(16).rarity(Rarity.RARE)));
	public static final RegistryObject<Item> SKY_MONOLITH_KEY = ITEMS.register("sky_monolith_key", () -> new MonolithKeyItem(new Item.Properties().stacksTo(16).rarity(Rarity.RARE)));

	public static final RegistryObject<Item> LAND_GUARDIAN_EYE = ITEMS.register("guardian_eye_land", () -> new GuardianEyeItem(GolemType.LAND, (new Item.Properties()).stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> OCEAN_GUARDIAN_EYE = ITEMS.register("guardian_eye_ocean", () -> new GuardianEyeItem(GolemType.OCEAN, (new Item.Properties()).stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> CORE_GUARDIAN_EYE = ITEMS.register("guardian_eye_core", () -> new GuardianEyeItem(GolemType.CORE, (new Item.Properties()).stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> NETHER_GUARDIAN_EYE = ITEMS.register("guardian_eye_nether", () -> new GuardianEyeItem(GolemType.NETHER, (new Item.Properties()).stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> END_GUARDIAN_EYE = ITEMS.register("guardian_eye_end", () -> new GuardianEyeItem(GolemType.END, (new Item.Properties()).stacksTo(8).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> SKY_GUARDIAN_EYE = ITEMS.register("guardian_eye_sky", () -> new GuardianEyeItem(GolemType.SKY, (new Item.Properties()).stacksTo(8).rarity(Rarity.EPIC)));

	public static final RegistryObject<Item> LAND_MONOLITH = ITEMS.register("land_monolith", () -> new MonolithItem(GolemType.LAND, (new Item.Properties()).stacksTo(1).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> OCEAN_MONOLITH = ITEMS.register("ocean_monolith", () -> new MonolithItem(GolemType.OCEAN, (new Item.Properties()).stacksTo(1).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> CORE_MONOLITH = ITEMS.register("core_monolith", () -> new MonolithItem(GolemType.CORE, (new Item.Properties()).stacksTo(1).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> NETHER_MONOLITH = ITEMS.register("nether_monolith", () -> new MonolithItem(GolemType.NETHER, (new Item.Properties()).stacksTo(1).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> END_MONOLITH = ITEMS.register("end_monolith", () -> new MonolithItem(GolemType.END, (new Item.Properties()).stacksTo(1).rarity(Rarity.EPIC)));
	public static final RegistryObject<Item> SKY_MONOLITH = ITEMS.register("sky_monolith", () -> new MonolithItem(GolemType.SKY, (new Item.Properties()).stacksTo(1).rarity(Rarity.EPIC)));

	public static final RegistryObject<Item> LAND_CHEST_SHARD = ITEMS.register("land_chest_shard", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistryObject<Item> OCEAN_CHEST_SHARD = ITEMS.register("ocean_chest_shard", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistryObject<Item> CORE_CHEST_SHARD = ITEMS.register("core_chest_shard", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistryObject<Item> NETHER_CHEST_SHARD = ITEMS.register("nether_chest_shard", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistryObject<Item> END_CHEST_SHARD = ITEMS.register("end_chest_shard", () -> new Item(new Item.Properties().stacksTo(16)));
	public static final RegistryObject<Item> SKY_CHEST_SHARD = ITEMS.register("sky_chest_shard", () -> new Item(new Item.Properties().stacksTo(16)));

	public static final RegistryObject<Item> LAND_RESONANCE_CRYSTAL = ITEMS.register("land_resonance_stone", () -> new ResonanceStoneItem(GolemType.LAND.getSerializedName(), new Item.Properties().stacksTo(1),7200));
	public static final RegistryObject<Item> OCEAN_RESONANCE_CRYSTAL = ITEMS.register("ocean_resonance_stone", () -> new ResonanceStoneItem(GolemType.OCEAN.getSerializedName(), new Item.Properties().stacksTo(1), 8400));
	public static final RegistryObject<Item> CORE_RESONANCE_CRYSTAL = ITEMS.register("core_resonance_stone", () -> new ResonanceStoneItem(GolemType.CORE.getSerializedName(), new Item.Properties().stacksTo(1), 5700));
	public static final RegistryObject<Item> NETHER_RESONANCE_CRYSTAL = ITEMS.register("nether_resonance_stone", () -> new ResonanceStoneItem(GolemType.NETHER.getSerializedName(), new Item.Properties().stacksTo(1), 3300));
	public static final RegistryObject<Item> END_RESONANCE_CRYSTAL = ITEMS.register("end_resonance_stone", () -> new ResonanceStoneItem(GolemType.END.getSerializedName(), new Item.Properties().stacksTo(1), 7800));
	public static final RegistryObject<Item> SKY_RESONANCE_CRYSTAL = ITEMS.register("sky_resonance_stone", () -> new ResonanceStoneItem(GolemType.SKY.getSerializedName(), new Item.Properties().stacksTo(1), 4200));
	public static final RegistryObject<Item> CITY_RESONANCE_CRYSTAL = ITEMS.register("city_resonance_stone", () -> new ResonanceStoneItem(GolemType.CITY.getSerializedName(), new Item.Properties().stacksTo(1), 2100));

	public static void register(IEventBus eventBus) {
		ITEMS.register(eventBus);
	}

}