package net.ltxprogrammer.changed.client.renderer.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.ltxprogrammer.changed.Changed;
import net.ltxprogrammer.changed.client.renderer.model.HairModel;
import net.ltxprogrammer.changed.client.renderer.model.LatexHumanoidModel;
import net.ltxprogrammer.changed.entity.HairStyle;
import net.ltxprogrammer.changed.entity.LatexEntity;
import net.ltxprogrammer.changed.init.ChangedParticles;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

import java.util.Arrays;
import java.util.EnumMap;

public class LatexHumanoidHairLayer<T extends LatexEntity, M extends LatexHumanoidModel<T>> extends RenderLayer<T, M> {
    private static final EnumMap<HairStyle, HairModel> MODEL_BY_HAIRSTYLE = new EnumMap<>(HairStyle.class);

    public LatexHumanoidHairLayer(RenderLayerParent<T, M> parent, EntityModelSet modelSet) {
        super(parent);
        Arrays.stream(HairStyle.values()).filter(HairStyle::hasModel).filter(style -> !MODEL_BY_HAIRSTYLE.containsKey(style)).forEach(style -> {
            try {
                MODEL_BY_HAIRSTYLE.put(style, new HairModel(modelSet.bakeLayer(style.model)));
            } catch (Exception ex) {
                Changed.LOGGER.error("Failed to load HairStyle model for {}", style.getSerializedName(), ex);
            }
        });
    }

    public void render(PoseStack pose, MultiBufferSource bufferSource, int packedLight, T entity, float p_116670_, float p_116671_, float red, float green, float blue, float alpha) {
        HairStyle style = entity.getHairStyle();
        if (style.model == null || style.textures.length == 0)
            return;

        pose.pushPose();
        ModelPart head = this.getParentModel().getHead();
        head.translateAndRotate(pose);
        Model hair = MODEL_BY_HAIRSTYLE.get(style);
        int colorLayer = 0;
        int overlay = LivingEntityRenderer.getOverlayCoords(entity, 0.0F);
        for (ResourceLocation layer : style.textures) {
            ChangedParticles.Color3 color = entity.getHairColor(colorLayer);
            hair.renderToBuffer(pose, bufferSource.getBuffer(RenderType.entityCutoutNoCull(layer)), packedLight,
                    overlay, color.red(), color.green(), color.blue(), alpha);
            ++colorLayer;
        }
        pose.popPose();
    }
}
