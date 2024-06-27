package tiny.grape.module.world;

import tiny.grape.module.ModuleHandler;

public class FastMine extends ModuleHandler {
    private static boolean enabled = false;

    public FastMine() {
        super("Fast Mine", "Breaks blocks faster", Category.WORLD);
    }

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
