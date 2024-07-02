package tiny.grape.module.settings;

import tiny.grape.utils.saving.ModuleSettings;
import tiny.grape.utils.saving.SettingsManager;

public class KeyBindSetting extends SettingsHandler {
    private int key;
    private boolean enabled;
    private KeyPressListener keyPressListener;
    private String moduleName;

    public KeyBindSetting(String name, int defaultKey, String moduleName) {
        super(name);
        this.key = defaultKey;
        this.moduleName = moduleName;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
        SettingsManager.setModuleSettings(moduleName, new ModuleSettings(this.key, this.enabled));
    }

    public void toggle() {
        this.enabled = !this.enabled;
        SettingsManager.setModuleSettings(moduleName, new ModuleSettings(this.key, this.enabled));
    }

    public void setKeyPressListener(KeyPressListener listener) {
        this.keyPressListener = listener;
    }

    public void handleKeyPress(int key, int action) {
        if (keyPressListener != null) {
            keyPressListener.onKeyPress(key, action);
        }
    }

    public interface KeyPressListener {
        void onKeyPress(int key, int action);
    }
}