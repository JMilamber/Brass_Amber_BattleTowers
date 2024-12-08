package com.brass_amber.ba_bt.sound;

import com.brass_amber.ba_bt.BABTMain;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;


public class BTSoundEvents {

	public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Registries.SOUND_EVENT, BABTMain.MODID);

	public static final SoundEvent ENTITY_GOLEM_HURT = registerSoundEvent("entity.golem.hurt");
	public static final SoundEvent ENTITY_GOLEM_DEATH = registerSoundEvent("entity.golem.death");
	public static final SoundEvent ENTITY_GOLEM_CHARGE = registerSoundEvent("entity.golem.charge");
	public static final SoundEvent ENTITY_GOLEM_AWAKEN = registerSoundEvent("entity.golem.awaken");
	public static final SoundEvent ENTITY_GOLEM_AMBIENT = registerSoundEvent("entity.golem.ambient");
	public static final SoundEvent ENTITY_GOLEM_SPECIAL = registerSoundEvent("entity.golem.special");

	public static final SoundEvent MUSIC_LAND_GOLEM_FIGHT = registerSoundEvent("entity.golem.land.fight");
	public static final SoundEvent MUSIC_OCEAN_GOLEM_FIGHT = registerSoundEvent("entity.golem.ocean.fight");
	public static final SoundEvent MUSIC_CORE_GOLEM_FIGHT = registerSoundEvent("entity.golem.core.fight");
	public static final SoundEvent MUSIC_NETHER_GOLEM_FIGHT = registerSoundEvent("entity.golem.nether.fight");
	public static final SoundEvent MUSIC_END_GOLEM_FIGHT = registerSoundEvent("entity.golem.end.fight");
	public static final SoundEvent MUSIC_SKY_GOLEM_FIGHT = registerSoundEvent("entity.golem.sky.fight");

	public static final SoundEvent MONOLITH_SPAWN_GOLEM = registerSoundEvent("monolith.spawn.golem");

	public static final SoundEvent TOWER_BREAK_START = registerSoundEvent("tower.break.start");
	public static final SoundEvent TOWER_BREAK_CRUMBLE = registerSoundEvent("tower.break.crumble");
	public static final SoundEvent TOWER_COLLAPSE = registerSoundEvent("tower.break.collapse");

	public static final SoundEvent MUSIC_LAND_TOWER = registerSoundEvent("tower.ambient.music.land");
	public static final SoundEvent MUSIC_OCEAN_TOWER = registerSoundEvent("tower.ambient.music.ocean");
	public static final SoundEvent MUSIC_CORE_TOWER = registerSoundEvent("tower.ambient.music.core");
	public static final SoundEvent MUSIC_NETHER_TOWER = registerSoundEvent("tower.ambient.music.nether");
	public static final SoundEvent MUSIC_END_TOWER = registerSoundEvent("tower.ambient.music.end");
	public static final SoundEvent MUSIC_SKY_TOWER = registerSoundEvent("tower.ambient.music.sky");
	public static final SoundEvent MUSIC_CITY = registerSoundEvent("tower.ambient.music.city");

	public static final Music TOWER_COLLAPSE_MUSIC = new Music(Holder.direct(TOWER_COLLAPSE), 1500, 2100, false);

	public static final Music LAND_TOWER_MUSIC = new Music(Holder.direct(MUSIC_LAND_TOWER), 3940, 4240, false);
	public static final Music LAND_GOLEM_FIGHT_MUSIC = new Music(Holder.direct(MUSIC_LAND_GOLEM_FIGHT), 2340, 2640, false);
	public static final Music OCEAN_TOWER_MUSIC = new Music(Holder.direct(MUSIC_OCEAN_TOWER), 4780, 5070, false);
	public static final Music OCEAN_GOLEM_FIGHT_MUSIC = new Music(Holder.direct(MUSIC_OCEAN_GOLEM_FIGHT), 1980, 2280, false);
	public static final Music CORE_TOWER_MUSIC = new Music(Holder.direct(MUSIC_CORE_TOWER), 4440, 4740, false);
	public static final Music CORE_GOLEM_FIGHT_MUSIC = new Music(Holder.direct(MUSIC_CORE_GOLEM_FIGHT), 2400, 2700, false);
	public static final Music NETHER_TOWER_MUSIC = new Music(Holder.direct(MUSIC_NETHER_TOWER), 3880, 4180, false);
	public static final Music NETHER_GOLEM_FIGHT_MUSIC = new Music(Holder.direct(MUSIC_NETHER_GOLEM_FIGHT), 2440, 2740, false);
	public static final Music END_TOWER_MUSIC = new Music(Holder.direct(MUSIC_END_TOWER), 7680, 7980, false);
	public static final Music END_GOLEM_FIGHT_MUSIC = new Music(Holder.direct(MUSIC_END_GOLEM_FIGHT), 2980, 3280, false);
	public static final Music SKY_TOWER_MUSIC = new Music(Holder.direct(MUSIC_SKY_TOWER), 3940, 4240, false);
	public static final Music SKY_GOLEM_FIGHT_MUSIC = new Music(Holder.direct(MUSIC_SKY_GOLEM_FIGHT), 2040, 2340, false);
	public static final Music CITY_TOWER_MUSIC = new Music(Holder.direct(MUSIC_CITY), 6560, 6860, false);


	/**
	 * Helper method for registering all SoundEvents
	 */
	private static SoundEvent registerSoundEvent(String registryName) {
		SoundEvent soundEvent = SoundEvent.createFixedRangeEvent(BABTMain.locate(registryName), 50);
		SOUND_EVENTS.register(registryName, () -> soundEvent);
		return soundEvent;
	}

	public static void register(IEventBus eventBus) {
		SOUND_EVENTS.register(eventBus);
	}

}
