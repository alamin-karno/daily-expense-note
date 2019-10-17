package com.bitmdeveloper.dailyexpensenote;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Spinner expense_typeSP;
    private Button expense_docBTN;

    private String[] categories={"Select expense type","Breakfast","Lunch","Dinner","Transport Cost","Electricity Bill","Internet Bill","Phone Bill"};
    private String expensetype;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        setspinner();

        expense_docBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheet_Camera_Gallery bottomSheet_camera_gallery = new BottomSheet_Camera_Gallery();
                bottomSheet_camera_gallery.show(getSupportFragmentManager(),"camera");
            }
        });
    }

    private void setspinner() {
        ArrayAdapter<String> dataApater = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,categories);
        dataApater.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        expense_typeSP.setAdapter(dataApater);
        expense_typeSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getItemAtPosition(i).equals("Select expense type")){

                    Toast.makeText(MainActivity.this, "Please select an item.", Toast.LENGTH_SHORT).show();
                }
                else{
                    expensetype = adapterView.getSelectedItem().toString();

                    Toast.makeText(MainActivity.this, "Selected: "+expensetype, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void init() {
        expense_typeSP = findViewById(R.id.expense_typeSP);
        expense_docBTN = findViewById(R.id.expense_docBTN);
    }
}
