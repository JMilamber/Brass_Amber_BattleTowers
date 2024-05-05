package com.brass_amber.ba_bt.datagen.recipes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;

import net.minecraft.world.level.ItemLike;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Consumer;

public class BTShapedRecipeBuilder extends CraftingRecipeBuilder implements RecipeBuilder {
    private final BTRecipeCategory category;
    private final Item result;
    private final int count;
    private final List<String> rows = Lists.newArrayList();
    private final Map<Character, Ingredient> key = Maps.newLinkedHashMap();
    private final Advancement.Builder advancement = Advancement.Builder.recipeAdvancement();
    @Nullable
    private String group;
    private boolean showNotification = true;

    public BTShapedRecipeBuilder(BTRecipeCategory recipeCategory, ItemLike itemLike, int count) {
        this.category = recipeCategory;
        this.result = itemLike.asItem();
        this.count = count;
    }

    public static BTShapedRecipeBuilder shaped(BTRecipeCategory p_250853_, ItemLike p_249747_) {
        return shaped(p_250853_, p_249747_, 1);
    }

    public static BTShapedRecipeBuilder shaped(BTRecipeCategory p_251325_, ItemLike p_250636_, int p_249081_) {
        return new BTShapedRecipeBuilder(p_251325_, p_250636_, p_249081_);
    }

    public BTShapedRecipeBuilder define(Character p_206417_, TagKey<Item> p_206418_) {
        return this.define(p_206417_, Ingredient.of(p_206418_));
    }

    public BTShapedRecipeBuilder define(Character p_126128_, ItemLike p_126129_) {
        return this.define(p_126128_, Ingredient.of(p_126129_));
    }

    public BTShapedRecipeBuilder define(Character p_126125_, Ingredient p_126126_) {
        if (this.key.containsKey(p_126125_)) {
            throw new IllegalArgumentException("Symbol '" + p_126125_ + "' is already defined!");
        } else if (p_126125_ == ' ') {
            throw new IllegalArgumentException("Symbol ' ' (whitespace) is reserved and cannot be defined");
        } else {
            this.key.put(p_126125_, p_126126_);
            return this;
        }
    }

    public BTShapedRecipeBuilder pattern(String p_126131_) {
        if (!this.rows.isEmpty() && p_126131_.length() != this.rows.get(0).length()) {
            throw new IllegalArgumentException("Pattern must be the same width on every line!");
        } else {
            this.rows.add(p_126131_);
            return this;
        }
    }

    public BTShapedRecipeBuilder unlockedBy(String p_126133_, CriterionTriggerInstance p_126134_) {
        this.advancement.addCriterion(p_126133_, p_126134_);
        return this;
    }

    public BTShapedRecipeBuilder group(@Nullable String p_126146_) {
        this.group = p_126146_;
        return this;
    }

    public BTShapedRecipeBuilder showNotification(boolean p_273326_) {
        this.showNotification = p_273326_;
        return this;
    }

    public Item getResult() {
        return this.result;
    }

    public void save(Consumer<FinishedRecipe> p_126141_, ResourceLocation p_126142_) {
        this.ensureValid(p_126142_);
        this.advancement.parent(ROOT_RECIPE_ADVANCEMENT).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(p_126142_)).rewards(AdvancementRewards.Builder.recipe(p_126142_)).requirements(RequirementsStrategy.OR);
        p_126141_.accept(new ShapedRecipeBuilder.Result(p_126142_, this.result, this.count, this.group == null ? "" : this.group, determineBookCategory(this.category), this.rows, this.key, this.advancement, p_126142_.withPrefix("recipes/" + this.category.getFolderName() + "/"), this.showNotification));
    }

    private void ensureValid(ResourceLocation p_126144_) {
        if (this.rows.isEmpty()) {
            throw new IllegalStateException("No pattern is defined for shaped recipe " + p_126144_ + "!");
        } else {
            Set<Character> set = Sets.newHashSet(this.key.keySet());
            set.remove(' ');

            for(String s : this.rows) {
                for(int i = 0; i < s.length(); ++i) {
                    char c0 = s.charAt(i);
                    if (!this.key.containsKey(c0) && c0 != ' ') {
                        throw new IllegalStateException("Pattern in recipe " + p_126144_ + " uses undefined symbol '" + c0 + "'");
                    }

                    set.remove(c0);
                }
            }

            if (!set.isEmpty()) {
                throw new IllegalStateException("Ingredients are defined but not used in pattern for recipe " + p_126144_);
            } else if (this.rows.size() == 1 && this.rows.get(0).length() == 1) {
                throw new IllegalStateException("Shaped recipe " + p_126144_ + " only takes in a single item - should it be a shapeless recipe instead?");
            } else if (this.advancement.getCriteria().isEmpty()) {
                throw new IllegalStateException("No way of obtaining recipe " + p_126144_);
            }
        }
    }

    static CraftingBookCategory determineBookCategory(BTRecipeCategory pCategory) {
        CraftingBookCategory var10000;
        switch (pCategory) {
            case BUILDING_BLOCKS:
                var10000 = CraftingBookCategory.BUILDING;
                break;
            case TOOLS:
            case COMBAT:
                var10000 = CraftingBookCategory.EQUIPMENT;
                break;
            case REDSTONE:
                var10000 = CraftingBookCategory.REDSTONE;
                break;
            default:
                var10000 = CraftingBookCategory.MISC;
        }

        return var10000;
    }
}
