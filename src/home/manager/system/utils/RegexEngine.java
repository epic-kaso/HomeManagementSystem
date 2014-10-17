package home.manager.system.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
