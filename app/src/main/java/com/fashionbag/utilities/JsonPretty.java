package com.fashionbag.utilities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import retrofit2.Response;

/**
 * Created by bootavo on 29/08/2017.
 */

public class JsonPretty {

    public static String getPrettyJson(Response response){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(response.body());
        return json;
    }

    public static String getPrettyJson(JSONObject jsonObject){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(jsonObject);
        //String json = gson.toJson(new JsonParser().parse(jsonObject.toString()));
        return json;
    }

}
