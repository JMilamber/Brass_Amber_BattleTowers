package com.brass_amber.ba_bt.util;

import com.brass_amber.ba_bt.BABTMain;
import com.brass_amber.ba_bt.init.BTBlockEntityType;
import com.brass_amber.ba_bt.init.BTBlocks;
import com.brass_amber.ba_bt.sound.BTSoundEvents;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.*;
import java.util.function.Supplier;

import static com.brass_amber.ba_bt.util.BTStatics.*;

public class BTUtil {
    static final Logger LOGGER = LogUtils.getLogger();


    public static ListTag newIntList(int... ints) {
        ListTag listtag = new ListTag();

        for (int d0 : ints) {
            listtag.add(IntTag.valueOf(d0));
        }

        return listtag;
    }


    public static CompoundTag newStringList(List<String> strings) {
        CompoundTag listtag = new CompoundTag();

        for (int i = 0; i < strings.size(); i++) {
            listtag.putString(String.valueOf(i), strings.get(i));
        }

        return listtag;
    }

    public static List<String> listFromTag(CompoundTag tag, Optional<List<String>> checkFrom) {
        List<String> list = new ArrayList<>();

        for (int i = 0; i < tag.getAllKeys().size(); i++) {
            String value = tag.getString(String.valueOf(i));
            // BABTMain.LOGGER.info("get compound value: " + value + " | is in list? " + checkFrom.get().contains(value));
            if (checkFrom.isPresent()) {
                list.add(checkFrom.get().contains(value) ? value : "Invalid") ;
            } else {
                list.add(value);
            }
        }

        return list;
    }

    public static Supplier<SoundEvent> getTowerMusic(GolemType type) {
        return switch (type) {
            case OCEAN -> () -> BTSoundEvents.MUSIC_OCEAN_TOWER;
            case CORE -> () -> BTSoundEvents.MUSIC_CORE_TOWER;
            case NETHER -> () -> BTSoundEvents.MUSIC_NETHER_TOWER;
            case END -> () -> BTSoundEvents.MUSIC_END_TOWER;
            case SKY -> () -> BTSoundEvents.MUSIC_SKY_TOWER;
            case CITY -> () -> BTSoundEvents.MUSIC_CITY;
            default -> () -> BTSoundEvents.MUSIC_LAND_TOWER;
        };
    }

    public static @NotNull BlockEntityType<? extends ChestBlockEntity> getChestEntity(Block block) {
        if (BTBlocks.LAND_CHEST.get().equals(block)) {
            return BTBlockEntityType.LAND_CHEST.get();
        } else if (BTBlocks.LAND_GOLEM_CHEST.get().equals(block)) {
            return BTBlockEntityType.LAND_GOLEM_CHEST.get();
        } else if (BTBlocks.OCEAN_CHEST.get().equals(block)) {
            return BTBlockEntityType.OCEAN_CHEST.get();
        } else if (BTBlocks.OCEAN_GOLEM_CHEST.get().equals(block)) {
            return BTBlockEntityType.OCEAN_GOLEM_CHEST.get();
        }

        return BTBlockEntityType.LAND_CHEST.get();
    }

    public static @NotNull String getTowerName(BlockEntityType<? extends ChestBlockEntity> block) {
        if (BTBlockEntityType.LAND_GOLEM_CHEST.get().equals(block) || BTBlockEntityType.LAND_CHEST.get().equals(block)) {
            return "land";
        } else if (BTBlockEntityType.OCEAN_CHEST.get().equals(block) || BTBlockEntityType.OCEAN_GOLEM_CHEST.get().equals(block)) {
            return "ocean";
        }

        return "land";
    }


    public static int median(ArrayList<Integer> nums) {
        Collections.sort(nums);
        if (nums.size() % 2 == 1)
            return nums.get((nums.size() + 1) / 2 - 1);
        else {
            double lower = nums.get(nums.size() / 2 - 1);
            double upper = nums.get(nums.size() / 2);

            return (int) ((lower + upper) / 2.0);
        }
    }

    /**
     * Returns the squared horizontal distance as a positive double.
     */
    public static double sqrDistanceTo2D(Entity self, double targetX, double targetZ) {
        double dX = self.getX() - targetX;
        double dZ = self.getZ() - targetZ;
        return Math.abs(dX * dX + dZ * dZ);
    }

    public static double sqrDistanceTo2D(Entity self, Entity target) {
        double dX = self.getX() - target.getX();
        double dZ = self.getZ() - target.getZ();
        return Math.abs(dX * dX + dZ * dZ);
    }

