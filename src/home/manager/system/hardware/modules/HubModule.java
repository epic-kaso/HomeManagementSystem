package home.manager.system.hardware.modules;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kaso on 10/17/2014.
 */
public class HubModule extends BaseModule implements ParentModule {

    private Map<Long, Module> moduleList;


    public HubModule() {
        super();
        moduleList = new HashMap<>();
    }

    @Override
    public boolean addModule(Module module) {
        moduleList.put(module.getId(), module);
        return true;
    }

    @Override
    public boolean addAllModule(Module... modules) {
        for (Module m : modules) {
            this.addModule(m);
            ;
        }
        return true;
    }

    @Override
    public Module getModule(long id) {
        return moduleList.get(id);
    }

    @Override
    public Collection<Module> getAllModules() {
        return new ArrayList<>(moduleList.values());
    }

    @Override
    public int size() {
        return moduleList.size();
    }

    @Override
    public boolean removeModule(long id) {
        moduleList.remove(id);
        return true;
    }

    @Override
    public boolean removeAllModule() {
        moduleList.clear();
        return true;
    }

    @Override
    public String toString() {
        return String.format("Class: %s, Id: %s", this.getClass().getSimpleName(), this.getId());
    }
}
