package com.brass_amber.ba_bt.entity.hostile.golem;

import com.brass_amber.ba_bt.entity.ai.goal.GolemFireballAttackGoal;
import com.brass_amber.ba_bt.entity.ai.goal.GolemLeapGoal;
import com.brass_amber.ba_bt.entity.ai.goal.GolemStompAttackGoal;


import com.brass_amber.ba_bt.sound.BTSoundEvents;
import com.brass_amber.ba_bt.util.GolemType;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;

public class BTLandGolem extends BTAbstractGolem {

	public BTLandGolem(EntityType<? extends BTLandGolem> type, Level levelIn) {
		super(type, levelIn, BossEvent.BossBarColor.BLUE);
		this.setGolemName(GolemType.LAND.getDisplayName());
		this.setBossBarName();

		// Sets the experience points to drop. Reference taken from the EnderDragon.
		this.xpReward = 315;
		this.golemType = GolemType.LAND;

		this.BOSS_MUSIC = BTSoundEvents.LAND_GOLEM_FIGHT_MUSIC;

		// Reference for disregarding lava taken from ZombiefiedPiglin
		this.setPathfindingMalus(BlockPathTypes.LAVA, 8.0F);

		// Reference for disregarding fire taken from Blaze
		this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 0.0F);
		this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, 0.0F);

		this.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
	}

	public static AttributeSupplier.Builder createBattleGolemAttributes() {
		return BTAbstractGolem.createBattleGolemAttributes()
				.add(Attributes.MAX_HEALTH, 250)
				.add(Attributes.MOVEMENT_SPEED, 0.3D)
				.add(Attributes.KNOCKBACK_RESISTANCE, 2.0D)
				.add(Attributes.ATTACK_DAMAGE, 15.0D)
				.add(Attributes.FOLLOW_RANGE, 60.0D)
				.add(Attributes.ARMOR, 4);
	}

	@Override
	protected void addBehaviorGoals() {
		super.addBehaviorGoals();
		this.goalSelector.addGoal(1, new GolemStompAttackGoal(this, 4.0F, 6));
		this.goalSelector.addGoal(6, new GolemFireballAttackGoal(this));
		this.goalSelector.addGoal(7, new GolemLeapGoal(this, 2, 10));
	}
}