    public static int chunkDistanceTo(ChunkPos start, ChunkPos end) {
        return Mth.floor(distanceTo2D(start.x - end.x, start.z - end.z));
    }

    /**
     * Returns the horizontal distance as a positive double.
     */
    public static double distanceTo2D(Entity self, double targetX, double targetZ) {
        double dX = self.getX() - targetX;
        double dZ = self.getZ() - targetZ;
        return Math.sqrt(Math.abs(dX * dX + dZ * dZ));
    }

    public static double distanceTo2D(Entity self, Entity entity) {
        double dX = Math.abs(self.getX() - entity.getX());
        double dZ = Math.abs(self.getZ() - entity.getZ());
        return Math.sqrt(dX * dX + dZ * dZ);
    }

    public static double distanceTo2D(Entity self, BlockPos end) {
        double dX = Math.abs(self.getX() - end.getX());
        double dZ = Math.abs(self.getZ() - end.getZ());
        return Math.sqrt(dX * dX + dZ * dZ);
    }

    public static double distanceTo2D(BlockPos origin, BlockPos end) {
        double dX = Math.abs(origin.getX() - end.getX());
        double dZ = Math.abs(origin.getZ() - end.getZ());
        return Math.sqrt(dX * dX + dZ * dZ);
    }

    public static double distanceTo2D(double side, double side2) {
        return  Math.sqrt(side * side + side2 * side2);
    }


    public static double distanceTo3D(Entity self, double targetX, double targetY, double targetZ) {
        double dXZ = distanceTo2D(self, targetX, targetZ);
        double dY = self.getY() - targetY;
        return Math.sqrt(Math.abs(dXZ * dXZ + dY * dY));
    }

    public static double distanceTo3D(Entity self, Entity entity) {
        double dXZ = distanceTo2D(self, entity);
        double dY = self.getY() - entity.getY();
        return Math.sqrt(Math.abs(dXZ * dXZ + dY * dY));
    }

    public static double distanceTo3D(Entity self, BlockPos end) {
        double dXZ = distanceTo2D(self, end);
        double dY = self.getY() - end.getY();
        return Math.sqrt(Math.abs(dXZ * dXZ + dY * dY));
    }

    public static double distanceTo3D(BlockPos origin, BlockPos end) {
        double dXZ = distanceTo2D(origin, end);
        double dY = origin.getY() - end.getY();
        return Math.sqrt(Math.abs(dXZ * dXZ + dY * dY));
    }

    public static void removeBodyOfWater(BlockPos start, Level level) {
        Set<BlockPos> waterPositions = new HashSet<>();
        int recursion = 0;
        removeBodyOWater(waterPositions, start, recursion, level);

        waterPositions.forEach((pos) -> level.setBlock(pos, Blocks.AIR.defaultBlockState(), 0));
    }

    public static void removeBodyOWater(Set<BlockPos> storage, BlockPos position, int recursion, Level level) {
        if(!level.isWaterAt(position) || recursion == 250) {
            return;
        }
        if(!storage.contains(position)) {
            storage.add(position);
        } else {
            return;
        }
        removeBodyOWater(storage, position.north(), recursion + 1, level);
        removeBodyOWater(storage, position.east(), recursion + 1, level);
        removeBodyOWater(storage, position.south(), recursion + 1, level);
        removeBodyOWater(storage, position.west(), recursion + 1, level);
        removeBodyOWater(storage, position.below(), recursion + 1, level);
    }

    public static Pair<List<Item>, List<Integer>> createItems(int rarity, List<String> pools, RandomSource randomSource) {
        List<Item> items = new ArrayList<>();
        List<Item> poolItems = new ArrayList<>();

        List<Integer> amounts = new ArrayList<>();
        List<Integer> poolMins = new ArrayList<>();
        List<Integer> poolMaxes = new ArrayList<>();

        while (pools.size() > 4) {
            pools.remove(randomSource.nextInt(pools.size()));
        }

        for (String pool: pools) {
            for (int i = Math.max(rarity-1, 0); i < Math.max(rarity + 1, 4); i++) {
                poolItems.addAll(itemPools.get(lootTypes.indexOf(pool)).get(i));
                List<Float> floats = itemPoolAmounts.get(lootTypes.indexOf(pool)).get(i);
                for (float amount: floats) {
                    BABTMain.LOGGER.info("Min amount = " + (int) amount + "  Max amount = " + ((amount - Mth.floor(amount)) * 10));
                    poolMins.add((int) amount);
                    poolMaxes.add((int) (((amount - (int) amount) * 10)));
                }
            }
        }

        for (int i = 0; i < 15; i++) {
            int index = randomSource.nextInt(poolItems.size());
            items.add(poolItems.remove(index));
            int min = poolMins.remove(index);
            int max = poolMaxes.remove(index);
            if (min < max) {
                amounts.add(randomSource.nextIntBetweenInclusive(min, max));
            } else {
                amounts.add(min);
            }
        }

        return Pair.of(items, amounts);
    }

