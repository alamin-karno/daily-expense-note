package com.bitmdeveloper.dailyexpensenote.activity.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.bitmdeveloper.dailyexpensenote.R;
import com.bitmdeveloper.dailyexpensenote.activity.fragments.DashboardFragment;
import com.bitmdeveloper.dailyexpensenote.activity.fragments.ExpenseFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ExpenseActivity extends AppCompatActivity {

   private BottomNavigationView bottomNavigationView;
   private FloatingActionButton favicon;
    private Spinner expense_typeSP;

    private String[] categories={"Select expense type","Breakfast","Lunch","Dinner","Transport Cost","Electricity Bill","Internet Bill","Phone Bill"};
    private String expensetype;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);

        init();

        replaceFragment(new DashboardFragment());
        bottomNavigation();

        setSpinner();

        getexpense();



    }

    private void setSpinner() {
        ArrayAdapter<String> dataApater = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,categories);
        dataApater.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        expense_typeSP.setAdapter(dataApater);
        expense_typeSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //if(adapterView.getItemAtPosition(i).equals("Select expense type")){
                //     Toast.makeText(MainActivity.this, "Select the type of expense.", Toast.LENGTH_SHORT).show();
                // }
                // else{
                expensetype = adapterView.getSelectedItem().toString();

                //Toast.makeText(MainActivity.this, expensetype+" selected.", Toast.LENGTH_SHORT).show();
                //  }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(ExpenseActivity.this, "Select expense type.", Toast.LENGTH_SHORT).show();
            }
        });
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
                        replaceFragment(new ExpenseFragment());
                        return true;
                    case R.id.nav_dashboard:
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
        expense_typeSP = findViewById(R.id.expense_typeSP);
    }
}
