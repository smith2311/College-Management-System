package com.example.duias;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Parse_For_Student_Internal_Marks {
    public static String[] marks_id;
    public static String[] studid;
    public static String[] rno;
    public static String[] marks;
    public static String[] stud_name;

    String s;

    public JSONArray users=null;
    public Parse_For_Student_Internal_Marks(String s) {
        this.s = s;
    }

    public void parseJSON() {
        JSONObject ja = null;

        try {
            ja = new JSONObject(s);
            users = ja.getJSONArray("items");

            marks_id = new String[users.length()];
            studid = new String[users.length()];
            rno = new String[users.length()];
            marks = new String[users.length()];
            stud_name = new String[users.length()];


            for(int i=0;i< users.length();i++){
                JSONObject jo = users.getJSONObject(i);
                marks_id[i] = jo.getString("marks_id");
                studid[i] = jo.getString("studid");
                rno[i] = jo.getString("rno");
                marks[i] = jo.getString("marks");
                stud_name[i] = jo.getString("stud_name");

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
