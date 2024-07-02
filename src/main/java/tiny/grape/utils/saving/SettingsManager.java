package tiny.grape.utils.saving;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SettingsManager {
    private static final String MODRINTH_PROFILES_BASE_DIR = Paths.get(System.getProperty("user.home"), "AppData", "Roaming", "com.modrinth.theseus", "profiles").toString();
    private static final String SETTINGS_FILE_NAME = "settings.json";
    private static String settingsDirectory;
    private static String settingsFilePath;
    private static final Gson gson = new Gson();
    private static Map<String, ModuleSettings> settingsMap = new HashMap<>();

    static {
        findProfileDirectory();
        if (settingsDirectory != null) {
            settingsFilePath = Paths.get(settingsDirectory, "enderite", SETTINGS_FILE_NAME).toString();
            System.out.println("Settings file path set to: " + settingsFilePath);
        } else {
            System.err.println("Settings directory not found. Settings file path not set.");
        }
    }

    private static void findProfileDirectory() {
        try {
            System.out.println("Searching for Modrinth profiles in: " + MODRINTH_PROFILES_BASE_DIR);
            Files.walk(Paths.get(MODRINTH_PROFILES_BASE_DIR), 1)
                    .filter(Files::isDirectory)
                    .forEach(profilePath -> {
                        System.out.println("Checking profile: " + profilePath.toString());
                        try {
                            Files.walk(profilePath)
                                    .filter(Files::isDirectory)
                                    .forEach(modsDirectory -> {
                                        System.out.println("Checking directory: " + modsDirectory.toString());
                                        File modsFolder = new File(modsDirectory.toFile(), "mods");
                                        System.out.println("Checking for mods directory: " + modsFolder.getPath());
                                        if (modsFolder.exists() && modsFolder.isDirectory()) {
                                            System.out.println("Found mods directory: " + modsFolder.getPath());
                                            File[] mods = modsFolder.listFiles((dir, name) -> name.equals("enderite-utility-mod-b8.jar"));
                                            if (mods != null && mods.length > 0) {
                                                System.out.println("Found enderite-utility-mod-b8.jar in: " + modsFolder.getPath());
                                                settingsDirectory = modsFolder.getPath();
                                                return;
                                            } else {
                                                System.out.println("enderite-utility-mod-b8.jar not found in: " + modsFolder.getPath());
                                            }
                                        }
                                    });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadSettings() {
        if (settingsFilePath == null) {
            System.err.println("Settings file path is not set. Unable to load settings.");
            return;
        }

        try {
            File settingsFile = new File(settingsFilePath);
            if (!settingsFile.exists()) {
                System.out.println("Settings file does not exist. Creating a new settings map.");
                settingsMap = new HashMap<>();
                return;
            }

            try (FileReader reader = new FileReader(settingsFile)) {
                Type type = new TypeToken<Map<String, ModuleSettings>>() {}.getType();
                settingsMap = gson.fromJson(reader, type);
                if (settingsMap == null) {
                    settingsMap = new HashMap<>();
                }
                System.out.println("Settings successfully loaded from: " + settingsFilePath);
            } catch (IOException e) {
                System.err.println("Error reading settings file: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.err.println("Error loading settings: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void saveSettings() {
        if (settingsFilePath == null) {
            System.err.println("Settings file path is not set. Unable to save settings.");
            return;
        }

        try {
            File settingsFile = new File(settingsFilePath);
            if (!settingsFile.getParentFile().exists()) {
                System.out.println("Creating settings directory: " + settingsFile.getParentFile().getPath());
                settingsFile.getParentFile().mkdirs();
            }

            try (FileWriter writer = new FileWriter(settingsFile)) {
                gson.toJson(settingsMap, writer);
                System.out.println("Settings successfully saved to: " + settingsFilePath);
            } catch (IOException e) {
                System.err.println("Error writing to settings file: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (Exception e) {
            System.err.println("Error saving settings: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static ModuleSettings getModuleSettings(String moduleName) {
        return settingsMap.getOrDefault(moduleName, new ModuleSettings(0, false));
    }

    public static void setModuleSettings(String moduleName, ModuleSettings settings) {
        settingsMap.put(moduleName, settings);
        saveSettings();
    }
}
