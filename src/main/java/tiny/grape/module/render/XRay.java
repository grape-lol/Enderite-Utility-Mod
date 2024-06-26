package tiny.grape.module.render;

import net.minecraft.block.BlockState;
import tiny.grape.module.ModuleHandler;
import tiny.grape.module.settings.KeyBindSetting;

import java.util.HashSet;

public class XRay extends ModuleHandler {
    private static XRay instance;
    private static boolean enabled = false;
    private HashSet<String> xrayBlocks = new HashSet<>();

    public XRay() {
        super("XRay", "See through specific blocks", Category.RENDER);
        addSetting(new KeyBindSetting("Keybind", 0));
        rayBlocks();
    }

    private void rayBlocks() {
        xrayBlocks.add("minecraft:coal_ore");
        xrayBlocks.add("minecraft:iron_ore");
        xrayBlocks.add("minecraft:diamond_ore");
        xrayBlocks.add("minecraft:emerald_ore");
        xrayBlocks.add("minecraft:lapis_ore");
        xrayBlocks.add("minecraft:redstone_ore");
    }

    public boolean shouldDrawBlock(BlockState state) {
        return xrayBlocks.contains(state.getBlock().toString());
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

    public static boolean isOn() {
        return enabled;
    }

    public static XRay getInstance() {
        if (instance == null) {
            instance = new XRay();
        }
        return instance;
    }
}
