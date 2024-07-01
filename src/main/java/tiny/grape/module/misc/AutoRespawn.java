package tiny.grape.module.misc;

import net.minecraft.text.Text;
import tiny.grape.module.ModuleHandler;
import tiny.grape.module.SearchTags;
import tiny.grape.module.settings.KeyBindSetting;

@SearchTags({"auto respawn"})
public class AutoRespawn extends ModuleHandler {
    public AutoRespawn() {
        super("Auto Respawn", Text.translatable("enderite.description.autorespawn"), Category.MISC);
        addSetting(new KeyBindSetting("Keybind", 0));
        addSetting(new KeyBindSetting("Keybind", 0));
    }

    @Override
    public void onTick() {
        client.player.requestRespawn();
        super.onTick();
    }
}
