package tiny.grape.module.settings;

public class KeyBindSetting extends SettingsHandler {
    private int key;
    private boolean enabled;
    private KeyPressListener keyPressListener;

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
