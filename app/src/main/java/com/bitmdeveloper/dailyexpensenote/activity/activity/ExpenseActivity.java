package com.bitmdeveloper.dailyexpensenote.activity.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bitmdeveloper.dailyexpensenote.R;
import com.bitmdeveloper.dailyexpensenote.activity.swipe_class.OnSwipeTouchListener;
import com.bitmdeveloper.dailyexpensenote.activity.fragments.DashboardFragment;
import com.bitmdeveloper.dailyexpensenote.activity.fragments.ExpenseFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class ExpenseActivity extends AppCompatActivity {

   private BottomNavigationView bottomNavigationView;
   private TextView titileTV;
   private FrameLayout frameLayout;
   private String screen = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);

        init();

        setScreen();

        swipeAction();

        bottomNavigation();


    }
    @Override
    public void onBackPressed(){
        if(bottomNavigationView.getSelectedItemId() == R.id.nav_expense){
            replaceFragment(new DashboardFragment());
            bottomNavigationView.setSelectedItemId(R.id.nav_dashboard);
        }
        else {
            finish();
        }
    }

    private void swipeAction() {

        frameLayout.setOnTouchListener(new OnSwipeTouchListener(ExpenseActivity.this) {
            public void onSwipeRight() {
                if(bottomNavigationView.getSelectedItemId() == R.id.nav_expense)
                {
                    replaceFragment(new DashboardFragment());
                    bottomNavigationView.setSelectedItemId(R.id.nav_dashboard);
                }
            }
            public void onSwipeLeft() {

                if(bottomNavigationView.getSelectedItemId() == R.id.nav_dashboard)
                {
                    replaceFragment(new ExpenseFragment());
                    bottomNavigationView.setSelectedItemId(R.id.nav_expense);
                }
            }
        });
    }

    private void setScreen() {
        screen = getIntent().getStringExtra("setScreen");
        if(screen != null){
            replaceFragment(new ExpenseFragment());
            bottomNavigationView.setSelectedItemId(R.id.nav_expense);
        }
        else {
            replaceFragment(new DashboardFragment());
            bottomNavigationView.setSelectedItemId(R.id.nav_dashboard);
        }
    }


    private void bottomNavigation() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_expense:
                        titileTV.setText("Expense");
                        replaceFragment(new ExpenseFragment());
                        return true;
                    case R.id.nav_dashboard:
                        titileTV.setText("DashBoard");
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
        titileTV = findViewById(R.id.titleTV);
        frameLayout = findViewById(R.id.frame_layout);
        titileTV.setText("DashBoard");

    }
}
