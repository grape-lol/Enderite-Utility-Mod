package tiny.grape.module.settings;

public class KeyBindSetting extends SettingsHandler {
    private int key;
    private boolean enabled;

    public KeyBindSetting(String name, int defaultKey) {
        super(name);
        this.key = defaultKey;
    }
    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public void toggle() {
        this.enabled = !this.enabled;
    }
}