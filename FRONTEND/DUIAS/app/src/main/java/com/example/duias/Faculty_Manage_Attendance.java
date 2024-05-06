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

public class Faculty_Manage_Attendance extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private static final Pattern NAME_PATTERN = Pattern.compile("[a-zA-Z ]{3,40}");
    private static final Pattern SCODE_PATTERN = Pattern.compile("[0-9]{1,5}");


    EditText edtltime;
    String name,scode,prog_id="0",sem="0",fid,divid="0",subid="0",ltime,lno="0";



    Spinner spinprog,spinsem,spinlno,spinsub,spindiv;
    Button btnsave,btnview;
    RequestQueue w;
    ProgressDialog pDialog,pd;
    Parse_For_View_Program pd1;
    Parse_For_View_Division pd2;
    Parse_For_View_Faculty_Subject pd3;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.faculty_manage_attendance,container,false);

        edtltime = (EditText) v.findViewById(R.id.editText_ltime);

        spinlno = (Spinner) v.findViewById(R.id.spinner_lno);
        spinprog = (Spinner) v.findViewById(R.id.spinner_program);
        spinsem = (Spinner) v.findViewById(R.id.spinner_sem);
        spindiv = (Spinner) v.findViewById(R.id.spinner_div);
        spinsub = (Spinner) v.findViewById(R.id.spinner_sub);
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


        List<String> sellno = new ArrayList<String>();
        sellno.add("--Select Lecture No--");
        sellno.add("1");
        sellno.add("2");
        sellno.add("3");
        sellno.add("4");
        sellno.add("5");
        sellno.add("6");
        sellno.add("7");
        sellno.add("8");
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, sellno);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinlno.setAdapter(dataAdapter2);

        btnsave = (Button) v.findViewById(R.id.button_save);
        btnview = (Button) v.findViewById(R.id.button_view);

        btnview.setOnClickListener(this);

        btnsave.setOnClickListener(this);
        w = Volley.newRequestQueue(getActivity());



        loadprogram();
        spinlno.setOnItemSelectedListener(this);
        spindiv.setOnItemSelectedListener(this);
        spinsem.setOnItemSelectedListener(this);
        spinprog.setOnItemSelectedListener(this);
        spinsub.setOnItemSelectedListener(this);
        return v;
    }

    private void loadprogram() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        fid = sp.getString("fid", "anon");
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Loading Program.....");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();


        StringRequest strreq =new StringRequest(Request.Method.GET, IPConfig.url + "faculty_fetch_program_api/"+fid,
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

    }

    @Override
    public void onClick(View view) {


        if(view==btnsave){
            ltime = edtltime.getText().toString().trim();

            if(lno.equals("0")){
                Toast.makeText(getActivity(), "Please Select Lecture No", Toast.LENGTH_SHORT).show();
            }else if(prog_id.equals("0")){
                Toast.makeText(getActivity(), "Please Select Program", Toast.LENGTH_SHORT).show();
            }else if(sem.equals("0")){
                Toast.makeText(getActivity(), "Please Select Semester", Toast.LENGTH_SHORT).show();
            }else if(divid.equals("0")){
                Toast.makeText(getActivity(), "Please Select Divison", Toast.LENGTH_SHORT).show();
            }else if(subid.equals("0")){
                Toast.makeText(getActivity(), "Please Select Subject", Toast.LENGTH_SHORT).show();
            }
            else if(ltime.equals("")){
                edtltime.setError("Please Enter Lecture Time");
                edtltime.setFocusable(true);
            }
            else{
                add_attend();
            }
        }

        if(view==btnview){
            Fragment fragment3 = new Faculty_View_Attendance();

            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.addToBackStack(null);
            transaction.replace(R.id.frame, fragment3);
            transaction.commit();
        }

    }

    private void add_attend() {
        pd = new ProgressDialog(getActivity());
        pd.setMessage("Adding Attendance.....");
        pd.setIndeterminate(false);
        pd.setCancelable(false);
        pd.show();


        StringRequest sr = new StringRequest(Request.Method.POST, IPConfig.url + "add_attendance_api",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        pd.dismiss();
                        try {
                            JSONObject ja= new JSONObject(s);
                            String result = ja.getString("status");
                            Toast.makeText(getActivity(), "result = "+result, Toast.LENGTH_SHORT).show();
                            if(result.equals("Attendance Added Succesfully")){
                                Toast.makeText(getActivity(), "Attendance Added Succesfully", Toast.LENGTH_SHORT).show();
                                openstudattend(ja.getString("aid"));

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
                map.put("lno",lno);
                map.put("progid",prog_id);
                map.put("sem",sem);
                map.put("divid",divid);
                map.put("subid",subid);
                map.put("fid",fid);
                map.put("ltime",ltime);
                return map;
            }
        };


        w.add(sr);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch(adapterView.getId()) {
            case R.id.spinner_lno:
                String item5 = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(getActivity(), "item = "+item5, Toast.LENGTH_SHORT).show();
                if(item5.equals("--Select Lecture No--"))
                {
                    lno="0";
                }else{
                    lno=item5.toString();
                }

                break;
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
                if(sem.equals("0") && prog_id.equals("0"))
                {
                    Toast.makeText(getActivity(), "Please select Semester And Program Both", Toast.LENGTH_SHORT).show();
                }else{
                    load_sub_div();
                }
                break;
            case R.id.spinner_div:
                String item3 = adapterView.getItemAtPosition(i).toString();
                if(!item3.equals("--Select Division--"))
                {
                    divid=Parse_For_View_Division.div_id[i-1].toString();
                }else{
                    divid="0";
                }

                break;
            case R.id.spinner_sub:
                String item4 = adapterView.getItemAtPosition(i).toString();
                if(!item4.equals("--Select Subject--"))
                {
                    subid=Parse_For_View_Faculty_Subject.sub_id[i-1].toString();
                }else{
                    subid="0";
                }

                break;
        }
    }

    private void load_sub_div() {
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Loading Division And Subject.....");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();


        StringRequest strreq =new StringRequest(Request.Method.GET, IPConfig.url + "faculty_fetch_div_sub_api/"+prog_id+"/"+sem+"/"+fid,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        pDialog.dismiss();
                        try {
                            JSONObject ja = new JSONObject(s);
                            String Status = ja.getString("status");
                            Toast.makeText(getActivity(), "status =  "+s, Toast.LENGTH_SHORT).show();
                            if (Status.equals("success")) {
                                showJSON4(s);
                            } else {
                                Toast.makeText(getActivity(), "No Subject And Division Found", Toast.LENGTH_LONG).show();
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

    private void showJSON4(String s) {
        pd2 = new Parse_For_View_Division(s);
        pd2.parseJSON();

        JSONObject jsonObject=null;
        List<String> seldiv = new ArrayList<String>();
        seldiv.add("--Select Division--");

        for (int i = 0; i < Parse_For_View_Division.div_id.length; i++) {
            seldiv.add(Parse_For_View_Division.division[i].toString());
        }
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, seldiv);
        // Drop down layout style - list view with radio button
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spindiv.setAdapter(dataAdapter1);


        pd3 = new Parse_For_View_Faculty_Subject(s);
        pd3.parseJSON();


        List<String> selsub = new ArrayList<String>();
        selsub.add("--Select Subject--");

        for (int i = 0; i < Parse_For_View_Faculty_Subject.sub_id.length; i++) {
            selsub.add(Parse_For_View_Faculty_Subject.sub_name[i].toString());
        }
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, selsub);
        // Drop down layout style - list view with radio button
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinsub.setAdapter(dataAdapter2);
    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void openstudattend(String aid) {
        Fragment fragment3 = new Faculty_View_Attendance_Detail();
        Bundle args = new Bundle();
        args.putString("aid",aid);
        fragment3.setArguments(args);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.addToBackStack(null);
        transaction.replace(R.id.frame, fragment3);
        transaction.commit();
    }

    private boolean checkName(String name) {
        return NAME_PATTERN.matcher(name).matches();
    }



    private boolean checkSCODE(String mno) {
        return SCODE_PATTERN.matcher(mno).matches();
    }
}
