package com.gm.mapr.demo;


public class YelpObject {
    private String name;
    private String yelpingSince;
    private String support;

    public YelpObject(String name, String yelpingSince, String support) {
        this.name = name;
        this.yelpingSince = yelpingSince;
        this.support = support;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYelpingSince() {
        return yelpingSince;
    }

    public void setYelpingSince(String yelpingSince) {
        this.yelpingSince = yelpingSince;
    }

    public String getSupport() {
        return support;
    }

    public void setSupport(String support) {
        this.support = support;
    }
}
