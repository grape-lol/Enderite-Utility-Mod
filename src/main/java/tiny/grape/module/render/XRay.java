package tiny.grape.module.render;

import net.minecraft.block.BlockState;
import net.minecraft.text.Text;
import tiny.grape.module.ModuleHandler;
import tiny.grape.module.settings.SearchTags;
import tiny.grape.module.settings.KeyBindSetting;
import tiny.grape.utils.saving.ModuleSettings;
import tiny.grape.utils.saving.SettingsManager;

import java.util.HashSet;
import java.util.logging.Logger;

// Future: Add a way to allow the user to add and remove specific blocks.
@SearchTags({"xray","x-ray","x ray","ore finder","orefinder"})
public class XRay extends ModuleHandler {
    private static final Logger LOGGER = Logger.getLogger(XRay.class.getName());

    private static XRay instance;
    private static boolean enabled = false;
    private HashSet<String> xrayBlocks = new HashSet<>();

    private final String moduleName = "XRay";
    private final KeyBindSetting keyBindSetting;

    public XRay() {
        super("XRay", Text.translatable("enderite.description.xray"), Category.RENDER);
        ModuleSettings settings = SettingsManager.getModuleSettings(moduleName);
        keyBindSetting = new KeyBindSetting("Keybind", settings.getKey(), moduleName);
        addSetting(keyBindSetting);
        this.setEnabled(settings.isEnabled());
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
        SettingsManager.setModuleSettings(moduleName, new ModuleSettings(keyBindSetting.getKey(), false));
    }

    @Override
    public void onEnable() {
        super.onEnable();
        enabled = true;
        SettingsManager.setModuleSettings(moduleName, new ModuleSettings(keyBindSetting.getKey(), true));
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