package tiny.grape.module.combat;

import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import tiny.grape.module.ModuleHandler;
import tiny.grape.module.settings.SearchTags;
import tiny.grape.module.settings.KeyBindSetting;
import tiny.grape.module.settings.ModeSetting;
import tiny.grape.utils.FindItemResult;
import tiny.grape.utils.InventoryUtils;
import tiny.grape.utils.saving.ModuleSettings;
import tiny.grape.utils.saving.SettingsManager;

// Future: Add a way to allow multiple items with priority to the items.
@SearchTags({"auto offhand", "auto totem"})
public class AutoOffhand extends ModuleHandler {
    private final String moduleName = "Auto Offhand";
    private final KeyBindSetting keyBindSetting;

    public ModeSetting mode = new ModeSetting("Mode", "Totem", "Totem", "Exp Bottle", "Shield");

    private static final Formatting Gray = Formatting.GRAY;

    public AutoOffhand() {
        super("Auto Offhand", Text.translatable("enderite.description.autooffhand"), Category.COMBAT);
        addSetting(mode);
        ModuleSettings settings = SettingsManager.getModuleSettings(moduleName);
        keyBindSetting = new KeyBindSetting("Keybind", settings.getKey(), moduleName);
        addSetting(keyBindSetting);
        this.setEnabled(settings.isEnabled());
    }

    @Override
    public void onTick() {
        this.setDisplayName("Auto Offhand" + Gray + " ["+mode.getMode()+"]");
        if(mode.isMode("Exp Bottle") && client.player.getOffHandStack().getItem() != Items.EXPERIENCE_BOTTLE) {
            FindItemResult iBottle = InventoryUtils.find(itemStack -> itemStack.getItem() == Items.EXPERIENCE_BOTTLE, 0, 35);
            if(iBottle.found()) {
                InventoryUtils.move().from(iBottle.slot()).toOffhand();
            } else return;
        }
        if(mode.isMode("Totem") && client.player.getOffHandStack().getItem() != Items.TOTEM_OF_UNDYING) {
            FindItemResult iTotem = InventoryUtils.find(itemStack -> itemStack.getItem() == Items.TOTEM_OF_UNDYING, 0, 35);
            if(iTotem.found()) {
                InventoryUtils.move().from(iTotem.slot()).toOffhand();
            } else return;
        }
        if(mode.isMode("Shield") && client.player.getOffHandStack().getItem() != Items.SHIELD) {
            FindItemResult iShield = InventoryUtils.find(itemStack -> itemStack.getItem() == Items.SHIELD, 0, 35);
            if(iShield.found()) {
                InventoryUtils.move().from(iShield.slot()).toOffhand();
            } else return;
        }
        super.onTick();
    }

    @Override
    public void onEnable() {
        super.onEnable();

        SettingsManager.setModuleSettings(moduleName, new ModuleSettings(keyBindSetting.getKey(), true));
    }

    @Override
    public void onDisable() {
        super.onDisable();

        SettingsManager.setModuleSettings(moduleName, new ModuleSettings(keyBindSetting.getKey(), false));

    }
}
