package tiny.grape.module.settings;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ModeSetting extends SettingsHandler{
    private List<String> modes;
    private String mode;
    private int index;

    public ModeSetting(String name, String defaultMode, String...modes) {
        super(name);
        this.modes = Arrays.asList(modes);
        this.mode = defaultMode;
        this.index = this.modes.indexOf(defaultMode);
    }

    public String getMode() {
        return mode;
    }

    public List<String> getModes() {
        return modes;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
        this.mode = modes.get(index);
    }

    public void setMode(String mode) {
        this.mode = mode;
        this.index = modes.indexOf(mode);
    }

    public void cycle(){
        if(index< modes.size()-1){
            index++;
            mode = modes.get(index);
        } else if (index >= modes.size()-1) {
            index = 0;
            mode = modes.get(0);
        }
    }

    public boolean isMode(String mode) {
        return Objects.equals(this.mode, mode);
    }
}
