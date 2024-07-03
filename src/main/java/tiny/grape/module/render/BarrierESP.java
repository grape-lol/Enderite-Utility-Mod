package tiny.grape.module.render;

import net.minecraft.text.Text;
import tiny.grape.module.ModuleHandler;
import tiny.grape.module.settings.SearchTags;
import tiny.grape.module.settings.KeyBindSetting;
import tiny.grape.utils.saving.ModuleSettings;
import tiny.grape.utils.saving.SettingsManager;

@SearchTags({"barrier esp", "BarrierESP"})
public class BarrierESP extends ModuleHandler {
    private final String moduleName = "Barrier ESP";
    private final KeyBindSetting keyBindSetting;

    public BarrierESP() {
        super("Barrier ESP", Text.translatable("enderite.description.barrieresp"), Category.RENDER);
        ModuleSettings settings = SettingsManager.getModuleSettings(moduleName);
        keyBindSetting = new KeyBindSetting("Keybind", settings.getKey(), moduleName);
        addSetting(keyBindSetting);
        this.setEnabled(settings.isEnabled());
    }

    private static boolean enabled = false;

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
