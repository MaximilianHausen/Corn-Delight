package net.totodev.corndelight;

import com.nhoryzon.mc.farmersdelight.FarmersDelightMod;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.totodev.corndelight.block.BlockRegistry;
import net.totodev.corndelight.item.ComposterRegistry;
import net.totodev.corndelight.item.ItemRegistry;
import net.totodev.corndelight.worldgen.WildCornGeneration;

// Missing features: Datagen
public class CornDelight implements ModInitializer {
    public static final ItemGroup ITEM_GROUP = FarmersDelightMod.ITEM_GROUP;
    public static final String MODID = "corn_delight";

    @Override
    public void onInitialize() {
        ItemRegistry.registerAll();
        BlockRegistry.registerAll();
        ComposterRegistry.registerCompost();
        WildCornGeneration.registerGeneration();
    }

    public static Item.Settings defaultItemSettings() {
        return new Item.Settings().group(ITEM_GROUP);
    }
}
