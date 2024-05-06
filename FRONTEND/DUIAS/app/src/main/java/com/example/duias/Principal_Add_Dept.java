package com.example.duias;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class Principal_Add_Dept extends Fragment implements View.OnClickListener {
    private static final Pattern NAME_PATTERN = Pattern.compile("[a-zA-Z ]{3,40}");
    EditText edtname;
    String name;
    Button btnsave,btnview;
    RequestQueue w;
    ProgressDialog pd;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.principal_add_dept,container,false);

        edtname = (EditText) v.findViewById(R.id.edit_name);

        btnsave = (Button) v.findViewById(R.id.button_save);
        btnview = (Button) v.findViewById(R.id.button_view);

        btnview.setOnClickListener(this);

        btnsave.setOnClickListener(this);
        w = Volley.newRequestQueue(getActivity());

        return v;
    }

    @Override
    public void onClick(View view) {
        if(view == btnsave){
            name = edtname.getText().toString().trim();
            if(name.equals(""))
            {
                edtname.setError("Please Enter Department Name");
                edtname.setFocusable(true);
            }else if(!checkName(name)){
                edtname.setError("Please Enter Only Alphabets in Your Name");
                edtname.setFocusable(true);
            }else{
                add_dept();
            }
        }

        if(view==btnview)
        {
            Fragment fragment3 = new Principal_View_Dept();

            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.addToBackStack(null);
            transaction.replace(R.id.frame, fragment3);
            transaction.commit();
        }
    }

    private void add_dept() {
        pd = new ProgressDialog(getActivity());
        pd.setMessage("Adding Department.....");
        pd.setIndeterminate(false);
        pd.setCancelable(false);
        pd.show();


        StringRequest sr = new StringRequest(Request.Method.POST, IPConfig.url + "add_dept_api",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        pd.dismiss();
                        try {
                            JSONObject ja= new JSONObject(s);
                            String result = ja.getString("result");

                            if(result.equals("Department Added Successfully")){
                                Toast.makeText(getActivity(), "Department Added Successfully", Toast.LENGTH_SHORT).show();
                                openDeptFragment();
                            }else{
                                Toast.makeText(getActivity(), "Check Your Data", Toast.LENGTH_SHORT).show();
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
                map.put("name",name);
                return map;
            }
        };


        w.add(sr);
    }

    private void openDeptFragment() {
        Fragment fragment3 = new Principal_Add_Dept();

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.addToBackStack(null);
        transaction.replace(R.id.frame, fragment3);
        transaction.commit();
    }

    private boolean checkName(String name) {
        return NAME_PATTERN.matcher(name).matches();
    }

}
