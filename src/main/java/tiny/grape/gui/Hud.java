package tiny.grape.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import tiny.grape.module.ModuleHandler;
import tiny.grape.module.ModuleManager;

import java.util.Comparator;
import java.util.List;

public class Hud {
    private static MinecraftClient client = MinecraftClient.getInstance();

    public static void render(DrawContext context, float tickDelta) {
        renderArrayList(context);
    }

    public static void renderArrayList(DrawContext context) {
        int index = 0;
        int sWidth = client.getWindow().getScaledWidth();
        int padding = 4;
        int spacing = 4;

        List<ModuleHandler> enabled = ModuleManager.INSTANACE.getEnabledModules();
        enabled.sort(Comparator.comparingInt(m -> client.textRenderer.getWidth(((ModuleHandler)m).getDisplayName())).reversed());

        for (ModuleHandler module : enabled) {
            String displayName = module.getDisplayName();
            int textWidth = client.textRenderer.getWidth(displayName);
            int xPos = (sWidth - 4) - textWidth;
            int yPos = 10 + (index * (client.textRenderer.fontHeight + padding + spacing));
            int color = 0xFFFFFF;

            drawRect(context, xPos - padding, yPos - padding, xPos + textWidth + padding, yPos + client.textRenderer.fontHeight + padding, 0x90000000);
            context.drawText(client.textRenderer, displayName, xPos, yPos, color, true);
            index++;
        }
    }

    private static void drawRect(DrawContext context, int left, int top, int right, int bottom, int color) {
        context.fill(left, top, right, bottom, color);
    }
}
