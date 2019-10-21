package com.bitmdeveloper.dailyexpensenote.activity.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bitmdeveloper.dailyexpensenote.R;
import com.bitmdeveloper.dailyexpensenote.activity.database.DatabaseHelper;
import com.bitmdeveloper.dailyexpensenote.activity.model_class.Expense;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    private Spinner expense_typeSP;
    private Button expense_docBTN,expense_dateBTN,expense_timeBTN,add_expenseBTN;
    private ImageView expense_imageIV;
    private EditText expense_amountET;
    private DatabaseHelper helper;

    private String[] categories={"Select expense type","Breakfast","Lunch","Dinner","Transport Cost","Electricity Bill","Internet Bill","Phone Bill"};
    private String expensetype,amount,date,time,doc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        setspinner();

        getdate();

        gettime();

        getImage();

        addexpense();


    }

    private void addexpense() {
        add_expenseBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                amount = expense_amountET.getText().toString();
                date = expense_dateBTN.getText().toString();
                time = expense_timeBTN.getText().toString();
                doc = expense_docBTN.getText().toString();


                if(expensetype.equals("Select expense type")){
                    Toast.makeText(MainActivity.this, "Please select an expense type.", Toast.LENGTH_SHORT).show();
                }
                else if(amount.equals("")){
                    Toast.makeText(MainActivity.this, "Enter amount.", Toast.LENGTH_SHORT).show();
                }
                else if(date.equals("")){
                    Toast.makeText(MainActivity.this, "Enter date.", Toast.LENGTH_SHORT).show();
                }

                else {
                    long id = helper.insertdata(expensetype,amount,date,time,doc);
                    Toast.makeText(MainActivity.this, expensetype+" "+amount+" TAKA inserted.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this,ExpenseActivity.class);
                    startActivity(intent);

                   // Toast.makeText(MainActivity.this, "Type-"+expensetype+", Amount-"+amount+", Date-"+date+", Time-"+time, Toast.LENGTH_LONG).show();
                }


            }
        });
    }

    private void gettime() {
        expense_timeBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                View view1 = getLayoutInflater().inflate(R.layout.custom_time_picker,null);

                final Button timedoneBTN = view1.findViewById(R.id.timedoneBTN);
                final TimePicker timePicker = view1.findViewById(R.id.timepickerTP);

                builder.setView(view1);

                final Dialog dialog = builder.create();
                dialog.show();

                timedoneBTN.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss aa");

                        @SuppressLint({"NewApi", "LocalSuppress"}) int hour = timePicker.getHour();
                        @SuppressLint({"NewApi", "LocalSuppress"}) int min = timePicker.getMinute();

                        Time time = new Time(hour,min,0);

                        expense_timeBTN.setText(timeFormat.format(time));
                        dialog.dismiss();

                    }
                });

            }
        });
    }

    private void getdate() {
        expense_dateBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month+1;

                        String currentdate = day+"/"+month+"/"+year;

                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

                        Date date = null;

                        try {
                            date = dateFormat.parse(currentdate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        expense_dateBTN.setText(dateFormat.format(date));
                    }
                };

                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,dateSetListener,year,month,day);
                datePickerDialog.show();
            }
        });
    }


    private void getImage() {
        expense_docBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                final View view1 = getLayoutInflater().inflate(R.layout.custom_camera_gallery,null);
                builder.setView(view1);
                LinearLayout cameraBTN = view1.findViewById(R.id.cameraBTN);
                LinearLayout galleryBTN = view1.findViewById(R.id.galleryBTN);
                Button imagedone = view1.findViewById(R.id.imagedoneBTN);


                final Dialog dialog = builder.create();
                dialog.show();
                cameraBTN.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openCamera(view);
                    }
                });
                galleryBTN.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        opengallery(view1);
                    }
                });

                imagedone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(expense_imageIV.equals("")){
                            Toast.makeText(MainActivity.this, "Please add an image.", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            dialog.dismiss();
                        }
                    }
                });

            }
        });
    }

    private void opengallery(View view1) {
        Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RESULT_OK){
            if (resultCode == 0){
                Bundle bundle = data.getExtras();
                Bitmap bitmap = (Bitmap) bundle.get("data");
                expense_imageIV.setImageBitmap(bitmap);
            }
            else if(resultCode == 1){
                Uri uri =data.getData();
                expense_imageIV.setImageURI(uri);
            }
        }
    }

    private void openCamera(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,0);
    }


    private void setspinner() {
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
                Toast.makeText(MainActivity.this, "Select expense type.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void init() {
        expense_amountET = findViewById(R.id.expense_amountET);
        expense_dateBTN = findViewById(R.id.expense_dateET);
        expense_timeBTN = findViewById(R.id.expense_timeET);
        expense_typeSP = findViewById(R.id.expense_typeSP);
        expense_docBTN = findViewById(R.id.expense_docBTN);
        expense_imageIV = findViewById(R.id.expense_imageIV);
        add_expenseBTN = findViewById(R.id.add_expenseBTN);
        helper = new DatabaseHelper(this);

    }
}

