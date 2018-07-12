package com.droid.filip.androidfragmentsandservices.mocks;

import com.droid.filip.androidfragmentsandservices.stakes.Location;
import com.droid.filip.androidfragmentsandservices.stakes.LocationsResponse;

import java.util.Arrays;

public class MockedResponse {

    public static synchronized LocationsResponse getMockedData() {
        LocationsResponse response = new LocationsResponse();
        response.setGeonames(new Location[]{new Location(21.431407, 785842, "MK", "Skopje", "city, village,...",
                "Skopje", "capital of a political entity", "en.wikipedia.org/wiki/Skopje", 41.996457,
                "P", 474889, "PPLC"),
        new Location(21.334736, 792578, "MK", "Bitola",
                "city, village,...", "Bitola", "seat of a first-order administrative division",
                "", 41.031429, "P", 86528, "PPLA")});
        return response;
    }

    public static synchronized String[] getMockedCities() {
        LocationsResponse response = getMockedData();
        String[] cities = new String[response.getGeonames().length];
        int i = 0;
        for (Location l : response.getGeonames()) {
            cities[i++] = l.getName();
        }
        return cities;
    }
}
