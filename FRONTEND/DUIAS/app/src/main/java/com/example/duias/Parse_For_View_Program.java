package com.example.duias;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Parse_For_View_Program {
    public static String[] program_id;
    public static String[] program_name;
    public static String[] dep_id;
    public static String[] clerk_id;
    public static String[] program_status;


    String s;

    public JSONArray users=null;
    public Parse_For_View_Program(String s) {
        this.s = s;
    }

    public void parseJSON() {
        JSONObject ja = null;

        try {
            ja = new JSONObject(s);
            users = ja.getJSONArray("pitems");

            program_id = new String[users.length()];
            program_name = new String[users.length()];
            dep_id = new String[users.length()];
            clerk_id = new String[users.length()];
            program_status = new String[users.length()];

            for(int i=0;i< users.length();i++){
                JSONObject jo = users.getJSONObject(i);
                program_id[i] = jo.getString("program_id");
                program_name[i] = jo.getString("program_name");
                dep_id[i] = jo.getString("dep_id");
                clerk_id[i] = jo.getString("clerk_id");
                program_status[i] = jo.getString("program_status");
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
