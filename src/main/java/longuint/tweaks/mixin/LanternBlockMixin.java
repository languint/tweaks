package longuint.tweaks.mixin;

import net.minecraft.block.*;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LanternBlock.class)
public abstract class LanternBlockMixin {

    @Inject(method = "canPlaceAt", at = @At("HEAD"), cancellable = true)
    private void allowUpsideDownStairs(BlockState state, @NotNull WorldView world, @NotNull BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        Direction direction = attachedDirection(state).getOpposite();
        BlockPos offsetPos = pos.offset(direction);
        BlockState offsetState = world.getBlockState(offsetPos);

        if (Block.sideCoversSmallSquare(world, offsetPos, direction.getOpposite())) {
            cir.setReturnValue(true);
            return;
        }

        // Custom behavior: Allow placement on upside-down stairs
        if (offsetState.getBlock() instanceof StairsBlock && offsetState.get(StairsBlock.HALF) == BlockHalf.TOP) {
            cir.setReturnValue(true);
        }
    }

    @Unique
    private static Direction attachedDirection(@NotNull BlockState state) {
        return state.get(LanternBlock.HANGING) ? Direction.DOWN : Direction.UP;
    }
}