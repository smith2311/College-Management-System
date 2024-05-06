package com.example.duias;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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

public class Hod_Change_Pwd extends Fragment implements View.OnClickListener {

    private static final Pattern PASSWORD_PATTERN = Pattern.compile("[a-zA-Z0-9+_.]{6,10}");

    EditText edtopwd,edtnpwd,edtcpwd;
    String opwd,npwd,cpwd,hodid;
    Button btnchange;
    RequestQueue w;
    ProgressDialog pDialog,pd;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.hod_change_pwd,container,false);

        edtopwd = (EditText) v.findViewById(R.id.edt_opwd);
        edtnpwd = (EditText) v.findViewById(R.id.edt_npwd);
        edtcpwd = (EditText) v.findViewById(R.id.edt_cpwd);
        btnchange = (Button) v.findViewById(R.id.button_change);
        btnchange.setOnClickListener(this);
        w = Volley.newRequestQueue(getActivity());

        return v;
    }


    @Override
    public void onClick(View view) {


        if(view==btnchange){
            opwd = edtopwd.getText().toString().trim();
            npwd = edtnpwd.getText().toString().trim();
            cpwd = edtcpwd.getText().toString().trim();


            if(opwd.equals("")){
                edtopwd.setError("Please Enter Old Password");
                edtopwd.setFocusable(true);
            }else if(!checkpwd(opwd)){
                edtopwd.setError("Please Enter Password 6 to 10 Character Long Old Password");
                edtopwd.setFocusable(true);
            }
            else if(npwd.equals("")){
                edtnpwd.setError("Please Enter New Password");
                edtnpwd.setFocusable(true);
            }else if(!checkpwd(npwd)){
                edtnpwd.setError("Please Enter Password 6 to 10 Character Long New Password");
                edtnpwd.setFocusable(true);
            }else if(opwd.equals(npwd)){
                Toast.makeText(getActivity(), "Please Dont Repeat Your Old Password into New Password", Toast.LENGTH_SHORT).show();
            }else if(!npwd.equals(cpwd)){
                Toast.makeText(getActivity(), "Please Enter Confirm Password same as New password", Toast.LENGTH_SHORT).show();
            }
            else{
                change_pwd();
            }
        }


    }

    private void change_pwd() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        hodid = sp.getString("hodid", "anon");
        pd = new ProgressDialog(getActivity());
        pd.setMessage("Hod Changing Password.....");
        pd.setIndeterminate(false);
        pd.setCancelable(false);
        pd.show();


        StringRequest sr = new StringRequest(Request.Method.POST, IPConfig.url + "hod_change_pwd",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        pd.dismiss();
                        try {
                            JSONObject ja= new JSONObject(s);
                            String result = ja.getString("result");

                            if(result.equals("Password Updated Successfully")){
                                Toast.makeText(getActivity(), "Password Updated Successfully", Toast.LENGTH_SHORT).show();
                                openchangepwd();
                            }else{
                                Toast.makeText(getActivity(), ja.getString("result"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getActivity(), volleyError.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map map = new HashMap();
                map.put("opwd",opwd);
                map.put("npwd",npwd);
                map.put("cpwd",cpwd);

                map.put("hodid",hodid);

                return map;
            }
        };


        w.add(sr);
    }



    private void openchangepwd() {
        Fragment fragment3 = new Hod_Change_Pwd();

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.addToBackStack(null);
        transaction.replace(R.id.frame, fragment3);
        transaction.commit();
    }

    private boolean checkpwd(String pwd) {
        return PASSWORD_PATTERN.matcher(pwd).matches();
    }

}
