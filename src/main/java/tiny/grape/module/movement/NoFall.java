package tiny.grape.module.movement;

import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.Formatting;
import tiny.grape.module.ModuleHandler;
import tiny.grape.module.SearchTags;
import tiny.grape.module.settings.BooleanSetting;
import tiny.grape.module.settings.KeyBindSetting;
import tiny.grape.module.settings.ModeSetting;
import tiny.grape.module.settings.NumberSetting;

@SearchTags({"nofall", "no fall", "fall damage"})
public class NoFall extends ModuleHandler {
    public ModeSetting mode = new ModeSetting("Mode", "On Ground", "On Ground", "Break Fall");

    public NoFall() {
        super("No Fall", "no fall no damage >:)", Category.MOVEMENT);
        addSetting(mode);
        addSetting(new KeyBindSetting("Keybind", 0));
    }

    private static final Formatting Gray = Formatting.GRAY;

    @Override
    public void onTick() {
        this.setDisplayName("No Fall" + Gray + " ["+mode.getMode()+"]");
        if (client.player == null || client.getNetworkHandler() == null) {
            return;
        }
        if (mode.getMode().equalsIgnoreCase("Break Fall")) {
            if (client.player.fallDistance > 2.5) {
                client.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.OnGroundOnly(true));
                client.player.setVelocity(0, 0.1, 0);
                client.player.fallDistance = 0;
            }
        } else if (mode.getMode().equalsIgnoreCase("On Ground")) {
            if (client.player.fallDistance > 2.5) client.getNetworkHandler().sendPacket(new PlayerMoveC2SPacket.OnGroundOnly(true));
        }
        super.onTick();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
