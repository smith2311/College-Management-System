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

public class Faculty_View_Internal_Marks_Card extends RecyclerView.Adapter<Faculty_View_Internal_Marks_Card.MyUi> {
    private String[] marks_id;
    private String[] marks_date;
    private String[] program_id;
    private String[] program_name;
    private String[] semester;
    private String[] sub_id;
    private String[] sub_name;
    private String[] faculty_id;
    private String[] faculty_name;
    private String[] internal_exam;
    private String[] total_marks;
    private String[] academic_year;
    private String[] div_id;
    private String[] division;
    private static Context c;
    ProgressDialog pd;
    public Faculty_View_Internal_Marks_Card(Context c, String[] marks_id, String[] marks_date, String[] program_id, String[] program_name, String[] semester, String[] sub_id, String[] sub_name, String[] faculty_id, String[] faculty_name, String[] internal_exam, String[] total_marks, String[] academic_year, String[] div_id,String[] division)
    {
        this.c=c;
        this.marks_id= marks_id;

        this.marks_date= marks_date;
        this.program_id= program_id;
        this.program_name= program_name;
        this.semester= semester;
        this.sub_id= sub_id;
        this.sub_name= sub_name;
        this.faculty_id= faculty_id;
        this.faculty_name= faculty_name;
        this.internal_exam= internal_exam;

        this.total_marks =total_marks;
        this.academic_year =academic_year;


        this.div_id =div_id;
        this.division =division;
    }

    @NonNull
    @Override
    public Faculty_View_Internal_Marks_Card.MyUi onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.faculty_view_internal_marks_card,parent,false);
        MyUi m = new MyUi(itemview);
        return m;
    }

    @Override
    public void onBindViewHolder(@NonNull Faculty_View_Internal_Marks_Card.MyUi holder, int position) {
        holder.tvmid.setText("Marks Id: "+marks_id[position]);
        holder.tvmdate.setText("Marks Date: "+marks_date[position]);
        holder.tvfname.setText("Faculty Name: "+faculty_name[position]);
        holder.tveno.setText("Exam No: "+internal_exam[position]);
        holder.tvpname.setText("Program Name"+program_name[position]);
        holder.tvsname.setText("Subject Name: "+sub_name[position]);
        holder.tvsem.setText("Semester: "+semester[position]);
        holder.tvtmarks.setText("Total Marks: "+total_marks[position]);
        holder.tvdiv.setText("Division: "+division[position]);

        holder.tvayear.setText("Academic Year: "+academic_year[position]);
    }

    @Override
    public int getItemCount() {
        return program_name.length;
    }

    public class MyUi extends RecyclerView.ViewHolder implements View.OnClickListener {

        Button btnview;
        TextView tvmid,tvmdate,tvfname,tveno,tvpname,tvsname,tvsem,tvtmarks,tvdiv,tvayear;
        public MyUi(@NonNull View itemView) {
            super(itemView);
            tvmid = (TextView) itemView.findViewById(R.id.tvmid);
            tvmdate = (TextView) itemView.findViewById(R.id.tvmdate);
            tvfname = (TextView) itemView.findViewById(R.id.tvfname);
            tveno = (TextView) itemView.findViewById(R.id.tveno);
            tvpname = (TextView) itemView.findViewById(R.id.tvpname);
            tvsname = (TextView) itemView.findViewById(R.id.tvsname);
            tvsem = (TextView) itemView.findViewById(R.id.tvsem);
            tvtmarks = (TextView) itemView.findViewById(R.id.tvtmarks);
            tvdiv = (TextView) itemView.findViewById(R.id.tvdiv);

            tvayear = (TextView) itemView.findViewById(R.id.tvayear);

            btnview = (Button) itemView.findViewById(R.id.btnview);


            btnview.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(view==btnview)
            {
                Fragment fragment3 = new Faculty_View_Internal_Marks_Detail();
                Bundle args = new Bundle();
                args.putString("mid", Parse_For_Faculty_View_Internal_Marks.marks_id[getAdapterPosition()]);

                fragment3.setArguments(args);

                ((Faculty_Dashboard)c).setFragment3(fragment3);
            }
        }
    }
}
