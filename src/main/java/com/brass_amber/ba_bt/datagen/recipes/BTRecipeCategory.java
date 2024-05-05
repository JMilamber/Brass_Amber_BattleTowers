package com.brass_amber.ba_bt.datagen.recipes;

public enum BTRecipeCategory {
    BATTLE_TOWERS("babattletowers"),
    BUILDING_BLOCKS("building_blocks"),
    DECORATIONS("decorations"),
    REDSTONE("redstone"),
    TRANSPORTATION("transportation"),
    TOOLS("tools"),
    COMBAT("combat"),
    FOOD("food"),
    BREWING("brewing"),
    MISC("misc");

    private final String recipeFolderName;

    private BTRecipeCategory(String pRecipeFolderName) {
        this.recipeFolderName = pRecipeFolderName;
    }

    public String getFolderName() {
        return this.recipeFolderName;
    }
}