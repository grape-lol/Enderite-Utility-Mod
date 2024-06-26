package tiny.grape.module.movement;

import net.minecraft.util.Formatting;
import net.minecraft.util.math.MathHelper;
import tiny.grape.module.ModuleHandler;
import tiny.grape.module.settings.KeyBindSetting;
import tiny.grape.module.settings.NumberSetting;

public class TridentBoost extends ModuleHandler {
    NumberSetting factor = new NumberSetting("Factor", 1, 10, 3, 1);
    NumberSetting upFactor = new NumberSetting("Up Factor", 1, 10, 3, 1);

    public TridentBoost() {
        super("Trident Boost", "description", Category.MOVEMENT);
        addSetting(factor);
        addSetting(upFactor);
        addSetting(new KeyBindSetting("Keybind", 0));
    }

    private static final Formatting Gray = Formatting.GRAY;

    @Override
    public void onTick() {
        this.setDisplayName("Trident Boost" + Gray + " [F: "+factor.getValue()+" H: "+upFactor.getValue()+"]");
        float yaw = (float) Math.toRadians(client.player.getYaw());
        float pitch = (float) Math.toRadians(client.player.getPitch());
        double vSpeed = factor.getValue() / 5;
        if(client.player.isUsingRiptide()) {
            client.player.setVelocity(-MathHelper.sin(yaw) * vSpeed, -MathHelper.sin(pitch) * vSpeed * upFactor.getValue(), MathHelper.cos(yaw) * vSpeed);
        }
        super.onTick();
    }
}
