package net.ltxprogrammer.changed.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.ltxprogrammer.changed.Changed;
import net.ltxprogrammer.changed.client.renderer.layers.GasMaskLayer;
import net.ltxprogrammer.changed.client.renderer.layers.TaurChestPackLayer;
import net.ltxprogrammer.changed.client.renderer.model.LightLatexCentaurModel;
import net.ltxprogrammer.changed.client.renderer.model.armor.ArmorLightLatexCentaurModel;
import net.ltxprogrammer.changed.entity.beast.LightLatexCentaur;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.SaddleLayer;
import net.minecraft.resources.ResourceLocation;

public class LightLatexCentaurRenderer extends LatexHumanoidRenderer<LightLatexCentaur, LightLatexCentaurModel, ArmorLightLatexCentaurModel> {
    public LightLatexCentaurRenderer(EntityRendererProvider.Context context) {
        super(context, new LightLatexCentaurModel(context.bakeLayer(LightLatexCentaurModel.LAYER_LOCATION)),
                ArmorLightLatexCentaurModel::new, ArmorLightLatexCentaurModel.INNER_ARMOR, ArmorLightLatexCentaurModel.OUTER_ARMOR, 0.7f);
        this.addLayer(new SaddleLayer<>(this, getModel(), Changed.modResource("textures/light_latex_centaur_saddle.png")));
        this.addLayer(new TaurChestPackLayer<>(this, context.getModelSet()));
        this.addLayer(GasMaskLayer.forSnouted(this, context.getModelSet()));
    }

    @Override
    public ResourceLocation getTextureLocation(LightLatexCentaur p_114482_) {
        return Changed.modResource("textures/light_latex_centaur.png");
    }

    @Override
    protected void scale(LightLatexCentaur entity, PoseStack pose, float partialTick) {
        super.scale(entity, pose, partialTick);
        pose.scale(1.05f, 1.05f, 1.05f);
    }
}