package tiny.grape.gui.screens.clickgui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import tiny.grape.gui.screens.clickgui.settings.Component;
import tiny.grape.module.ModuleHandler;
import tiny.grape.module.ModuleManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Frame {
    public int x, y, width, height, dragX, dragY;
    public ModuleHandler.Category category;
    public boolean dragging, extended;

    private List<ModuleButton> buttons;

    protected MinecraftClient client = MinecraftClient.getInstance();

    public Frame(ModuleHandler.Category category, int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.category = category;
        this.dragging = false;
        this.extended = false;
        buttons = new ArrayList<>();

        int offset = height;
        for (ModuleHandler module : ModuleManager.INSTANACE.getModulesInCategory(category)) {
            buttons.add(new ModuleButton(module, offset, this));
            offset += height;
        }
    }

    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(x, y, x + width, y + height, Color.darkGray.getRGB());
        int offset = ((height / 2) - client.textRenderer.fontHeight / 2);

        context.drawText(client.textRenderer, category.name, x + offset, y + offset, Color.red.brighter().getRGB(), true);
        context.drawText(client.textRenderer, extended ? "-" : "+", x + width - offset - 2 - client.textRenderer.getWidth("+"), y + offset, Color.white.getRGB(), true);

        if (extended) {
            for (ModuleButton button : buttons) {
                button.render(context, mouseX, mouseY, delta);
            }
        }
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isHovered(mouseX, mouseY)) {
            if (button == 0) {
                dragging = true;
                dragX = (int) (mouseX - x);
                dragY = (int) (mouseY - y);
            } else if (button == 1) {
                extended = !extended;
            }
        }

        if (extended) {
            for (ModuleButton mb : buttons) {
                mb.mouseClicked(mouseX, mouseY, button);
            }
        }
    }

    public void mouseReleased(double mouseX, double mouseY, int button) {
        if ((button == 0 && dragging == true)) dragging = false;

        for (ModuleButton mb : buttons) {
            mb.mouseReleased(mouseX, mouseY, button);
        }
    }

    public boolean isHovered(double mouseX, double mouseY) {
        return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
    }

    public void updatePos(double mouseX, double mouseY) {
        if (dragging) {
            x = (int) (mouseX - dragX);
            y = (int) (mouseY - dragY);
        }
    }

    public void updateButtons() {
        int offset = height;

        for (ModuleButton button : buttons) {
            button.offset = offset;
            offset += height;

            if (button.extended) {
                for (Component component : button.components) {
                    if (component.setting.isVisble()) offset += height;
                }
            }
        }
    }

    public void keyPressed(int key) {
        for (ModuleButton mb : buttons) {
            mb.keyPressed(key);
        }

    }
}