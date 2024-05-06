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

public class Hod_View_Subject_Card extends RecyclerView.Adapter<Hod_View_Subject_Card.MyUi> {
    private String[] sub_id;
    private String[] sub_name;
    private String[] program_id;
    private String[] program_name;
    private String[] sub_code;
    private String[] semester;
    private String[] hod_id;
    private String[] sub_status;

    private static Context c;
    ProgressDialog pd;
    public Hod_View_Subject_Card(Context c, String[] sub_id, String[] sub_name, String[] program_id, String[] program_name, String[] sub_code, String[] semester, String[] hod_id, String[] sub_status)
    {
        this.c=c;
        this.sub_id= sub_id;
        this.sub_name= sub_name;
        this.program_id= program_id;
        this.program_name= program_name;
        this.sub_code= sub_code;
        this.semester= semester;
        this.hod_id= hod_id;
        this.sub_status= sub_status;

    }

    @NonNull
    @Override
    public Hod_View_Subject_Card.MyUi onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.hod_view_subject_card,parent,false);
        MyUi m = new MyUi(itemview);
        return m;
    }

    @Override
    public void onBindViewHolder(@NonNull Hod_View_Subject_Card.MyUi holder, int position) {
        holder.txtsid.setText("SUBJECT ID: "+sub_id[position]);
        holder.txtsname.setText("SUBJECT NAME: "+sub_name[position]);
        holder.txtpname.setText("Program Name: "+program_name[position]);
        holder.txtscode.setText("Subject Code: "+sub_code[position]);
        holder.txtsem.setText("Semester"+semester[position]);

        if(sub_status[position].equals("0")) {
            holder.txtstatus.setText("Status: ACTIVE");
            holder.txtstatus.setTextColor(Color.GREEN);
            holder.btnactive.setVisibility(View.GONE);
            holder.btninactive.setVisibility(View.VISIBLE);

        }else{
            holder.txtstatus.setText("Status: INACTIVE");
            holder.txtstatus.setTextColor(Color.RED);
            holder.btnactive.setVisibility(View.VISIBLE);
            holder.btninactive.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return sub_name.length;
    }

    public class MyUi extends RecyclerView.ViewHolder implements View.OnClickListener {

        Button btnedit,btnactive,btninactive;
        TextView txtsid,txtsname,txtpname,txtscode,txtsem,txtstatus;
        public MyUi(@NonNull View itemView) {
            super(itemView);
            txtsid = (TextView) itemView.findViewById(R.id.tvsid);
            txtsname = (TextView) itemView.findViewById(R.id.tvsname);
            txtpname = (TextView) itemView.findViewById(R.id.tvpname);
            txtscode = (TextView) itemView.findViewById(R.id.tvscode);
            txtsem = (TextView) itemView.findViewById(R.id.tvsem);
            txtstatus = (TextView) itemView.findViewById(R.id.tvstatus);



            btnedit = (Button) itemView.findViewById(R.id.btnedit);
            btnactive = (Button) itemView.findViewById(R.id.btnactive);
            btninactive = (Button) itemView.findViewById(R.id.btninactive);
            btnedit.setOnClickListener(this);
            btnactive.setOnClickListener(this);
            btninactive.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(view==btnedit)
            {
                Fragment fragment3 = new Hod_Edit_Subject();
                Bundle args = new Bundle();
                args.putString("subid", Parse_For_View_Subject.sub_id[getAdapterPosition()]);
                args.putString("subname", Parse_For_View_Subject.sub_name[getAdapterPosition()]);
                args.putString("program_id", Parse_For_View_Subject.program_id[getAdapterPosition()]);
                args.putString("program_name", Parse_For_View_Subject.program_name[getAdapterPosition()]);
                args.putString("sub_code", Parse_For_View_Subject.sub_code[getAdapterPosition()]);
                args.putString("semester", Parse_For_View_Subject.semester[getAdapterPosition()]);

                fragment3.setArguments(args);
                // android.support.v4.app.FragmentTransaction fragmentTransaction3 = context.getSupportFragmentManager().beginTransaction();
                //fragmentTransaction3.replace(R.id.frame, fragment3);
                ((Hod_Dashboard)c).setFragment3(fragment3);
            }

            if(view == btnactive)
            {
                active_subject();
            }

            if(view == btninactive)
            {
                inactive_subject();
            }
        }

        private void active_subject() {
            pd = new ProgressDialog(c);
            pd.setMessage("Active Subject.....");
            pd.setIndeterminate(false);
            pd.setCancelable(false);
            pd.show();

            StringRequest sr= new StringRequest(Request.Method.POST, IPConfig.url + "active_subject",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            pd.dismiss();
                            try {
                                JSONObject ja= new JSONObject(s);
                                String result = ja.getString("result");

                                if(result.equals("Subject Active Successfully")){
                                    Toast.makeText(c, "Subject Active Successfully", Toast.LENGTH_SHORT).show();
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

                    map.put("subid",Parse_For_View_Subject.sub_id[getAdapterPosition()]);

                    return map;
                }
            };


            RequestQueue rq = Volley.newRequestQueue(c);
            rq.add(sr);
        }

        private void inactive_subject() {

            pd = new ProgressDialog(c);
            pd.setMessage("Inactive Subject.....");
            pd.setIndeterminate(false);
            pd.setCancelable(false);
            pd.show();

            StringRequest sr= new StringRequest(Request.Method.POST, IPConfig.url + "inactive_subject",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            pd.dismiss();
                            try {
                                JSONObject ja= new JSONObject(s);
                                String result = ja.getString("result");

                                if(result.equals("Subject Inactive Successfully")){
                                    Toast.makeText(c, "Subject Inactive Successfully", Toast.LENGTH_SHORT).show();
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

                    map.put("subid",Parse_For_View_Subject.sub_id[getAdapterPosition()]);

                    return map;
                }
            };


            RequestQueue rq = Volley.newRequestQueue(c);
            rq.add(sr);
        }

        private void openPage() {
            Fragment fragment3 = new Hod_View_Subject();

            ((Hod_Dashboard)c).setFragment3(fragment3);
        }
    }
}
