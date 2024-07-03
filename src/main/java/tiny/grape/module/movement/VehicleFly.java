package tiny.grape.module.movement;

import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import tiny.grape.module.ModuleHandler;
import tiny.grape.module.settings.SearchTags;
import tiny.grape.module.settings.KeyBindSetting;
import tiny.grape.utils.PacketHelper;
import tiny.grape.utils.saving.ModuleSettings;
import tiny.grape.utils.saving.SettingsManager;

@SearchTags({"vehicle fly", "boat fly"})
public class VehicleFly extends ModuleHandler {
    private int floatingTickCount = 0;
    private Vec3d oldPos = Vec3d.ZERO;
    private final String moduleName = "Vehicle Fly";
    private final KeyBindSetting keyBindSetting;

    public VehicleFly() {
        super("Vehicle Fly", Text.translatable("enderite.description.vehiclefly"), Category.MOVEMENT);
        ModuleSettings settings = SettingsManager.getModuleSettings(moduleName);
        keyBindSetting = new KeyBindSetting("Keybind", settings.getKey(), moduleName);
        addSetting(keyBindSetting);
        this.setEnabled(settings.isEnabled());
    }

    @Override
    public void onEnable() {
        super.onEnable();
        floatingTickCount = 0;
        assert client.player != null;
        oldPos = client.player.getPos();
        SettingsManager.setModuleSettings(moduleName, new ModuleSettings(keyBindSetting.getKey(), true));
    }

    @Override
    public void onDisable() {
        super.onDisable();
        if (client.player != null && client.player.hasVehicle()) {
            client.player.getVehicle().setVelocity(Vec3d.ZERO);
        }
        SettingsManager.setModuleSettings(moduleName, new ModuleSettings(keyBindSetting.getKey(), false));
    }

    @Override
    public void onTick() {
        if (client.player != null && client.player.hasVehicle()) {
            if (client.player.getPos().getY() >= oldPos.getY() - 0.485D) {
                floatingTickCount++;
            }

            oldPos = client.player.getPos();

            if (floatingTickCount > 20) {
                BlockPos checkPos = new BlockPos((int) client.player.getX(), (int) (client.player.getY() - 0.485D), (int) client.player.getZ());
                assert client.world != null;
                if (client.world.getBlockState(checkPos).isAir()) {
                    PacketHelper.sendPosition(client.player.getPos().subtract(0.0, -0.485D, 0.0));
                    floatingTickCount = 0;
                }
            }

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
}
