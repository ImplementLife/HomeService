package com.homeService.lib.myJSON;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MyJsonArray implements Iterable<String> {
    private JSONArray arr;
    public MyJsonArray(String JSON) {
        arr = getJSONArray(JSON);
    }

    public List<String> get() {
        List<String> list = new ArrayList<>();
        for (Object o : arr) list.add(o.toString());
        return list;
    }

    private JSONArray getJSONArray(String JSON) {
        try {
            return (JSONArray) new JSONParser().parse(JSON);
        } catch (Exception e) {
            return new JSONArray();
        }
    }

    public List<MyJsonObject> getObjects() {
        List<MyJsonObject> list = new ArrayList<>();
        for (Object o : arr) list.add(new MyJsonObject(o.toString()));
        return list;
    }

    @Override
    public Iterator<String> iterator() {
        return get().iterator();
    }
}
