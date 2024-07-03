package tiny.grape.module.settings;

public class SettingsHandler {
    private String name;
    private boolean visble = true;

    public SettingsHandler(String name) {
        this.name = name;
    }

    public boolean isVisble() {
        return visble;
    }

    public void setVisble(boolean visble) {
        this.visble = visble;
    }

    public String getName() {
        return name;
    }
}
