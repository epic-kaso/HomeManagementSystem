package home.manager.system;

import home.manager.system.common.Communicable;
import home.manager.system.common.Message;
import home.manager.system.hardware.SystemHardWareMessage;

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

public class HomeManagerSystem {

    /**
     * @param serverCommunication ServerCommunications instance
     * @param systemHardWare      SystemHardWar instance
     */
    public void manage(final Communicable serverCommunication,
                       final Communicable systemHardWare) {


        serverCommunication.addListener((communicable, message) -> systemHardWare.sendMessage(message));


        systemHardWare.addListener((communicable, message) -> serverCommunication.sendMessage(message));

        testMessaging(systemHardWare);

    }

    private void testMessaging(Communicable c) {
        Message m = new SystemHardWareMessage();
        m.setModuleId(4);
        m.setMessage("1");

        c.sendMessage(m);
    }
}
