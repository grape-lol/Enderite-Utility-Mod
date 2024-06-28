package tiny.grape.module.movement;

import tiny.grape.module.ModuleHandler;
import tiny.grape.module.SearchTags;
import tiny.grape.module.settings.KeyBindSetting;

@SearchTags({"fly", "flight"})
public class Flight extends ModuleHandler {
    public Flight() {
        super("Flight", "bro is superman", Category.MOVEMENT);
        addSetting(new KeyBindSetting("Keybind", 0));
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onTick() {
        if(client.options.jumpKey.isPressed()) {
            client.player.jump();
        }

        super.onTick();
    }
    @Override
    public void onDisable() {
        super.onDisable();
    }
}
