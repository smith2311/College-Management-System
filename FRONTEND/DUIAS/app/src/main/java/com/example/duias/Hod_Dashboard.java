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

public class Hod_Dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout dl;
    NavigationView nv;
    Toolbar tbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hod_dashboard);

        tbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tbar);

        nv = (NavigationView) findViewById(R.id.navigation_view);
        nv.setNavigationItemSelectedListener(this);

        Hod_Add_Subject ob1=new Hod_Add_Subject();

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

                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(Hod_Dashboard.this);
                txtemail.setText("Welcome "+sp.getString("hemail","anon"));
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
            case R.id.manage_subject:
                Toast.makeText(this, "Manage Subject", Toast.LENGTH_SHORT).show();
                Hod_Add_Subject ob1=new Hod_Add_Subject();

                FragmentTransaction ft1= getSupportFragmentManager().beginTransaction();
                ft1.addToBackStack(null);
                ft1.replace(R.id.frame,ob1);
                ft1.commit();
                return true;

            case R.id.change_pwd:
                Toast.makeText(this, "Change Password", Toast.LENGTH_SHORT).show();
                Hod_Change_Pwd ob3=new Hod_Change_Pwd();

                FragmentTransaction ft3= getSupportFragmentManager().beginTransaction();
                ft3.addToBackStack(null);
                ft3.replace(R.id.frame,ob3);
                ft3.commit();
                return true;
            case R.id.view_attendance:
                Toast.makeText(this, "View Attendance", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.view_assignment:
                Toast.makeText(this, "View Assignment", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.view_internal_marks:
                Toast.makeText(this, "View Internal Marks", Toast.LENGTH_SHORT).show();
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
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(Hod_Dashboard.this);
        sp.edit().remove("hemail").commit();
        sp.edit().remove("hodid").commit();

        Intent i = new Intent(Hod_Dashboard.this,Login.class);
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
