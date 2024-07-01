package tiny.grape.module.movement;

import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import tiny.grape.module.ModuleHandler;
import tiny.grape.module.SearchTags;
import tiny.grape.module.settings.KeyBindSetting;
import tiny.grape.module.settings.ModeSetting;

@SearchTags({"sprint"})
public class Sprint extends ModuleHandler {
    public ModeSetting sprintMode = new ModeSetting("Mode", "Legit", "Legit", "Always Sprinting");

    public Sprint() {
        super("Sprint", Text.translatable("enderite.description.sprint"), Category.MOVEMENT);
        addSetting(sprintMode);
        addSetting(new KeyBindSetting("Keybind", 0));
    }

    private static final Formatting Gray = Formatting.GRAY;

    @Override
    public void onTick() {
        this.setDisplayName("Sprint" + Gray + " ["+sprintMode.getMode()+"]");

        boolean shouldSprint = ((client.player.input.movementForward > 0 && client.player.input.movementSideways > 0) ||
                (client.player.input.movementForward > 0 && !client.player.isSneaking()));
        if(sprintMode.isMode("Legit")) {
            client.player.setSprinting(shouldSprint);
        } else if (sprintMode.isMode("Always Sprinting")) {
            client.player.setSprinting(true);
        }
        super.onTick();
    }

    @Override
    public void onDisable() {
        client.player.setSprinting(false);
        super.onDisable();
    }
}
