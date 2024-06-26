package tiny.grape.module.settings;

import java.text.DecimalFormat;

public class NumberSetting extends SettingsHandler {
    private double min, max, increment;
    private double value;
    private DecimalFormat df = new DecimalFormat("#.##");

    public NumberSetting(String name, double min, double max, double defaultV, double increment) {
        super(name);
        this.min = min;
        this.max = max;
        this.value = defaultV;
        this.increment = increment;
    }

    public static double clamp(double value, double min, double max) {
        value = Math.max(min, value);
        value = Math.min(max, value);
        return value;
    }

    public double getValue() {
        return value;
    }

    public float getValueFloat() {
        return (float)value;
    }

    public int getValueInt() {
        return (int)value;
    }

    public void setValue(double value) {
        value = clamp(value, this.min, this.max);
        value = Math.round(value * (1.0 / this.increment)) / (1.0 / this.increment);
        this.value = value;
    }

    public double getIncrement() {
        return increment;
    }

    public void increment(boolean positive) {
        if (positive) setValue(getValue() + getIncrement());
        else setValue(getValue() - getIncrement());
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public String getFormattedValue() {
        return df.format(value);
    }
}
