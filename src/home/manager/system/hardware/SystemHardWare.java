package home.manager.system.hardware;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import home.manager.system.common.CommunicableListener;
import home.manager.system.common.Message;
import home.manager.system.hardware.modules.Module;
import home.manager.system.hardware.modules.ParentModule;
import home.manager.system.servercomms.ServerCommunicationMessage;
import home.manager.system.utils.Settings;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kaso
 */
public class SystemHardWare extends CommunicableListener {

    static SystemHardWare systemHardWare;
    private ModulesManager modulesManager;

    private SystemHardWare() {

        modulesManager = new ModulesManager();
        System.out.println(modulesManager.parentModuleList);
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

    public void processAction(ActionType actionType, String value) {
        System.out.printf("Instructions received- Action: %s Value: %s", actionType.name(), value);
    }

    private void createMessage(ActionType actionType, String value) {
        Thread t = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    System.out.println("Sending ,message to server");
                    ServerCommunicationMessage message = new ServerCommunicationMessage();
                    message.setMessageType(0);
                    message.setMessage(encodeMessage("Testing UpStream...."));
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

    private String encodeMessage(String s) {
        return Base64.encode(s.getBytes());
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
        this.processAction(
                SystemHardWare.ActionType.valueOf(message.getMessageTypeDescription()),
                message.getMessage()
        );
        return true;
    }

    public enum ActionType {MeterControl, SectorControl, StatusCheck}


    class ModulesManager {
        public List<ParentModule> parentModuleList;
        private String ParentModuleSettingsParam = "parent-modules";
        private String unitModuleSettingsParam = "unit-modules";
        private String ParentModuleClassSettingsParam = "parent-module-class";
        private String unitModuleClassSettingsParam = "unit-module-class";


        public ModulesManager() {
            parentModuleList = new ArrayList<>();

            int parentModulesCount = Integer.parseInt(Settings.getInstance().getParam(ParentModuleSettingsParam));
            int unitModulesCount = Integer.parseInt(Settings.getInstance().getParam(unitModuleSettingsParam));


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
    }
}
