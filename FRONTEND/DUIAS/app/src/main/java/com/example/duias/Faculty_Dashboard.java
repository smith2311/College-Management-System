package com.example.duias;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

public class Faculty_Dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout dl;
    NavigationView nv;
    Toolbar tbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faculty_dashboard);

        tbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tbar);

        nv = (NavigationView) findViewById(R.id.navigation_view);
        nv.setNavigationItemSelectedListener(this);

        Faculty_Manage_Attendance ob1=new Faculty_Manage_Attendance();

        FragmentTransaction ft1= getSupportFragmentManager().beginTransaction();
        ft1.addToBackStack(null);
        ft1.replace(R.id.frame,ob1);
        ft1.commit();



        dl = (DrawerLayout) findViewById(R.id.drawer);

        ActionBarDrawerToggle abdt = new ActionBarDrawerToggle(this,dl,tbar,R.string.drawer_open,R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                TextView txtemail;
                txtemail = (TextView)  drawerView.findViewById(R.id.email);

                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(Faculty_Dashboard.this);
                txtemail.setText("Welcome "+sp.getString("femail","anon"));
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        dl.addDrawerListener(abdt);
        abdt.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if(item.isChecked())
        {
            item.setChecked(false);
        }else{
            item.setChecked(true);
        }
        dl.closeDrawers();


        switch(item.getItemId())
        {
            case R.id.manage_attendance:
                Toast.makeText(this, "Manage Attendance", Toast.LENGTH_SHORT).show();
                Faculty_Manage_Attendance ob1=new Faculty_Manage_Attendance();

                FragmentTransaction ft1= getSupportFragmentManager().beginTransaction();
                ft1.addToBackStack(null);
                ft1.replace(R.id.frame,ob1);
                ft1.commit();

                return true;
            case R.id.manage_internal_marks:
                Toast.makeText(this, "Manage Internal Marks", Toast.LENGTH_SHORT).show();
                Faculty_Manage_Internal_Marks ob2=new Faculty_Manage_Internal_Marks();

                FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
                ft2.addToBackStack(null);
                ft2.replace(R.id.frame,ob2);
                ft2.commit();
                return true;
            case R.id.manage_assignment_marks:
                Toast.makeText(this, "Manage Assignment Marks", Toast.LENGTH_SHORT).show();
                Faculty_Manage_Assign_Marks ob3=new Faculty_Manage_Assign_Marks();

                FragmentTransaction ft3 = getSupportFragmentManager().beginTransaction();
                ft3.addToBackStack(null);
                ft3.replace(R.id.frame,ob3);
                ft3.commit();
                return true;
            case R.id.change_pwd:
                Toast.makeText(this, "Change Password", Toast.LENGTH_SHORT).show();
                Faculty_Change_Pwd ob4=new Faculty_Change_Pwd();

                FragmentTransaction ft4= getSupportFragmentManager().beginTransaction();
                ft4.addToBackStack(null);
                ft4.replace(R.id.frame,ob4);
                ft4.commit();
                return true;

            case R.id.logout:
                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
                logout();
                return true;
            default:
                    return true;

        }

    }

    private void logout() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(Faculty_Dashboard.this);
        sp.edit().remove("femail").commit();
        sp.edit().remove("fid").commit();

        Intent i = new Intent(Faculty_Dashboard.this,Login.class);
        finish();
        startActivity(i);
    }

    public void setFragment3(Fragment fragment3) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.addToBackStack(null);
        transaction.replace(R.id.frame, fragment3);
        transaction.commit();
    }
}
