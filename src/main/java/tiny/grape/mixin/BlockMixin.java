package tiny.grape.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tiny.grape.module.render.XRay;

@Mixin(Block.class)
public class BlockMixin {
    @Inject(method = "shouldDrawSide", at = @At("HEAD"), cancellable = true)
    private static void shouldDrawSide(BlockState state, BlockView world, BlockPos pos, Direction side, BlockPos otherPos, CallbackInfoReturnable<Boolean> ci) {
        if (XRay.isOn()) {
            boolean shouldDraw = XRay.getInstance().shouldDrawBlock(state);
            ci.setReturnValue(shouldDraw);
            if (shouldDraw) {
                System.out.println("Drawing block: " + state.getBlock().getTranslationKey());
            } else {
                System.out.println("Not drawing block: " + state.getBlock().getTranslationKey());
            }
        }
    }
}
