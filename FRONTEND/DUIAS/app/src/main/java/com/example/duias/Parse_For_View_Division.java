package com.example.duias;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Parse_For_View_Division {
    public static String[] div_id;
    public static String[] academic_year;
    public static String[] class_name;
    public static String[] program_id;
    public static String[] semester;
    public static String[] division;
    public static String[] clerk_id;
    public static String[] div_status;


    String s;

    public JSONArray users=null;
    public Parse_For_View_Division(String s) {
        this.s = s;
    }

    public void parseJSON() {
        JSONObject ja = null;

        try {
            ja = new JSONObject(s);
            users = ja.getJSONArray("pitems");

            div_id = new String[users.length()];
            academic_year = new String[users.length()];
            class_name = new String[users.length()];
            program_id = new String[users.length()];
            clerk_id = new String[users.length()];
            semester = new String[users.length()];
            division = new String[users.length()];

            div_status = new String[users.length()];

            for(int i=0;i< users.length();i++){
                JSONObject jo = users.getJSONObject(i);
                div_id[i] = jo.getString("div_id");
                academic_year[i] = jo.getString("academic_year");
                program_id[i] = jo.getString("program_id");
                class_name[i] = jo.getString("class_name");
                semester[i] = jo.getString("semester");
                division[i] = jo.getString("division");


                clerk_id[i] = jo.getString("clerk_id");

                div_status[i] = jo.getString("div_status");
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
