package tiny.grape.module.movement;

import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import tiny.grape.module.ModuleHandler;
import tiny.grape.module.SearchTags;
import tiny.grape.module.settings.KeyBindSetting;
import tiny.grape.utils.PacketHelper;

@SearchTags({"vehicle fly", "boat fly"})
public class VehicleFly extends ModuleHandler {
    private int floatingTickCount = 0;
    private Vec3d oldPos = Vec3d.ZERO;

    public VehicleFly() {
        super("Vehicle Fly", Text.translatable("enderite.description.vehiclefly"), Category.MOVEMENT);
        addSetting(new KeyBindSetting("Keybind", 0));
    }

    @Override
    public void onEnable() {
        super.onEnable();
        floatingTickCount = 0;
        oldPos = client.player.getPos();
    }

    @Override
    public void onTick() {
        if (client.player != null && client.player.hasVehicle()) {
            // Check if player in vehicle is floating
            if (client.player.getPos().getY() >= oldPos.getY() - 0.485D) {
                floatingTickCount++;
            }

            oldPos = client.player.getPos();

            // Send position packet
            if (floatingTickCount > 20) {
                BlockPos checkPos = new BlockPos((int) client.player.getX(), (int) (client.player.getY() - 0.485D), (int) client.player.getZ());
                if (client.world.getBlockState(checkPos).isAir()) {
                    PacketHelper.sendPosition(client.player.getPos().subtract(0.0, -0.485D, 0.0));
                    floatingTickCount = 0;
                }
            }

            // Change vehicle motion to allow flying
            Vec3d velocity = client.player.getVehicle().getVelocity();
            double flySpeed = 0.06;

            if (client.options.jumpKey.isPressed()) {
                velocity = velocity.add(0, flySpeed, 0);
            } else if (client.options.sneakKey.isPressed()) {
                velocity = velocity.add(0, -flySpeed, 0);
            }

            client.player.getVehicle().setVelocity(velocity);
        }
        super.onTick();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        // Reset
        if (client.player != null && client.player.hasVehicle()) {
            client.player.getVehicle().setVelocity(Vec3d.ZERO);
        }
    }
}
