/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.manager.system.servercomms;

import home.manager.system.common.Message;

import java.util.HashMap;
import java.util.Map;

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
public class ServerCommunicationMessage implements Message {
    public static Map<Integer, String> messageTypesMap;

    static {
        messageTypesMap = new HashMap<>();
        messageTypesMap.put(0, "MeterControl");
        messageTypesMap.put(1, "StatusCheck");
        messageTypesMap.put(2, "SectorControl");
    }

    private int messageType;
    private String message;

    @Override
    public int getMessageType() {
        return messageType;
    }

    @Override
    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getMessageTypeDescription() {
        return messageTypesMap.get(this.getMessageType());
    }
}
