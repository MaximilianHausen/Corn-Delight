package net.totodev.corndelight.item;

import com.nhoryzon.mc.farmersdelight.registry.EffectsRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.UseAction;
import net.minecraft.util.registry.Registry;
import net.totodev.corndelight.CornDelight;
import net.totodev.corndelight.block.BlockRegistry;

public class ItemRegistry {
    // Effect durations taken from the fabric port of farmers delight, not the forge version

    // BlockItems
    public static final Item WILD_CORN = new BlockItem(BlockRegistry.WILD_CORN, CornDelight.defaultItemSettings());

    public static final Item CORN_CRATE = new BlockItem(BlockRegistry.CORN_CRATE, CornDelight.defaultItemSettings());

    public static final Item CORN_KERNEL_BAG = new BlockItem(BlockRegistry.CORN_KERNEL_BAG, CornDelight.defaultItemSettings());

    // Seeds
    public static final Item CORN_SEEDS = seed(BlockRegistry.CORN_CROP, 1, .2f);

    // Food
    public static final Item CORN = food(2, .2f);

    public static final Item GRILLED_CORN = food(6, .2f);

    public static final Item BOILED_CORN = food(6, .2f);

    public static final Item POPCORN = food(3, .5f);

    public static final Item CREAMED_CORN = food(7, .5f);

    public static final Item CORN_SOUP = food(10, .9f);

    public static final Item CREAMY_CORN_DRINK = drink(2, .6f, Items.GLASS_BOTTLE,
            new StatusEffectInstance(EffectsRegistry.COMFORT.get(), 1200),
            new StatusEffectInstance(StatusEffects.REGENERATION, 1200));

    public static final Item CORNBREAD_BATTER = food(1, .2f);

    public static final Item CORNBREAD = food(4, .5f);

    public static final Item RAW_TORTILLA = food(1, .2f);

    public static final Item TORTILLA = food(3, .4f);

    public static final Item TACO = food(12, .8f,
            new StatusEffectInstance(StatusEffects.STRENGTH, 300));

    public static final Item CORNBREAD_STUFFING = food(18, 1f, Items.BOWL,
            new StatusEffectInstance(EffectsRegistry.NOURISHED.get(), 4800));

    public static void registerAll() {
        register("wild_corn", WILD_CORN);
        register("corn_crate", CORN_CRATE);
        register("corn_kernel_bag", CORN_KERNEL_BAG);

        register("corn", CORN);
        register("corn_seeds", CORN_SEEDS);
        register("grilled_corn", GRILLED_CORN);
        register("boiled_corn", BOILED_CORN);
        register("popcorn", POPCORN);
        register("creamed_corn", CREAMED_CORN);
        register("corn_soup", CORN_SOUP);
        register("creamy_corn_drink", CREAMY_CORN_DRINK);
        register("cornbread_batter", CORNBREAD_BATTER);
        register("cornbread", CORNBREAD);
        register("tortilla_raw", RAW_TORTILLA);
        register("tortilla", TORTILLA);
        register("taco", TACO);
        register("cornbread_stuffing", CORNBREAD_STUFFING);
    }

    private static void register(String name, Item item) {
        Registry.register(Registry.ITEM, new Identifier(CornDelight.MODID, name), item);
    }

    private static Item food(int hunger, float saturation, StatusEffectInstance... effects) {
        FoodComponent.Builder food = new FoodComponent.Builder()
                .hunger(hunger)
                .saturationModifier(saturation);

        for (StatusEffectInstance e : effects)
            food.statusEffect(e, 1);

        return new Item(CornDelight.defaultItemSettings()
                .food(food.build()));
    }

    private static Item food(int hunger, float saturation, Item remainder, StatusEffectInstance... effects) {
        FoodComponent.Builder food = new FoodComponent.Builder()
                .hunger(hunger)
                .saturationModifier(saturation);

        for (StatusEffectInstance e : effects)
            food.statusEffect(e, 1);

        return new Item(CornDelight.defaultItemSettings()
                .food(food.build())
                .maxCount(16)
                .recipeRemainder(remainder));
    }

    private static Item drink(int hunger, float saturation, Item remainder, StatusEffectInstance... effects) {
        FoodComponent.Builder food = new FoodComponent.Builder()
                .hunger(hunger)
                .saturationModifier(saturation);

        for (StatusEffectInstance e : effects)
            food.statusEffect(e, 1);

        return new Item(CornDelight.defaultItemSettings()
                .food(food.build())
                .maxCount(16)
                .recipeRemainder(remainder)) {
            @Override
            public UseAction getUseAction(ItemStack stack) {
                return stack.getItem().isFood() ? UseAction.DRINK : UseAction.NONE;
            }
        };
    }

    private static Item seed(Block block, int hunger, float saturation) {
        return new AliasedBlockItem(block, CornDelight.defaultItemSettings()
                .food(new FoodComponent.Builder()
                        .hunger(hunger)
                        .saturationModifier(saturation)
                        .build()));
    }
}
