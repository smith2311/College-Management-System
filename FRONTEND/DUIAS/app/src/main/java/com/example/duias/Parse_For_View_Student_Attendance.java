package com.example.duias;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Parse_For_View_Student_Attendance {
    public static String[] attend_id;
    public static String[] studid;
    public static String[] rno;
    public static String[] present_absent;
    public static String[] stud_name;



    String s;

    public JSONArray users=null;
    public Parse_For_View_Student_Attendance(String s) {
        this.s = s;
    }

    public void parseJSON() {
        JSONObject ja = null;

        try {
            ja = new JSONObject(s);
            users = ja.getJSONArray("items");

            attend_id = new String[users.length()];
            studid = new String[users.length()];
            rno = new String[users.length()];
            present_absent = new String[users.length()];
            stud_name = new String[users.length()];

            for(int i=0;i< users.length();i++){
                JSONObject jo = users.getJSONObject(i);
                attend_id[i] = jo.getString("attend_id");
                studid[i] = jo.getString("studid");
                rno[i] = jo.getString("rno");
                present_absent[i] = jo.getString("present_absent");
                stud_name[i] = jo.getString("stud_name");

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
