package com.homeService.lib;

public class Path {
    private String URL;
    private String name;

    public Path() {}
    public Path(String URL, String name) {
        this.name = name;
        this.URL = URL;
    }

    public String getURL() {
        return URL;
    }
    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
