package tiny.grape.module.movement;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import tiny.grape.module.ModuleHandler;
import tiny.grape.module.settings.SearchTags;
import tiny.grape.module.settings.KeyBindSetting;
import tiny.grape.utils.saving.ModuleSettings;
import tiny.grape.utils.saving.SettingsManager;

@SearchTags({"spider","wall walk","wallwalk"})
public class Spider extends ModuleHandler {
    private final String moduleName = "Spider";
    private final KeyBindSetting keyBindSetting;

    public Spider() {
        super("Spider", Text.translatable("enderite.description.spider"), Category.MOVEMENT);
        ModuleSettings settings = SettingsManager.getModuleSettings(moduleName);
        keyBindSetting = new KeyBindSetting("Keybind", settings.getKey(), moduleName);
        addSetting(keyBindSetting);
        this.setEnabled(settings.isEnabled());
    }

    @Override
    public void onTick() {
        ClientPlayerEntity player = client.player;
        if(!player.horizontalCollision)
            return;

        Vec3d velocity = player.getVelocity();
        if(velocity.y >= 0.2)
            return;

        player.setVelocity(velocity.x, 0.2, velocity.z);
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
