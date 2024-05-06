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

public class Principal_View_Hod_Card extends RecyclerView.Adapter<Principal_View_Hod_Card.MyUi> {
    private String[] hod_id;
    private String[] hod_name;
    private String[] address;
    private String[] city;
    private String[] mobile_no;
    private String[] email_id;
    private String[] pwd;
    private String[] gender;
    private String[] security_que;
    private String[] security_ans;

    private String[] did;
    private String[] dname;
    private String[] status;
    private static Context c;
    ProgressDialog pd;
    public Principal_View_Hod_Card(Context c, String[] hod_id, String[] hod_name,String[] address, String[] city,String[] mobile_no, String[] email_id, String[] pwd, String[] gender, String[] security_que, String[] security_ans,String[] did, String[] dname, String[] status)
    {
        this.c=c;
        this.hod_id= hod_id;

        this.hod_name= hod_name;
        this.address= address;
        this.city= city;
        this.mobile_no= mobile_no;
        this.email_id= email_id;
        this.pwd= pwd;
        this.gender= gender;
        this.security_que= security_que;
        this.security_ans= security_ans;
        this.did= did;
        this.dname =dname;
        this.status =status;
    }

    @NonNull
    @Override
    public Principal_View_Hod_Card.MyUi onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.principal_view_hod_card,parent,false);
        MyUi m = new MyUi(itemview);
        return m;
    }

    @Override
    public void onBindViewHolder(@NonNull Principal_View_Hod_Card.MyUi holder, int position) {
        holder.txthodid.setText("HOD Id: "+hod_id[position]);
        holder.txthname.setText("HOD NAME: "+hod_name[position]);
        holder.txtadd.setText("Address: "+address[position]);
        holder.txtcity.setText("City: "+city[position]);
        holder.txtmno.setText("Mobile No: "+mobile_no[position]);
        holder.txtemail.setText("Email: "+email_id[position]);
        holder.txtgender.setText("Gender: "+gender[position]);
        holder.txtsque.setText("Security Question: "+security_que[position]);
        holder.txtsans.setText("Security Answer: "+security_ans[position]);
        holder.txtdname.setText("Department Name: "+dname[position]);
        if(status[position].equals("0")) {
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
        return hod_name.length;
    }

    public class MyUi extends RecyclerView.ViewHolder implements View.OnClickListener {

        Button btnedit,btnactive,btninactive;
        TextView txthodid,txthname,txtadd,txtcity,txtmno,txtemail,txtgender,txtsque,txtsans,txtdname,txtstatus;
        public MyUi(@NonNull View itemView) {
            super(itemView);
            txthodid = (TextView) itemView.findViewById(R.id.tvhodid);
            txthname = (TextView) itemView.findViewById(R.id.tvhname);
            txtadd = (TextView) itemView.findViewById(R.id.tvadd);
            txtcity = (TextView) itemView.findViewById(R.id.tvcity);
            txtmno = (TextView) itemView.findViewById(R.id.tvmno);
            txtemail = (TextView) itemView.findViewById(R.id.tvemail);
            txtgender = (TextView) itemView.findViewById(R.id.tvgender);
            txtsque = (TextView) itemView.findViewById(R.id.tvsque);
            txtsans = (TextView) itemView.findViewById(R.id.tvsans);

            txtdname = (TextView) itemView.findViewById(R.id.tvdname);
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
                Fragment fragment3 = new Principal_Edit_Hod();
                Bundle args = new Bundle();
                args.putString("hodid", Parse_For_View_Hod.hod_id[getAdapterPosition()]);
                args.putString("hodname", Parse_For_View_Hod.hod_name[getAdapterPosition()]);
                args.putString("address", Parse_For_View_Hod.address[getAdapterPosition()]);
                args.putString("city", Parse_For_View_Hod.city[getAdapterPosition()]);
                args.putString("mno", Parse_For_View_Hod.mobile_no[getAdapterPosition()]);
                args.putString("email", Parse_For_View_Hod.email_id[getAdapterPosition()]);
                args.putString("pwd", Parse_For_View_Hod.pwd[getAdapterPosition()]);

                args.putString("dep_id", Parse_For_View_Hod.dep_id[getAdapterPosition()]);
                args.putString("dep_name", Parse_For_View_Hod.dep_name[getAdapterPosition()]);
                args.putString("sque", Parse_For_View_Hod.security_que[getAdapterPosition()]);
                args.putString("sans", Parse_For_View_Hod.security_ans[getAdapterPosition()]);
                args.putString("gender", Parse_For_View_Hod.gender[getAdapterPosition()]);
                fragment3.setArguments(args);
                // android.support.v4.app.FragmentTransaction fragmentTransaction3 = context.getSupportFragmentManager().beginTransaction();
                //fragmentTransaction3.replace(R.id.frame, fragment3);
                ((Principal_Dashboard)c).setFragment3(fragment3);
            }

            if(view == btnactive)
            {
                active_hod();
            }

            if(view == btninactive)
            {
                inactive_hod();
            }
        }

        private void active_hod() {
            pd = new ProgressDialog(c);
            pd.setMessage("Active Department.....");
            pd.setIndeterminate(false);
            pd.setCancelable(false);
            pd.show();

            StringRequest sr= new StringRequest(Request.Method.POST, IPConfig.url + "active_hod",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            pd.dismiss();
                            try {
                                JSONObject ja= new JSONObject(s);
                                String result = ja.getString("result");

                                if(result.equals("Hod Active Successfully")){
                                    Toast.makeText(c, "Hod Active Successfully", Toast.LENGTH_SHORT).show();
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

                    map.put("hodid",Parse_For_View_Hod.hod_id[getAdapterPosition()]);

                    return map;
                }
            };


            RequestQueue rq = Volley.newRequestQueue(c);
            rq.add(sr);
        }

        private void inactive_hod() {

            pd = new ProgressDialog(c);
            pd.setMessage("Inactive HOD.....");
            pd.setIndeterminate(false);
            pd.setCancelable(false);
            pd.show();

            StringRequest sr= new StringRequest(Request.Method.POST, IPConfig.url + "inactive_hod",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            pd.dismiss();
                            try {
                                JSONObject ja= new JSONObject(s);
                                String result = ja.getString("result");

                                if(result.equals("Hod Inactive Successfully")){
                                    Toast.makeText(c, "Hod Inactive Successfully", Toast.LENGTH_SHORT).show();
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

                    map.put("hodid",Parse_For_View_Hod.hod_id[getAdapterPosition()]);

                    return map;
                }
            };


            RequestQueue rq = Volley.newRequestQueue(c);
            rq.add(sr);
        }

        private void openPage() {
            Fragment fragment3 = new Principal_View_Hod();

            ((Principal_Dashboard)c).setFragment3(fragment3);
        }
    }
}
