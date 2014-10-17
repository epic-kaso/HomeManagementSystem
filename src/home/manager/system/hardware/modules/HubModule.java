package home.manager.system.hardware.modules;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * **********************************************************************
 *
 * @author :  OKAFOR AKACHUKWU
 * @email :  kasoprecede47@gmail.com
 * @date :  10/17/2014
 * This file was created by the said author as written above
 * see http://www.kaso.co/
 * **********************************************************************
 * %%
 * Copyright (C) 2012 - 2014 OKAFOR AKACHUKWU
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
    public boolean turnOffModule(long moduleId) {
        if (this.getId() == moduleId)
            this.turnOff();
        this.getModule(moduleId).turnOff();
        return true;
    }

    @Override
    public boolean turnOnModule(long moduleId) {
        if (this.getId() == moduleId)
            this.turnOn();
        this.getModule(moduleId).turnOn();
        return true;
    }

    @Override
    public String toString() {
        return String.format("Class: %s, Id: %s", this.getClass().getSimpleName(), this.getId());
    }
}
