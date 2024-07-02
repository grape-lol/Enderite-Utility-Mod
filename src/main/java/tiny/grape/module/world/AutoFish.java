package tiny.grape.module.world;

import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import tiny.grape.module.ModuleHandler;
import tiny.grape.module.SearchTags;
import tiny.grape.module.settings.KeyBindSetting;
import tiny.grape.utils.saving.ModuleSettings;
import tiny.grape.utils.saving.SettingsManager;

@SearchTags({"fish", "auto fish"})
public class AutoFish extends ModuleHandler {
    public static int recastRod = -1;
    private static boolean enabled = false;

    private final String moduleName = "Auto Fish";
    private final KeyBindSetting keyBindSetting;

    public AutoFish() {
        super("Auto Fish", Text.translatable("enderite.description.autofish"), Category.WORLD);
        ModuleSettings settings = SettingsManager.getModuleSettings(moduleName);
        keyBindSetting = new KeyBindSetting("Keybind", settings.getKey(), moduleName);
        addSetting(keyBindSetting);
        this.setEnabled(settings.isEnabled());
    }

    @Override
    public void onTick() {
        if (!enabled) {
            return;
        }
        if (recastRod > 0) {
            recastRod--;
        }
        if (recastRod == 0) {
            assert client.interactionManager != null;
            client.interactionManager.interactItem(client.player, Hand.MAIN_HAND);
            recastRod = -1;
        }
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

    public static void setRecastRod(int countdown) {
        recastRod = countdown;
    }

    public static boolean water() {
        return enabled;
    }
}
