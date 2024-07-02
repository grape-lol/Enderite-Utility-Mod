package tiny.grape.gui.screens.clickgui.settings;

import net.minecraft.client.gui.DrawContext;
import tiny.grape.gui.screens.clickgui.ModuleButton;
import tiny.grape.module.settings.KeyBindSetting;
import tiny.grape.module.settings.SettingsHandler;
import org.lwjgl.glfw.GLFW;

import java.awt.*;

public class KeyBind extends Component {
    private KeyBindSetting binding = (KeyBindSetting)setting;
    public boolean isBinding = false;

    public KeyBind(SettingsHandler setting, ModuleButton parent, int offset) {
        super(setting, parent, offset);
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isHovered(mouseX, mouseY) && button == 0) {
            binding.toggle();
            isBinding = true;
        }
        super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void keyPressed(int key) {
        if (isBinding == true) {
            parent.module.setKey(key);
            binding.setKey(key);
            isBinding = false;
        }
        if ((binding.getKey() == 256)) { // Escape key
            parent.module.setKey(0);
            binding.setKey(0);
            isBinding = false;
        }
        if ((binding.getKey() == 259)) { // Backspace key
            parent.module.setKey(0);
            binding.setKey(0);
            isBinding = false;
        }
        super.keyPressed(key);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(parent.parent.x, parent.parent.y + parent.offset + offset, parent.parent.x+parent.parent.width, parent.parent.y + parent.offset + offset + parent.parent.height, new Color(0,0,0, 160).getRGB());
        int offset2 = ((parent.parent.height/2) - client.textRenderer.fontHeight/2);

        String keyName = getKeyName(binding.getKey());
        if (isBinding == false) context.drawText(client.textRenderer, "Keybind: " + keyName, parent.parent.x + offset2, parent.parent.y + parent.offset + offset + offset2, Color.white.getRGB(), true);
        if (isBinding == true) context.drawText(client.textRenderer, "Binding: " + keyName, parent.parent.x + offset2, parent.parent.y + parent.offset + offset + offset2, Color.white.getRGB(), true);

        super.render(context, mouseX, mouseY, delta);
    }

    private String getKeyName(int keyCode) {
        // Return None if key is 0
        if (keyCode == 0) return "None";
        String keyName = GLFW.glfwGetKeyName(keyCode, 0);
        // Return Unknown if key is <0
        return keyName != null ? keyName.toUpperCase() : "Unknown";
    }
}
