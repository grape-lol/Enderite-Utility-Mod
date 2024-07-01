package tiny.grape.module.render;

import net.minecraft.text.Text;
import tiny.grape.module.ModuleHandler;
import tiny.grape.module.SearchTags;

@SearchTags({"barrier esp", "BarrierESP"})
public class BarrierESP extends ModuleHandler {
    public BarrierESP() {
        super("Barrier ESP", Text.translatable("enderite.description.barrieresp"), Category.RENDER);
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
