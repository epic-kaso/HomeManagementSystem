package home.manager.system.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
