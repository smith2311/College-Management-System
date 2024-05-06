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

public class Principal_View_Clerk_Card extends RecyclerView.Adapter<Principal_View_Clerk_Card.MyUi> {
    private String[] clerk_id;
    private String[] clerk_name;
    private String[] address;
    private String[] city;
    private String[] mobile_no;
    private String[] email_id;
    private String[] pwd;
    private String[] gender;
    private String[] security_que;
    private String[] security_ans;
    private String[] status;
    private static Context c;
    ProgressDialog pd;
    public Principal_View_Clerk_Card(Context c, String[] clerk_id, String[] clerk_name, String[] address, String[] city, String[] mobile_no, String[] email_id, String[] pwd, String[] gender, String[] security_que, String[] security_ans, String[] status)
    {
        this.c=c;
        this.clerk_id= clerk_id;

        this.clerk_name= clerk_name;
        this.address= address;
        this.city= city;
        this.mobile_no= mobile_no;
        this.email_id= email_id;
        this.pwd= pwd;
        this.gender= gender;
        this.security_que= security_que;
        this.security_ans= security_ans;

        this.status =status;
    }

    @NonNull
    @Override
    public Principal_View_Clerk_Card.MyUi onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.principal_view_clerk_card,parent,false);
        MyUi m = new MyUi(itemview);
        return m;
    }

    @Override
    public void onBindViewHolder(@NonNull Principal_View_Clerk_Card.MyUi holder, int position) {
        holder.txtclerkid.setText("Clerk Id: "+clerk_id[position]);
        holder.txtcname.setText("Clerk NAME: "+clerk_name[position]);
        holder.txtadd.setText("Address: "+address[position]);
        holder.txtcity.setText("City: "+city[position]);
        holder.txtmno.setText("Mobile No: "+mobile_no[position]);
        holder.txtemail.setText("Email: "+email_id[position]);
        holder.txtgender.setText("Gender: "+gender[position]);
        holder.txtsque.setText("Security Question: "+security_que[position]);
        holder.txtsans.setText("Security Answer: "+security_ans[position]);

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
        return clerk_name.length;
    }

    public class MyUi extends RecyclerView.ViewHolder implements View.OnClickListener {

        Button btnedit,btnactive,btninactive;
        TextView txtclerkid,txtcname,txtadd,txtcity,txtmno,txtemail,txtgender,txtsque,txtsans,txtstatus;
        public MyUi(@NonNull View itemView) {
            super(itemView);
            txtclerkid = (TextView) itemView.findViewById(R.id.tvcid);
            txtcname = (TextView) itemView.findViewById(R.id.tvcname);
            txtadd = (TextView) itemView.findViewById(R.id.tvadd);
            txtcity = (TextView) itemView.findViewById(R.id.tvcity);
            txtmno = (TextView) itemView.findViewById(R.id.tvmno);
            txtemail = (TextView) itemView.findViewById(R.id.tvemail);
            txtgender = (TextView) itemView.findViewById(R.id.tvgender);
            txtsque = (TextView) itemView.findViewById(R.id.tvsque);
            txtsans = (TextView) itemView.findViewById(R.id.tvsans);


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
                Fragment fragment3 = new Principal_Edit_Clerk();
                Bundle args = new Bundle();
                args.putString("clerkid", Parse_For_View_Clerk.clerk_id[getAdapterPosition()]);
                args.putString("clerkname", Parse_For_View_Clerk.clerk_name[getAdapterPosition()]);
                args.putString("address", Parse_For_View_Clerk.address[getAdapterPosition()]);
                args.putString("city", Parse_For_View_Clerk.city[getAdapterPosition()]);
                args.putString("mno", Parse_For_View_Clerk.mobile_no[getAdapterPosition()]);
                args.putString("email", Parse_For_View_Clerk.email_id[getAdapterPosition()]);
                args.putString("pwd", Parse_For_View_Clerk.pwd[getAdapterPosition()]);


                args.putString("sque", Parse_For_View_Clerk.security_que[getAdapterPosition()]);
                args.putString("sans", Parse_For_View_Clerk.security_ans[getAdapterPosition()]);
                args.putString("gender", Parse_For_View_Clerk.gender[getAdapterPosition()]);
                fragment3.setArguments(args);

                ((Principal_Dashboard)c).setFragment3(fragment3);
            }

            if(view == btnactive)
            {
                active_clerk();
            }

            if(view == btninactive)
            {
                inactive_clerk();
            }
        }

        private void active_clerk() {
            pd = new ProgressDialog(c);
            pd.setMessage("Active Clerk.....");
            pd.setIndeterminate(false);
            pd.setCancelable(false);
            pd.show();

            StringRequest sr= new StringRequest(Request.Method.POST, IPConfig.url + "active_clerk",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            pd.dismiss();
                            try {
                                JSONObject ja= new JSONObject(s);
                                String result = ja.getString("result");

                                if(result.equals("Clerk Active Successfully")){
                                    Toast.makeText(c, "Clerk Active Successfully", Toast.LENGTH_SHORT).show();
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

                    map.put("clerkid",Parse_For_View_Clerk.clerk_id[getAdapterPosition()]);

                    return map;
                }
            };


            RequestQueue rq = Volley.newRequestQueue(c);
            rq.add(sr);
        }

        private void inactive_clerk() {

            pd = new ProgressDialog(c);
            pd.setMessage("Inactive Clerk.....");
            pd.setIndeterminate(false);
            pd.setCancelable(false);
            pd.show();

            StringRequest sr= new StringRequest(Request.Method.POST, IPConfig.url + "inactive_clerk",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            pd.dismiss();
                            try {
                                JSONObject ja= new JSONObject(s);
                                String result = ja.getString("result");

                                if(result.equals("Clerk Inactive Successfully")){
                                    Toast.makeText(c, "Clerk Inactive Successfully", Toast.LENGTH_SHORT).show();
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

                    map.put("clerkid",Parse_For_View_Clerk.clerk_id[getAdapterPosition()]);

                    return map;
                }
            };


            RequestQueue rq = Volley.newRequestQueue(c);
            rq.add(sr);
        }

        private void openPage() {
            Fragment fragment3 = new Principal_View_Clerk();

            ((Principal_Dashboard)c).setFragment3(fragment3);
        }
    }
}
