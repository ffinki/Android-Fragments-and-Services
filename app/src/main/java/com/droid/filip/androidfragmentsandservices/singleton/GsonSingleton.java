package com.droid.filip.androidfragmentsandservices.singleton;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonSingleton {

    private static Gson gson;
    private static GsonSingleton singleton;

    private GsonSingleton() {
        gson = new GsonBuilder().create();
    }

    public static Gson getInstance() {
        if (singleton == null)
            singleton = new GsonSingleton();
        return singleton.getGson();
    }

    private Gson getGson() {
        return gson;
    }

}
