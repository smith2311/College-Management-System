package com.example.duias;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Principal_Edit_Dept extends Fragment implements View.OnClickListener {
    private static final Pattern NAME_PATTERN = Pattern.compile("[a-zA-Z ]{3,40}");
    EditText edtname;
    String name,did,dname;
    Button btnupdate;
    RequestQueue w;
    ProgressDialog pd;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.principal_edit_dept,container,false);

        did = getArguments().getString("did");
        dname = getArguments().getString("dname");
        edtname = (EditText) v.findViewById(R.id.edit_name);
        edtname.setText(dname);
        btnupdate = (Button) v.findViewById(R.id.button_update);


        btnupdate.setOnClickListener(this);
        w = Volley.newRequestQueue(getActivity());

        return v;
    }

    @Override
    public void onClick(View view) {
        if(view == btnupdate){
            name = edtname.getText().toString().trim();
            if(name.equals(""))
            {
                edtname.setError("Please Enter Department Name");
                edtname.setFocusable(true);
            }else if(!checkName(name)){
                edtname.setError("Please Enter Only Alphabets in Your Name");
                edtname.setFocusable(true);
            }else{
                update_dept();
            }
        }


    }

    private void update_dept() {
        pd = new ProgressDialog(getActivity());
        pd.setMessage("Updating Department.....");
        pd.setIndeterminate(false);
        pd.setCancelable(false);
        pd.show();


        StringRequest sr = new StringRequest(Request.Method.POST, IPConfig.url + "update_dept_api",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        pd.dismiss();
                        try {
                            JSONObject ja= new JSONObject(s);
                            String result = ja.getString("result");

                            if(result.equals("Department Updated Successfully")){
                                Toast.makeText(getActivity(), "Department Updated Successfully", Toast.LENGTH_SHORT).show();
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
                map.put("did",did);
                map.put("name",name);
                return map;
            }
        };


        w.add(sr);
    }

    private void openDeptFragment() {
        Fragment fragment3 = new Principal_View_Dept();

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.addToBackStack(null);
        transaction.replace(R.id.frame, fragment3);
        transaction.commit();
    }

    private boolean checkName(String name) {
        return NAME_PATTERN.matcher(name).matches();
    }

}
