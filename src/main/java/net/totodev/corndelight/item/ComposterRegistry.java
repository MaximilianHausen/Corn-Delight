package net.totodev.corndelight.item;

import net.minecraft.block.ComposterBlock;
import net.minecraft.item.Item;

public class ComposterRegistry {
    public static void registerCompost() {
        register(ItemRegistry.CORN_SEEDS, 0.35F);
        register(ItemRegistry.POPCORN, 0.5F);
        register(ItemRegistry.WILD_CORN, 0.55F);
        register(ItemRegistry.CORN, 0.65F);
        register(ItemRegistry.BOILED_CORN, 0.65F);
        register(ItemRegistry.GRILLED_CORN, 0.65F);
        register(ItemRegistry.RAW_TORTILLA, 0.75F);
        register(ItemRegistry.TORTILLA, 0.75F);
        register(ItemRegistry.CORNBREAD_BATTER, 0.85F);
        register(ItemRegistry.CORNBREAD, 0.85F);
        register(ItemRegistry.TACO, 1F);
    }

    private static void register(Item item, float chance) {
        ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(item, chance);
    }
}
