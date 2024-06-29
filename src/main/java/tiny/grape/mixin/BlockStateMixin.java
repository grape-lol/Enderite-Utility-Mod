package tiny.grape.mixin;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tiny.grape.module.render.XRay;

@Mixin(AbstractBlock.AbstractBlockState.class)
public class BlockStateMixin {
    @Inject(method = "isSideInvisible", at = @At("HEAD"), cancellable = true)
    private void injectIsSideInvisible(BlockState state, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        if (XRay.isOn()) {
            if (XRay.getInstance().shouldDrawBlock((BlockState) (Object) this)) {
                cir.setReturnValue(false);
                return;
            } else {
                cir.setReturnValue(true);
                return;
            }
        }
    }

    @Inject(method = "getLuminance", at = @At("HEAD"), cancellable = true)
    private void injectGetLuminance(CallbackInfoReturnable<Integer> cir) {
        if (XRay.isOn()) {
            if (XRay.getInstance().shouldDrawBlock((BlockState) (Object) this)) {
                cir.setReturnValue(12);
            }
        }
    }

    @Inject(method = "getAmbientOcclusionLightLevel", at = @At("HEAD"), cancellable = true)
    private void injectGetAmbientOcclusionLightLevel(CallbackInfoReturnable<Float> cir) {
        if (XRay.isOn()) {
            if (XRay.getInstance().shouldDrawBlock((BlockState) (Object) this)) {
                cir.setReturnValue(1.0f);
            }
        }
    }

    @Inject(method = "getCullingFace", at = @At("HEAD"), cancellable = true)
    private void injectGetCullingFace(CallbackInfoReturnable<VoxelShape> cir) {
        if (XRay.isOn()) {
            if (XRay.getInstance().shouldDrawBlock((BlockState) (Object) this)) {
                cir.setReturnValue(VoxelShapes.fullCube());
            }
        }
    }
}