    public static void btListFill(List<Item> loot, List<Integer> amounts, Container container, LootContext lootContext) {
        Random random = new Random();

        // Get possible slots to put items in (empty slots) should be all for tower chests.
        List<Integer> possibleSlots = btGetAvailableSlots(container, random);
        ItemStack addStack;
        int randomSlot;

        List<ItemStack> chestLoot = new ArrayList<>();
        for (int i = 0; i < loot.size(); i++) {
            chestLoot.add(new ItemStack(loot.get(i), amounts.get(i)));
        }

        btShuffleAndSplitItems(chestLoot, possibleSlots.size(), lootContext.getRandom());
        for (int i = 0; i < chestLoot.size(); i++) {
            randomSlot = possibleSlots.remove(random.nextInt(possibleSlots.size()));
            addStack = new ItemStack(loot.get(i), amounts.get(i));
            container.setItem(randomSlot, addStack);
            if (possibleSlots.isEmpty()) {
                break;
            }
        }
    }

    /** All below are recreations of @LootTable methods.
     * Needed Due to bukkit servers overwriting the fill method and breaking it.
     */
    public static void btFill(LootTable loot, Container container, LootContext lootContext, LootParams lootparams) {
        List<ItemStack> list = loot.getRandomItems(lootparams);
        RandomSource random = lootContext.getRandom();
        List<Integer> list1 = btGetAvailableSlots(container,  new Random());
        btShuffleAndSplitItems(list, list1.size(), random);

        for(ItemStack itemstack : list) {
            if (list1.isEmpty()) {
                LOGGER.warn("Tried to over-fill a container");
                return;
            }

            if (itemstack.isEmpty()) {
                container.setItem(list1.remove(list1.size() - 1), ItemStack.EMPTY);
            } else {
                container.setItem(list1.remove(list1.size() - 1), itemstack);
            }
        }

    }

    private static List<Integer> btGetAvailableSlots(Container container, Random random) {
        List<Integer> list = Lists.newArrayList();

        for(int i = 0; i < container.getContainerSize(); ++i) {
            if (container.getItem(i).isEmpty()) {
                list.add(i);
            }
        }

        Collections.shuffle(list, random);
        return list;
    }


    private static void btShuffleAndSplitItems(List<ItemStack> itemStackList, int listSize, RandomSource random) {
        List<ItemStack> list = Lists.newArrayList();
        Iterator<ItemStack> iterator = itemStackList.iterator();

        while (iterator.hasNext()) {
            ItemStack itemstack = iterator.next();
            if (itemstack.isEmpty()) {
                iterator.remove();
            } else if (itemstack.getCount() > 1) {
                list.add(itemstack);
                iterator.remove();
            }
        }

        while (listSize - itemStackList.size() - list.size() > 0 && !list.isEmpty()) {
            ItemStack itemstack2 = list.remove(Mth.nextInt(random, 0, list.size() - 1));
            int i = Mth.nextInt(random, 1, itemstack2.getCount() / 2);
            ItemStack itemstack1 = itemstack2.split(i);
            if (itemstack2.getCount() > 1 && random.nextBoolean()) {
                list.add(itemstack2);
            } else {
                itemStackList.add(itemstack2);
            }

            if (itemstack1.getCount() > 1 && random.nextBoolean()) {
                list.add(itemstack1);
            } else {
                itemStackList.add(itemstack1);
            }
        }

        itemStackList.addAll(list);
        Collections.shuffle(itemStackList, new Random());
    }

    public static void doCommand(Entity self, String command) {
        Commands commands = self.level().getServer().getCommands();
        commands.performPrefixedCommand(self.createCommandSourceStack().withPermission(4).withSuppressedOutput(), command);
    }

    public static void doNoOutputCommand(Entity self, String command) {
        Commands commands = self.level().getServer().getCommands();
        commands.performPrefixedCommand(self.createCommandSourceStack().withPermission(4).withSuppressedOutput(), command);
    }

    public static void doNoOutputPostionedCommand(Entity self, String command, Vec3 vec) {
        Commands commands = self.level().getServer().getCommands();
        commands.performPrefixedCommand(self.createCommandSourceStack().withPermission(4).withSuppressedOutput().withPosition(vec), command);
    }

}
