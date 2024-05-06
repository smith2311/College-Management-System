package com.example.duias;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Parse_For_View_Hod {
    public static String[] hod_id;
    public static String[] hod_name;
    public static String[] address;
    public static String[] city;
    public static String[] mobile_no;
    public static String[] email_id;
    public static String[] pwd;
    public static String[] gender;
    public static String[] security_que;
    public static String[] security_ans;
    public static String[] dep_id;
    public static String[] dep_name;
    public static String[] hod_status;


    String s;

    public JSONArray users=null;
    public Parse_For_View_Hod(String s) {
        this.s = s;
    }

    public void parseJSON() {
        JSONObject ja = null;

        try {
            ja = new JSONObject(s);
            users = ja.getJSONArray("hitems");

            hod_id = new String[users.length()];
            hod_name = new String[users.length()];
            address = new String[users.length()];
            city = new String[users.length()];
            mobile_no = new String[users.length()];
            email_id = new String[users.length()];
            pwd = new String[users.length()];
            gender = new String[users.length()];
            security_que = new String[users.length()];
            security_ans = new String[users.length()];
            dep_id = new String[users.length()];
            dep_name = new String[users.length()];
            hod_status = new String[users.length()];

            for(int i=0;i< users.length();i++){
                JSONObject jo = users.getJSONObject(i);
                hod_id[i] = jo.getString("hod_id");
                hod_name[i] = jo.getString("hod_name");
                address[i] = jo.getString("address");
                city[i] = jo.getString("city");
                mobile_no[i] = jo.getString("mobile_no");
                email_id[i] = jo.getString("email_id");
                pwd[i] = jo.getString("pwd");
                gender[i] = jo.getString("gender");
                security_que[i] = jo.getString("security_que");
                security_ans[i] = jo.getString("security_ans");
                dep_id[i] = jo.getString("dep_id");
                dep_name[i] = jo.getString("dep_name");
                hod_status[i] = jo.getString("hod_status");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
