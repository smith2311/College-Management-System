package com.example.duias;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Parse_For_Faculty_View_Internal_Marks {
    public static String[] marks_id;
    public static String[] marks_date;
    public static String[] program_id;
    public static String[] program_name;
    public static String[] semester;
    public static String[] sub_id;
    public static String[] sub_name;
    public static String[] faculty_id;
    public static String[] faculty_name;
    public static String[] internal_exam;
    public static String[] total_marks;
    public static String[] academic_year;
    public static String[] div_id;
    public static String[] division;
    String s;

    public JSONArray users=null;
    public Parse_For_Faculty_View_Internal_Marks(String s) {
        this.s = s;
    }

    public void parseJSON() {
        JSONObject ja = null;

        try {
            ja = new JSONObject(s);
            users = ja.getJSONArray("items");

            marks_id = new String[users.length()];
            marks_date = new String[users.length()];
            program_id = new String[users.length()];
            program_name = new String[users.length()];
            semester = new String[users.length()];
            sub_id = new String[users.length()];
            sub_name = new String[users.length()];
            faculty_id = new String[users.length()];
            faculty_name = new String[users.length()];
            semester = new String[users.length()];
            internal_exam = new String[users.length()];
            total_marks = new String[users.length()];
            academic_year = new String[users.length()];
            div_id = new String[users.length()];
            division = new String[users.length()];

            for(int i=0;i< users.length();i++){
                JSONObject jo = users.getJSONObject(i);
                marks_id[i] = jo.getString("marks_id");
                marks_date[i] = jo.getString("marks_date");
                program_id[i] = jo.getString("program_id");
                program_name[i] = jo.getString("program_name");
                semester[i] = jo.getString("semester");
                sub_id[i] = jo.getString("sub_id");
                sub_name[i] = jo.getString("sub_name");
                faculty_id[i] = jo.getString("faculty_id");
                faculty_name[i] = jo.getString("faculty_name");
                internal_exam[i] = jo.getString("internal_exam");
                total_marks[i] = jo.getString("total_marks");
                academic_year[i] = jo.getString("academic_year");
                div_id[i] = jo.getString("div_id");
                division[i] = jo.getString("division");
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
