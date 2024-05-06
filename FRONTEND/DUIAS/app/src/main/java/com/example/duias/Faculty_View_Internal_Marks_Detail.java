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

public class Faculty_View_Internal_Marks_Detail extends Fragment {
    RecyclerView StudView;
    RecyclerView.Adapter AStudView;
    RequestQueue w,x;
    TextView txtresult;
    String mid="";
    ProgressDialog pDialog;
    Parse_For_Student_Internal_Marks pd1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.faculty_view_internal_marks_detail,container,false);
        mid = getArguments().getString("mid");
        StudView = (RecyclerView) v.findViewById(R.id.recyclerView);
        txtresult = (TextView) v.findViewById(R.id.txt3);
        StudView.setHasFixedSize(true);

        w = Volley.newRequestQueue(getActivity());
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        StudView.setLayoutManager(llm);

        load_hod();
        return v;
    }

    private void load_hod() {

        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Fetching Student Marks Detail.....");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();

        StringRequest sr = new StringRequest(Request.Method.GET, IPConfig.url + "faculty_view_student_marks_detail_api/"+mid,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        pDialog.dismiss();
                        try {
                            JSONObject ja = new JSONObject(s);
                            String status = ja.getString("status");
                            if(status.equals("Success")){
                                showJSON(s);
                            }else{
                                Toast.makeText(getActivity(), "No Internal Marks Found", Toast.LENGTH_SHORT).show();
                                StudView.getRecycledViewPool().clear();
                                StudView.setAdapter(null);
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
        pd1 = new Parse_For_Student_Internal_Marks(s);
        pd1.parseJSON();

        AStudView = new Faculty_View_Internal_Marks_Detail_Card(getActivity(),Parse_For_Student_Internal_Marks.marks_id,Parse_For_Student_Internal_Marks.studid,Parse_For_Student_Internal_Marks.rno,Parse_For_Student_Internal_Marks.marks,Parse_For_Student_Internal_Marks.stud_name);
        StudView.setAdapter(AStudView);
    }
}
