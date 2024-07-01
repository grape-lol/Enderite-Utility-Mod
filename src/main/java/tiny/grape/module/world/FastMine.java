package tiny.grape.module.world;

import net.minecraft.text.Text;
import tiny.grape.module.ModuleHandler;
import tiny.grape.module.SearchTags;
import tiny.grape.module.settings.KeyBindSetting;

@SearchTags({"FastMine", "SpeedMine", "fast break",
        "fast mine", "speed mine", "NoBreakDelay",
        "no break delay"})
public class FastMine extends ModuleHandler {
    private static boolean enabled = false;

    public FastMine() {
        super("Fast Mine", Text.translatable("enderite.description.fastmine"), Category.WORLD);
        addSetting(new KeyBindSetting("Keybind", 0));
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
