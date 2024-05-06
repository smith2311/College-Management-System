package com.example.duias;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

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

public class Faculty_View_Attendance_Detail_Card extends RecyclerView.Adapter<Faculty_View_Attendance_Detail_Card.MyUi> {
    private String[] attend_id;
    private String[] studid;
    private String[] rno;
    private String[] present_absent;
    private String[] stud_name;

    private static Context c;
    ProgressDialog pd;
    public Faculty_View_Attendance_Detail_Card(Context c, String[] attend_id, String[] studid, String[] rno, String[] present_absent, String[] stud_name)
    {
        this.c=c;
        this.attend_id= attend_id;
        this.studid= studid;
        this.rno= rno;
        this.present_absent= present_absent;
        this.stud_name= stud_name;

    }

    @NonNull
    @Override
    public Faculty_View_Attendance_Detail_Card.MyUi onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.faculty_view_attendance_detail_card,parent,false);
        MyUi m = new MyUi(itemview);
        return m;
    }

    @Override
    public void onBindViewHolder(@NonNull Faculty_View_Attendance_Detail_Card.MyUi holder, int position) {
        holder.tvsid.setText("Student Id: "+studid[position]);
        holder.tvstudname.setText("Student Name: "+stud_name[position]);
        holder.tvrno.setText("Roll No: "+rno[position]);
        if(present_absent[position].equals("P")) {
            holder.tvstatus.setText("PRESENT");
            holder.tvstatus.setTextColor(Color.GREEN);
            holder.btnpresent.setVisibility(View.GONE);
            holder.btnabsent.setVisibility(View.VISIBLE);

        }else{
            holder.tvstatus.setText("ABSENT");
            holder.tvstatus.setTextColor(Color.RED);
            holder.btnpresent.setVisibility(View.VISIBLE);
            holder.btnabsent.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return attend_id.length;
    }

    public class MyUi extends RecyclerView.ViewHolder implements View.OnClickListener {

        Button btnpresent,btnabsent;
        TextView tvsid,tvstudname,tvstatus,tvrno;
        public MyUi(@NonNull View itemView) {
            super(itemView);
            tvsid = (TextView) itemView.findViewById(R.id.tvsid);
            tvstudname = (TextView) itemView.findViewById(R.id.tvstudname);
            tvstatus = (TextView) itemView.findViewById(R.id.tvstatus);
            tvrno = (TextView) itemView.findViewById(R.id.tvrno);
            btnpresent = (Button) itemView.findViewById(R.id.btnpresent);
            btnabsent = (Button) itemView.findViewById(R.id.btnabsent);

            btnabsent.setOnClickListener(this);
            btnpresent.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {


            if(view == btnabsent)
            {
                absent_student();
            }

            if(view == btnpresent)
            {
                present_student();
            }
        }

        private void present_student() {
            pd = new ProgressDialog(c);
            pd.setMessage("Present Student....");
            pd.setIndeterminate(false);
            pd.setCancelable(false);
            pd.show();

            StringRequest sr= new StringRequest(Request.Method.POST, IPConfig.url + "present_student_api",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            pd.dismiss();
                            try {
                                JSONObject ja= new JSONObject(s);
                                String result = ja.getString("result");

                                if(result.equals("Student Present Successfully")){
                                    Toast.makeText(c, "Student Present Successfully", Toast.LENGTH_SHORT).show();
                                    openPage();
                                }else{
                                    Toast.makeText(c, "Check Your Data", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Toast.makeText(c, volleyError.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map map = new HashMap();

                    map.put("attendid",Parse_For_View_Student_Attendance.attend_id[getAdapterPosition()]);
                    map.put("studid",Parse_For_View_Student_Attendance.studid[getAdapterPosition()]);
                    return map;
                }
            };


            RequestQueue rq = Volley.newRequestQueue(c);
            rq.add(sr);
        }

        private void absent_student() {

            pd = new ProgressDialog(c);
            pd.setMessage("Absent Student.....");
            pd.setIndeterminate(false);
            pd.setCancelable(false);
            pd.show();

            StringRequest sr= new StringRequest(Request.Method.POST, IPConfig.url + "absent_student_api",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            pd.dismiss();
                            try {
                                JSONObject ja= new JSONObject(s);
                                String result = ja.getString("result");

                                if(result.equals("Student Absent Successfully")){
                                    Toast.makeText(c, "Student Absent Successfully", Toast.LENGTH_SHORT).show();
                                    openPage();
                                }else{
                                    Toast.makeText(c, "Check Your Data", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Toast.makeText(c, volleyError.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map map = new HashMap();

                    map.put("attendid",Parse_For_View_Student_Attendance.attend_id[getAdapterPosition()]);
                    map.put("studid",Parse_For_View_Student_Attendance.studid[getAdapterPosition()]);
                    return map;
                }
            };


            RequestQueue rq = Volley.newRequestQueue(c);
            rq.add(sr);
        }

        private void openPage() {
            Fragment fragment3 = new Faculty_View_Attendance_Detail();
            Bundle args = new Bundle();
            args.putString("aid",Parse_For_View_Student_Attendance.attend_id[getAdapterPosition()]);
            fragment3.setArguments(args);
            ((Faculty_Dashboard)c).setFragment3(fragment3);
        }
    }
}
