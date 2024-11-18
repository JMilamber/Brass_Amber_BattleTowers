package com.brass_amber.ba_bt.datagen;

import com.brass_amber.ba_bt.BABTMain;
import com.brass_amber.ba_bt.init.BTBlocks;
import com.brass_amber.ba_bt.init.BTItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class BTModelProvider extends ItemModelProvider {
    public BTModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, BABTMain.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(BTItems.TAB_ICON);

        simpleExtraFolderItem(BTItems.LAND_MONOLITH_KEY, "monolith_key");
        simpleExtraFolderItem(BTItems.OCEAN_MONOLITH_KEY, "monolith_key");
        simpleExtraFolderItem(BTItems.CORE_MONOLITH_KEY, "monolith_key");
        simpleExtraFolderItem(BTItems.NETHER_MONOLITH_KEY, "monolith_key");
        simpleExtraFolderItem(BTItems.END_MONOLITH_KEY, "monolith_key");
        simpleExtraFolderItem(BTItems.SKY_MONOLITH_KEY, "monolith_key");

        simpleExtraFolderItem(BTItems.LAND_GUARDIAN_EYE, "guardian_eye");
        simpleExtraFolderItem(BTItems.OCEAN_GUARDIAN_EYE, "guardian_eye");
        simpleExtraFolderItem(BTItems.CORE_GUARDIAN_EYE, "guardian_eye");
        simpleExtraFolderItem(BTItems.NETHER_GUARDIAN_EYE, "guardian_eye");
        simpleExtraFolderItem(BTItems.END_GUARDIAN_EYE, "guardian_eye");
        simpleExtraFolderItem(BTItems.SKY_GUARDIAN_EYE, "guardian_eye");

        monolithItem(BTItems.LAND_MONOLITH);
        monolithItem(BTItems.OCEAN_MONOLITH);
        monolithItem(BTItems.CORE_MONOLITH);
        monolithItem(BTItems.NETHER_MONOLITH);
        monolithItem(BTItems.END_MONOLITH);
        monolithItem(BTItems.SKY_MONOLITH);

        simpleItem(BTItems.LAND_CHEST_SHARD);
        simpleItem(BTItems.OCEAN_CHEST_SHARD);
        simpleItem(BTItems.CORE_CHEST_SHARD);
        simpleItem(BTItems.NETHER_CHEST_SHARD);
        simpleItem(BTItems.END_CHEST_SHARD);
        simpleItem(BTItems.SKY_CHEST_SHARD);

        simpleExtraFolderItem(BTItems.LAND_RESONANCE_CRYSTAL, "resonance_crystal");
        simpleExtraFolderItem(BTItems.OCEAN_RESONANCE_CRYSTAL, "resonance_crystal");
        simpleExtraFolderItem(BTItems.CORE_RESONANCE_CRYSTAL, "resonance_crystal");
        simpleExtraFolderItem(BTItems.NETHER_RESONANCE_CRYSTAL, "resonance_crystal");
        simpleExtraFolderItem(BTItems.END_RESONANCE_CRYSTAL, "resonance_crystal");
        simpleExtraFolderItem(BTItems.SKY_RESONANCE_CRYSTAL, "resonance_crystal");
        simpleExtraFolderItem(BTItems.CITY_RESONANCE_CRYSTAL, "resonance_crystal");

    }

    private ItemModelBuilder emptyItem(ResourceLocation location) {
        return getBuilder(location.toString());
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(BABTMain.MODID, "item/" + item.getId().getPath()));
    }

    private ItemModelBuilder simpleExtraFolderItem(RegistryObject<Item> item, String folder) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(BABTMain.MODID, "item/"+ folder + "/" + item.getId().getPath()));
    }

    private ItemModelBuilder resonanceStoneItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/template_music_disc")).texture("texture",
                new ResourceLocation(BABTMain.MODID, "item/resonance_crystal/" + item.getId().getPath()));
    }


    private ItemModelBuilder monolithItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation(BABTMain.MODID, "item/monolith_template")).texture("texture",
                new ResourceLocation(BABTMain.MODID, "item/monolith/" + item.getId().getPath()));
    }
}
