package tiny.grape.gui.screens.clickgui.settings;

import net.minecraft.client.gui.DrawContext;
import tiny.grape.gui.screens.clickgui.ModuleButton;
import tiny.grape.module.settings.ModeSetting;
import tiny.grape.module.settings.NumberSetting;
import tiny.grape.module.settings.SettingsHandler;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Slider extends Component{
    private NumberSetting numSet = (NumberSetting) setting;
    private boolean sliding = false;

    public Slider(SettingsHandler setting, ModuleButton parent, int offset) {
        super(setting, parent, offset);
        this.numSet = (NumberSetting) setting;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(parent.parent.x, parent.parent.y + parent.offset + offset, parent.parent.x+parent.parent.width, parent.parent.y + parent.offset + offset + parent.parent.height, new Color(0,0,0, 160).getRGB());

        double diff = Math.min(parent.parent.width, Math.max(0, mouseX-parent.parent.x));
        int renderWidth = (int)(parent.parent.width * (numSet.getValue() - numSet.getMin()) / (numSet.getMax() - numSet.getMin()));

        context.fill(parent.parent.x, parent.parent.y + parent.offset + offset, parent.parent.x+renderWidth, parent.parent.y + parent.offset + offset + parent.parent.height, Color.red.getRGB());

        if(sliding){
            if(diff==0) {
                numSet.setValue(numSet.getMin());
            } else{
                numSet.setValue(roundToPlace(((diff/parent.parent.width)*(numSet.getMax()-numSet.getMin())+numSet.getMin()),2));
            }
        }

        int offset2 = ((parent.parent.height/2) - client.textRenderer.fontHeight/2);
        context.drawText(client.textRenderer, numSet.getName() + ": " + roundToPlace(numSet.getValue(), 2), parent.parent.x + offset2, parent.parent.y + parent.offset + offset + offset2, Color.white.getRGB(), true);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if(isHovered(mouseX,mouseY)) sliding=true;
        super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void mouseReleased(double mouseX, double mouseY, int button) {
        sliding=false;
        super.mouseReleased(mouseX, mouseY, button);
    }

    private double roundToPlace(double value, int place) {
        if (place < 0) {
            return value;
        }

        BigDecimal bg = new BigDecimal(value);
        bg = bg.setScale(place, RoundingMode.HALF_UP);

        return value;
    }
}
