package tiny.grape.module;
// tiny.grape.module.misc/combat/movement/render/world

import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import tiny.grape.module.settings.BooleanSetting;
import tiny.grape.module.settings.KeyBindSetting;
import tiny.grape.module.settings.ModeSetting;
import tiny.grape.module.settings.NumberSetting;
import tiny.grape.utils.saving.ModuleSettings;
import tiny.grape.utils.saving.SettingsManager;

public class ExampleModule extends ModuleHandler{
    public NumberSetting numSetting = new NumberSetting("Number", 1f, 50f, 25f, 1f); // Min, Max, Default, Increment
    public ModeSetting modeSetting = new ModeSetting("Mode", "Default", "Default", "Default2", "Default3"); // Name, Default Mode, Modes
    public BooleanSetting boolSetting = new BooleanSetting("Boolean", true); // True / False

    private final String moduleName = "Example Module";
    private final KeyBindSetting keyBindSetting;

    private static final Formatting Gray = Formatting.GRAY;

    public ExampleModule() {
        super("Example Module", Text.translatable("enderite.description.examplemodule"), Category.MISC); // MISC, COMBAT, MOVEMENT, RENDER, WORLD

        ModuleSettings settings = SettingsManager.getModuleSettings(moduleName);
        keyBindSetting = new KeyBindSetting("Keybind", settings.getKey(), moduleName);

        addSetting(numSetting);
        addSetting(modeSetting);
        addSetting(boolSetting);
        addSetting(keyBindSetting);
        this.setEnabled(settings.isEnabled());
    }

    @Override
    public void onTick() {
        // getValue (double) | getValueInt | getValueFloat
        this.setDisplayName("Example Module" + Gray + " [M: " + modeSetting.getMode() + " N: " + numSetting.getValue() + " B: " + boolSetting.isEnabled() + "]");

        super.onTick();
    }

    @Override
    public void onEnable() {
        SettingsManager.setModuleSettings(moduleName, new ModuleSettings(keyBindSetting.getKey(), true));

        super.onEnable();
    }

    @Override
    public void onDisable() {
        SettingsManager.setModuleSettings(moduleName, new ModuleSettings(keyBindSetting.getKey(), false));

        super.onDisable();
    }
}
