package tiny.grape.module.movement;

import tiny.grape.module.ModuleHandler;
import tiny.grape.module.settings.KeyBindSetting;
import tiny.grape.module.settings.NumberSetting;

public class Flight extends ModuleHandler {
    public NumberSetting speed = new NumberSetting("Speed", 0.1f, 1f, 0.1f, 0.1f);

    public Flight() {
        super("Flight", "bro is superman", Category.MOVEMENT);
        addSetting(new KeyBindSetting("Keybind", 0));
        addSetting(speed);
    }

    @Override
    public void onTick() {
        client.player.getAbilities().flying = true;
        client.player.getAbilities().setFlySpeed(speed.getValueFloat());
        super.onTick();
    }

    @Override
    public void onDisable() {
        client.player.getAbilities().flying = false;
        super.onDisable();
    }
}
