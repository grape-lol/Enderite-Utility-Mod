package tiny.grape.module.world;

import tiny.grape.module.ModuleHandler;

public class AutoRespawn extends ModuleHandler {
    public AutoRespawn() {
        super("Auto Respawn", "end", Category.WORLD);
    }

    @Override
    public void onTick() {
        client.player.requestRespawn();
        super.onTick();
    }
}
