package com.droid.filip.androidfragmentsandservices.stakes;

import android.os.Parcelable;

import java.io.Serializable;

public class Location implements Serializable{

    private double lng;
    private long geonameId;
    private String countrycode;
    private String name;
    private String fclName;
    private String toponymName;
    private String fcodeName;
    private String wikipedia;
    private double lat;
    private String fcl;
    private long population;
    private String fcode;

    public Location() {}

    public Location(double lng, long geonameId, String countrycode, String name, String fclName, String toponymName, String fcodeName, String wikipedia, double lat, String fcl, long population, String fcode) {
        this.lng = lng;
        this.geonameId = geonameId;
        this.countrycode = countrycode;
        this.name = name;
        this.fclName = fclName;
        this.toponymName = toponymName;
        this.fcodeName = fcodeName;
        this.wikipedia = wikipedia;
        this.lat = lat;
        this.fcl = fcl;
        this.population = population;
        this.fcode = fcode;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public long getGeonameId() {
        return geonameId;
    }

    public void setGeonameId(long geonameId) {
        this.geonameId = geonameId;
    }

    public String getCountrycode() {
        return countrycode;
    }

    public void setCountrycode(String countrycode) {
        this.countrycode = countrycode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFclName() {
        return fclName;
    }

    public void setFclName(String fclName) {
        this.fclName = fclName;
    }

    public String getToponymName() {
        return toponymName;
    }

    public void setToponymName(String toponymName) {
        this.toponymName = toponymName;
    }

    public String getFcodeName() {
        return fcodeName;
    }

    public void setFcodeName(String fcodeName) {
        this.fcodeName = fcodeName;
    }

    public String getWikipedia() {
        return wikipedia;
    }

    public void setWikipedia(String wikipedia) {
        this.wikipedia = wikipedia;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getFcl() {
        return fcl;
    }

    public void setFcl(String fcl) {
        this.fcl = fcl;
    }

    public long getPopulation() {
        return population;
    }

    public void setPopulation(long population) {
        this.population = population;
    }

    public String getFcode() {
        return fcode;
    }

    public void setFcode(String fcode) {
        this.fcode = fcode;
    }
}
