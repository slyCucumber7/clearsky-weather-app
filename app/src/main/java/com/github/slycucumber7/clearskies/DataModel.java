package com.github.slycucumber7.clearskies;

import org.json.JSONObject;

public class DataModel {
    String entry_date;
    String lattitude;
    String longitude;
    String timezone;
    String units;
    String elevation;
    String light_pollution;
    String[] hourly_temperature;
    String[] hourly_cloud_cover;
    String[] hourly_precipitation_chance;
    String[] hourly_uv_index;

    public DataModel(){

    }

    public void update(JSONObject response){

    }



}
