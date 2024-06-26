package tiny.grape.module;

import net.minecraft.client.MinecraftClient;
import tiny.grape.module.settings.SettingsHandler;

import java.util.ArrayList;
import java.util.List;

public class ModuleHandler {
    private String name;
    private String description;
    private String displayName;
    private Category category;
    private int key;
    private boolean enabled;

    private List<SettingsHandler> settings = new ArrayList<>();

    protected MinecraftClient client = MinecraftClient.getInstance();

    public ModuleHandler(String name, String description, Category category) {
        this.name = name;
        this.displayName = name;
        this.description = description;
        this.category = category;
    }

    public void toggle() {
        this.enabled = !this.enabled;

        if (enabled) onEnable();
        else onDisable();
    }

    public List<SettingsHandler> getSettings() {
        return settings;
    }

    public void addSetting(SettingsHandler setting) {
        settings.add(setting);
    }

    public void addSettings(SettingsHandler...settings){
        for(SettingsHandler setting : settings) addSettings(setting);
    }

    public void onEnable() {

    }

    public void onDisable() {

    }

    public void onTick() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;

        if (enabled) onEnable();
        else onDisable();
    }

    public enum Category {
        COMBAT("Combat"),
        MOVEMENT("Movement"),
        RENDER("Render"),
        WORLD("World"),
        MISC("Misc");

        public String name;
        private Category(String name) {
            this.name = name;
        }
    }
}
