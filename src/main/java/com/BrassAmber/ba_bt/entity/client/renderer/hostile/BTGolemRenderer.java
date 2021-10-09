package com.BrassAmber.ba_bt.entity.client.renderer.hostile;

import com.BrassAmber.ba_bt.BrassAmberBattleTowers;
import com.BrassAmber.ba_bt.entity.client.model.hostile.GolemModel;
import com.BrassAmber.ba_bt.entity.hostile.BTGolemEntity;
import com.BrassAmber.ba_bt.entity.hostile.BTGolemEntityAbstract;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BTGolemRenderer extends MobRenderer<BTGolemEntity, BipedModel<BTGolemEntity>> {
	private static final ResourceLocation GOLEM_TEXTURES_DORMANT = BrassAmberBattleTowers.locate("textures/entity/hostile/land_golem_dormant.png");
	private static final ResourceLocation GOLEM_TEXTURES_AWAKEN = BrassAmberBattleTowers.locate("textures/entity/hostile/land_golem.png");
	private static final ResourceLocation GOLEM_TEXTURES_ENRAGED = BrassAmberBattleTowers.locate("textures/entity/hostile/land_golem_enraged.png");
	private static final float SCALE = BTGolemEntityAbstract.SCALE;

	public BTGolemRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new GolemModel(), 0.5F * SCALE);
		this.addLayer(new HeldItemLayer<>(this));
		this.addLayer(new BipedArmorLayer<>(this, new GolemModel(0.5F), new GolemModel(1.0F)));
	}

	@Override
	protected void scale(BTGolemEntity entitylivingbaseIn, MatrixStack matrixStackIn, float partialTickTime) {
		matrixStackIn.scale(SCALE, SCALE, SCALE);
	}

	@Override
	public ResourceLocation getTextureLocation(BTGolemEntity entity) {
		return entity.isAwake() ? GOLEM_TEXTURES_AWAKEN : entity.isEnraged() ? GOLEM_TEXTURES_ENRAGED : GOLEM_TEXTURES_DORMANT;
	}

}
