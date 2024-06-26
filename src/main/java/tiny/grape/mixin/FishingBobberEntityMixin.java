package tiny.grape.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tiny.grape.module.world.AutoFish;

@Mixin(FishingBobberEntity.class)
public class FishingBobberEntityMixin {
    @Shadow private boolean caughtFish;

    @Inject(method = "onTrackedDataSet", at = @At("TAIL"))
    public void onTrackedDataSet(TrackedData<?> data, CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();

        if (caughtFish && AutoFish.water()) {
            assert client.interactionManager != null;
            AutoFish.setRecastRod(20);
            client.interactionManager.interactItem(client.player, Hand.MAIN_HAND);
        }
    }
}
