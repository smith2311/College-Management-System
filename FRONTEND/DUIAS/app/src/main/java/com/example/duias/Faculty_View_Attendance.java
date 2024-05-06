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

public class Faculty_View_Attendance extends Fragment {
    RecyclerView AttendView;
    RecyclerView.Adapter AAttendView;
    RequestQueue w,x;
    TextView txtresult;
    ProgressDialog pDialog;
    Parse_For_Faculty_View_Attendance pd1;
    String fid;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.faculty_view_attendance,container,false);
        AttendView = (RecyclerView) v.findViewById(R.id.recyclerView);
        txtresult = (TextView) v.findViewById(R.id.txt3);
        AttendView.setHasFixedSize(true);

        w = Volley.newRequestQueue(getActivity());
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        AttendView.setLayoutManager(llm);

        load_attendance();
        return v;
    }

    private void load_attendance() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        fid = sp.getString("fid", "anon");
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Fetching Attendance Detail.....");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();

        StringRequest sr = new StringRequest(Request.Method.GET, IPConfig.url + "faculty_fetch_attend_detail/"+fid,
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
                                Toast.makeText(getActivity(), "No Attendance Found", Toast.LENGTH_SHORT).show();
                                AttendView.getRecycledViewPool().clear();
                                AttendView.setAdapter(null);
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
        pd1 = new Parse_For_Faculty_View_Attendance(s);
        pd1.parseJSON();

        AAttendView = new Faculty_View_Attendance_Card(getActivity(),Parse_For_Faculty_View_Attendance.attend_id,Parse_For_Faculty_View_Attendance.attend_date,Parse_For_Faculty_View_Attendance.faculty_id,Parse_For_Faculty_View_Attendance.faculty_name,Parse_For_Faculty_View_Attendance.lec_no,Parse_For_Faculty_View_Attendance.program_id,Parse_For_Faculty_View_Attendance.program_name,Parse_For_Faculty_View_Attendance.sub_id,Parse_For_Faculty_View_Attendance.sub_name,Parse_For_Faculty_View_Attendance.semester,Parse_For_Faculty_View_Attendance.lec_time,Parse_For_Faculty_View_Attendance.div_id,Parse_For_Faculty_View_Attendance.division);
        AttendView.setAdapter(AAttendView);
    }
}
