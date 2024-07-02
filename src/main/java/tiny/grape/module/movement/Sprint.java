package tiny.grape.module.movement;

import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import tiny.grape.module.ModuleHandler;
import tiny.grape.module.SearchTags;
import tiny.grape.module.settings.KeyBindSetting;
import tiny.grape.module.settings.ModeSetting;
import tiny.grape.utils.saving.ModuleSettings;
import tiny.grape.utils.saving.SettingsManager;

@SearchTags({"sprint"})
public class Sprint extends ModuleHandler {
    public ModeSetting sprintMode = new ModeSetting("Mode", "Legit", "Legit", "Always Sprinting");
    private final String moduleName = "Sprint";
    private final KeyBindSetting keyBindSetting;

    public Sprint() {
        super("Sprint", Text.translatable("enderite.description.sprint"), Category.MOVEMENT);
        addSetting(sprintMode);
        ModuleSettings settings = SettingsManager.getModuleSettings(moduleName);
        keyBindSetting = new KeyBindSetting("Keybind", settings.getKey(), moduleName);
        addSetting(keyBindSetting);
        this.setEnabled(settings.isEnabled());
    }

    private static final Formatting Gray = Formatting.GRAY;

    @Override
    public void onTick() {
        this.setDisplayName("Sprint" + Gray + " ["+sprintMode.getMode()+"]");

        boolean shouldSprint = ((client.player.input.movementForward > 0 && client.player.input.movementSideways > 0) ||
                (client.player.input.movementForward > 0 && !client.player.isSneaking()));

        if(sprintMode.isMode("Legit") && client.player!=null) {
            client.player.setSprinting(shouldSprint);
        } else if (sprintMode.isMode("Always Sprinting") && client.player!=null) {
            client.player.setSprinting(true);
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
        client.player.setSprinting(false);
        SettingsManager.setModuleSettings(moduleName, new ModuleSettings(keyBindSetting.getKey(), false));
        super.onDisable();
    }
}
