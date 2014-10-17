package home.manager.system.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
public class Settings {

    private static Settings settings;

    static {
        settings = new Settings();
    }

    private String regexPattern = "<(.*?)>(.*?)</\\1>"; //<(.*?)>(.*?)</\1>
    private String settingsFilePath = "/settings.config";
    private RegexEngine regexEngine;

    private Settings() {
        String settings = this.getSettingsString();
        if (settings != null) {
            this.regexEngine = new RegexEngine(this.regexPattern, settings);
        }
    }

    public static Settings getInstance() {
        return settings;
    }

    public String getUsername() {
        return this.getParam("username");
    }

    public String getPassword() {
        return this.getParam("password");
    }

    public String getHostAddress() {
        return this.getParam("host-address");
    }

    public String getParam(String param) {
        if (this.regexEngine != null)
            return this.regexEngine.getValue(param);
        return null;
    }

    private String getSettingsString() {
        InputStream inputStream = Settings.class.getResourceAsStream(this.settingsFilePath);
        if (null == inputStream)
            return null;

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                inputStream
        ));
        String line;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
