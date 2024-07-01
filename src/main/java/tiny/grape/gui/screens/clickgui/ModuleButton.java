package tiny.grape.gui.screens.clickgui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import tiny.grape.gui.screens.clickgui.settings.*;
import tiny.grape.module.ModuleHandler;
import tiny.grape.module.settings.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class ModuleButton {
    public ModuleHandler module;
    public Frame parent;
    public int offset;
    public List<tiny.grape.gui.screens.clickgui.settings.Component> components;
    public boolean extended;

    private MinecraftClient client = MinecraftClient.getInstance();

    public ModuleButton(ModuleHandler module, int offset, Frame parent) {
        this.module = module;
        this.offset = offset;
        this.parent = parent;
        this.extended = false;
        this.components = new ArrayList<>();

        int setOffset = parent.height;
        for (SettingsHandler setting : module.getSettings()) {
            if (setting instanceof BooleanSetting){
                components.add(new CheckBox(setting, this, setOffset));
            } else if (setting instanceof NumberSetting){
                components.add(new Slider(setting, this, setOffset));
            } else if (setting instanceof ModeSetting){
                components.add(new ModeBox(setting, this, setOffset));
            } else if (setting instanceof KeyBindSetting) {
                components.add(new KeyBind(setting, this, setOffset));
            }
            setOffset += parent.height;
        }
    }

    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(parent.x, parent.y + offset, parent.x + parent.width, parent.y + offset + parent.height, new Color(0, 0, 0, 160).getRGB());
        if (isHovered(mouseX, mouseY)) context.fill(parent.x, parent.y + offset, parent.x + parent.width, parent.y + offset + parent.height, new Color(0, 0, 0, 160).getRGB());

        int offset2 = ((parent.height / 2) - client.textRenderer.fontHeight / 2);
        context.drawText(client.textRenderer, module.getName(), parent.x + offset2, parent.y + offset + offset2, module.isEnabled() ? new Color(128, 36, 231).getRGB() : Color.white.getRGB(), true);

        if (extended) {
            for (tiny.grape.gui.screens.clickgui.settings.Component component : components) {
                component.render(context, mouseX, mouseY, delta);
            }
        }
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isHovered(mouseX, mouseY)) {
            if (button == 0) {
                module.toggle();
            } else if (button == 1) {
                extended = !extended;
                parent.updateButtons();
            }
        }

        for (tiny.grape.gui.screens.clickgui.settings.Component component : components) {
            component.mouseClicked(mouseX, mouseY, button);
        }
    }

    public void mouseReleased(double mouseX, double mouseY, int button) {
        for (tiny.grape.gui.screens.clickgui.settings.Component component : components) {
            component.mouseReleased(mouseX, mouseY, button);
        }
    }

    public boolean isHovered(double mouseX, double mouseY) {
        return mouseX > parent.x && mouseX < parent.x + parent.width && mouseY > parent.y + offset && mouseY < parent.y + offset + parent.height;
    }

    public void keyPressed(int key) {
        for (tiny.grape.gui.screens.clickgui.settings.Component component : components) {
            component.keyPressed(key);
        }
    }

    public ModuleHandler getModule() {
        return module;
    }

}