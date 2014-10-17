package home.manager.system.hardware;

import home.manager.system.common.CommunicableListener;
import home.manager.system.common.Message;
import home.manager.system.hardware.modules.Module;
import home.manager.system.hardware.modules.ParentModule;
import home.manager.system.servercomms.ServerCommunicationMessage;
import home.manager.system.utils.Settings;

import java.util.ArrayList;
import java.util.List;

/**
 * **********************************************************************
 *
 * @author :  OKAFOR AKACHUKWU
 * @email :  kasoprecede47@gmail.com
 * @date :  10/16/2014
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
public class SystemHardWare extends CommunicableListener {

    static SystemHardWare systemHardWare;
    private ModulesManager modulesManager;

    private SystemHardWare() {
        modulesManager = new ModulesManager();
    }

    /**
     * Setup the System Hardware.
     */
    public static void initialize() {
        systemHardWare = new SystemHardWare();
    }

    /**
     * Get an instance of SystemHardWare.
     *
     * @return SystemHardWare
     */
    public static SystemHardWare getInstance() {
        if (null == systemHardWare)
            initialize();
        // systemHardWare.createMessage(ActionType.MeterControl, String.valueOf(System.nanoTime()));
        return systemHardWare;
    }

    public void processAction(long moduleId, int onOrOff) {
        ParentModule m = this.modulesManager.getParentModule(moduleId);

        if (m != null) {
            if (onOrOff == 0) {
                m.turnOffModule(moduleId);
            } else {
                m.turnOnModule(moduleId);
            }
        }
    }

    private void createMessage(ActionType actionType, String value) {
        Thread t = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    System.out.println("Sending ,message to server");
                    ServerCommunicationMessage message = new ServerCommunicationMessage();
                    message.setMessageType(0);
                    message.setMessage("Testing UpStream....");
                    informObservers(message);
                    try {
                        Thread.sleep(4000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t.start();

    }

    /**
     * Inform Observers of new state of the ServerComms.
     *
     * @param o Message
     */
    private synchronized void informObservers(Message o) {
        System.out.println("SystemHardWare: Informing observers....");
        this.notifyListeners(o);
    }

    @Override
    public boolean sendMessage(Message message) {
        try {
            this.processAction(message.getModuleId(),
                    Integer.parseInt(message.getMessage())
            );
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public enum ActionType {MeterControl, SectorControl, StatusCheck}


    class ModulesManager {
        public List<ParentModule> parentModuleList;
        private String ParentModuleSettingsParam = "parent-modules";
        private String unitModuleSettingsParam = "unit-modules";
        private String ParentModuleClassSettingsParam = "parent-module-class";
        private String unitModuleClassSettingsParam = "unit-module-class";
        private int unitModulesCount;
        private int parentModulesCount;


        public ModulesManager() {
            parentModuleList = new ArrayList<>();

            this.parentModulesCount = Integer.parseInt(Settings.getInstance().getParam(ParentModuleSettingsParam));
            this.unitModulesCount = Integer.parseInt(Settings.getInstance().getParam(unitModuleSettingsParam));


            ParentModule parentModule;
            for (int i = 0; i < parentModulesCount; i++) {
                try {
                    parentModule = getInstanceOfParentModule();
                    parentModule.addAllModule(createUnitModules(unitModulesCount));
                    parentModuleList.add(parentModule);
                } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                    e.printStackTrace();
                }

            }

        }

        private Module[] createUnitModules(int count) {
            Module[] unitModules = new Module[count];
            for (int i = 0; i < count; i++) {
                try {
                    unitModules[i] = getInstanceOfUnitModule();
                } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                    e.printStackTrace();
                }
            }

            return unitModules;
        }

        private ParentModule getInstanceOfParentModule() throws ClassNotFoundException,
                IllegalAccessException, InstantiationException {
            String className = Settings.getInstance()
                    .getParam(ParentModuleClassSettingsParam).replace('_', '.');
            Class instance = Class.forName(className);
            return (ParentModule) instance.newInstance();
        }

        private Module getInstanceOfUnitModule() throws ClassNotFoundException,
                IllegalAccessException, InstantiationException {
            String className = Settings.getInstance()
                    .getParam(unitModuleClassSettingsParam).replace('_', '.');
            Class instance = Class.forName(className);
            return (Module) instance.newInstance();
        }

        protected ParentModule getParentModule(long moduleId) {
            for (ParentModule m : parentModuleList) {
                if (m.getId() <= moduleId && moduleId <= (this.unitModulesCount + m.getId()))
                    return m;
            }
            return null;
        }
    }
}
