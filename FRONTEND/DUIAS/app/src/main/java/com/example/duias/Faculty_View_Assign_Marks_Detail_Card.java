package com.example.duias;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
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

public class Faculty_View_Assign_Marks_Detail_Card extends RecyclerView.Adapter<Faculty_View_Assign_Marks_Detail_Card.MyUi>{
    private String[] assign_detail_id;
    private String[] assign_id;
    private String[] studid;
    private String[] rno;
    private String[] assign_no;
    private String[] assign_received;
    private String[] stud_name;
    private String[] marks;

    public String omarks1,rn="N";
    private static Context c;
    ProgressDialog pd;
    public Faculty_View_Assign_Marks_Detail_Card(Context c, String[] assign_detail_id,String[] assign_id, String[] studid, String[] rno,String[] assign_no,String[] assign_received, String[] stud_name,String[] marks)
    {
        this.c=c;
        this.assign_detail_id= assign_detail_id;
        this.assign_id= assign_id;
        this.studid= studid;
        this.rno= rno;
        this.assign_no= assign_no;
        this.assign_received= assign_received;

        this.stud_name= stud_name;
        this.marks= marks;
    }

    @NonNull
    @Override
    public Faculty_View_Assign_Marks_Detail_Card.MyUi onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.faculty_view_assign_marks_detail_card,parent,false);
        MyUi m = new MyUi(itemview);
        return m;
    }

    @Override
    public void onBindViewHolder(@NonNull Faculty_View_Assign_Marks_Detail_Card.MyUi holder, int position) {
        holder.tvsid.setText("Student Id: "+studid[position]);
        holder.tvstudname.setText("Student Name: "+stud_name[position]);
        holder.tvrno.setText("Roll No: "+rno[position]);
        holder.tvano.setText("Assingment No: "+assign_no[position]);
        holder.edt_omarks.setText(marks[position]);
        if(assign_received[position].equals("N"))
        {
            holder.chkran.setChecked(false);

        }else{
            holder.chkran.setChecked(true);

        }
        holder.btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.chkran.isChecked())
                {
                    rn = "Y";
                }else{
                    rn = "N";
                }
                omarks1 = holder.edt_omarks.getText().toString();
                //Toast.makeText(view.getContext(), "omarks1 = "+omarks1, Toast.LENGTH_SHORT).show();
                holder.update_marks();
            }
        });
    }

    @Override
    public int getItemCount() {
        return assign_detail_id.length;
    }



    public class MyUi extends RecyclerView.ViewHolder implements View.OnClickListener {

        Button btnupdate;
        CheckBox chkran;
        TextView tvsid,tvstudname,tvstatus,tvrno,tvano;
        EditText edt_omarks;
        public MyUi(@NonNull View itemView) {
            super(itemView);
            tvsid = (TextView) itemView.findViewById(R.id.tvsid);
            tvstudname = (TextView) itemView.findViewById(R.id.tvstudname);
            edt_omarks = (EditText) itemView.findViewById(R.id.edt_omarks);
            chkran = (CheckBox) itemView.findViewById(R.id.chkran);
            tvrno = (TextView) itemView.findViewById(R.id.tvrno);
            tvano = (TextView) itemView.findViewById(R.id.tvano);
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
            pd.setMessage("Update Student Assignment Marks....");
            pd.setIndeterminate(false);
            pd.setCancelable(false);
            pd.show();

            StringRequest sr= new StringRequest(Request.Method.POST, IPConfig.url + "update_student_assignment_marks_api",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            pd.dismiss();
                            try {
                                JSONObject ja= new JSONObject(s);
                                String result = ja.getString("result");

                                if(result.equals("Student Assignment Marks Updated Successfully")){
                                    Toast.makeText(c, "Student Assignment Marks Updated Successfully", Toast.LENGTH_SHORT).show();
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

                    map.put("adid",Parse_For_Student_Assignment_Marks_Detail.assign_detail_id[getAdapterPosition()]);
                    map.put("studid",Parse_For_Student_Assignment_Marks_Detail.studid[getAdapterPosition()]);
                    map.put("rn",rn);
                    map.put("omarks",omarks1);
                    return map;
                }
            };


            RequestQueue rq = Volley.newRequestQueue(c);
            rq.add(sr);
        }


        private void openPage() {
            Fragment fragment3 = new Faculty_View_Assign_Marks_Detail();
            Bundle args = new Bundle();
            args.putString("aid",Parse_For_Student_Assignment_Marks_Detail.assign_id[getAdapterPosition()]);
            fragment3.setArguments(args);
            ((Faculty_Dashboard)c).setFragment3(fragment3);
        }
    }
}
