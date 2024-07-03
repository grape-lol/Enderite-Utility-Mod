package tiny.grape.module.world;

import net.minecraft.text.Text;
import tiny.grape.module.ModuleHandler;
import tiny.grape.module.settings.SearchTags;
import tiny.grape.module.settings.KeyBindSetting;
import tiny.grape.utils.saving.ModuleSettings;
import tiny.grape.utils.saving.SettingsManager;

@SearchTags({"FastMine", "SpeedMine", "fast break",
        "fast mine", "speed mine", "NoBreakDelay",
        "no break delay"})
public class FastMine extends ModuleHandler {
    private static boolean enabled = false;

    private final String moduleName = "Fast Mine";
    private final KeyBindSetting keyBindSetting;

    public FastMine() {
        super("Fast Mine", Text.translatable("enderite.description.fastmine"), Category.WORLD);
        ModuleSettings settings = SettingsManager.getModuleSettings(moduleName);
        keyBindSetting = new KeyBindSetting("Keybind", settings.getKey(), moduleName);
        addSetting(keyBindSetting);
        this.setEnabled(settings.isEnabled());
    }

    public static boolean checkEnable() {
        return enabled;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        enabled = false;
        SettingsManager.setModuleSettings(moduleName, new ModuleSettings(keyBindSetting.getKey(), false));
    }

    @Override
    public void onEnable() {
        super.onEnable();
        enabled = true;
        SettingsManager.setModuleSettings(moduleName, new ModuleSettings(keyBindSetting.getKey(), true));
    }
}
