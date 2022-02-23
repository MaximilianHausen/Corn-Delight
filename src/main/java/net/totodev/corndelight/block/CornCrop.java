package net.totodev.corndelight.block;

import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;

import java.util.Random;

public class CornCrop extends CropBlock {
    public static final int GROW_UPPER_AGE = 3;
    public static final BooleanProperty UPPER = BooleanProperty.of("upper");
    private static final VoxelShape[] UPPER_SHAPE_BY_AGE = new VoxelShape[]{
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D),
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D),
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D),
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D),
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D),
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D),
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D)
    };
    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D),
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D),
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D),
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D),
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D),
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D),
            Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)
    };

    public CornCrop(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(UPPER, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE, UPPER);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return state.get(UPPER) ? UPPER_SHAPE_BY_AGE[state.get(getAgeProperty())] : SHAPE_BY_AGE[state.get(getAgeProperty())];
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos downpos = pos.down();
        if (world.getBlockState(downpos).isOf(this))
            return !world.getBlockState(downpos).get(UPPER)
                    && (world.getLuminance(pos) >= 8 || world.isSkyVisible(pos))
                    && getAge(world.getBlockState(downpos)) >= GROW_UPPER_AGE;
        return super.canPlaceAt(state, world, pos); // Forge: Not into MML because HighCropBlock has the same condition
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random rand) {
        if (!world.isChunkLoaded(pos.getX(), pos.getZ()))
            return; // Forge: Idk what this does but I kept it
        int age = getAge(state);
        if (world.getLuminance(pos) >= 9 && age < getMaxAge())
            world.setBlockState(pos, withAge(age + 1).with(UPPER, state.get(UPPER)), 2);
        if (state.get(UPPER))
            return;
        if (age >= GROW_UPPER_AGE && getDefaultState().canPlaceAt(world, pos.up()) && world.isAir(pos.up()))
            world.setBlockState(pos.up(), getDefaultState().with(UPPER, true));
    }

    @Override
    public boolean isFertilizable(BlockView worldIn, BlockPos pos, BlockState state, boolean isClient) {
        BlockState upperState = worldIn.getBlockState(pos.up());
        if (upperState.isOf(this))
            return !isMature(upperState);
        if (state.get(UPPER))
            return !isMature(state);
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random rand, BlockPos pos, BlockState state) {
        int ageGrowth = Math.min(getAge(state) + getGrowthAmount(world), 15);
        if (ageGrowth <= getMaxAge()) {
            world.setBlockState(pos, state.with(AGE, ageGrowth));
        } else {
            world.setBlockState(pos, state.with(AGE, getMaxAge()));
            if (state.get(UPPER))
                return;
            BlockState top = world.getBlockState(pos.up());
            if (top.isOf(this)) {
                Fertilizable growable = (Fertilizable) top.getBlock();
                if (growable.isFertilizable(world, pos.up(), top, false))
                    growable.grow(world, world.random, pos.up(), top);
            } else {
                int remainingGrowth = ageGrowth - getMaxAge() - 1;
                if (getDefaultState().canPlaceAt(world, pos.up()) && world.isAir(pos.up()))
                    world.setBlockState(pos.up(), getDefaultState()
                            .with(UPPER, true)
                            .with(getAgeProperty(), remainingGrowth), 3);
            }
        }
    }
}
