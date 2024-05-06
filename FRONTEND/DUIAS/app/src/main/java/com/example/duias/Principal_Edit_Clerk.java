package com.example.duias;

import android.app.ProgressDialog;
import android.os.Bundle;
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

public class Principal_Edit_Clerk extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private static final Pattern NAME_PATTERN = Pattern.compile("[a-zA-Z ]{3,40}");
    private static final Pattern MNO_PATTERN = Pattern.compile("[0-9]{10,10}");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("[a-zA-Z0-9+._%-+]{1,100}" + "@"
            + "[a-zA-Z0-9][a-zA-Z0-9-]{0,10}" + "(" + "."
            + "[a-zA-Z0-9][a-zA-Z0-9-]{0,20}" +
            ")+");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("[a-zA-Z0-9+_.]{6,10}");
    EditText edtname,edtadd,edtcity,edtmno,edtemail,edtpwd,edtsans;
    String name,add,city,mno,email,pwd,sans,gender="x",sque="0",clerkid;

    RadioButton rbtnmale,rbtnfemale;

    Spinner spinsque;
    Button btnupdate;
    RequestQueue w;
    ProgressDialog pd,pDialog;
    Parse_For_View_Dept pd1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.principal_edit_clerk,container,false);

        clerkid = getArguments().getString("clerkid");
        name = getArguments().getString("clerkname");
        add = getArguments().getString("address");
        city = getArguments().getString("city");
        mno = getArguments().getString("mno");
        email = getArguments().getString("email");
        pwd = getArguments().getString("pwd");

        sque = getArguments().getString("sque");
        sans = getArguments().getString("sans");
        gender = getArguments().getString("gender");


        edtname = (EditText) v.findViewById(R.id.editText_name);
        edtadd = (EditText) v.findViewById(R.id.editText_address);
        edtcity = (EditText) v.findViewById(R.id.editText_city);
        edtmno = (EditText) v.findViewById(R.id.editText_mno);
        edtemail = (EditText) v.findViewById(R.id.editText_email);
        edtpwd = (EditText) v.findViewById(R.id.editText_pwd);
        edtsans = (EditText) v.findViewById(R.id.editText_sans);


        spinsque = (Spinner) v.findViewById(R.id.spinner_sque);
        btnupdate = (Button) v.findViewById(R.id.button_update);

        List<String> selsque = new ArrayList<String>();
        selsque.add("--Select Security Question--");
        selsque.add("First School Name");
        selsque.add("First Pet Name");
        selsque.add("Favourite Food");
        selsque.add("Favourite Color");
        selsque.add("Favourite Sports");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, selsque);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinsque.setAdapter(dataAdapter);


        for(int i=0;i< spinsque.getCount() ; i++)
        {
            if(spinsque.getItemAtPosition(i).equals(sque))
            {
                spinsque.setSelection(i);
            }
        }

        edtname.setText(name);
        edtadd.setText(add);
        edtcity.setText(city);
        edtmno.setText(mno);
        edtemail.setText(email);
        edtpwd.setText(pwd);
        edtsans.setText(sans);

        rbtnmale = (RadioButton) v.findViewById(R.id.radioButton1);
        rbtnfemale = (RadioButton) v.findViewById(R.id.radioButton2);

        if(gender.equals("MALE"))
        {
            rbtnmale.setChecked(true);
        }else if(gender.equals("FEMALE")){
            rbtnfemale.setChecked(true);
        }
        btnupdate.setOnClickListener(this);
        w = Volley.newRequestQueue(getActivity());
        rbtnmale.setOnClickListener(this);
        rbtnfemale.setOnClickListener(this);



        spinsque.setOnItemSelectedListener(this);

        return v;
    }




    @Override
    public void onClick(View view) {
        if(view == btnupdate){
            name = edtname.getText().toString().trim();
            add = edtadd.getText().toString().trim();
            city = edtcity.getText().toString().trim();
            mno = edtmno.getText().toString().trim();
            email = edtemail.getText().toString().trim();
            pwd = edtpwd.getText().toString().trim();
            sans = edtsans.getText().toString().trim();



            if(name.equals("")){
                edtname.setError("Please Enter Hod Name");
                edtname.setFocusable(true);
            }else if(!checkName(name)){
                edtname.setError("Please Enter Only Alphabets in Hod Name");
                edtname.setFocusable(true);
            }else if(add.equals("")){
                edtadd.setError("Please Enter Hod Address");
                edtadd.setFocusable(true);
            }else if(city.equals("")){
                edtcity.setError("Please Enter City");
                edtcity.setFocusable(true);
            }else if(!checkName(city)){
                edtcity.setError("Please Enter Only Alphabets in City Name");
                edtcity.setFocusable(true);
            }
            else if(mno.equals("")){
                edtmno.setError("Please Enter Hod Mobile No");
                edtmno.setFocusable(true);
            }else if(!checkMNO(mno)){
                edtmno.setError("Please Enter Only Digits in Your Mobile No");
                edtmno.setFocusable(true);
            }else if(email.equals("")){
                edtemail.setError("Please Enter Hod Email ID");
                edtemail.setFocusable(true);
            }else if(!checkEMAIL(email)){
                edtemail.setError("Please Enter Valid Email ID");
                edtemail.setFocusable(true);
            }else if(pwd.equals("")){
                edtpwd.setError("Please Enter Hod Password");
                edtpwd.setFocusable(true);
            }else if(!checkPWD(pwd)){
                edtpwd.setError("Please Enter Proper Password");
                edtpwd.setFocusable(true);
            }else if(sque.equals("0")){
                Toast.makeText(getActivity(), "Please Select Security Question", Toast.LENGTH_SHORT).show();
            }else if(sans.equals("")){
                edtsans.setError("Please Enter Security Answer");
                edtsans.setFocusable(true);
            }else if(gender.equals("x")){
                Toast.makeText(getActivity(), "Please Select Gender", Toast.LENGTH_SHORT).show();
            }
            else{
                update_clerk();
            }
        }


        if(view==rbtnmale){
            gender = "MALE";
            rbtnmale.setChecked(true);
            rbtnfemale.setChecked(false);
        }

        if(view==rbtnfemale){
            gender = "FEMALE";
            rbtnmale.setChecked(false);
            rbtnfemale.setChecked(true);
        }
    }

    private void update_clerk() {
        pd = new ProgressDialog(getActivity());
        pd.setMessage("Updating Clerk Detail.....");
        pd.setIndeterminate(false);
        pd.setCancelable(false);
        pd.show();


        StringRequest sr = new StringRequest(Request.Method.POST, IPConfig.url + "update_clerk_api",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        pd.dismiss();
                        try {
                            JSONObject ja= new JSONObject(s);
                            String result = ja.getString("result");

                            if(result.equals("Clerk Updated Successfully")){
                                Toast.makeText(getActivity(), "Clerk Updated Successfully", Toast.LENGTH_SHORT).show();
                                openHodFragment();
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
                map.put("clerkid",clerkid);
                map.put("name",name);
                map.put("add",add);
                map.put("city",city);
                map.put("mno",mno);
                map.put("email",email);
                map.put("pwd",pwd);

                map.put("sque",sque);
                map.put("sans",sans);
                map.put("gender",gender);
                return map;
            }
        };


        w.add(sr);
    }

    private void openHodFragment() {
        Fragment fragment3 = new Principal_View_Clerk();

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.addToBackStack(null);
        transaction.replace(R.id.frame, fragment3);
        transaction.commit();
    }



    private boolean checkName(String name) {
        return NAME_PATTERN.matcher(name).matches();
    }

    private boolean checkPWD(String pwd) {
        return PASSWORD_PATTERN.matcher(pwd).matches();
    }

    private boolean checkEMAIL(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    private boolean checkMNO(String mno) {
        return MNO_PATTERN.matcher(mno).matches();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch(adapterView.getId()) {

            case R.id.spinner_sque:
                String item1 = adapterView.getItemAtPosition(i).toString();
                if(item1.equals("--Select Security Question--"))
                {
                    sque="0";
                }else{
                    sque=item1.toString();
                }
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
