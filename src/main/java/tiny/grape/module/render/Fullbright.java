package tiny.grape.module.render;

import net.minecraft.client.option.SimpleOption;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.Formatting;
import tiny.grape.module.ModuleHandler;
import tiny.grape.module.settings.KeyBindSetting;
import tiny.grape.module.settings.ModeSetting;

public class Fullbright extends ModuleHandler {
    public ModeSetting fbMode = new ModeSetting("Mode", "Night Vision", "Night Vision", "Gamma");
    private SimpleOption<Double> originalGamma;

    private static final Formatting Gray = Formatting.GRAY;

    public Fullbright() {
        super("Fullbright", "spooky dark", Category.RENDER);
        addSetting(fbMode);
        addSetting(new KeyBindSetting("Keybind", 0));
    }

    @Override
    public void onEnable() {
        originalGamma = client.options.getGamma();
        super.onEnable();
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
        } else {
            client.options.getGamma().setValue(1D);
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
        client.options.getGamma().setValue(originalGamma.getValue());
        super.onDisable();
    }
}