package com.homeService.lib.myJSON;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class MyJsonObject {
    private JSONObject object;
    public MyJsonObject(String JSON) {
        object = getJSONObject(JSON);
    }

    public String get(String key) {
        return getOrElse(key, "");
    }

    public String getOrElse(String key, String elseValue) {
        if (object == null) return elseValue;
        Object temp = object.get(key);
        return temp != null ? temp.toString() : elseValue;
    }

    private JSONObject getJSONObject(String JSON) {
        try {
            return (JSONObject) new JSONParser().parse(JSON);
        } catch (Exception e) {
            return new JSONObject();
        }
    }

    public MyJsonObject getObject(String key) {
        return new MyJsonObject(get(key));
    }
    public MyJsonArray getArray(String key) {
        return new MyJsonArray(get(key));
    }
}
