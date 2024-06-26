package tiny.grape.module.render;

import tiny.grape.module.ModuleHandler;

public class BarrierESP extends ModuleHandler {
    public BarrierESP() {
        super("Barrier ESP", "Lets you see barriers without holding a barrier.", Category.RENDER);
    }

    private static boolean enabled = false;

    public static boolean checkEnable() {
        return enabled;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        enabled = false;
    }

    @Override
    public void onEnable() {
        super.onEnable();
        enabled = true;
    }
}
