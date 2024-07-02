package tiny.grape.module.misc;

import net.minecraft.text.Text;
import tiny.grape.module.ModuleHandler;
import tiny.grape.module.SearchTags;
import tiny.grape.module.settings.KeyBindSetting;
import tiny.grape.utils.saving.ModuleSettings;
import tiny.grape.utils.saving.SettingsManager;

@SearchTags({"auto respawn"})
public class AutoRespawn extends ModuleHandler {
    private final String moduleName = "Auto Respawn";
    private final KeyBindSetting keyBindSetting;

    public AutoRespawn() {
        super("Auto Respawn", Text.translatable("enderite.description.autorespawn"), Category.MISC);
        ModuleSettings settings = SettingsManager.getModuleSettings(moduleName);
        keyBindSetting = new KeyBindSetting("Keybind", settings.getKey(), moduleName);
        addSetting(keyBindSetting);
        this.setEnabled(settings.isEnabled());
    }

    @Override
    public void onTick() {
        client.player.requestRespawn();
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
