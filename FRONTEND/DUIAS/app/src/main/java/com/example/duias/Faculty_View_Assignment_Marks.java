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

public class Faculty_View_Assignment_Marks extends Fragment {
    RecyclerView AssignView;
    RecyclerView.Adapter AassignView;
    RequestQueue w,x;
    TextView txtresult;
    ProgressDialog pDialog;
    Parse_For_Faculty_View_Assign_Marks pd1;
    String fid;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.faculty_view_internal_marks,container,false);
        AssignView = (RecyclerView) v.findViewById(R.id.recyclerView);
        txtresult = (TextView) v.findViewById(R.id.txt3);
        AssignView.setHasFixedSize(true);

        w = Volley.newRequestQueue(getActivity());
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        AssignView.setLayoutManager(llm);

        load_attendance();
        return v;
    }

    private void load_attendance() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        fid = sp.getString("fid", "anon");
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Fetching Internal Marks Detail.....");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();

        StringRequest sr = new StringRequest(Request.Method.GET, IPConfig.url + "faculty_fetch_assign_marks_api/"+fid,
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
                                Toast.makeText(getActivity(), "No Marks Found", Toast.LENGTH_SHORT).show();
                                AssignView.getRecycledViewPool().clear();
                                AssignView.setAdapter(null);
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
        pd1 = new Parse_For_Faculty_View_Assign_Marks(s);
        pd1.parseJSON();

        AassignView = new Faculty_View_Assign_Marks_Card(getActivity(),Parse_For_Faculty_View_Assign_Marks.assign_id,Parse_For_Faculty_View_Assign_Marks.program_id,Parse_For_Faculty_View_Assign_Marks.program_name,Parse_For_Faculty_View_Assign_Marks.faculty_id,Parse_For_Faculty_View_Assign_Marks.faculty_name,Parse_For_Faculty_View_Assign_Marks.sub_id,Parse_For_Faculty_View_Assign_Marks.sub_name,Parse_For_Faculty_View_Assign_Marks.semester,Parse_For_Faculty_View_Assign_Marks.div_id,Parse_For_Faculty_View_Assign_Marks.division,Parse_For_Faculty_View_Assign_Marks.no_of_assign);
        AssignView.setAdapter(AassignView);
    }
}
