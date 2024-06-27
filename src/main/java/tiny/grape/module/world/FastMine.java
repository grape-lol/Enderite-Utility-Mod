package tiny.grape.module.world;

import tiny.grape.module.ModuleHandler;

public class FastMine extends ModuleHandler {
    public FastMine() {
        super("Fast Mine", "Breaks blocks faster", Category.WORLD);
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
