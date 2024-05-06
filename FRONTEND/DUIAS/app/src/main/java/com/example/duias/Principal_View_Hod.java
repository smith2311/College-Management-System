package com.example.duias;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Principal_View_Hod extends Fragment {
    RecyclerView HodView;
    RecyclerView.Adapter AHodView;
    RequestQueue w,x;
    TextView txtresult;
    ProgressDialog pDialog;
    Parse_For_View_Hod pd1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.principal_view_hod,container,false);
        HodView = (RecyclerView) v.findViewById(R.id.recyclerView);
        txtresult = (TextView) v.findViewById(R.id.txt3);
        HodView.setHasFixedSize(true);

        w = Volley.newRequestQueue(getActivity());
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        HodView.setLayoutManager(llm);

        load_hod();
        return v;
    }

    private void load_hod() {

        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Fetching Hod Detail.....");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();

        StringRequest sr = new StringRequest(Request.Method.GET, IPConfig.url + "principal_fetch_hod_detail",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        pDialog.dismiss();
                        try {
                            JSONObject ja = new JSONObject(s);
                            String status = ja.getString("status");
                            if(status.equals("success")){
                                showJSON(s);
                            }else{
                                Toast.makeText(getActivity(), "No Hod Found", Toast.LENGTH_SHORT).show();
                                HodView.getRecycledViewPool().clear();
                                HodView.setAdapter(null);
                                txtresult.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getActivity(), volleyError.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        w.add(sr);
    }

    private void showJSON(String s) {
        pd1 = new Parse_For_View_Hod(s);
        pd1.parseJSON();

        AHodView = new Principal_View_Hod_Card(getActivity(),Parse_For_View_Hod.hod_id,Parse_For_View_Hod.hod_name,Parse_For_View_Hod.address,Parse_For_View_Hod.city,Parse_For_View_Hod.mobile_no,Parse_For_View_Hod.email_id,Parse_For_View_Hod.pwd,Parse_For_View_Hod.gender,Parse_For_View_Hod.security_que,Parse_For_View_Hod.security_ans,Parse_For_View_Hod.dep_id,Parse_For_View_Hod.dep_name,Parse_For_View_Hod.hod_status);
        HodView.setAdapter(AHodView);
    }
}
