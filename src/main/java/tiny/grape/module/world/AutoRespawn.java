package tiny.grape.module.world;

import tiny.grape.module.ModuleHandler;
import tiny.grape.module.settings.KeyBindSetting;

public class AutoRespawn extends ModuleHandler {
    public AutoRespawn() {
        super("Auto Respawn", "end", Category.WORLD);
        addSetting(new KeyBindSetting("Keybind", 0));
    }

    @Override
    public void onTick() {
        client.player.requestRespawn();
        super.onTick();
    }
}
