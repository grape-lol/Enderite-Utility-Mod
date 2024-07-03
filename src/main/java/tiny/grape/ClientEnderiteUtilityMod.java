package tiny.grape;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tiny.grape.gui.screens.clickgui.ClickGUI;
import tiny.grape.module.ModuleHandler;
import tiny.grape.module.ModuleManager;
import tiny.grape.utils.saving.SettingsManager;

public class ClientEnderiteUtilityMod implements ModInitializer, ClientModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("enderite-utility-mod");
	public static final ClientEnderiteUtilityMod INSTANCE = new ClientEnderiteUtilityMod();

	private MinecraftClient client = MinecraftClient.getInstance();

	@Override
	public void onInitializeClient() {
		SettingsManager.loadSettings();
	}

	@Override
	public void onInitialize() {
	}

	public void onKeyPress(int key, int action) {
		if (action == GLFW.GLFW_PRESS) {
			MinecraftClient client = MinecraftClient.getInstance();
			if (client == null) {
				System.err.println("Client is null. Cannot proceed.");
				return;
			}

			ClientPlayerEntity player = client.player;
			ClientWorld world = client.world;

			if (player != null && world != null) {
				boolean isSinglePlayer = client.isInSingleplayer();
				boolean isConnectedToServer = client.getCurrentServerEntry() != null;

				if (isSinglePlayer || isConnectedToServer) {
					for (ModuleHandler module : ModuleManager.INSTANACE.getModules()) {
						if (key == module.getKey()) {
							if (module != null) {
								System.out.println("Toggling module: " + module.getName());
								module.toggle();
							} else {
								System.err.println("Module is null. Cannot toggle.");
							}
						}
					}

					if (key == GLFW.GLFW_KEY_RIGHT_ALT) {
						System.out.println("Opening ClickGUI");
						client.setScreen(ClickGUI.INSTANCE);
					}
				} else {
					System.err.println("Player is not in a singleplayer world or connected to a server.");
				}
			} else {
				System.err.println("Player or world is null. Cannot proceed.");
			}
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
