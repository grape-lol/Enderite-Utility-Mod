package tiny.grape.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.debug.DebugRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.hit.EntityHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tiny.grape.ClientEnderiteUtilityMod;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    public void onTick(CallbackInfo ci) {
        ClientEnderiteUtilityMod.INSTANCE.onTick();
    }
}
