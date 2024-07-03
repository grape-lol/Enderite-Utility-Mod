package tiny.grape.module.render;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import tiny.grape.module.ModuleHandler;
import tiny.grape.module.settings.SearchTags;
import tiny.grape.module.settings.KeyBindSetting;
import tiny.grape.module.settings.ModeSetting;
import tiny.grape.utils.saving.ModuleSettings;
import tiny.grape.utils.saving.SettingsManager;

@SearchTags({"night vision", "gamma","nightvision","fullbright"})
public class Fullbright extends ModuleHandler {
    public ModeSetting fbMode = new ModeSetting("Mode", "Night Vision", "Night Vision");
    private final String moduleName = "Fullbright";
    private final KeyBindSetting keyBindSetting;

    private static final Formatting Gray = Formatting.GRAY;

    public Fullbright() {
        super("Fullbright", Text.translatable("enderite.description.fullbright"), Category.RENDER);
        addSetting(fbMode);
        ModuleSettings settings = SettingsManager.getModuleSettings(moduleName);
        keyBindSetting = new KeyBindSetting("Keybind", settings.getKey(), moduleName);
        addSetting(keyBindSetting);
        this.setEnabled(settings.isEnabled());
    }

    @Override
    public void onEnable() {
        super.onEnable();
        SettingsManager.setModuleSettings(moduleName, new ModuleSettings(keyBindSetting.getKey(), true));
    }

    @Override
    public void onTick() {
        this.setDisplayName("Fullbright" + Gray + " ["+fbMode.getMode()+"]");
        if(fbMode.isMode("Night Vision")) {
            assert client.player != null;
            if (!client.player.hasStatusEffect(StatusEffects.NIGHT_VISION))
                client.player.addStatusEffect(
                        new StatusEffectInstance(
                                StatusEffects.NIGHT_VISION,
                                999999999,
                                Integer.MAX_VALUE,
                                false,
                                false,
                                false
                        )
                );
        }
        super.onTick();
    }

    @Override
    public void onDisable() {
        assert client.player != null;
        var effect = client.player.getStatusEffect(StatusEffects.NIGHT_VISION);
        if (effect != null && effect.getDuration() >= 10000) {
            client.player.removeStatusEffect(StatusEffects.NIGHT_VISION);
        }

        SettingsManager.setModuleSettings(moduleName, new ModuleSettings(keyBindSetting.getKey(), false));
        super.onDisable();
    }
}
