package com.brass_amber.ba_bt.client.renderer.golem;

import com.brass_amber.ba_bt.BABTMain;
import com.brass_amber.ba_bt.entity.hostile.golem.BTAbstractGolem;

import com.brass_amber.ba_bt.entity.hostile.golem.BTNetherGolem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class AbstractBTGolemRenderer<E extends BTAbstractGolem, M extends EntityModel<E>> extends MobRenderer<E, M> {
	private static final float SCALE = BTAbstractGolem.SCALE;
	protected ResourceLocation golemTexturesDormant;
	protected ResourceLocation golemTexturesAwaken;
	protected ResourceLocation golemTexturesEnraged;
	private String golemType;

	public AbstractBTGolemRenderer(EntityRendererProvider.Context context, M model, String golemType) {
		super(context, model, 0.5F * SCALE);
		this.golemType = golemType;
	}

	@Override
	protected void scale(E entitylivingbaseIn, PoseStack poseStack, float partialTickTime) {
		poseStack.scale(SCALE, SCALE, SCALE);
	}

	@Override
	public ResourceLocation getTextureLocation(BTAbstractGolem entity) {

		if (entity instanceof BTNetherGolem netherGolem) {
			float missingHealthPercentage = (netherGolem.getMaxHealth() - netherGolem.getHealth()) / netherGolem.getMaxHealth();
			BABTMain.LOGGER.info("NETHER GOLEM HEALTH PERCENTAGE REMAINING: {}", missingHealthPercentage);

			if (missingHealthPercentage < 0.25) {
				return entity.isAwake() ? golemTexturesAwaken : golemTexturesDormant;
			}
			else if (missingHealthPercentage >= 0.25 && missingHealthPercentage < 0.5) {
				return BABTMain.locate("textures/entity/golem/" + this.golemType + "/nether_golem_cracked_stage_1.png");
			}
			else if (missingHealthPercentage >= 0.5 && missingHealthPercentage < 0.75) {
				return BABTMain.locate("textures/entity/golem/" + this.golemType + "/nether_golem_cracked_stage_2.png");
			}
			else {
				return BABTMain.locate("textures/entity/golem/" + this.golemType + "/blaze_metal_golem/blaze_metal_golem.png");
			}
		}

		return entity.isEnraged() ? golemTexturesEnraged : entity.isAwake() ? golemTexturesAwaken : golemTexturesDormant;
	}

	protected void setGolemTextures(String dormant, String awake, String special) {
		this.golemTexturesDormant = this.setGolemTexture(dormant);
		this.golemTexturesAwaken = this.setGolemTexture(awake);
		this.golemTexturesEnraged = this.setGolemTexture(special);
	}
	
	protected ResourceLocation setGolemTexture(String textureName) {
		return BABTMain.locate("textures/entity/golem/" + this.golemType + "/" + textureName + ".png");
	}
}
