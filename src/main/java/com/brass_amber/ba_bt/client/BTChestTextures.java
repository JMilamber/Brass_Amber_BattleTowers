package com.brass_amber.ba_bt.client;

import com.brass_amber.ba_bt.BABTMain;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(modid = BABTMain.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class BTChestTextures {
	public static final ResourceLocation[] LAND_GOLEM_CHEST_TEXTURES = locateChestTextures("golem", "land_golem");
	public static final ResourceLocation[] LAND_CHEST_TEXTURES = locateChestTextures("tower", "land");
	public static final ResourceLocation[] OCEAN_GOLEM_CHEST_TEXTURES = locateChestTextures("golem", "ocean_golem");
	public static final ResourceLocation[] OCEAN_CHEST_TEXTURES = locateChestTextures("tower", "ocean");
	public static final ResourceLocation[] CORE_GOLEM_CHEST_TEXTURES = locateChestTextures("golem", "core_golem");
	public static final ResourceLocation[] CORE_CHEST_TEXTURES = locateChestTextures("tower", "core");
	public static final ResourceLocation[] NETHER_GOLEM_CHEST_TEXTURES = locateChestTextures("golem", "nether_golem");
	public static final ResourceLocation[] NETHER_CHEST_TEXTURES = locateChestTextures("tower", "nether");
	public static final ResourceLocation[] END_GOLEM_CHEST_TEXTURES = locateChestTextures("golem", "end_golem");
	public static final ResourceLocation[] END_CHEST_TEXTURES = locateChestTextures("tower", "end");
	public static final ResourceLocation[] SKY_GOLEM_CHEST_TEXTURES = locateChestTextures("golem", "sky_golem");
	public static final ResourceLocation[] SKY_CHEST_TEXTURES = locateChestTextures("tower", "sky");


	/**
	 * We need to stitch the textures before we can use them. Otherwise they'll just appear as missing textures.
	 */
	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent()
	public static void textureStitch(TextureStitchEvent event) {
		stitchAll(event, LAND_GOLEM_CHEST_TEXTURES);
		stitchAll(event, LAND_CHEST_TEXTURES);
		stitchAll(event, OCEAN_GOLEM_CHEST_TEXTURES);
		stitchAll(event, OCEAN_CHEST_TEXTURES);
		stitchAll(event, CORE_GOLEM_CHEST_TEXTURES);
		stitchAll(event, CORE_CHEST_TEXTURES);
		stitchAll(event, NETHER_GOLEM_CHEST_TEXTURES);
		stitchAll(event, NETHER_CHEST_TEXTURES);
		stitchAll(event, END_GOLEM_CHEST_TEXTURES);
		stitchAll(event, END_CHEST_TEXTURES);
		stitchAll(event, SKY_GOLEM_CHEST_TEXTURES);
		stitchAll(event, SKY_CHEST_TEXTURES);
	}

	/**
	 * Stitch all textures in the array
	 */
	private static void stitchAll(TextureStitchEvent event, ResourceLocation[] textureLocations) {
		for (ResourceLocation chestTexture : textureLocations) {
			event.getAtlas().getSprite(chestTexture);
		}
	}

	/**
	 * Helper method for new chest textures
	 */
	private static ResourceLocation[] locateChestTextures(String chestType, String chestName) {
		return new ResourceLocation[] {
			BABTMain.locate("entity/chest/" + chestType + "_chest/" + chestName),
			BABTMain.locate("entity/chest/" + chestType + "_chest/" + chestName + "_left"),
			BABTMain.locate("entity/chest/" + chestType + "_chest/" + chestName + "_right")
		};
	}

	public static ModelLayerLocation locateChestLayer(String chestType, String chestName, int side) {
		if (side == 0) {
			return new ModelLayerLocation(BABTMain.locate("entity/chest/" + chestType + "_chest/" + chestName), "main");
		} else if (side == 1) {
			return  new ModelLayerLocation(BABTMain.locate("entity/chest/" + chestType + "_chest/" + chestName + "_left"), "main");
		} else if (side == 2) {
			return  new ModelLayerLocation(BABTMain.locate("entity/chest/" + chestType + "_chest/" + chestName + "_right"), "main");
		}
		return null;
	}
}
