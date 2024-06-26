package tiny.grape.gui.screens.clickgui.settings;

import net.minecraft.client.gui.DrawContext;
import tiny.grape.gui.screens.clickgui.ModuleButton;
import tiny.grape.module.settings.BooleanSetting;
import tiny.grape.module.settings.SettingsHandler;

import java.awt.*;

public class CheckBox extends Component{
    private BooleanSetting boolSet = (BooleanSetting) setting;

    public CheckBox(SettingsHandler setting, ModuleButton parent, int offset) {
        super(setting, parent, offset);
        this.boolSet = (BooleanSetting) setting;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(parent.parent.x, parent.parent.y + parent.offset + offset, parent.parent.x+parent.parent.width, parent.parent.y + parent.offset + offset + parent.parent.height, new Color(0,0,0, 160).getRGB());
        int offset2 = ((parent.parent.height/2) - client.textRenderer.fontHeight/2);
        context.drawText(client.textRenderer, boolSet.getName() + ": " + boolSet.isEnabled(), parent.parent.x + offset2, parent.parent.y + parent.offset + offset + offset2, Color.white.getRGB(), true);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isHovered(mouseX, mouseY) && button == 0){
            boolSet.toggle();
        }
        super.mouseClicked(mouseX, mouseY, button);
    }
}
