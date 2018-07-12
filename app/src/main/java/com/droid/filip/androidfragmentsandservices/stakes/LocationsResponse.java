package com.droid.filip.androidfragmentsandservices.stakes;

import java.io.Serializable;

public class LocationsResponse implements Serializable{

    private Location[] geonames;

    public LocationsResponse() {}

    public LocationsResponse(int size) {
        geonames = new Location[size];
    }

    public LocationsResponse(Location[] geonames) {
        this.geonames = geonames;
    }

    public Location[] getGeonames() {
        return geonames;
    }

    public void setGeonames(Location[] geonames) {
        this.geonames = geonames;
    }
}
