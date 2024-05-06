package com.example.duias;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Parse_For_Student_Assignment_Marks_Detail {
    public static String[] assign_detail_id;
    public static String[] assign_id;
    public static String[] studid;
    public static String[] rno;
    public static String[] assign_no;
    public static String[] assign_received;
    public static String[] stud_name;
    public static String[] marks;
    String s;

    public JSONArray users=null;
    public Parse_For_Student_Assignment_Marks_Detail(String s) {
        this.s = s;
    }

    public void parseJSON() {
        JSONObject ja = null;

        try {
            ja = new JSONObject(s);
            users = ja.getJSONArray("items");

            assign_detail_id = new String[users.length()];
            assign_id = new String[users.length()];
            studid = new String[users.length()];
            rno = new String[users.length()];
            assign_no = new String[users.length()];
            assign_received = new String[users.length()];
            stud_name = new String[users.length()];
            marks = new String[users.length()];

            for(int i=0;i< users.length();i++){
                JSONObject jo = users.getJSONObject(i);
                assign_detail_id[i] = jo.getString("assign_detail_id");
                assign_id[i] = jo.getString("assign_detail_id");
                studid[i] = jo.getString("studid");
                rno[i] = jo.getString("rno");
                assign_no[i] = jo.getString("assign_no");
                assign_received[i] = jo.getString("assign_received");
                stud_name[i] = jo.getString("stud_name");
                marks[i] = jo.getString("marks");
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
