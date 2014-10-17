package home.manager.system;

import home.manager.system.common.Communicable;
import home.manager.system.common.Listener;
import home.manager.system.common.Message;

/**
 * @author kaso
 */

public class HomeManagerSystem {

    /**
     * @param serverCommunication ServerCommunications instance
     * @param systemHardWare      SystemHardWar instance
     */
    public void manage(final Communicable serverCommunication,
                       final Communicable systemHardWare) {


        serverCommunication.addListener(new Listener() {
            @Override
            public void executeMessage(Communicable communicable, Message message) {
                systemHardWare.sendMessage(message);
            }
        });


        systemHardWare.addListener(new Listener() {
            @Override
            public void executeMessage(Communicable communicable, Message message) {
                serverCommunication.sendMessage(message);
            }
        });

    }
}
