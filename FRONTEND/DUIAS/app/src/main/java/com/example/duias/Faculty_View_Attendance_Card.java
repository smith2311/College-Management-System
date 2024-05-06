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

public class Faculty_View_Attendance_Card extends RecyclerView.Adapter<Faculty_View_Attendance_Card.MyUi> {
    private String[] attend_id;
    private String[] attend_date;
    private String[] faculty_id;
    private String[] faculty_name;
    private String[] lec_no;
    private String[] program_id;
    private String[] program_name;
    private String[] sub_id;
    private String[] sub_name;
    private String[] semester;
    private String[] lec_time;
    private String[] div_id;
    private String[] division;
    private static Context c;
    ProgressDialog pd;
    public Faculty_View_Attendance_Card(Context c, String[] attend_id, String[] attend_date, String[] faculty_id, String[] faculty_name, String[] lec_no, String[] program_id, String[] program_name, String[] sub_id, String[] sub_name, String[] semester, String[] lec_time,String[] div_id,String[] division)
    {
        this.c=c;
        this.attend_id= attend_id;

        this.attend_date= attend_date;
        this.faculty_id= faculty_id;
        this.faculty_name= faculty_name;
        this.lec_no= lec_no;
        this.program_id= program_id;
        this.program_name= program_name;
        this.sub_id= sub_id;
        this.sub_name= sub_name;
        this.semester= semester;

        this.lec_time =lec_time;
        this.div_id =div_id;
        this.division =division;
    }

    @NonNull
    @Override
    public Faculty_View_Attendance_Card.MyUi onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.faculty_view_attendance_card,parent,false);
        MyUi m = new MyUi(itemview);
        return m;
    }

    @Override
    public void onBindViewHolder(@NonNull Faculty_View_Attendance_Card.MyUi holder, int position) {
        holder.tvaid.setText("Attendance Id: "+attend_id[position]);
        holder.tvadate.setText("Attendance Date: "+attend_date[position]);
        holder.tvfname.setText("Faculty Name: "+faculty_name[position]);
        holder.tvlno.setText("Lecture No: "+lec_no[position]);
        holder.tvpname.setText("Program Name"+program_name[position]);
        holder.tvsname.setText("Subject Name: "+sub_name[position]);
        holder.tvsem.setText("Semester: "+semester[position]);
        holder.tvltime.setText("Lecture Time: "+lec_time[position]);
        holder.tvdiv.setText("Division: "+division[position]);


    }

    @Override
    public int getItemCount() {
        return attend_date.length;
    }

    public class MyUi extends RecyclerView.ViewHolder implements View.OnClickListener {

        Button btnview;
        TextView tvaid,tvadate,tvfname,tvlno,tvpname,tvsname,tvsem,tvltime,tvdiv;
        public MyUi(@NonNull View itemView) {
            super(itemView);
            tvaid = (TextView) itemView.findViewById(R.id.tvaid);
            tvadate = (TextView) itemView.findViewById(R.id.tvadate);
            tvfname = (TextView) itemView.findViewById(R.id.tvfname);
            tvlno = (TextView) itemView.findViewById(R.id.tvlno);
            tvpname = (TextView) itemView.findViewById(R.id.tvpname);
            tvsname = (TextView) itemView.findViewById(R.id.tvsname);
            tvsem = (TextView) itemView.findViewById(R.id.tvsem);
            tvltime = (TextView) itemView.findViewById(R.id.tvltime);
            tvdiv = (TextView) itemView.findViewById(R.id.tvdiv);



            btnview = (Button) itemView.findViewById(R.id.btnview);


            btnview.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(view==btnview)
            {
                Fragment fragment3 = new Faculty_View_Attendance_Detail();
                Bundle args = new Bundle();
                args.putString("aid", Parse_For_Faculty_View_Attendance.attend_id[getAdapterPosition()]);

                fragment3.setArguments(args);

                ((Faculty_Dashboard)c).setFragment3(fragment3);
            }
        }
    }
}
