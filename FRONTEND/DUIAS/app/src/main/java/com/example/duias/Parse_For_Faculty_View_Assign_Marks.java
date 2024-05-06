package com.example.duias;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Parse_For_Faculty_View_Assign_Marks {
    public static String[] assign_id;

    public static String[] program_id;
    public static String[] program_name;
    public static String[] faculty_id;
    public static String[] faculty_name;
    public static String[] sub_id;
    public static String[] sub_name;
    public static String[] semester;
    public static String[] div_id;
    public static String[] division;
    public static String[] no_of_assign;

    String s;

    public JSONArray users=null;
    public Parse_For_Faculty_View_Assign_Marks(String s) {
        this.s = s;
    }

    public void parseJSON() {
        JSONObject ja = null;

        try {
            ja = new JSONObject(s);
            users = ja.getJSONArray("items");

            assign_id = new String[users.length()];

            program_id = new String[users.length()];
            program_name = new String[users.length()];
            faculty_id = new String[users.length()];
            faculty_name = new String[users.length()];
            sub_id = new String[users.length()];
            sub_name = new String[users.length()];
            semester = new String[users.length()];
            div_id = new String[users.length()];
            division = new String[users.length()];

            no_of_assign = new String[users.length()];


            for(int i=0;i< users.length();i++){
                JSONObject jo = users.getJSONObject(i);
                assign_id[i] = jo.getString("assign_id");

                program_id[i] = jo.getString("program_id");
                program_name[i] = jo.getString("program_name");
                faculty_id[i] = jo.getString("faculty_id");
                faculty_name[i] = jo.getString("faculty_name");
                sub_id[i] = jo.getString("sub_id");
                sub_name[i] = jo.getString("sub_name");
                semester[i] = jo.getString("semester");
                div_id[i] = jo.getString("div_id");
                division[i] = jo.getString("division");
                no_of_assign[i] = jo.getString("no_of_assign");
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
