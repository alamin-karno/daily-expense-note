package com.bitmdeveloper.dailyexpensenote.activity.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bitmdeveloper.dailyexpensenote.R;
import com.bitmdeveloper.dailyexpensenote.activity.fragments.DashboardFragment;
import com.bitmdeveloper.dailyexpensenote.activity.fragments.ExpenseFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ExpenseActivity extends AppCompatActivity {

   private BottomNavigationView bottomNavigationView;
   private FloatingActionButton favicon;
   private TextView titleTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);

        init();



        replaceFragment(new DashboardFragment());

        bottomNavigation();

        getexpense();



    }



    private void getexpense() {
        favicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExpenseActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void bottomNavigation() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_expense:
                        titleTV.setText("Expense");
                        replaceFragment(new ExpenseFragment());
                        return true;
                    case R.id.nav_dashboard:
                        titleTV.setText("Dashboard");
                        replaceFragment(new DashboardFragment());
                        return true;
                }

                return false;
            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout,fragment);
            fragmentTransaction.commit();
    }

    private void init() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        favicon = findViewById(R.id.favicon);
        titleTV = findViewById(R.id.titleTV);
        titleTV.setText("Dashboard");

    }
}
