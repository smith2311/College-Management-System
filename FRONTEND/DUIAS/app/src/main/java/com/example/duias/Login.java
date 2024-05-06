package com.example.duias;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Login extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    EditText edtemail,edtpwd;
    String email,pwd,utype,selltype="0";
    Spinner spinutype;
    Button btnlogin;
    ProgressDialog pd;
    private static final Pattern EMAIL_PATTERN = Pattern.compile("[a-zA-Z0-9+._%-+]{1,100}" + "@"
            + "[a-zA-Z0-9][a-zA-Z0-9-]{0,10}" + "(" + "."
            + "[a-zA-Z0-9][a-zA-Z0-9-]{0,20}" +
            ")+");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("[a-zA-Z0-9+_.]{6,10}");
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        edtemail = (EditText) findViewById(R.id.edt_email);
        edtpwd = (EditText) findViewById(R.id.edt_pwd);
        spinutype = (Spinner) findViewById(R.id.spinner_utype);
        btnlogin = (Button) findViewById(R.id.btn_login);

        List<String> selutype = new ArrayList<String>();
        selutype.add("--Select User Type--");
        selutype.add("Principal");
        selutype.add("Hod");
        selutype.add("Faculty");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, selutype);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinutype.setAdapter(dataAdapter);

        spinutype.setOnItemSelectedListener(this);

        btnlogin.setOnClickListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
         utype = adapterView.getItemAtPosition(i).toString();
         if(utype=="Principal")
         {
            selltype="1";
         }else if(utype=="Hod")
         {
             selltype="3";
         }else if(utype=="Faculty")
         {
             selltype="4";
         }else{
             selltype="0";
         }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        if(view==btnlogin)
        {
            email = edtemail.getText().toString().trim();
            pwd = edtpwd.getText().toString().trim();
            if(email.equals("")){
                edtemail.setError("Please Enter Email Id");
                edtemail.requestFocus();
            }else if(!checkemail(email)){
                edtemail.setError("Please Enter Valid Email Id");
                edtemail.requestFocus();
            }else if(pwd.equals("")){
                edtpwd.setError("Please Enter Password");
                edtpwd.requestFocus();
            }else if(!checkpwd(pwd)){
                edtpwd.setError("Please Enter Password 6 to 10 Character Long");
                edtpwd.requestFocus();
            }else if(selltype.equals("0")){
                Toast.makeText(this, "Please Select User Type", Toast.LENGTH_SHORT).show();

            }
            else{
                login_stud();
            }
        }

    }

    private void login_stud() {

        pd = new ProgressDialog(this);
        pd.setMessage("Logging User.....");
        pd.setIndeterminate(false);
        pd.setCancelable(false);
        pd.show();


        StringRequest sr = new StringRequest(Request.Method.POST, IPConfig.url + "loginapi",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        pd.dismiss();
                        try {
                            JSONObject ja= new JSONObject(s);
                            String result = ja.getString("result");

                            if(result.equals("Principal Login Successfull")){
                                Toast.makeText(Login.this, "Principal Login Successfull", Toast.LENGTH_SHORT).show();
                                openprincipalDashboard(ja.getString("pid"));
                            }else if(result.equals("Hod Login Successfull")){
                                Toast.makeText(Login.this, "Hod Login Successfull", Toast.LENGTH_SHORT).show();
                                openHodDashboard(ja.getString("hodid"));
                            }else if(result.equals("Faculty Login Successfull")){
                                Toast.makeText(Login.this, "Faculty Login Successfull", Toast.LENGTH_SHORT).show();
                                openFacultyDashboard(ja.getString("fid"));
                            }
                            else{
                                Toast.makeText(Login.this, "Check Your Email ID or Password", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(Login.this, volleyError.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map map = new HashMap();
                map.put("txtemail",email);
                map.put("txtpwd",pwd);
                map.put("selltype",selltype);
                return map;
            }
        };

        RequestQueue rq = Volley.newRequestQueue(this);
        rq.add(sr);
    }

    private void openFacultyDashboard(String fid) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit1 = sp.edit();
        edit1.putString("femail",email);
        edit1.putString("fid",fid);
        edit1.commit();

        Intent i = new Intent(Login.this,Faculty_Dashboard.class);
        finish();
        startActivity(i);
    }

    private void openHodDashboard(String hodid) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit1 = sp.edit();
        edit1.putString("hemail",email);
        edit1.putString("hodid",hodid);
        edit1.commit();

        Intent i = new Intent(Login.this,Hod_Dashboard.class);
        finish();
        startActivity(i);
    }

    private void openprincipalDashboard(String pid) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit1 = sp.edit();
        edit1.putString("pemail",email);
        edit1.putString("pid",pid);
        edit1.commit();

        Intent i = new Intent(Login.this,Principal_Dashboard.class);
        finish();
        startActivity(i);
    }


    private boolean checkpwd(String pwd) {
        return PASSWORD_PATTERN.matcher(pwd).matches();
    }

    private boolean checkemail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

}
