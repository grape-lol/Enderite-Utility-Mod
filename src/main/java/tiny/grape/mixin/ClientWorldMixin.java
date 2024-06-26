package tiny.grape.mixin;

import net.minecraft.block.Blocks;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import tiny.grape.module.render.BarrierESP;

@Mixin(ClientWorld.class)
public class ClientWorldMixin {
    @ModifyArgs(
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/world/ClientWorld;randomBlockDisplayTick(IIIILnet/minecraft/util/math/random/Random;Lnet/minecraft/block/Block;Lnet/minecraft/util/math/BlockPos$Mutable;)V"),
            method = "doRandomBlockDisplayTicks")
    private void doRandomBlockDisplayTicks(Args args)
    {
        if(!BarrierESP.checkEnable())
            return;

        args.set(5, Blocks.BARRIER);
    }
}
