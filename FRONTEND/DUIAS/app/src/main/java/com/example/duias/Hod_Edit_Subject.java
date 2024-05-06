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
import android.widget.RadioButton;
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

public class Hod_Edit_Subject extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private static final Pattern NAME_PATTERN = Pattern.compile("[a-zA-Z ]{3,40}");
    private static final Pattern SCODE_PATTERN = Pattern.compile("[0-9]{1,5}");


    EditText edtname,edtscode;
    String name,scode,prog_id="0",sem="0",hodid,subid,pname;



    Spinner spinprog,spinsem;
    Button btnupdate;
    RequestQueue w;
    ProgressDialog pDialog,pd;
    Parse_For_View_Program pd1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.hod_edit_subject,container,false);

        subid = getArguments().getString("subid");
        name = getArguments().getString("subname");
        prog_id = getArguments().getString("program_id");

        sem = getArguments().getString("semester");
        scode = getArguments().getString("sub_code");
        pname = getArguments().getString("program_name");


        edtname = (EditText) v.findViewById(R.id.editText_name);
        edtscode = (EditText) v.findViewById(R.id.editText_scode);

        spinprog = (Spinner) v.findViewById(R.id.spinner_program);
        spinsem = (Spinner) v.findViewById(R.id.spinner_sem);

        btnupdate = (Button) v.findViewById(R.id.button_update);
        List<String> selsem = new ArrayList<String>();
        selsem.add("--Select Semester--");
        selsem.add("SEM1");
        selsem.add("SEM2");
        selsem.add("SEM3");
        selsem.add("SEM4");
        selsem.add("SEM5");
        selsem.add("SEM6");
        selsem.add("SEM7");
        selsem.add("SEM8");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, selsem);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinsem.setAdapter(dataAdapter);


        for(int i=0;i< spinsem.getCount() ; i++)
        {
            if(spinsem.getItemAtPosition(i).equals(sem))
            {
                spinsem.setSelection(i);
            }
        }

        edtname.setText(name);
        edtscode.setText(scode);



        btnupdate.setOnClickListener(this);
        w = Volley.newRequestQueue(getActivity());

        loadprogram();
        spinsem.setOnItemSelectedListener(this);
        spinprog.setOnItemSelectedListener(this);
        return v;
    }

    private void loadprogram() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        hodid = sp.getString("hodid", "anon");
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Loading Program.....");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();


        StringRequest strreq =new StringRequest(Request.Method.GET, IPConfig.url + "fetch_program_api/"+hodid,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        pDialog.dismiss();
                        try {
                            JSONObject ja = new JSONObject(s);
                            String Status = ja.getString("status");

                            if (Status.equals("success")) {
                                showJSON(s);
                            } else {
                                Toast.makeText(getActivity(), "No Program Found", Toast.LENGTH_LONG).show();
                            }
                        }catch(JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getActivity(), volleyError.toString(), Toast.LENGTH_SHORT).show();
                    }
                }

        );
        w.add(strreq);
    }

    private void showJSON(String s) {
        pd1 = new Parse_For_View_Program(s);
        pd1.parseJSON();

        JSONObject jsonObject=null;
        List<String> selprog = new ArrayList<String>();
        selprog.add("--Select Program--");

        for (int i = 0; i < Parse_For_View_Program.program_id.length; i++) {
            selprog.add(Parse_For_View_Program.program_name[i].toString());
        }
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, selprog);
        // Drop down layout style - list view with radio button
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinprog.setAdapter(dataAdapter1);
        for(int i=0;i< spinprog.getCount() ; i++)
        {
            if(spinprog.getItemAtPosition(i).equals(pname))
            {
                spinprog.setSelection(i);
            }
        }
    }


    @Override
    public void onClick(View view) {
        if (view == btnupdate) {
            name = edtname.getText().toString().trim();
            scode = edtscode.getText().toString().trim();


            if (name.equals("")) {
                edtname.setError("Please Enter Subject Name");
                edtname.setFocusable(true);
            } else if (!checkName(name)) {
                edtname.setError("Please Enter Only Alphabets in Subject Name");
                edtname.setFocusable(true);
            } else if (scode.equals("")) {
                edtscode.setError("Please Enter Subject Code");
                edtscode.setFocusable(true);
            } else if (!checkSCODE(scode)) {
                edtscode.setError("Please Enter Only Digits in Your Subject Code");
                edtscode.setFocusable(true);
            } else if (prog_id.equals("0")) {
                Toast.makeText(getActivity(), "Please Select Program", Toast.LENGTH_SHORT).show();
            } else if (sem.equals("0")) {
                Toast.makeText(getActivity(), "Please Select Semester", Toast.LENGTH_SHORT).show();
            } else {
                update_subject();
            }
        }


    }

    private void update_subject() {
        pd = new ProgressDialog(getActivity());
        pd.setMessage("Updating Subject.....");
        pd.setIndeterminate(false);
        pd.setCancelable(false);
        pd.show();


        StringRequest sr = new StringRequest(Request.Method.POST, IPConfig.url + "update_subject_api",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        pd.dismiss();
                        try {
                            JSONObject ja= new JSONObject(s);
                            String result = ja.getString("result");

                            if(result.equals("Subject Updated Successfully")){
                                Toast.makeText(getActivity(), "Subject Updated Successfully", Toast.LENGTH_SHORT).show();
                                openSubjectFragment();
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
                map.put("scode",scode);
                map.put("progid",prog_id);
                map.put("sem",sem);
                map.put("hodid",hodid);
                map.put("subid",subid);
                return map;
            }
        };


        w.add(sr);
    }

    private void openSubjectFragment() {
        Fragment fragment3 = new Hod_View_Subject();

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.addToBackStack(null);
        transaction.replace(R.id.frame, fragment3);
        transaction.commit();
    }



    private boolean checkName(String name) {
        return NAME_PATTERN.matcher(name).matches();
    }



    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch(adapterView.getId()) {
            case R.id.spinner_program:
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(getActivity(), "item = "+item, Toast.LENGTH_SHORT).show();
                if(!item.equals("--Select Program--"))
                {
                    prog_id=Parse_For_View_Program.program_id[i-1].toString();
                }else{
                    prog_id="0";
                }

                break;
            case R.id.spinner_sem:
                String item1 = adapterView.getItemAtPosition(i).toString();
                if(item1.equals("--Select Semester--"))
                {
                    sem="0";
                }else{
                    sem=item1.toString();
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }



    private boolean checkSCODE(String mno) {
        return SCODE_PATTERN.matcher(mno).matches();
    }
}
