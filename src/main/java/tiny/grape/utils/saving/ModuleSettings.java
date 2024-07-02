package tiny.grape.utils.saving;

public class ModuleSettings {
    private int key;
    private boolean enabled;

    public ModuleSettings(int key, boolean enabled) {
        this.key = key;
        this.enabled = enabled;
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
    }
}

