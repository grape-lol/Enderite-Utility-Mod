package tiny.grape.module.combat;

import net.minecraft.item.Items;
import net.minecraft.util.Formatting;
import tiny.grape.module.ModuleHandler;
import tiny.grape.module.settings.ModeSetting;
import tiny.grape.utils.FindItemResult;
import tiny.grape.utils.InventoryUtils;

public class AutoOffhand extends ModuleHandler {
    public ModeSetting mode = new ModeSetting("Mode", "Totem", "Totem", "Exp Bottle", "Gapple");

    private static final Formatting Gray = Formatting.GRAY;

    public AutoOffhand() {
        super("Auto Offhand", "wowsers", Category.COMBAT);
        addSetting(mode);
    }

    @Override
    public void onTick() {
        this.setDisplayName("Auto Offhand" + Gray + " ["+mode.getMode()+"]");
        if(mode.isMode("Exp Bottle") && client.player.getOffHandStack().getItem() != Items.EXPERIENCE_BOTTLE) {
            FindItemResult iBottle = InventoryUtils.find(itemStack -> itemStack.getItem() == Items.EXPERIENCE_BOTTLE, 0, 35);
            if(iBottle.found()) {
                InventoryUtils.move().from(iBottle.getSlot()).toOffhand();
            } else return;
        }
        if(mode.isMode("Totem") && client.player.getOffHandStack().getItem() != Items.TOTEM_OF_UNDYING) {
            FindItemResult iTotem = InventoryUtils.find(itemStack -> itemStack.getItem() == Items.TOTEM_OF_UNDYING, 0, 35);
            if(iTotem.found()) {
                InventoryUtils.move().from(iTotem.getSlot()).toOffhand();
            } else return;
        }
        if(mode.isMode("Gapple") && client.player.getOffHandStack().getItem() != Items.GOLDEN_APPLE && client.player.getOffHandStack().getItem() != Items.ENCHANTED_GOLDEN_APPLE) {
            FindItemResult iApple = InventoryUtils.find(itemStack -> itemStack.getItem() == Items.ENCHANTED_GOLDEN_APPLE, 0, 35);
            if(iApple.found()) {
                InventoryUtils.move().from(iApple.getSlot()).toOffhand();
            } else return;
        }
        super.onTick();
    }
}
