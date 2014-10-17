package home.manager.system.hardware.modules;

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
abstract public class BaseModule implements Module {

    private static long classId = 0l;
    private long id;
    private STATE currentState;


    public BaseModule() {
        this(classId++);
    }

    public BaseModule(long id) {
        this.setId(id);
    }

    @Override
    public long getId() {
        return this.id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean turnOn() {
        this.currentState = STATE.ON;
        return true;
    }

    @Override
    public boolean turnOff() {
        this.currentState = STATE.OFF;
        return true;
    }

    @Override
    public STATE getCurrentState() {
        return this.currentState;
    }

    @Override
    public String toString() {
        return String.format("Class: %s, Id: %s", this.getClass().getSimpleName(), this.getId());
    }
}
