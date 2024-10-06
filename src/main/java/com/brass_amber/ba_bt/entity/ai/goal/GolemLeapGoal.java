package com.brass_amber.ba_bt.entity.ai.goal;

import com.brass_amber.ba_bt.BABTMain;
import com.brass_amber.ba_bt.entity.hostile.golem.BTLandGolem;
import com.brass_amber.ba_bt.util.BTUtil;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class GolemLeapGoal extends Goal {
    private final BTLandGolem golem;
    private LivingEntity target;
    private final float minleap;
    private final float maxleap;

    protected static final int WARMUP_TICKS = 40;

    private int warmup = WARMUP_TICKS;
    private int lerpSteps;

    public GolemLeapGoal(BTLandGolem golemIn, float minimumLeap, float maximumLeap) {
        this.golem = golemIn;
        this.minleap = minimumLeap;
        this.maxleap = maximumLeap;
        this.lerpSteps = 10;
        this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
    }


    public boolean canUse() {
        if (this.golem.isVehicle()) {
            return false;
        }
        if (this.golem.getTarget() != null && !this.golem.isDormant() && this.golem.onGround()) {
            this.target = this.golem.getTarget();

            if (this.target == null || !this.golem.onGround()) {
                return false;
            }

            double distanceToTarget = BTUtil.distanceTo2D(this.golem, this.target);
            boolean horizontal = this.minleap < distanceToTarget && (distanceToTarget <= (this.maxleap * 2));

            // warmup is ticked down until the goal can be used. Clever way to implement this.
            if (horizontal) {
                this.warmup--;
                return this.warmup <= 0;
            } else {
                return false;
            }
        }

        return false;
    }

    public boolean canContinueToUse() {return !this.golem.onGround() && this.golem.isAwake();}

    public void start() {
        Vec3 targetPos = (this.target != null) ? this.target.position() : null;
        if (targetPos != null) {
            this.lerpSteps = 10;
        }
    }

    @Override
    public void tick() {
        Vec3 targetPos = this.target.position();
        if (this.lerpSteps > 0) {
            this.golem.lerpTo(targetPos.x, targetPos.y + 5, targetPos.z, 0, 0, 25, true);
            lerpToTarget();

            // Cheeky little way to ensure the golem actually reaches the player when leaping.
            if (this.lerpSteps == 0) {
                this.golem.setPos(targetPos.x, targetPos.y + 2, targetPos.z);
            }
        }
    }

    private void lerpToTarget() {
        if (this.lerpSteps > 0 && this.golem.isAwake()) {
            double d0 = this.golem.getX() + (this.golem.getLerpX() - this.golem.getX()) / (double)this.lerpSteps;
            double d1 = this.golem.getY() + (this.golem.getLerpY() - this.golem.getY()) / (double)this.lerpSteps;
            double d2 = this.golem.getZ() + (this.golem.getLerpZ() - this.golem.getZ()) / (double)this.lerpSteps;
            this.golem.setYRot(this.golem.getYRot() + (float) Mth.wrapDegrees(this.golem.getLerpYRot() - (double)this.golem.getYRot()) / (float)this.lerpSteps);
            this.golem.setXRot(this.golem.getXRot() + (float)(this.golem.getLerpXRot() - (double)this.golem.getXRot()) / (float)this.lerpSteps);
            --this.lerpSteps;
            this.golem.setPos(d0, d1, d2);
            this.golem.setRot(this.golem.getYRot(), this.golem.getXRot());
        }
    }

    @Override
    public void stop() {
        BABTMain.LOGGER.info("LEAP STOPPED");
        this.warmup = WARMUP_TICKS;
    }
}