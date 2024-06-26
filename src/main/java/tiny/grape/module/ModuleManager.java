package tiny.grape.module;

import tiny.grape.module.combat.*;
import tiny.grape.module.movement.*;
import tiny.grape.module.render.*;
import tiny.grape.module.world.*;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {
    public static final ModuleManager INSTANACE = new ModuleManager();
    private List<ModuleHandler> modules = new ArrayList<>();

    public ModuleManager() {
        addModules();
    }

    public List<ModuleHandler> getModules() {
        return modules;
    }

    public List<ModuleHandler> getEnabledModules() {
        List<ModuleHandler> enabled = new ArrayList<>();
        for (ModuleHandler module : modules) {
            if (module.isEnabled()) enabled.add(module);
        }
        return enabled;
    }

    public List<ModuleHandler> getModulesInCategory(ModuleHandler.Category category) {
        List<ModuleHandler> categoryModules = new ArrayList<>();

        for (ModuleHandler module : modules){
            if(module.getCategory() == category) {
                categoryModules.add(module);
            }
        }

        return categoryModules;
    }

    private void addModules() {
        modules.add(new Flight());
        modules.add(new Sprint());
        modules.add(new AutoFish());
        modules.add(new Fullbright());
        modules.add(new Jesus());
        modules.add(new NoFall());
        modules.add(new Spider());
        modules.add(new AutoOffhand());
        modules.add(new AutoRespawn());
        modules.add(new XRay());
        modules.add(new AutoFarm());
        modules.add(new TridentBoost());
        modules.add(new BarrierESP());
    }
}
