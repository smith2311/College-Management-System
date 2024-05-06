package com.example.duias;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Parse_For_Faculty_View_Attendance {
    public static String[] attend_id;
    public static String[] attend_date;
    public static String[] faculty_id;
    public static String[] faculty_name;
    public static String[] lec_no;
    public static String[] program_id;
    public static String[] program_name;
    public static String[] sub_id;
    public static String[] sub_name;
    public static String[] semester;
    public static String[] lec_time;
    public static String[] div_id;
    public static String[] division;
    String s;

    public JSONArray users=null;
    public Parse_For_Faculty_View_Attendance(String s) {
        this.s = s;
    }

    public void parseJSON() {
        JSONObject ja = null;

        try {
            ja = new JSONObject(s);
            users = ja.getJSONArray("items");

            attend_id = new String[users.length()];
            attend_date = new String[users.length()];
            faculty_id = new String[users.length()];
            faculty_name = new String[users.length()];
            lec_no = new String[users.length()];
            program_id = new String[users.length()];
            program_name = new String[users.length()];
            sub_id = new String[users.length()];
            sub_name = new String[users.length()];
            semester = new String[users.length()];
            lec_time = new String[users.length()];
            div_id = new String[users.length()];
            division = new String[users.length()];

            for(int i=0;i< users.length();i++){
                JSONObject jo = users.getJSONObject(i);
                attend_id[i] = jo.getString("attend_id");
                attend_date[i] = jo.getString("attend_date");
                faculty_id[i] = jo.getString("faculty_id");
                faculty_name[i] = jo.getString("faculty_name");
                lec_no[i] = jo.getString("lec_no");
                program_id[i] = jo.getString("program_id");
                program_name[i] = jo.getString("program_name");
                sub_id[i] = jo.getString("sub_id");
                sub_name[i] = jo.getString("sub_name");
                semester[i] = jo.getString("semester");
                lec_time[i] = jo.getString("lec_time");
                div_id[i] = jo.getString("div_id");
                division[i] = jo.getString("division");
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
