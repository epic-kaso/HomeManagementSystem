package home.manager.system.common;

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
abstract public class CommunicableListener implements Communicable {
    private List<Listener> listenerList;


    /**
     * This instantiates the Object, Setting up the system for optimal work
     */
    public CommunicableListener() {
        listenerList = new ArrayList<>();
    }

    /**
     * <p>
     * Add a listener to the Communicable so as to be notified of
     * new messages when a new message becomes available.
     * </p>
     *
     * @param listener Listener
     * @return boolean true on success, false on failure
     */
    @Override
    public boolean addListener(Listener listener) {
        return listenerList != null && this.listenerList.add(listener);
    }

    /**
     * This function notifies the registered listeners of new message
     *
     * @param message the message object passed to the listeners @see Message
     */
    protected void notifyListeners(Message message) {
        if (listenerList != null && listenerList.size() > 0) {
            for (Listener l : listenerList) {
                l.executeMessage(this, message);
            }
        }
    }
}
