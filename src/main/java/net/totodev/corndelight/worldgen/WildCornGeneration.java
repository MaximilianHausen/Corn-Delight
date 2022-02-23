package net.totodev.corndelight.worldgen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.decorator.RarityFilterPlacementModifier;
import net.minecraft.world.gen.decorator.SquarePlacementModifier;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.totodev.corndelight.CornDelight;
import net.totodev.corndelight.block.BlockRegistry;

public class WildCornGeneration {
    public static ConfiguredFeature<RandomPatchFeatureConfig, ?> FEATURE_PATCH_WILD_CORN;
    public static RegistryKey<PlacedFeature> WILD_CORN_REGISTRY_KEY;

    public static void registerGeneration() {
        FEATURE_PATCH_WILD_CORN = Feature.RANDOM_PATCH
                .configure(createRandomPatchFeatureConfig(BlockRegistry.WILD_CORN, 128, 8, 4,
                        BlockPredicate.matchingBlock(Blocks.GRASS_BLOCK, new Vec3i(0, -1, 0))));

        Identifier configId = new Identifier(CornDelight.MODID, "patch_wild_corn");
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, configId, FEATURE_PATCH_WILD_CORN);

        Identifier featureId = new Identifier(CornDelight.MODID, "corn");
        Registry.register(BuiltinRegistries.PLACED_FEATURE, featureId, FEATURE_PATCH_WILD_CORN.withPlacement(PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, RarityFilterPlacementModifier.of(80), SquarePlacementModifier.of()));
        WILD_CORN_REGISTRY_KEY = RegistryKey.of(Registry.PLACED_FEATURE_KEY, featureId);

        BiomeModifications.addFeature(x -> x.getBiome().getTemperature() > .5f && x.getBiome().getTemperature() < 1f && x.getBiome().getDownfall() > .3f && x.getBiome().getDownfall() < .8f, GenerationStep.Feature.VEGETAL_DECORATION, WILD_CORN_REGISTRY_KEY);
    }

    private static RandomPatchFeatureConfig createRandomPatchFeatureConfig(Block block, int tries, int spreadX, int spreadZ, BlockPredicate blockPredicate) {
        return new RandomPatchFeatureConfig(tries, spreadX, spreadZ, () -> Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(BlockStateProvider.of(block))).withBlockPredicateFilter(blockPredicate));
    }
}
