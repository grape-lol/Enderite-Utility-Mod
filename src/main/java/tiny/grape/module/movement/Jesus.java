package tiny.grape.module.movement;

import net.minecraft.client.option.GameOptions;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Vec3d;
import tiny.grape.module.ModuleHandler;
import tiny.grape.module.settings.SearchTags;
import tiny.grape.module.settings.KeyBindSetting;
import tiny.grape.module.settings.ModeSetting;
import tiny.grape.module.settings.NumberSetting;
import tiny.grape.utils.saving.ModuleSettings;
import tiny.grape.utils.saving.SettingsManager;

@SearchTags({"water walk","waterwalk","jesus","dolphin"})
public class Jesus extends ModuleHandler {
    public ModeSetting mode = new ModeSetting("Mode", "Static", "Static", "Velocity", "Dolphin");
    public NumberSetting speed = new NumberSetting("Static Speed", 1, 10, 1, 1);
    public NumberSetting velStrength = new NumberSetting("Velocity Strength", 0.0003, 0.3, 0.1, 0.0001);
    private final String moduleName = "Jesus";
    private final KeyBindSetting keyBindSetting;

    public Jesus() {
        super("Jesus", Text.translatable("enderite.description.jesus"), Category.MOVEMENT);
        addSetting(mode);
        addSetting(speed);
        addSetting(velStrength);
        ModuleSettings settings = SettingsManager.getModuleSettings(moduleName);
        keyBindSetting = new KeyBindSetting("Keybind", settings.getKey(), moduleName);
        addSetting(keyBindSetting);
        this.setEnabled(settings.isEnabled());
    }

    private static final Formatting Gray = Formatting.GRAY;

    @Override
    public void onTick() {
        this.setDisplayName("Jesus" + Gray + " ["+mode.getMode()+"]");
        if (mode.isMode("Static")) {
            assert client.player != null;
            if (client.player.isTouchingWater()) {
                client.player.setVelocity(0, 0.1, 0);
                GameOptions go = client.options;
                float y = client.player.getYaw();
                int mx = 0, mz = 0;
                if (go.backKey.isPressed()) {
                    mz++;
                }
                if (go.leftKey.isPressed()) {
                    mx--;
                }
                if (go.rightKey.isPressed()) {
                    mx++;
                }
                if (go.forwardKey.isPressed()) {
                    mz--;
                }
                double ts = speed.getValueFloat() / 2;
                double s = Math.sin(Math.toRadians(y));
                double c = Math.cos(Math.toRadians(y));
                double nx = ts * mz * s;
                double nz = ts * mz * -c;
                nx += ts * mx * -c;
                nz += ts * mx * -s;
                Vec3d nv3 = new Vec3d(nx, client.player.getVelocity().y, nz);
                client.player.setVelocity(nv3);
            }
        } else if (mode.isMode("Velocity")) {
            assert client.player != null;
            if (client.player.isTouchingWater()) {
                client.player.setVelocity(client.player.getVelocity().x, velStrength.getValue(), client.player.getVelocity().z);
            }
        } else if (mode.isMode("Dolphin")) {
            if(!client.player.isTouchingWater() || client.player.isSneaking())
                return;

            Vec3d velocity = client.player.getVelocity();
                client.player.setVelocity(velocity.x, velocity.y + 0.04, velocity.z);
        }
        super.onTick();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        SettingsManager.setModuleSettings(moduleName, new ModuleSettings(keyBindSetting.getKey(), false));
    }

    @Override
    public void onEnable() {
        super.onEnable();
        SettingsManager.setModuleSettings(moduleName, new ModuleSettings(keyBindSetting.getKey(), true));
    }
}
