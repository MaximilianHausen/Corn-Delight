package net.totodev.corndelight.block;

import com.nhoryzon.mc.farmersdelight.block.WildCropBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.totodev.corndelight.CornDelight;

public class BlockRegistry {
    public static final Block CORN_CROP = new CornCrop(FabricBlockSettings.copyOf(Blocks.WHEAT));

    public static final Block WILD_CORN = new WildCropBlock();

    public static final Block CORN_CRATE = new Block(FabricBlockSettings.copyOf(Blocks.OAK_WOOD).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD));

    public static final Block CORN_KERNEL_BAG = new Block(FabricBlockSettings.copyOf(Blocks.WHITE_WOOL));

    public static void registerAll() {
        register("corn_crop", CORN_CROP);
        register("wild_corn", WILD_CORN);
        register("corn_crate", CORN_CRATE);
        register("corn_kernel_bag", CORN_KERNEL_BAG);
    }

    private static void register(String name, Block block) {
        Registry.register(Registry.BLOCK, new Identifier(CornDelight.MODID, name), block);
    }
}
