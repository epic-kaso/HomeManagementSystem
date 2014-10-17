package home.manager.system.hardware;

import home.manager.system.common.EncryptedMessage;

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
public class SystemHardWareMessage extends EncryptedMessage {
    private int messageType;
    private long moduleId;

    @Override
    public int getMessageType() {
        return this.messageType;
    }

    @Override
    public void setMessageType(int type) {
        this.messageType = type;
    }

    @Override
    public String getMessageTypeDescription() {
        return null;
    }

    @Override
    public long getModuleId() {
        return moduleId;
    }

    @Override
    public void setModuleId(long moduleId) {
        this.moduleId = moduleId;
    }
}
