package com.example.duias;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class Principal_View_Dept_Card extends RecyclerView.Adapter<Principal_View_Dept_Card.MyUi> {

    private String[] did;
    private String[] dname;
    private String[] status;
    private static Context c;
    ProgressDialog pd;
    public Principal_View_Dept_Card(Context c, String[] did,String[] dname,String[] status)
    {
        this.c=c;
        this.did= did;
        this.dname =dname;
        this.status =status;
    }

    @NonNull
    @Override
    public Principal_View_Dept_Card.MyUi onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.principal_view_dept_card,parent,false);
        MyUi m = new MyUi(itemview);
        return m;
    }

    @Override
    public void onBindViewHolder(@NonNull Principal_View_Dept_Card.MyUi holder, int position) {
        holder.txtdid.setText("Department Id: "+did[position]);
        holder.txtdname.setText("Name: "+dname[position]);
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
        return dname.length;
    }

    public class MyUi extends RecyclerView.ViewHolder implements View.OnClickListener {

        Button btnedit,btnactive,btninactive;
        TextView txtdid,txtdname,txtstatus;
        public MyUi(@NonNull View itemView) {
            super(itemView);
            txtdid = (TextView) itemView.findViewById(R.id.tvdid);
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
                Fragment fragment3 = new Principal_Edit_Dept();
                Bundle args = new Bundle();
                args.putString("did", Parse_For_View_Dept.dep_id[getAdapterPosition()]);
                args.putString("dname", Parse_For_View_Dept.dep_name[getAdapterPosition()]);

                fragment3.setArguments(args);
                // android.support.v4.app.FragmentTransaction fragmentTransaction3 = context.getSupportFragmentManager().beginTransaction();
                //fragmentTransaction3.replace(R.id.frame, fragment3);
                ((Principal_Dashboard)c).setFragment3(fragment3);
            }

            if(view == btnactive)
            {
                active_dept();
            }

            if(view == btninactive)
            {
                inactive_dept();
            }
        }

        private void active_dept() {
            pd = new ProgressDialog(c);
            pd.setMessage("Active Department.....");
            pd.setIndeterminate(false);
            pd.setCancelable(false);
            pd.show();

            StringRequest sr= new StringRequest(Request.Method.POST, IPConfig.url + "active_dept",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            pd.dismiss();
                            try {
                                JSONObject ja= new JSONObject(s);
                                String result = ja.getString("result");

                                if(result.equals("Department Active Successfully")){
                                    Toast.makeText(c, "Department Active Successfully", Toast.LENGTH_SHORT).show();
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

                    map.put("did",Parse_For_View_Dept.dep_id[getAdapterPosition()]);

                    return map;
                }
            };


            RequestQueue rq = Volley.newRequestQueue(c);
            rq.add(sr);
        }

        private void inactive_dept() {

            pd = new ProgressDialog(c);
            pd.setMessage("Inactive Department.....");
            pd.setIndeterminate(false);
            pd.setCancelable(false);
            pd.show();

            StringRequest sr= new StringRequest(Request.Method.POST, IPConfig.url + "inactive_dept",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            pd.dismiss();
                            try {
                                JSONObject ja= new JSONObject(s);
                                String result = ja.getString("result");

                                if(result.equals("Department Inactive Successfully")){
                                    Toast.makeText(c, "Department Inactive Successfully", Toast.LENGTH_SHORT).show();
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

                    map.put("did",Parse_For_View_Dept.dep_id[getAdapterPosition()]);

                    return map;
                }
            };


            RequestQueue rq = Volley.newRequestQueue(c);
            rq.add(sr);
        }

        private void openPage() {
            Fragment fragment3 = new Principal_View_Dept();

            ((Principal_Dashboard)c).setFragment3(fragment3);
        }
    }
}
