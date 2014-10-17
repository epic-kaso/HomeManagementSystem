package home.manager.system.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
public class RegexEngine {

    private String patternString;
    private String inputString;
    private Pattern pattern;
    private Map<String, String> cache;


    /**
     * !!!!patternStr must contain 2 groups,
     * first group representing the key,
     * the second group representing the value.!!!!
     *
     * @param patternStr
     * @param input
     */
    public RegexEngine(String patternStr, String input) {
        this.setPatternString(patternStr);
        this.setInputString(input);
    }

    public String getValue(String name) {
        if (this.cache.containsKey(name))
            return this.cache.get(name);

        Matcher matcher = this.pattern.matcher(getInputString());
        while (matcher.find()) {
            String key = matcher.group(1);
            String value = matcher.group(2);

            this.cache.put(key, value);
            if (key.equalsIgnoreCase(name)) {
                return value;
            }
        }
        return null;
    }

    public String getPatternString() {
        return patternString;
    }

    public void setPatternString(String patternString) {
        this.patternString = patternString;
        this.pattern = Pattern.compile(this.patternString, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
    }

    public String getInputString() {
        return inputString;
    }

    public void setInputString(String inputString) {
        this.inputString = inputString;
        this.cache = null;
        this.cache = new HashMap<>();
    }


}
