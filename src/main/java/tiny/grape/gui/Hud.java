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

        List<ModuleHandler> enabled = ModuleManager.INSTANACE.getEnabledModules();

        enabled.sort(Comparator.comparingInt(m -> client.textRenderer.getWidth(((ModuleHandler)m).getDisplayName())).reversed());

        for(ModuleHandler module : enabled) {
            context.drawText(client.textRenderer, module.getDisplayName(), (sWidth-4)-client.textRenderer.getWidth(module.getDisplayName()), 10 + (index*client.textRenderer.fontHeight), -1, true);
            index++;
        }
    }
}
