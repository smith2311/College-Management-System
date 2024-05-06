package com.example.duias;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

public class Faculty_View_Internal_Marks_Detail_Card extends RecyclerView.Adapter<Faculty_View_Internal_Marks_Detail_Card.MyUi>{
    private String[] marks_id;
    private String[] studid;
    private String[] rno;
    private String[] marks;
    private String[] stud_name;

    public String omarks1;
    private static Context c;
    ProgressDialog pd;
    public Faculty_View_Internal_Marks_Detail_Card(Context c, String[] marks_id, String[] studid, String[] rno, String[] marks, String[] stud_name)
    {
        this.c=c;
        this.marks_id= marks_id;
        this.studid= studid;
        this.rno= rno;
        this.marks= marks;
        this.stud_name= stud_name;

    }

    @NonNull
    @Override
    public Faculty_View_Internal_Marks_Detail_Card.MyUi onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.faculty_view_internal_marks_detail_card,parent,false);
        MyUi m = new MyUi(itemview);
        return m;
    }

    @Override
    public void onBindViewHolder(@NonNull Faculty_View_Internal_Marks_Detail_Card.MyUi holder, int position) {
        holder.tvsid.setText("Student Id: "+studid[position]);
        holder.tvstudname.setText("Student Name: "+stud_name[position]);
        holder.tvrno.setText("Roll No: "+rno[position]);
        holder.edt_omarks.setText(marks[position]);

        holder.btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                omarks1 = holder.edt_omarks.getText().toString();
                //Toast.makeText(view.getContext(), "omarks1 = "+omarks1, Toast.LENGTH_SHORT).show();
                holder.update_marks();
            }
        });
    }

    @Override
    public int getItemCount() {
        return marks_id.length;
    }



    public class MyUi extends RecyclerView.ViewHolder implements View.OnClickListener {

        Button btnupdate;
        TextView tvsid,tvstudname,tvstatus,tvrno;
        EditText edt_omarks;
        public MyUi(@NonNull View itemView) {
            super(itemView);
            tvsid = (TextView) itemView.findViewById(R.id.tvsid);
            tvstudname = (TextView) itemView.findViewById(R.id.tvstudname);
            edt_omarks = (EditText) itemView.findViewById(R.id.edt_omarks);
            tvrno = (TextView) itemView.findViewById(R.id.tvrno);
            btnupdate = (Button) itemView.findViewById(R.id.btnupdate);



        }

        @Override
        public void onClick(View view) {


            if(view == btnupdate)
            {

               // update_marks();
            }


        }

        private void update_marks() {
            pd = new ProgressDialog(c);
            pd.setMessage("Update Student Marks....");
            pd.setIndeterminate(false);
            pd.setCancelable(false);
            pd.show();

            StringRequest sr= new StringRequest(Request.Method.POST, IPConfig.url + "update_student_internal_marks_api",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            pd.dismiss();
                            try {
                                JSONObject ja= new JSONObject(s);
                                String result = ja.getString("result");

                                if(result.equals("Student Marks Updated Successfully")){
                                    Toast.makeText(c, "Student Marks Updated Successfully", Toast.LENGTH_SHORT).show();
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

                    map.put("mid",Parse_For_Student_Internal_Marks.marks_id[getAdapterPosition()]);
                    map.put("studid",Parse_For_Student_Internal_Marks.studid[getAdapterPosition()]);
                    map.put("omarks",omarks1);
                    return map;
                }
            };


            RequestQueue rq = Volley.newRequestQueue(c);
            rq.add(sr);
        }


        private void openPage() {
            Fragment fragment3 = new Faculty_View_Internal_Marks_Detail();
            Bundle args = new Bundle();
            args.putString("mid",Parse_For_Student_Internal_Marks.marks_id[getAdapterPosition()]);
            fragment3.setArguments(args);
            ((Faculty_Dashboard)c).setFragment3(fragment3);
        }
    }
}
