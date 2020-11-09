package com.homeService.lib;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public final class ConfigWriteReader {
    private ConfigWriteReader() {}

    public static Map<String, String> parse(File file) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream(file));

        Map<String, String> map = new HashMap<>();
        for (String s : properties.stringPropertyNames()) {
            map.put(s, properties.getProperty(s));
        }
        return map;
    }


    /*
    private Map<String, Boolean> getBool() {
        Map<String, Boolean> mapBool = new HashMap<>();
        return mapBool;
    }
    private Map<String, String> getString() {
        Map<String, String> mapString = new HashMap<>();
        return mapString;
    }
    private Map<String, Integer> getInteger() {
        Map<String, Integer> mapInteger = new HashMap<>();
        return mapInteger;
    }
    private Map<String, Double> getDouble() {
        Map<String, Double> mapDouble = new HashMap<>();
        return mapDouble;
    }

    private Map<String, Boolean[]> getBoolArray() {
        Map<String, Boolean[]> mapBool = new HashMap<>();
        return mapBool;
    }
    private Map<String, String> getStringArray() {
        Map<String, String> mapString = new HashMap<>();
        return mapString;
    }
    private Map<String, Integer> getIntegerArray() {
        Map<String, Integer> mapInteger = new HashMap<>();
        return mapInteger;
    }
    private Map<String, Double> getDoubleArray() {
        Map<String, Double> mapDouble = new HashMap<>();
        return mapDouble;
    }
    */
}