package tiny.grape.module.render;

import net.minecraft.block.BlockState;
import tiny.grape.module.ModuleHandler;
import tiny.grape.module.settings.KeyBindSetting;

import java.util.HashSet;
import java.util.logging.Logger;

public class XRay extends ModuleHandler {
    private static final Logger LOGGER = Logger.getLogger(XRay.class.getName());

    private static XRay instance;
    private static boolean enabled = false;
    private HashSet<String> xrayBlocks = new HashSet<>();

    public XRay() {
        super("XRay", "See through specific blocks", Category.RENDER);
        addSetting(new KeyBindSetting("Keybind", 0));
        rayBlocks();
    }

    private void rayBlocks() {
        xrayBlocks.add("block.minecraft.coal_ore");
        xrayBlocks.add("block.minecraft.iron_ore");
        xrayBlocks.add("block.minecraft.diamond_ore");
        xrayBlocks.add("block.minecraft.emerald_ore");
        xrayBlocks.add("block.minecraft.lapis_ore");
        xrayBlocks.add("block.minecraft.redstone_ore");
        xrayBlocks.add("block.minecraft.deepslate_coal_ore");
        xrayBlocks.add("block.minecraft.deepslate_iron_ore");
        xrayBlocks.add("block.minecraft.deepslate_diamond_ore");
        xrayBlocks.add("block.minecraft.deepslate_lapis_ore");
        xrayBlocks.add("block.minecraft.deepslate_emerald_ore");
        xrayBlocks.add("block.minecraft.deepslate_redstone_ore");
        xrayBlocks.add("block.minecraft.water");
        xrayBlocks.add("block.minecraft.lava");
        xrayBlocks.add("block.minecraft.bedrock");
        xrayBlocks.add("block.minecraft.chest");
        xrayBlocks.add("block.minecraft.trapped_chest");
        xrayBlocks.add("block.minecraft.ender_chest");
        xrayBlocks.add("block.minecraft.monster_spawner");
    }

    public boolean shouldDrawBlock(BlockState state) {
        String blockId = state.getBlock().getTranslationKey();
        boolean shouldDraw = xrayBlocks.contains(blockId);

        if (shouldDraw) {
            LOGGER.info("Drawing block: " + blockId);
        } else {
            LOGGER.info("Not drawing block: " + blockId);
        }

        return shouldDraw;
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