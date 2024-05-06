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

public class Principal_Dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout dl;
    NavigationView nv;
    Toolbar tbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal_dashboard);

        tbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tbar);

        nv = (NavigationView) findViewById(R.id.navigation_view);
        nv.setNavigationItemSelectedListener(this);


        Principal_Add_Dept ob1=new Principal_Add_Dept();

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

                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(Principal_Dashboard.this);
                txtemail.setText("Welcome "+sp.getString("pemail","anon"));
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
            case R.id.manage_dept:
                Toast.makeText(this, "Manage Department", Toast.LENGTH_SHORT).show();
                Principal_Add_Dept ob1=new Principal_Add_Dept();

                FragmentTransaction ft1= getSupportFragmentManager().beginTransaction();
                ft1.addToBackStack(null);
                ft1.replace(R.id.frame,ob1);
                ft1.commit();
                return true;
            case R.id.manage_hod:
                Toast.makeText(this, "Manage Hod", Toast.LENGTH_SHORT).show();
                Principal_Add_Hod ob2=new Principal_Add_Hod();

                FragmentTransaction ft2= getSupportFragmentManager().beginTransaction();
                ft2.addToBackStack(null);
                ft2.replace(R.id.frame,ob2);
                ft2.commit();
                return true;
            case R.id.manage_clerk:
                Toast.makeText(this, "Manage Clerk", Toast.LENGTH_SHORT).show();
                Principal_Add_Clerk ob3=new Principal_Add_Clerk();

                FragmentTransaction ft3= getSupportFragmentManager().beginTransaction();
                ft3.addToBackStack(null);
                ft3.replace(R.id.frame,ob3);
                ft3.commit();
                return true;
            case R.id.report:
                Toast.makeText(this, "Manage Reports", Toast.LENGTH_SHORT).show();
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
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(Principal_Dashboard.this);
        sp.edit().remove("pemail").commit();
        sp.edit().remove("pid").commit();

        Intent i = new Intent(Principal_Dashboard.this,Login.class);
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
