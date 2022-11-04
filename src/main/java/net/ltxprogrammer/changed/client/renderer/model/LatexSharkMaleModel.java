package net.ltxprogrammer.changed.client.renderer.model;
// Made with Blockbench 4.1.5
// Exported for Minecraft version 1.17 with Mojang mappings
// Paste this class into your mod and generate all required imports

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.ltxprogrammer.changed.Changed;
import net.ltxprogrammer.changed.entity.beast.LatexSharkMale;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LatexSharkMaleModel extends LatexHumanoidModel<LatexSharkMale> implements LatexHumanoidModelInterface {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Changed.modResource("latex_shark_male"), "main");
	private final ModelPart RightLeg;
	private final ModelPart LeftLeg;
	private final ModelPart RightArm;
	private final ModelPart LeftArm;
	private final ModelPart Head;
	private final ModelPart Torso;
	private final ModelPart Tail;
	private final LatexHumanoidModelController controller;

	public LatexSharkMaleModel(ModelPart root) {
		super(root);
		this.RightLeg = root.getChild("RightLeg");
		this.LeftLeg = root.getChild("LeftLeg");
		this.Head = root.getChild("Head");
		this.Torso = root.getChild("Torso");
		this.Tail = Torso.getChild("Tail");
		this.RightArm = root.getChild("RightArm");
		this.LeftArm = root.getChild("LeftArm");
		controller = LatexHumanoidModelController.Builder.of(this, Head, Torso, Tail, RightArm, LeftArm, RightLeg, LeftLeg).tailAidsInSwim().build();
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition RightLeg = partdefinition.addOrReplaceChild("RightLeg", CubeListBuilder.create(), PartPose.offset(-2.75F, 10.0F, 0.0F));

		PartDefinition Toe_r1 = RightLeg.addOrReplaceChild("Toe_r1", CubeListBuilder.create().texOffs(51, 3).addBox(-4.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, NO_DEFORMATION)
				.texOffs(51, 3).addBox(-2.5F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, NO_DEFORMATION)
				.texOffs(51, 3).addBox(-1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, NO_DEFORMATION), PartPose.offsetAndRotation(2.0F, 13.0F, -2.25F, -0.6109F, 0.0F, 0.0F));

		PartDefinition Toe_r2 = RightLeg.addOrReplaceChild("Toe_r2", CubeListBuilder.create().texOffs(51, 3).addBox(-1.0F, -3.0F, -1.5F, 1.0F, 1.0F, 1.0F, NO_DEFORMATION)
				.texOffs(51, 3).addBox(-2.5F, -3.0F, -1.5F, 1.0F, 1.0F, 1.0F, NO_DEFORMATION)
				.texOffs(51, 3).addBox(-4.0F, -3.0F, -1.5F, 1.0F, 1.0F, 1.0F, NO_DEFORMATION)
				.texOffs(49, 7).addBox(-4.0F, -4.0F, -1.0F, 4.0F, 2.0F, 3.0F, NO_DEFORMATION), PartPose.offsetAndRotation(2.0F, 16.0F, -0.75F, 0.0F, 0.0F, 0.0F));

		PartDefinition RightLowerLeg_r1 = RightLeg.addOrReplaceChild("RightLowerLeg_r1", CubeListBuilder.create().texOffs(41, 43).addBox(-2.0F, -7.5F, -1.0F, 4.0F, 6.0F, 3.0F, NO_DEFORMATION), PartPose.offsetAndRotation(0.0F, 14.25F, -1.25F, -0.5236F, 0.0F, 0.0F));

		PartDefinition RightMidLeg_r1 = RightLeg.addOrReplaceChild("RightMidLeg_r1", CubeListBuilder.create().texOffs(32, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 7.0F, 4.0F, TEXTURE_DEFORMATION), PartPose.offsetAndRotation(0.0F, 4.25F, -1.5F, 0.5672F, 0.0F, 0.0F));

		PartDefinition RightUpperLeg_r1 = RightLeg.addOrReplaceChild("RightUpperLeg_r1", CubeListBuilder.create().texOffs(40, 22).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, NO_DEFORMATION), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2182F, 0.0F, 0.0F));

		PartDefinition LeftLeg = partdefinition.addOrReplaceChild("LeftLeg", CubeListBuilder.create(), PartPose.offset(2.75F, 10.0F, 0.0F));

		PartDefinition Toe_r3 = LeftLeg.addOrReplaceChild("Toe_r3", CubeListBuilder.create().texOffs(51, 3).addBox(-2.5F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, NO_DEFORMATION)
				.texOffs(51, 3).addBox(-1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, NO_DEFORMATION)
				.texOffs(51, 3).addBox(-4.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, NO_DEFORMATION), PartPose.offsetAndRotation(2.0F, 13.0F, -2.25F, -0.6109F, 0.0F, 0.0F));

		PartDefinition Toe_r4 = LeftLeg.addOrReplaceChild("Toe_r4", CubeListBuilder.create().texOffs(51, 3).addBox(-1.0F, -3.0F, -1.5F, 1.0F, 1.0F, 1.0F, NO_DEFORMATION)
				.texOffs(51, 3).addBox(-2.5F, -3.0F, -1.5F, 1.0F, 1.0F, 1.0F, NO_DEFORMATION)
				.texOffs(51, 3).addBox(-4.0F, -3.0F, -1.5F, 1.0F, 1.0F, 1.0F, NO_DEFORMATION)
				.texOffs(49, 1).addBox(-4.0F, -4.0F, -1.0F, 4.0F, 2.0F, 3.0F, NO_DEFORMATION), PartPose.offsetAndRotation(2.0F, 16.0F, -0.75F, 0.0F, 0.0F, 0.0F));

		PartDefinition LeftLowerLeg_r1 = LeftLeg.addOrReplaceChild("LeftLowerLeg_r1", CubeListBuilder.create().texOffs(27, 43).addBox(-2.0F, -7.5F, -1.0F, 4.0F, 6.0F, 3.0F, NO_DEFORMATION), PartPose.offsetAndRotation(0.0F, 14.25F, -1.25F, -0.5236F, 0.0F, 0.0F));

		PartDefinition LeftMidLeg_r1 = LeftLeg.addOrReplaceChild("LeftMidLeg_r1", CubeListBuilder.create().texOffs(16, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 7.0F, 4.0F, TEXTURE_DEFORMATION), PartPose.offsetAndRotation(0.0F, 4.25F, -1.5F, 0.5672F, 0.0F, 0.0F));

		PartDefinition LeftUpperLeg_r1 = LeftLeg.addOrReplaceChild("LeftUpperLeg_r1", CubeListBuilder.create().texOffs(40, 12).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, NO_DEFORMATION), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2182F, 0.0F, 0.0F));

		PartDefinition Head = partdefinition.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -34.0F + 26.5F, -4.0F, 8.0F, 8.0F, 8.0F, NO_DEFORMATION)
				.texOffs(12, 51).addBox(-1.5F, -29.0F + 26.5F, -7.0F, 3.0F, 2.0F, 3.0F, NO_DEFORMATION)
				.texOffs(50, 52).addBox(-1.5F, -27.0F + 26.5F, -6.0F, 3.0F, 1.0F, 2.0F, NO_DEFORMATION), PartPose.offset(0.0F, 25.0F, 0.0F));

		PartDefinition Snout_r1 = Head.addOrReplaceChild("Snout_r1", CubeListBuilder.create().texOffs(8, 56).addBox(0.0F, 0.0F + 26.5F, 0.0F, 1.0F, 2.0F, 4.0F, NO_DEFORMATION), PartPose.offsetAndRotation(-1.5F, -29.0F, -7.0F, 0.0F, -0.2182F, 0.0F));

		PartDefinition Snout_r2 = Head.addOrReplaceChild("Snout_r2", CubeListBuilder.create().texOffs(8, 56).addBox(-1.0F, 0.0F + 26.5F, 0.0F, 1.0F, 2.0F, 4.0F, NO_DEFORMATION), PartPose.offsetAndRotation(1.5F, -29.0F, -7.0F, 0.0F, 0.2182F, 0.0F));

		PartDefinition Fins = Head.addOrReplaceChild("Fins", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F + 26.5F, 0.0F));

		PartDefinition Base_r1 = Fins.addOrReplaceChild("Base_r1", CubeListBuilder.create().texOffs(52, 40).addBox(-2.1808F, -1.0F, -0.5736F, 3.0F, 2.0F, 2.0F, NO_DEFORMATION), PartPose.offsetAndRotation(3.5F, -27.75F, -2.0F, -0.5236F, 0.9599F, -3.1416F));
		PartDefinition Base_r2 = Fins.addOrReplaceChild("Base_r2", CubeListBuilder.create().texOffs(52, 12).addBox(-2.1808F, -1.0F, -0.5736F, 3.0F, 2.0F, 2.0F, NO_DEFORMATION), PartPose.offsetAndRotation(-3.5F, -27.75F, -2.0F, 0.5236F, 0.9599F, 0.0F));
		PartDefinition Base_r3 = Fins.addOrReplaceChild("Base_r3", CubeListBuilder.create().texOffs(32, 12).addBox(-0.25F, -1.0F, 0.0F, 3.0F, 2.0F, 2.0F, NO_DEFORMATION), PartPose.offsetAndRotation(-4.0F, -32.75F, -3.0F, -0.3492F, -0.886F, -2.8667F));
		PartDefinition Base_r4 = Fins.addOrReplaceChild("Base_r4", CubeListBuilder.create().texOffs(52, 22).addBox(-0.25F, -1.0F, 0.0F, 3.0F, 2.0F, 2.0F, NO_DEFORMATION), PartPose.offsetAndRotation(4.0F, -32.75F, -3.0F, 0.3492F, -0.886F, -0.2749F));
		PartDefinition Base_r5 = Fins.addOrReplaceChild("Base_r5", CubeListBuilder.create().texOffs(46, 55).addBox(-1.75F, -1.0934F, 3.0373F, 3.0F, 3.0F, 4.0F, NO_DEFORMATION), PartPose.offsetAndRotation(0.0F, -33.75F, -2.5F, 0.1872F, 0.1841F, -0.7681F));
		PartDefinition Base_r6 = Fins.addOrReplaceChild("Base_r6", CubeListBuilder.create().texOffs(11, 43).addBox(-1.5F, -1.3434F, -1.2127F, 3.0F, 3.0F, 5.0F, NO_DEFORMATION), PartPose.offsetAndRotation(0.0F, -32.25F, -1.5F, 0.6155F, 0.5236F, -0.6155F));

		PartDefinition Torso = partdefinition.addOrReplaceChild("Torso", CubeListBuilder.create().texOffs(0, 16).addBox(-4.0F, -25.0F + 25.5F, -2.0F, 8.0F, 12.0F, 4.0F, NO_DEFORMATION), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition BackFin_r1 = Torso.addOrReplaceChild("BackFin_r1", CubeListBuilder.create().texOffs(0, 48).addBox(-1.0F, 0.0F, -4.0F, 2.0F, 6.0F, 4.0F, NO_DEFORMATION), PartPose.offsetAndRotation(0.0F, -24.25F + 25.5F, 2.0F, 0.5236F, 0.0F, 0.0F));

		PartDefinition Tail = Torso.addOrReplaceChild("Tail", CubeListBuilder.create(), PartPose.offset(0.0F, -14.0F + 25.5F, 0.0F));

		PartDefinition TailFin_r1 = Tail.addOrReplaceChild("TailFin_r1", CubeListBuilder.create().texOffs(17, 20).addBox(0.0F, 0.5F, -1.75F, 1.0F, 8.0F, 2.0F, NO_DEFORMATION), PartPose.offsetAndRotation(-0.5F, 0.75F, 2.0F, 1.9199F, 0.0F, 0.0F));
		PartDefinition Base_r8 = Tail.addOrReplaceChild("Base_r8", CubeListBuilder.create().texOffs(26, 52).addBox(-0.5F, -1.5538F, -1.8296F, 1.0F, 6.0F, 3.0F, NO_DEFORMATION), PartPose.offsetAndRotation(0.0F, 2.75F, 16.0F, 0.6109F, 0.0F, 0.0F));
		PartDefinition Base_r9 = Tail.addOrReplaceChild("Base_r9", CubeListBuilder.create().texOffs(34, 52).addBox(-0.5F, -5.1668F, -2.1179F, 1.0F, 6.0F, 3.0F, NO_DEFORMATION), PartPose.offsetAndRotation(0.0F, 1.75F, 16.0F, -0.48F, 0.0F, 0.0F));
		PartDefinition Base_r10 = Tail.addOrReplaceChild("Base_r10", CubeListBuilder.create().texOffs(42, 52).addBox(-1.0F, -0.3449F, -0.7203F, 2.0F, 5.0F, 2.0F, NO_DEFORMATION), PartPose.offsetAndRotation(0.0F, 2.75F, 12.0F, 1.6581F, 0.0F, 0.0F));
		PartDefinition Base_r11 = Tail.addOrReplaceChild("Base_r11", CubeListBuilder.create().texOffs(48, 32).addBox(-1.5F, -1.3563F, -0.9588F, 3.0F, 5.0F, 3.0F, NO_DEFORMATION), PartPose.offsetAndRotation(0.0F, 2.75F, 8.5F, 1.4835F, 0.0F, 0.0F));
		PartDefinition Base_r12 = Tail.addOrReplaceChild("Base_r12", CubeListBuilder.create().texOffs(32, 0).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 8.0F, 4.0F, NO_DEFORMATION), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.1781F, 0.0F, 0.0F));

		PartDefinition RightArm = partdefinition.addOrReplaceChild("RightArm", CubeListBuilder.create().texOffs(24, 16).addBox(-8.0F + 5F, -26.0F + 24.5F, -2.0F, 4.0F, 12.0F, 4.0F, NO_DEFORMATION)
				.texOffs(44, 2).addBox(-5.0F + 5F, -14.25F + 24.5F, -2.0F, 1.0F, 1.0F, 1.0F, NO_DEFORMATION)
				.texOffs(44, 2).addBox(-8.0F + 5F, -14.25F + 24.5F, 1.0F, 1.0F, 1.0F, 1.0F, NO_DEFORMATION)
				.texOffs(44, 2).addBox(-8.0F + 5F, -14.25F + 24.5F, -0.5F, 1.0F, 1.0F, 1.0F, NO_DEFORMATION)
				.texOffs(44, 2).addBox(-8.0F + 5F, -14.25F + 24.5F, -2.0F, 1.0F, 1.0F, 1.0F, NO_DEFORMATION), PartPose.offset(0.0F, 25.0F, 0.0F));

		PartDefinition Spike_r1 = RightArm.addOrReplaceChild("Spike_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -2.5F, -2.0F, 1.0F, 5.0F, 2.0F, NO_DEFORMATION), PartPose.offsetAndRotation(-7.0789F + 5F, -20.1254F + 24.5F, 1.1151F, -2.6425F, 0.8346F, 3.1091F));

		PartDefinition LeftArm = partdefinition.addOrReplaceChild("LeftArm", CubeListBuilder.create().texOffs(0, 32).addBox(4.0F - 5F, -26.0F + 24.5F, -2.0F, 4.0F, 12.0F, 4.0F, NO_DEFORMATION)
				.texOffs(44, 2).addBox(7.0F - 5F, -14.25F + 24.5F, 1.0F, 1.0F, 1.0F, 1.0F, NO_DEFORMATION)
				.texOffs(44, 2).addBox(7.0F - 5F, -14.25F + 24.5F, -0.5F, 1.0F, 1.0F, 1.0F, NO_DEFORMATION)
				.texOffs(44, 2).addBox(7.0F - 5F, -14.25F + 24.5F, -2.0F, 1.0F, 1.0F, 1.0F, NO_DEFORMATION)
				.texOffs(44, 2).addBox(4.0F - 5F, -14.25F + 24.5F, -2.0F, 1.0F, 1.0F, 1.0F, NO_DEFORMATION), PartPose.offset(0.0F, 25.0F, 0.0F));

		PartDefinition Spike_r2 = LeftArm.addOrReplaceChild("Spike_r2", CubeListBuilder.create().texOffs(25, 1).addBox(-0.125F, -1.5F, -0.5F, 2.0F, 5.0F, 1.0F, NO_DEFORMATION), PartPose.offsetAndRotation(7.6568F - 5F, -20.9289F + 24.5F, 1.6568F, -0.4796F, -0.6979F, 0.7102F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void prepareMobModel(LatexSharkMale p_102861_, float p_102862_, float p_102863_, float p_102864_) {
		this.prepareMobModel(controller, p_102861_, p_102862_, p_102863_, p_102864_);
	}

	public void setupHand() {
		controller.setupHand();
	}

	@Override
	public LatexHumanoidModelController getController() {
		return controller;
	}

	@Override
	public void setupAnim(LatexSharkMale entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		controller.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
	}

	public void translateToHand(HumanoidArm p_102854_, PoseStack p_102855_) {
		this.getArm(p_102854_).translateAndRotate(p_102855_);
	}

	public ModelPart getArm(HumanoidArm p_102852_) {
		return p_102852_ == HumanoidArm.LEFT ? this.LeftArm : this.RightArm;
	}

	public ModelPart getHead() {
		return this.Head;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		RightLeg.render(poseStack, buffer, packedLight, packedOverlay);
		LeftLeg.render(poseStack, buffer, packedLight, packedOverlay);
		Head.render(poseStack, buffer, packedLight, packedOverlay);
		Torso.render(poseStack, buffer, packedLight, packedOverlay);
		RightArm.render(poseStack, buffer, packedLight, packedOverlay);
		LeftArm.render(poseStack, buffer, packedLight, packedOverlay);
	}
}