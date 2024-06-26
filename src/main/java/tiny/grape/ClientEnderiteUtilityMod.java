package tiny.grape;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tiny.grape.gui.screens.clickgui.ClickGUI;
import tiny.grape.module.ModuleHandler;
import tiny.grape.module.ModuleManager;

public class ClientEnderiteUtilityMod implements ModInitializer, ClientModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("enderite-utility-mod");
	public static final ClientEnderiteUtilityMod INSTANCE = new ClientEnderiteUtilityMod();

	private MinecraftClient client = MinecraftClient.getInstance();

	@Override
	public void onInitializeClient() {
	}
	@Override
	public void onInitialize() {
	}

	public void onKeyPress(int key, int action) {
		if (action == GLFW.GLFW_PRESS) {
			for (ModuleHandler module : ModuleManager.INSTANACE.getModules()) {
				if (key == module.getKey()) module.toggle();
			}

			if(key == GLFW.GLFW_KEY_RIGHT_SHIFT) client.setScreen(ClickGUI.INSTANCE);
		}
	}

	public void onTick() {
		if (client.player != null) {
			for (ModuleHandler module : ModuleManager.INSTANACE.getEnabledModules()) {
				module.onTick();
			}
		}
	}
}
