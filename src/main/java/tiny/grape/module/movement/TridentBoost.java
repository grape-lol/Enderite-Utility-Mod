package tiny.grape.module.movement;

import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.MathHelper;
import tiny.grape.module.ModuleHandler;
import tiny.grape.module.settings.SearchTags;
import tiny.grape.module.settings.KeyBindSetting;
import tiny.grape.module.settings.NumberSetting;
import tiny.grape.utils.saving.ModuleSettings;
import tiny.grape.utils.saving.SettingsManager;

@SearchTags({"trident boost", "trident fly"})
public class TridentBoost extends ModuleHandler {
    NumberSetting factor = new NumberSetting("Factor", 1, 10, 3, 1);
    NumberSetting upFactor = new NumberSetting("Up Factor", 1, 10, 3, 1);
    private final String moduleName = "Trident Boost";
    private final KeyBindSetting keyBindSetting;

    public TridentBoost() {
        super("Trident Boost", Text.translatable("enderite.description.tridentboost"), Category.MOVEMENT);
        addSetting(factor);
        addSetting(upFactor);
        ModuleSettings settings = SettingsManager.getModuleSettings(moduleName);
        keyBindSetting = new KeyBindSetting("Keybind", settings.getKey(), moduleName);
        addSetting(keyBindSetting);
        this.setEnabled(settings.isEnabled());
    }

    private static final Formatting Gray = Formatting.GRAY;

    @Override
    public void onTick() {
        this.setDisplayName("Trident Boost" + Gray + " [F: "+factor.getValue()+" U: "+upFactor.getValue()+"]");
        float yaw = (float) Math.toRadians(client.player.getYaw());
        float pitch = (float) Math.toRadians(client.player.getPitch());
        double vSpeed = factor.getValue() / 5;
        if(client.player.isUsingRiptide()) {
            client.player.setVelocity(-MathHelper.sin(yaw) * vSpeed, -MathHelper.sin(pitch) * vSpeed * upFactor.getValue(), MathHelper.cos(yaw) * vSpeed);
        }
        super.onTick();
    }

    @Override
    public void onEnable() {
        super.onEnable();

        SettingsManager.setModuleSettings(moduleName, new ModuleSettings(keyBindSetting.getKey(), true));
    }

    @Override
    public void onDisable() {
        super.onDisable();

        SettingsManager.setModuleSettings(moduleName, new ModuleSettings(keyBindSetting.getKey(), false));
    }
}
