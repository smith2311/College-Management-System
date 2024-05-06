package com.example.duias;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Parse_For_View_Dept {
    public static String[] dep_id;
    public static String[] dep_name;
    public static String[] dep_status;


    String s;

    public JSONArray users=null;
    public Parse_For_View_Dept(String s) {
        this.s = s;
    }

    public void parseJSON() {
        JSONObject ja = null;

        try {
            ja = new JSONObject(s);
            users = ja.getJSONArray("ditems");

            dep_id = new String[users.length()];
            dep_name = new String[users.length()];
            dep_status = new String[users.length()];

            for(int i=0;i< users.length();i++){
                JSONObject jo = users.getJSONObject(i);
                dep_id[i] = jo.getString("dep_id");
                dep_name[i] = jo.getString("dep_name");
                dep_status[i] = jo.getString("dep_status");

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
