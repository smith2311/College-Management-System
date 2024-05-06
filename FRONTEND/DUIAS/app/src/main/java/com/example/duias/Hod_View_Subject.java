package com.example.duias;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

public class Hod_View_Subject extends Fragment {
    RecyclerView SubView;
    RecyclerView.Adapter ASubView;
    RequestQueue w,x;
    TextView txtresult;
    String hodid="0";
    ProgressDialog pDialog;
    Parse_For_View_Subject pd1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.hod_view_subject,container,false);
        SubView = (RecyclerView) v.findViewById(R.id.recyclerView);
        txtresult = (TextView) v.findViewById(R.id.txt3);
        SubView.setHasFixedSize(true);

        w = Volley.newRequestQueue(getActivity());
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        SubView.setLayoutManager(llm);

        load_hod();
        return v;
    }

    private void load_hod() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        hodid = sp.getString("hodid", "anon");
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Fetching Subject Detail.....");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();

        StringRequest sr = new StringRequest(Request.Method.GET, IPConfig.url + "hod_fetch_subject_detail/"+hodid,
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
                                Toast.makeText(getActivity(), "No Subject Found", Toast.LENGTH_SHORT).show();
                                SubView.getRecycledViewPool().clear();
                                SubView.setAdapter(null);
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
        pd1 = new Parse_For_View_Subject(s);
        pd1.parseJSON();

        ASubView = new Hod_View_Subject_Card(getActivity(),Parse_For_View_Subject.sub_id,Parse_For_View_Subject.sub_name,Parse_For_View_Subject.program_id,Parse_For_View_Subject.program_name,Parse_For_View_Subject.sub_code,Parse_For_View_Subject.semester,Parse_For_View_Subject.hod_id,Parse_For_View_Subject.sub_status);
        SubView.setAdapter(ASubView);
    }
}
