package net.ltxprogrammer.changed.entity;

import net.ltxprogrammer.changed.entity.variant.TransfurVariantInstance;
import net.ltxprogrammer.changed.init.ChangedBlocks;
import net.ltxprogrammer.changed.init.ChangedEntities;
import net.ltxprogrammer.changed.init.ChangedItems;
import net.ltxprogrammer.changed.item.AbstractGooBucket;
import net.ltxprogrammer.changed.item.AbstractGooItem;
import net.ltxprogrammer.changed.process.ProcessTransfur;
import net.ltxprogrammer.changed.util.Color3;
import net.ltxprogrammer.changed.util.EntityUtil;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.common.IExtensibleEnum;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public enum GooType implements StringRepresentable, IExtensibleEnum {
    NEUTRAL(),
    BLACK_GOO(ChangedItems.DARK_LATEX_GOO, ChangedItems.DARK_LATEX_BUCKET, ChangedBlocks.DARK_LATEX_BLOCK, ChangedEntities.BLACK_GOO_PUP, Color3.DARK, MaterialColor.COLOR_GRAY),
    PURE_WHITE_GOO(ChangedItems.WHITE_LATEX_GOO, ChangedItems.WHITE_LATEX_BUCKET, ChangedBlocks.WHITE_LATEX_BLOCK, () -> null /* null for now */, Color3.WHITE, MaterialColor.QUARTZ);

    public final Supplier<? extends AbstractGooItem> goo;
    public final Supplier<? extends AbstractGooBucket> gooBucket;
    public final Supplier<? extends Block> block;
    public final Supplier<? extends EntityType<?>> pup;
    public final Color3 color;
    public final MaterialColor materialColor;

    GooType() {
        this.goo = () -> null;
        this.gooBucket = () -> null;
        this.block = () -> null;
        this.pup = () -> null;
        this.color = Color3.WHITE;
        this.materialColor = MaterialColor.WOOL;
    }
    GooType(Supplier<? extends AbstractGooItem> goo, Supplier<? extends AbstractGooBucket> gooBucket, Supplier<? extends Block> block, Supplier<? extends EntityType<?>> pup, Color3 color,
            MaterialColor materialColor) {
        this.goo = goo;
        this.gooBucket = gooBucket;
        this.block = block;
        this.pup = pup;
        this.color = color;
        this.materialColor = materialColor;
    }

    public static GooType getEntityGooType(@NotNull Entity entity) {
        if (entity instanceof ChangedEntity changedEntity) {
            return changedEntity.getGooType();
        }

        else
            return ProcessTransfur.ifPlayerTransfurred(EntityUtil.playerOrNull(entity), TransfurVariantInstance::getGooType, () -> null);
    }

    public static boolean hasGooType(@NotNull Entity entity) {
        return getEntityGooType(entity) != null;
    }

    public static boolean hasFactionGooType(@NotNull Entity entity) {
        return isFaction(getEntityGooType(entity));
    }

    public static boolean isFaction(GooType type) {
        if (type == NEUTRAL || type == null) return false;
        return true;
    }

    public static GooType factionOrNull(GooType type) {
        if (type == NEUTRAL) return null;
        return type;
    }

    public static GooType getEntityFactionGooType(@NotNull Entity entity) {
        return factionOrNull(getEntityGooType(entity));
    }

    public boolean isHostileTo(GooType other) {
        return (isFaction(this) && isFaction(other) && this != other) || other == null;
    }

    @Override
    public String getSerializedName() {
        return toString().toLowerCase();
    }

    public static GooType create(String name, Supplier<? extends Item> goo, Supplier<? extends Item> gooBucket, Supplier<Block> block, Supplier<? extends EntityType<?>> pup, Color3 color, MaterialColor materialColor)
    {
        throw new IllegalStateException("Enum not extended");
    }
}