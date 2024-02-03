package net.ltxprogrammer.changed.client.tfanimations;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import net.ltxprogrammer.changed.client.CubeExtender;
import net.ltxprogrammer.changed.client.FormRenderHandler;
import net.ltxprogrammer.changed.client.PoseStackExtender;
import net.ltxprogrammer.changed.client.renderer.LatexHumanoidRenderer;
import net.ltxprogrammer.changed.client.renderer.model.LatexHumanoidModel;
import net.ltxprogrammer.changed.entity.LimbCoverTransition;
import net.ltxprogrammer.changed.entity.variant.LatexVariantInstance;
import net.ltxprogrammer.changed.util.Color3;
import net.ltxprogrammer.changed.util.Transition;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class TransfurAnimator {
    public record ModelPose(PoseStack.Pose matrix, PartPose pose) {}
    public static final ModelPose NULL_POSE = new ModelPose(null, PartPose.ZERO);

    private static final Map<ModelPart, ModelPose> CAPTURED_MODELS = new HashMap<>();

    private static ModelPart.Cube copyCube(ModelPart.Cube cube) {
        ModelPart.Cube newCube = new ModelPart.Cube(0, 0, cube.minX, cube.minY, cube.minZ,
                cube.maxX - cube.minX, cube.maxY - cube.minY, cube.maxZ - cube.minZ,
                0.0f, 0.0f, 0.0f, false, 1.0f, 1.0f);
        ((CubeExtender)newCube).copyPolygonsFrom(cube);
        return newCube;
    }
    private static ModelPart deepCopyPart(ModelPart part) {
        ModelPart copied = new ModelPart(
                part.cubes.stream().map(TransfurAnimator::copyCube).toList(),
                part.children.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> deepCopyPart(entry.getValue()))));
        copied.loadPose(part.storePose());
        return copied;
    }

    private static ModelPart.Cube clampCube(ModelPart.Cube a, ModelPart.Cube clampBy) {
        float clampSizeX = Math.min(clampBy.maxX - clampBy.minX, a.maxX - a.minX);
        float clampSizeY = Math.min(clampBy.maxY - clampBy.minY, a.maxY - a.minY);
        float clampSizeZ = Math.min(clampBy.maxZ - clampBy.minZ, a.maxZ - a.minZ);

        float minX = Mth.clamp(a.minX, clampBy.minX, clampBy.maxX);
        float minY = Mth.clamp(a.minY, clampBy.minY, clampBy.maxY);
        float minZ = Mth.clamp(a.minZ, clampBy.minZ, clampBy.maxZ);

        return new ModelPart.Cube(0, 0, minX, minY, minZ,
                clampSizeX, clampSizeY, clampSizeZ,
                0.0f, 0.0f, 0.0f, false, 1.0f, 1.0f);
    }

    private static ModelPart replaceCubesAndZeroParts(ModelPart part, ModelPart.Cube cube) {
        ModelPart ret = new ModelPart(
                part.cubes.stream().map(otherCube -> clampCube(otherCube, cube)).toList(),
                part.children.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> replaceCubesAndZeroParts(entry.getValue(), cube))));
        //ret.loadPose(part.storePose());
        return ret;
    }

    private static ModelPart shallowCopyPart(ModelPart part) {
        return new ModelPart(part.cubes.stream().map(TransfurAnimator::copyCube).toList(), Map.of());
    }

    private static ModelPart matchCubeCount(ModelPart to, ModelPart from, ModelPart.Cube copyWith) {
        List<ModelPart.Cube> cubes = new ArrayList<>();

        final int targetCubeCount = Math.max(to.cubes.size(), from.cubes.size());
        int cubeCount = 0;

        for (var cube : to.cubes) {
            if (cubeCount >= targetCubeCount) break;

            cubes.add(copyCube(cube));
            cubeCount++;
        }

        for (var cube : from.cubes) {
            if (cubeCount >= targetCubeCount) break;

            cubes.add(clampCube(cube, copyWith));
            cubeCount++;
        }

        cubes.sort((c1, c2) -> {
            int yCompare = Float.compare(c1.maxY, c2.maxY);
            int xCompare = Float.compare(c1.maxX, c2.maxX);
            int zCompare = Float.compare(c1.maxZ, c2.maxZ);

            if (yCompare == 0) {
                if (xCompare == 0)
                    return zCompare;
                return xCompare;
            }
            return yCompare;
        });

        Map<String, ModelPart> children = new HashMap<>();

        for (var k : to.children.keySet()) {
            children.put(k, deepCopyPart(to.children.get(k)));
        }

        ModelPart.Cube copyOverride = to.cubes.size() > 0 ? to.cubes.get(0) : copyWith;

        for (var k : from.children.keySet()) {
            if (to.children.containsKey(k)) {
                var model = matchCubeCount(to.children.get(k), from.children.get(k), copyOverride);
                model.loadPose(to.children.get(k).storePose());
                children.put(k, model);
            } else {
                children.put(k, replaceCubesAndZeroParts(from.children.get(k), copyOverride));
            }
        }

        return new ModelPart(cubes, children);
    }

    private static ModelPart matchCubeCount(ModelPart to, ModelPart from) {
        return matchCubeCount(to, from, findCube(to));
    }

    private static ModelPart.Vertex lerpVertex(ModelPart.Vertex a, ModelPart.Vertex b, float lerp) {
        return new ModelPart.Vertex(
                Mth.lerp(lerp, a.pos.x(), b.pos.x()),
                Mth.lerp(lerp, a.pos.y(), b.pos.y()),
                Mth.lerp(lerp, a.pos.z(), b.pos.z()),
                Mth.lerp(lerp, a.u, b.u),
                Mth.lerp(lerp, a.v, b.v)
        );
    }

    private static final float GOOP_CUBE_WIDTH = 16.0f;
    private static final float GOOP_CUBE_HEIGHT = 16.0f;

    private static ModelPart.Polygon lerpPolygon(ModelPart.Polygon a, ModelPart.Polygon b, float lerp) {
        ModelPart.Polygon ret = new ModelPart.Polygon(a.vertices, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, false,
                Direction.getNearest(a.normal.x(), a.normal.y(), a.normal.z()));

        for (int i = 0; i < ret.vertices.length; ++i) {
            ret.vertices[i] = lerpVertex(a.vertices[i], b.vertices[i], lerp);
        }

        float polygonWidth;
        float polygonHeight;

        if (Mth.abs(a.normal.x()) > 0f) {
            polygonWidth = Mth.abs(ret.vertices[1].pos.z() - ret.vertices[0].pos.z());
            polygonHeight = Mth.abs(ret.vertices[2].pos.y() - ret.vertices[1].pos.y());
        } else if (Mth.abs(a.normal.y()) > 0f) {
            polygonWidth = Mth.abs(ret.vertices[1].pos.x() - ret.vertices[0].pos.x());
            polygonHeight = Mth.abs(ret.vertices[2].pos.z() - ret.vertices[1].pos.z());
        } else {
            polygonWidth = Mth.abs(ret.vertices[1].pos.x() - ret.vertices[0].pos.x());
            polygonHeight = Mth.abs(ret.vertices[2].pos.y() - ret.vertices[1].pos.y());
        }

        polygonWidth /= GOOP_CUBE_WIDTH;
        polygonHeight /= GOOP_CUBE_HEIGHT;

        ret.vertices[0] = ret.vertices[0].remap(polygonWidth, 0.0f);
        ret.vertices[1] = ret.vertices[1].remap(0.0f, 0.0f);
        ret.vertices[2] = ret.vertices[2].remap(0.0f, polygonHeight);
        ret.vertices[3] = ret.vertices[3].remap(polygonWidth, polygonHeight);

        return ret;
    }

    private static ModelPart.Cube lerpCube(ModelPart.Cube a, ModelPart.Cube b, float lerp) {
        float lerpMinX = Mth.lerp(lerp, a.minX, b.minX);
        float lerpMinY = Mth.lerp(lerp, a.minY, b.minY);
        float lerpMinZ = Mth.lerp(lerp, a.minZ, b.minZ);
        float lerpMaxX = Mth.lerp(lerp, a.maxX, b.maxX);
        float lerpMaxY = Mth.lerp(lerp, a.maxY, b.maxY);
        float lerpMaxZ = Mth.lerp(lerp, a.maxZ, b.maxZ);

        ModelPart.Cube ret = new ModelPart.Cube(0, 0, lerpMinX, lerpMinY, lerpMinZ, lerpMaxX - lerpMinX, lerpMaxY - lerpMinY, lerpMaxZ - lerpMinZ,
                0.0f, 0.0f, 0.0f, false, 0.0f, 0.0f);

        final var polyA = ((CubeExtender)a).getPolygons();
        final var polyB = ((CubeExtender)b).getPolygons();
        final var polyR = ((CubeExtender)ret).getPolygons();

        for (int i = 0; i < polyR.length; ++i) {
            polyR[i] = lerpPolygon(polyA[i], polyB[i], lerp);
        }

        return ret;
    }

    private static ModelPart lerpModelPart(ModelPart a, ModelPart b, float lerp) {
        List<ModelPart.Cube> copiedCubes = new ArrayList<>();
        for (int i = 0; i < a.cubes.size(); ++i)
            copiedCubes.add(lerpCube(a.cubes.get(i), b.cubes.get(i), lerp));
        Map<String, ModelPart> copiedChildren = new HashMap<>();
        for (var k : a.children.keySet())
            copiedChildren.put(k, lerpModelPart(a.children.get(k), b.children.get(k), lerp));

        var lerped = new ModelPart(copiedCubes, copiedChildren);
        lerped.x = Mth.lerp(lerp, a.x, b.x);
        lerped.y = Mth.lerp(lerp, a.y, b.y);
        lerped.z = Mth.lerp(lerp, a.z, b.z);
        lerped.xRot = Mth.lerp(lerp, a.xRot, b.xRot);
        lerped.yRot = Mth.lerp(lerp, a.yRot, b.yRot);
        lerped.zRot = Mth.lerp(lerp, a.zRot, b.zRot);
        return lerped;
    }

    private static ModelPart.Cube findCube(ModelPart part) {
        final AtomicReference<ModelPart.Cube> cubeReturn = new AtomicReference<>(null);

        part.visit(new PoseStack(), (pose, name, id, cube) -> {
            cubeReturn.compareAndSet(null, cube);
        });

        return cubeReturn.getAcquire();
    }

    private static ModelPart transitionModelPart(ModelPart before, ModelPart after, float lerp) {
        ModelPart beforeCopy = matchCubeCount(deepCopyPart(before), after);
        ModelPart afterCopy = matchCubeCount(deepCopyPart(after), before);

        return lerpModelPart(beforeCopy, afterCopy, lerp);
    }

    private static Matrix4f lerpMatrix(Matrix4f a, Matrix4f b, float lerp) {
        a.multiply(1.0f - lerp);
        b.multiply(lerp);
        a.add(b);
        return a;
    }

    private static Matrix3f lerpMatrix(Matrix3f a, Matrix3f b, float lerp) {
        a.mul(1.0f - lerp);
        b.mul(lerp);
        a.add(b);
        return a;
    }

    private static ModelPose transitionModelPose(ModelPose before, ModelPose after, float lerp) {
        var tmp = new PoseStack();
        tmp.pushPose();

        Matrix4f m = lerpMatrix(before.matrix.pose().copy(), after.matrix.pose().copy(), lerp);
        Matrix3f n = lerpMatrix(before.matrix.normal().copy(), after.matrix.normal().copy(), lerp);
        ((PoseStackExtender)tmp).setPose(m, n);

        return new ModelPose(tmp.last(), PartPose.offsetAndRotation(
                Mth.lerp(lerp, before.pose.x, after.pose.x),
                Mth.lerp(lerp, before.pose.y, after.pose.y),
                Mth.lerp(lerp, before.pose.z, after.pose.z),
                Mth.lerp(lerp, before.pose.xRot, after.pose.xRot),
                Mth.lerp(lerp, before.pose.yRot, after.pose.yRot),
                Mth.lerp(lerp, before.pose.zRot, after.pose.zRot)
        ));
    }

    private static ModelPart maybeReplaceWithHelper(LatexHumanoidModel<?> afterModel, Limb limb, ModelPart orDefault) {
        var helper = afterModel.getTransfurHelperModel(limb);
        return helper == null ? orDefault : helper;
    }

    private static void renderMorphedLimb(LivingEntity entity, Limb limb, HumanoidModel<?> beforeModel, LatexHumanoidModel<?> afterModel, float morphProgress, Color3 color, float alpha, PoseStack stack, MultiBufferSource buffer, int light, float partialTick) {
        ModelPart before = limb.getModelPart(beforeModel);
        final ModelPart after = limb.getModelPart(afterModel);
        if (before == null || after == null)
            return;

        final ModelPose beforePose = CAPTURED_MODELS.getOrDefault(before, NULL_POSE);
        final ModelPose afterPose = CAPTURED_MODELS.getOrDefault(after, NULL_POSE);

        before = maybeReplaceWithHelper(afterModel, limb, before);

        final ModelPart transitionPart = transitionModelPart(before, after, morphProgress);
        final ModelPose transitionPose = transitionModelPose(beforePose, afterPose, morphProgress);

        final var vertexConsumer = buffer.getBuffer(
                alpha >= 1f ? RenderType.entityCutoutNoCull(LimbCoverTransition.LATEX_CUBE)
                        : RenderType.entityTranslucent(LimbCoverTransition.LATEX_CUBE)
        );

        final int overlay = LivingEntityRenderer.getOverlayCoords(entity, 0.0f);

        stack.pushPose();
        ((PoseStackExtender)stack).setPose(transitionPose.matrix);

        transitionPart.loadPose(transitionPose.pose);
        transitionPart.render(stack, vertexConsumer, light, overlay, color.red(), color.green(), color.blue(), alpha);
        //transitionPart.loadPose(beforePose.pose);

        stack.popPose();
    }

    public static void renderMorphedEntity(LivingEntity entity, HumanoidModel<?> beforeModel, LatexHumanoidModel<?> afterModel, float morphProgress, Color3 color, float alpha, PoseStack stack, MultiBufferSource buffer, int light, float partialTick) {
        Arrays.stream(Limb.values()).forEach(limb -> {
            renderMorphedLimb(entity, limb, beforeModel, afterModel, morphProgress, color, alpha, stack, buffer, light, partialTick);
        });
    }

    private static float getCoverProgression(float transfurProgression) {
        return Mth.clamp(Mth.map(transfurProgression, 0.0f, 0.38f, 0.0f, 1.0f), 0.0f, 1.0f);
    }

    private static float getCoverAlpha(float transfurProgression) {
        return Mth.clamp(Mth.map(transfurProgression, 0.38f, 0.45f, 1.0f, 0.0f), 0.0f, 1.0f);
    }

    private static float getMorphAlpha(float transfurProgression) {
        if (transfurProgression < 0.5f)
            return Mth.clamp(Mth.map(transfurProgression, 0.35f, 0.4f, 0.0f, 1.0f), 0.0f, 1.0f);
        else
            return Mth.clamp(Mth.map(transfurProgression, 0.8f, 0.85f, 1.0f, 0.0f), 0.0f, 1.0f);
    }

    private static void renderCoveringLimb(LivingEntity entity, LatexVariantInstance<?> variant, float coverProgress, float coverAlpha, ModelPart part, Limb limb, PoseStack stack, MultiBufferSource buffer, int light, float partialTick) {
        final float progress = switch (limb) {
            case HEAD -> variant.cause.getHeadProgress(coverProgress);
            case TORSO -> variant.cause.getTorsoProgress(coverProgress);
            case LEFT_ARM -> variant.cause.getLeftArmProgress(coverProgress);
            case RIGHT_ARM -> variant.cause.getRightArmProgress(coverProgress);
            case LEFT_LEG -> variant.cause.getLeftLegProgress(coverProgress);
            case RIGHT_LEG -> variant.cause.getRightLegProgress(coverProgress);
            default -> 1.0f;
        } * coverAlpha;

        final LimbCoverTransition transition = switch (limb) {
            case HEAD -> variant.cause.getHeadTransition();
            case TORSO -> variant.cause.getTorsoTransition();
            case LEFT_ARM -> variant.cause.getLeftArmTransition();
            case RIGHT_ARM -> variant.cause.getRightArmTransition();
            case LEFT_LEG -> variant.cause.getLeftLegTransition();
            case RIGHT_LEG -> variant.cause.getRightLegTransition();
            default -> LimbCoverTransition.INSTANT;
        };

        if (progress <= 0f)
            return;

        final ModelPart copiedPart = deepCopyPart(part);
        final ModelPose pose = CAPTURED_MODELS.get(part);

        stack.pushPose();
        ((PoseStackExtender)stack).setPose(pose.matrix);
        stack.scale(1.005f, 1.005f, 1.005f);

        final float alpha = transition.getAlphaForProgress(progress);
        final var vertexConsumer = buffer.getBuffer(alpha >= 1f ? RenderType.entityCutoutNoCull(
                        transition.getTextureForProgress(progress)) : RenderType.entityTranslucent(transition.getTextureForProgress(progress))
        );
        final Color3 color = variant.getTransfurColor();

        copiedPart.loadPose(pose.pose);
        copiedPart.render(stack, vertexConsumer, light, LivingEntityRenderer.getOverlayCoords(entity, 0.0f), color.red(), color.green(), color.blue(), alpha);

        stack.popPose();
    }

    public static void renderTransfurringPlayer(Player player, LatexVariantInstance<?> variant, PoseStack stack, MultiBufferSource buffer, int light, float partialTick) {
        final Minecraft minecraft = Minecraft.getInstance();
        final EntityRenderDispatcher dispatcher = minecraft.getEntityRenderDispatcher();
        final var playerRenderer = dispatcher.getRenderer(player);
        final var latexRenderer = dispatcher.getRenderer(variant.getLatexEntity());

        if (!(playerRenderer instanceof LivingEntityRenderer<?,?> livingPlayerRenderer)) return;
        if (!(livingPlayerRenderer.getModel() instanceof HumanoidModel<?> playerHumanoidModel)) return;

        if (!(latexRenderer instanceof LatexHumanoidRenderer<?,?,?> latexHumanoidRenderer)) return;

        final float transfurProgression = variant.getTransfurProgression(partialTick);
        final float coverProgress = getCoverProgression(transfurProgression);
        final float coverAlpha = Transition.easeInOutSine(getCoverAlpha(transfurProgression));
        final float morphProgress = variant.getMorphProgression(partialTick);
        final float morphAlpha = Transition.easeInOutSine(getMorphAlpha(transfurProgression));

        if (morphAlpha < 1f) { // Render normal living entity, when they are still seen
            if (coverProgress < 1f) { // Render player, being covered
                forceRenderPlayer = true;
                FormRenderHandler.renderLiving(player, stack, buffer, light, partialTick);
                forceRenderPlayer = false;
            } else if (morphProgress > 0.5f) // Render latex at the end
                FormRenderHandler.renderLiving(variant.getLatexEntity(), stack, buffer, light, partialTick);
        }

        if (coverAlpha > 0f) {
            renderCoveringLimb(player, variant, coverProgress, 1.0f, playerHumanoidModel.head, Limb.HEAD, stack, buffer, light, partialTick);
            renderCoveringLimb(player, variant, coverProgress, 1.0f, playerHumanoidModel.hat, Limb.HEAD, stack, buffer, light, partialTick);
            renderCoveringLimb(player, variant, coverProgress, 1.0f, playerHumanoidModel.body, Limb.TORSO, stack, buffer, light, partialTick);
            renderCoveringLimb(player, variant, coverProgress, 1.0f, playerHumanoidModel.leftArm, Limb.LEFT_ARM, stack, buffer, light, partialTick);
            renderCoveringLimb(player, variant, coverProgress, 1.0f, playerHumanoidModel.rightArm, Limb.RIGHT_ARM, stack, buffer, light, partialTick);
            renderCoveringLimb(player, variant, coverProgress, 1.0f, playerHumanoidModel.leftLeg, Limb.LEFT_LEG, stack, buffer, light, partialTick);
            renderCoveringLimb(player, variant, coverProgress, 1.0f, playerHumanoidModel.rightLeg, Limb.RIGHT_LEG, stack, buffer, light, partialTick);
            if (playerHumanoidModel instanceof PlayerModel<?> playerModel) {
                renderCoveringLimb(player, variant, coverProgress, coverAlpha, playerModel.jacket, Limb.TORSO, stack, buffer, light, partialTick);
                renderCoveringLimb(player, variant, coverProgress, coverAlpha, playerModel.leftSleeve, Limb.LEFT_ARM, stack, buffer, light, partialTick);
                renderCoveringLimb(player, variant, coverProgress, coverAlpha, playerModel.rightSleeve, Limb.RIGHT_ARM, stack, buffer, light, partialTick);
                renderCoveringLimb(player, variant, coverProgress, coverAlpha, playerModel.leftPants, Limb.LEFT_LEG, stack, buffer, light, partialTick);
                renderCoveringLimb(player, variant, coverProgress, coverAlpha, playerModel.rightPants, Limb.RIGHT_LEG, stack, buffer, light, partialTick);
            }
        }

        if (morphAlpha <= 0f)
            return; // Don't bother rendering

        final var colors = variant.getTransfurColor();
        renderMorphedEntity(player, playerHumanoidModel, latexHumanoidRenderer.getModel(variant.getLatexEntity()),
                morphProgress, colors, morphAlpha, stack, buffer, light, partialTick);
    }

    private static boolean capturingPose = false;
    private static boolean forceRenderPlayer = false;

    public static void startCapture() {
        capturingPose = true;
        CAPTURED_MODELS.clear();
    }

    public static void endCapture() {
        capturingPose = false;
    }

    public static boolean isCapturing() {
        return capturingPose || forceRenderPlayer;
    }

    public static boolean capture(ModelPart part, PoseStack pose) {
        if (!capturingPose)
            return false;

        if (!CAPTURED_MODELS.containsKey(part)) {
            CAPTURED_MODELS.put(part, new ModelPose(pose.last(), part.storePose()));
        }

        return true;
    }
}
