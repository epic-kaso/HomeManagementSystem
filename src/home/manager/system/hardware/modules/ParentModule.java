package home.manager.system.hardware.modules;

import java.util.Collection;

/**
 * Created by kaso on 10/17/2014.
 */
public interface ParentModule {
    public boolean addModule(Module module);

    public boolean addAllModule(Module... modules);

    public Module getModule(long id);

    public Collection<Module> getAllModules();

    public int size();

    public boolean removeModule(long id);

    public boolean removeAllModule();
}
