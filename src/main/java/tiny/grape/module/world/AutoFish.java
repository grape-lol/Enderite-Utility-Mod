package tiny.grape.module.world;

import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import tiny.grape.module.ModuleHandler;
import tiny.grape.module.SearchTags;
import tiny.grape.module.settings.KeyBindSetting;

@SearchTags({"fish", "auto fish"})
public class AutoFish extends ModuleHandler {
    public static int recastRod = -1;
    private static boolean enabled = false;

    public AutoFish() {
        super("Auto Fish", Text.translatable("enderite.description.autofish"), Category.WORLD);
        addSetting(new KeyBindSetting("Keybind", 0));
    }

    @Override
    public void onTick() {
        if (!enabled) {
            return;
        }
        if (recastRod > 0) {
            recastRod--;
        }
        if (recastRod == 0) {
            assert client.interactionManager != null;
            client.interactionManager.interactItem(client.player, Hand.MAIN_HAND);
            recastRod = -1;
        }
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

    public static void setRecastRod(int countdown) {
        recastRod = countdown;
    }

    public static boolean water() {
        return enabled;
    }
}
