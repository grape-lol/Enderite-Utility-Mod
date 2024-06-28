package tiny.grape.module.movement;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import tiny.grape.module.ModuleHandler;
import tiny.grape.module.SearchTags;
import tiny.grape.module.settings.KeyBindSetting;
import tiny.grape.utils.PacketHelper;

@SearchTags({"fly", "flight"})
public class Flight extends ModuleHandler {
    private int floatingTickCount = 0;
    private Vec3d oldPos = Vec3d.ZERO;

    public Flight() {
        super("Flight", "bro is superman", Category.MOVEMENT);
        addSetting(new KeyBindSetting("Keybind", 0));
    }

    @Override
    public void onEnable() {
        super.onEnable();
        floatingTickCount = 0;
        oldPos = client.player.getPos();
        // Enable flying
        if (client.player != null) {
            client.player.getAbilities().flying = true;
            client.player.getAbilities().allowFlying = true;
        }
    }

    @Override
    public void onTick() {
        if (client.player != null) {
            // Check if player is floating
            if (client.player.getPos().getY() >= oldPos.getY() - 0.485D) {
                floatingTickCount++;
            }

            oldPos = client.player.getPos();

            // Check conditions to send position packet
            if (floatingTickCount > 20) {
                BlockPos checkPos = new BlockPos((int) client.player.getX(), (int) (client.player.getY() - 0.485D), (int) (client.player.getZ() + 9));
                if (client.world.getBlockState(checkPos).isAir()) {
                    PacketHelper.sendPosition(client.player.getPos().subtract(0.0, -0.485D, 0.0));
                    floatingTickCount = 0;
                }
            }
        }
        super.onTick();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        // Disable flying
        if (client.player != null) {
            client.player.getAbilities().flying = false;
            client.player.getAbilities().allowFlying = false;
        }
    }
}