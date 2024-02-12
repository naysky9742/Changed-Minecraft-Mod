package net.ltxprogrammer.changed.entity.beast;

import net.ltxprogrammer.changed.entity.ChangedEntity;
import net.ltxprogrammer.changed.entity.GooType;
import net.ltxprogrammer.changed.entity.TransfurCause;
import net.ltxprogrammer.changed.util.Color3;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber
public abstract class AbstractGooShark extends AbstractAquaticEntity {
    @SubscribeEvent
    public static void canEntitySpawn(LivingSpawnEvent.CheckSpawn event) {
        if (event.getEntityLiving() instanceof AquaticEntity && event.getSpawnReason() == MobSpawnType.NATURAL)
            event.setResult(Event.Result.ALLOW);
    }

    public AbstractGooShark(EntityType<? extends AbstractGooShark> p_19870_, Level p_19871_) {
        super(p_19870_, p_19871_);
    }

    private static boolean isDeepEnoughToSpawn(LevelAccessor p_32367_, BlockPos p_32368_) {
        return p_32368_.getY() < p_32367_.getSeaLevel() - 5;
    }

    public static boolean checkAquaticLatexSpawnRules(EntityType<? extends ChangedEntity> p_32350_, ServerLevelAccessor p_32351_, MobSpawnType p_32352_, BlockPos p_32353_, Random p_32354_) {
        if (!p_32351_.getFluidState(p_32353_.below()).is(FluidTags.WATER)) {
            return false;
        } else {
            Holder<Biome> holder = p_32351_.getBiome(p_32353_);
            boolean flag = p_32351_.getDifficulty() != Difficulty.PEACEFUL && isDarkEnoughToSpawn(p_32351_, p_32353_, p_32354_) && (p_32352_ == MobSpawnType.SPAWNER || p_32351_.getFluidState(p_32353_).is(FluidTags.WATER));
            if (!holder.is(Biomes.RIVER) && !holder.is(Biomes.FROZEN_RIVER)) {
                return p_32354_.nextInt(40) == 0 && isDeepEnoughToSpawn(p_32351_, p_32353_) && flag;
            } else {
                return p_32354_.nextInt(15) == 0 && flag;
            }
        }
    }

    @Override
    public GooType getGooType() {
        return GooType.NEUTRAL;
    }

    @Override
    public Color3 getDripColor() {
        return level.random.nextInt(10) > 3 ? Color3.GRAY : Color3.WHITE;
    }

    @Override
    public Color3 getHairColor(int layer) {
        return Color3.WHITE;
    }

    public Color3 getTransfurColor(TransfurCause cause) {
        return Color3.GRAY;
    }
}