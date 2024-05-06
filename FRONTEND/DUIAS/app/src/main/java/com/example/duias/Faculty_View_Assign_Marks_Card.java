package com.example.duias;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class Faculty_View_Assign_Marks_Card extends RecyclerView.Adapter<Faculty_View_Assign_Marks_Card.MyUi> {
    private String[] assign_id;

    private String[] program_id;
    private String[] program_name;
    private String[] faculty_id;
    private String[] faculty_name;
    private String[] sub_id;
    private String[] sub_name;
    private String[] semester;
    private String[] div_id;
    private String[] division;

    private String[] no_of_assign;

    private static Context c;
    ProgressDialog pd;
    public Faculty_View_Assign_Marks_Card(Context c, String[] assign_id, String[] program_id, String[] program_name, String[] faculty_id, String[] faculty_name, String[] sub_id, String[] sub_name,  String[] semester, String[] div_id,String[] division,String[] no_of_assign)
    {
        this.c=c;
        this.assign_id= assign_id;
        this.program_id= program_id;
        this.program_name= program_name;
        this.faculty_id= faculty_id;
        this.faculty_name= faculty_name;
        this.sub_id= sub_id;
        this.sub_name= sub_name;
        this.semester= semester;
        this.div_id =div_id;
        this.division =division;
        this.no_of_assign= no_of_assign;
    }

    @NonNull
    @Override
    public Faculty_View_Assign_Marks_Card.MyUi onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.faculty_view_assign_marks_card,parent,false);
        MyUi m = new MyUi(itemview);
        return m;
    }

    @Override
    public void onBindViewHolder(@NonNull Faculty_View_Assign_Marks_Card.MyUi holder, int position) {
        holder.tvaid.setText("Assignment Id: "+assign_id[position]);

        holder.tvfname.setText("Faculty Name: "+faculty_name[position]);

        holder.tvpname.setText("Program Name"+program_name[position]);
        holder.tvsname.setText("Subject Name: "+sub_name[position]);
        holder.tvsem.setText("Semester: "+semester[position]);
        holder.tvnassign.setText("No Of Assignment "+no_of_assign[position]);
        holder.tvdiv.setText("Division: "+division[position]);
    }

    @Override
    public int getItemCount() {
        return program_name.length;
    }

    public class MyUi extends RecyclerView.ViewHolder implements View.OnClickListener {

        Button btnview;
        TextView tvaid,tvfname,tvpname,tvsname,tvsem,tvnassign,tvdiv;
        public MyUi(@NonNull View itemView) {
            super(itemView);
            tvaid = (TextView) itemView.findViewById(R.id.tvaid);

            tvfname = (TextView) itemView.findViewById(R.id.tvfname);

            tvpname = (TextView) itemView.findViewById(R.id.tvpname);
            tvsname = (TextView) itemView.findViewById(R.id.tvsname);
            tvsem = (TextView) itemView.findViewById(R.id.tvsem);
            tvnassign = (TextView) itemView.findViewById(R.id.tvnassign);
            tvdiv = (TextView) itemView.findViewById(R.id.tvdiv);


            btnview = (Button) itemView.findViewById(R.id.btnview);


            btnview.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(view==btnview)
            {
                Fragment fragment3 = new Faculty_View_Assign_Marks_Detail();
                Bundle args = new Bundle();
                args.putString("aid", Parse_For_Faculty_View_Assign_Marks.assign_id[getAdapterPosition()]);

                fragment3.setArguments(args);

                ((Faculty_Dashboard)c).setFragment3(fragment3);
            }
        }
    }
}
