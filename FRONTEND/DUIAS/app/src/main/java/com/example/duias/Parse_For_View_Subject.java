package com.example.duias;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Parse_For_View_Subject {
    public static String[] sub_id;
    public static String[] sub_name;
    public static String[] program_id;
    public static String[] program_name;
    public static String[] sub_code;
    public static String[] semester;
    public static String[] hod_id;
    public static String[] sub_status;

    String s;

    public JSONArray users=null;
    public Parse_For_View_Subject(String s) {
        this.s = s;
    }

    public void parseJSON() {
        JSONObject ja = null;

        try {
            ja = new JSONObject(s);
            users = ja.getJSONArray("sitems");

            sub_id = new String[users.length()];
            sub_name = new String[users.length()];
            program_id = new String[users.length()];
            program_name = new String[users.length()];
            sub_code = new String[users.length()];
            semester = new String[users.length()];
            hod_id = new String[users.length()];
            sub_status = new String[users.length()];

            for(int i=0;i< users.length();i++){
                JSONObject jo = users.getJSONObject(i);
                sub_id[i] = jo.getString("sub_id");
                sub_name[i] = jo.getString("sub_name");
                program_id[i] = jo.getString("program_id");
                program_name[i] = jo.getString("program_name");
                sub_code[i] = jo.getString("sub_code");
                semester[i] = jo.getString("semester");
                hod_id[i] = jo.getString("hod_id");
                sub_status[i] = jo.getString("sub_status");

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
