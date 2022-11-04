package net.ltxprogrammer.changed.client.renderer.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.ltxprogrammer.changed.Changed;
import net.ltxprogrammer.changed.entity.beast.DarkLatexDragonMale;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class DarkLatexDragonMaleModel extends LatexHumanoidModel<DarkLatexDragonMale> implements LatexHumanoidModelInterface {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(Changed.modResource("dark_latex_dragon_male"), "main");
    private final ModelPart RightLeg;
    private final ModelPart LeftLeg;
    private final ModelPart RightArm;
    private final ModelPart LeftArm;
    private final ModelPart Head;
    private final ModelPart Torso;
    private final ModelPart Tail;
    private final ModelPart RightWing;
    private final ModelPart LeftWing;
    private final LatexHumanoidModelController controller;

    public DarkLatexDragonMaleModel(ModelPart root) {
        super(root);
        this.RightLeg = root.getChild("RightLeg");
        this.LeftLeg = root.getChild("LeftLeg");
        this.Head = root.getChild("Head");
        this.Torso = root.getChild("Torso");
        this.Tail = Torso.getChild("Tail");
        this.RightArm = root.getChild("RightArm");
        this.LeftArm = root.getChild("LeftArm");
        this.RightWing = Torso.getChild("RightWing");
        this.LeftWing = Torso.getChild("LeftWing");
        controller = LatexHumanoidModelController.Builder.of(this, Head, Torso, Tail, RightArm, LeftArm, RightLeg, LeftLeg).wings(RightWing, LeftWing).build();
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition RightLeg = partdefinition.addOrReplaceChild("RightLeg", CubeListBuilder.create().texOffs(12, 44).addBox(-2.0F, 12.0F, -2.75F, 4.0F, 2.0F, 4.0F, NO_DEFORMATION), PartPose.offset(-2.5F, 10.0F, 0.0F));

        PartDefinition Toe_r1 = RightLeg.addOrReplaceChild("Toe_r1", CubeListBuilder.create().texOffs(44, 15).addBox(-2.5F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, NO_DEFORMATION)
                .texOffs(44, 15).addBox(-4.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, NO_DEFORMATION)
                .texOffs(44, 15).addBox(-1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, NO_DEFORMATION), PartPose.offsetAndRotation(2.0F, 13.0F, -3.25F, -0.6109F, 0.0F, 0.0F));

        PartDefinition Toe_r2 = RightLeg.addOrReplaceChild("Toe_r2", CubeListBuilder.create().texOffs(38, 4).addBox(-4.0F, -3.0F, -1.5F, 1.0F, 1.0F, 1.0F, NO_DEFORMATION)
                .texOffs(38, 4).addBox(-2.5F, -3.0F, -1.5F, 1.0F, 1.0F, 1.0F, NO_DEFORMATION)
                .texOffs(38, 4).addBox(-1.0F, -3.0F, -1.5F, 1.0F, 1.0F, 1.0F, NO_DEFORMATION), PartPose.offsetAndRotation(2.0F, 16.0F, -1.75F, 0.0F, 0.0F, 0.0F));

        PartDefinition RightLowerLeg_r1 = RightLeg.addOrReplaceChild("RightLowerLeg_r1", CubeListBuilder.create().texOffs(29, 42).addBox(-2.0F, -7.5F, -1.0F, 4.0F, 6.0F, 3.0F, NO_DEFORMATION), PartPose.offsetAndRotation(0.0F, 14.25F, -1.25F, -0.5236F, 0.0F, 0.0F));

        PartDefinition RightMidLeg_r1 = RightLeg.addOrReplaceChild("RightMidLeg_r1", CubeListBuilder.create().texOffs(16, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 7.0F, 4.0F, NO_DEFORMATION), PartPose.offsetAndRotation(0.0F, 4.25F, -1.5F, 0.5672F, 0.0F, 0.0F));

        PartDefinition RightUpperLeg_r1 = RightLeg.addOrReplaceChild("RightUpperLeg_r1", CubeListBuilder.create().texOffs(40, 11).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, NO_DEFORMATION), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2182F, 0.0F, 0.0F));

        PartDefinition LeftLeg = partdefinition.addOrReplaceChild("LeftLeg", CubeListBuilder.create().texOffs(43, 42).addBox(-2.0F, 12.0F, -2.75F, 4.0F, 2.0F, 4.0F, NO_DEFORMATION), PartPose.offset(2.5F, 10.0F, 0.0F));

        PartDefinition Toe_r3 = LeftLeg.addOrReplaceChild("Toe_r3", CubeListBuilder.create().texOffs(36, 4).addBox(-2.5F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, NO_DEFORMATION)
                .texOffs(36, 4).addBox(-4.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, NO_DEFORMATION)
                .texOffs(36, 4).addBox(-1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, NO_DEFORMATION), PartPose.offsetAndRotation(2.0F, 13.0F, -3.25F, -0.6109F, 0.0F, 0.0F));

        PartDefinition Toe_r4 = LeftLeg.addOrReplaceChild("Toe_r4", CubeListBuilder.create().texOffs(36, 4).addBox(-4.0F, -3.0F, -1.5F, 1.0F, 1.0F, 1.0F, NO_DEFORMATION)
                .texOffs(36, 4).addBox(-2.5F, -3.0F, -1.5F, 1.0F, 1.0F, 1.0F, NO_DEFORMATION)
                .texOffs(36, 4).addBox(-1.0F, -3.0F, -1.5F, 1.0F, 1.0F, 1.0F, NO_DEFORMATION), PartPose.offsetAndRotation(2.0F, 16.0F, -1.75F, 0.0F, 0.0F, 0.0F));

        PartDefinition LeftLowerLeg_r1 = LeftLeg.addOrReplaceChild("LeftLowerLeg_r1", CubeListBuilder.create().texOffs(40, 21).addBox(-2.0F, -7.5F, -1.0F, 4.0F, 6.0F, 3.0F, NO_DEFORMATION), PartPose.offsetAndRotation(0.0F, 14.25F, -1.25F, -0.5236F, 0.0F, 0.0F));

        PartDefinition LeftMidLeg_r1 = LeftLeg.addOrReplaceChild("LeftMidLeg_r1", CubeListBuilder.create().texOffs(32, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 7.0F, 4.0F, NO_DEFORMATION), PartPose.offsetAndRotation(0.0F, 4.25F, -1.5F, 0.5672F, 0.0F, 0.0F));

        PartDefinition LeftUpperLeg_r1 = LeftLeg.addOrReplaceChild("LeftUpperLeg_r1", CubeListBuilder.create().texOffs(32, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, NO_DEFORMATION), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2182F, 0.0F, 0.0F));

        PartDefinition Head = partdefinition.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -34.0F, -4.0F, 8.0F, 8.0F, 8.0F, NO_DEFORMATION)
                .texOffs(0, 48).addBox(-1.5F, -28.0F, -6.0F, 3.0F, 2.0F, 2.0F, NO_DEFORMATION)
                .texOffs(44, 30).addBox(-2.0F, -29.0F, -7.0F, 4.0F, 2.0F, 3.0F, NO_DEFORMATION), PartPose.offset(0.0F, 25.0F, 0.0F));

        PartDefinition RightEar = Head.addOrReplaceChild("RightEar", CubeListBuilder.create(), PartPose.offsetAndRotation(-3.0F, -34.0F, 0.0F, 0.0F, 0.0F, -0.2618F));

        PartDefinition Base_r1 = RightEar.addOrReplaceChild("Base_r1", CubeListBuilder.create().texOffs(34, 3).addBox(-0.7884F, -3.4697F, -2.0512F, 2.0F, 5.0F, 2.0F, NO_DEFORMATION), PartPose.offsetAndRotation(-2.5F, 3.0F, 0.0F, -2.0891F, -0.4533F, 1.2989F));

        PartDefinition Base_r2 = RightEar.addOrReplaceChild("Base_r2", CubeListBuilder.create().texOffs(21, 18).addBox(-1.5088F, -5.0341F, -1.0F, 2.0F, 7.0F, 3.0F, NO_DEFORMATION), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.7681F, -0.1841F, 0.0746F));

        PartDefinition LeftEar = Head.addOrReplaceChild("LeftEar", CubeListBuilder.create(), PartPose.offsetAndRotation(2.5F, -34.0F, 0.0F, 0.0F, 0.0F, 0.2618F));

        PartDefinition Base_r3 = LeftEar.addOrReplaceChild("Base_r3", CubeListBuilder.create().texOffs(29, 19).addBox(-2.0403F, -4.0223F, -1.0863F, 2.0F, 7.0F, 2.0F, NO_DEFORMATION), PartPose.offsetAndRotation(3.0F, 3.0F, 0.0F, -1.1519F, 0.4605F, 0.4624F));

        PartDefinition Base_r4 = LeftEar.addOrReplaceChild("Base_r4", CubeListBuilder.create().texOffs(28, 19).addBox(0.0088F, -5.0341F, -1.0F, 2.0F, 7.0F, 3.0F, NO_DEFORMATION), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.7681F, 0.1841F, -0.0746F));

        PartDefinition Hair = Head.addOrReplaceChild("Hair", CubeListBuilder.create().texOffs(40, 55).addBox(-2.0F, -35.0F, -4.0F, 4.0F, 1.0F, 8.0F, NO_DEFORMATION)
                .texOffs(42, 60).addBox(-4.0F, -34.5F, -3.5F, 8.0F, 1.0F, 3.0F, NO_DEFORMATION)
                .texOffs(52, 59).addBox(2.0F, -35.0F, 0.0F, 2.0F, 1.0F, 4.0F, NO_DEFORMATION)
                .texOffs(44, 53).addBox(2.5F, -34.0F, -4.0F, 2.0F, 3.0F, 8.0F, new CubeDeformation(0.025F))
                .texOffs(48, 62).addBox(-3.5F, -33.0F, -4.25F, 7.0F, 1.0F, 1.0F, NO_DEFORMATION)
                .texOffs(60, 61).addBox(3.5F, -34.0F, -4.5F, 1.0F, 2.0F, 1.0F, NO_DEFORMATION)
                .texOffs(60, 61).addBox(-4.5F, -34.0F, -4.5F, 1.0F, 2.0F, 1.0F, NO_DEFORMATION)
                .texOffs(50, 57).addBox(2.5F, -31.0F, -1.0F, 2.0F, 2.0F, 5.0F, NO_DEFORMATION)
                .texOffs(50, 57).addBox(-4.5F, -31.0F, -1.0F, 2.0F, 2.0F, 5.0F, NO_DEFORMATION)
                .texOffs(44, 53).addBox(-4.5F, -34.0F, -4.0F, 2.0F, 3.0F, 8.0F, new CubeDeformation(0.025F))
                .texOffs(52, 59).addBox(-4.0F, -35.0F, 0.0F, 2.0F, 1.0F, 4.0F, NO_DEFORMATION)
                .texOffs(46, 62).addBox(-4.0F, -34.0F, -4.5F, 8.0F, 1.0F, 1.0F, NO_DEFORMATION)
                .texOffs(46, 57).addBox(-4.0F, -34.0F, 4.0F, 8.0F, 6.0F, 1.0F, NO_DEFORMATION), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition Torso = partdefinition.addOrReplaceChild("Torso", CubeListBuilder.create().texOffs(0, 16).addBox(-4.0F, -25.0F, -2.0F, 8.0F, 12.0F, 4.0F, NO_DEFORMATION), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition Tail = Torso.addOrReplaceChild("Tail", CubeListBuilder.create(), PartPose.offset(0.0F, -14.0F, 0.0F));

        PartDefinition Base_r5 = Tail.addOrReplaceChild("Base_r5", CubeListBuilder.create().texOffs(53, 1).addBox(-1.0F, 0.1914F, -0.9483F, 2.0F, 7.0F, 2.0F, NO_DEFORMATION), PartPose.offsetAndRotation(0.0F, 2.0F, 6.0F, 1.4835F, 0.0F, 0.0F));

        PartDefinition Base_r6 = Tail.addOrReplaceChild("Base_r6", CubeListBuilder.create().texOffs(52, 0).addBox(-1.5F, 0.0F, -1.0F, 3.0F, 7.0F, 3.0F, NO_DEFORMATION), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.1781F, 0.0F, 0.0F));

        PartDefinition LeftWing = Torso.addOrReplaceChild("LeftWing", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition Base_r7 = LeftWing.addOrReplaceChild("Base_r7", CubeListBuilder.create().texOffs(20, 24).addBox(8.0F, -27.0F, 0.0F, 3.0F, 16.0F, 1.0F, NO_DEFORMATION)
                .texOffs(21, 26).addBox(7.0F, -26.0F, 0.0F, 1.0F, 12.0F, 1.0F, NO_DEFORMATION)
                .texOffs(20, 27).addBox(5.0F, -24.0F, 0.0F, 1.0F, 5.0F, 1.0F, NO_DEFORMATION)
                .texOffs(20, 27).addBox(6.0F, -25.0F, 0.0F, 1.0F, 9.0F, 1.0F, NO_DEFORMATION)
                .texOffs(20, 27).addBox(2.0F, -24.0F, 0.0F, 3.0F, 4.0F, 1.0F, NO_DEFORMATION), PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, 0.0F, -0.6545F, 0.0F));

        PartDefinition RightWing = Torso.addOrReplaceChild("RightWing", CubeListBuilder.create(), PartPose.offset(1.0F, 0.0F, 1.0F));

        PartDefinition Base_r8 = RightWing.addOrReplaceChild("Base_r8", CubeListBuilder.create().texOffs(20, 23).addBox(8.0F, -27.0F, -1.0F, 3.0F, 16.0F, 1.0F, NO_DEFORMATION)
                .texOffs(20, 27).addBox(7.0F, -26.0F, -1.0F, 1.0F, 12.0F, 1.0F, NO_DEFORMATION)
                .texOffs(20, 27).addBox(6.0F, -25.0F, -1.0F, 1.0F, 9.0F, 1.0F, NO_DEFORMATION)
                .texOffs(20, 27).addBox(5.0F, -24.0F, -1.0F, 1.0F, 5.0F, 1.0F, NO_DEFORMATION)
                .texOffs(20, 27).addBox(2.0F, -24.0F, -1.0F, 3.0F, 4.0F, 1.0F, NO_DEFORMATION), PartPose.offsetAndRotation(-1.0F, 1.0F, -1.0F, 0.0F, -2.4871F, 0.0F));

        PartDefinition RightArm = partdefinition.addOrReplaceChild("RightArm", CubeListBuilder.create().texOffs(24, 16).addBox(-8.0F, -26.0F, -2.0F, 4.0F, 12.0F, 4.0F, NO_DEFORMATION)
                .texOffs(10, 50).addBox(-5.0F, -14.25F, -2.0F, 1.0F, 1.0F, 1.0F, NO_DEFORMATION)
                .texOffs(10, 50).addBox(-8.0F, -14.25F, 1.0F, 1.0F, 1.0F, 1.0F, NO_DEFORMATION)
                .texOffs(10, 50).addBox(-8.0F, -14.25F, -0.5F, 1.0F, 1.0F, 1.0F, NO_DEFORMATION)
                .texOffs(10, 50).addBox(-8.0F, -14.25F, -2.0F, 1.0F, 1.0F, 1.0F, NO_DEFORMATION), PartPose.offset(0.0F, 25.0F, 0.0F));

        PartDefinition LeftArm = partdefinition.addOrReplaceChild("LeftArm", CubeListBuilder.create().texOffs(0, 32).addBox(4.0F, -26.0F, -2.0F, 4.0F, 12.0F, 4.0F, NO_DEFORMATION)
                .texOffs(10, 50).addBox(4.0F, -14.25F, -2.0F, 1.0F, 1.0F, 1.0F, NO_DEFORMATION)
                .texOffs(10, 50).addBox(7.0F, -14.25F, 1.0F, 1.0F, 1.0F, 1.0F, NO_DEFORMATION)
                .texOffs(10, 50).addBox(7.0F, -14.25F, -0.5F, 1.0F, 1.0F, 1.0F, NO_DEFORMATION)
                .texOffs(10, 50).addBox(7.0F, -14.25F, -2.0F, 1.0F, 1.0F, 1.0F, NO_DEFORMATION), PartPose.offset(0.0F, 25.0F, 0.0F));

        return LayerDefinition.create(process(meshdefinition), 64, 64);
    }

    @Override
    public void prepareMobModel(DarkLatexDragonMale p_102861_, float p_102862_, float p_102863_, float p_102864_) {
        this.prepareMobModel(controller, p_102861_, p_102862_, p_102863_, p_102864_);
    }

    public void setupHand() {
        controller.setupHand();
    }

    @Override
    public void setupAnim(@NotNull DarkLatexDragonMale entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
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

    @Override
    public LatexHumanoidModelController getController() {
        return controller;
    }
}