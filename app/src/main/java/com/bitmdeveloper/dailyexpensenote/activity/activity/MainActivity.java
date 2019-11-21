package com.bitmdeveloper.dailyexpensenote.activity.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bitmdeveloper.dailyexpensenote.R;
import com.bitmdeveloper.dailyexpensenote.activity.database.DatabaseHelper;
import com.bitmdeveloper.dailyexpensenote.activity.model_class.Expense;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    private Spinner expense_typeSP;
    private Button expense_docBTN,expense_dateBTN,expense_timeBTN,add_expenseBTN;
    private TextView settileTV;
    private ImageView expense_imageIV,cancelimageIV,backIV;
    private EditText expense_amountET;
    private DatabaseHelper helper;
    private ArrayAdapter<String> arrayAdapter;

    private String[] categories={"Select expense type","Breakfast","Lunch","Dinner","Transport Cost","Medicine","Phone Bill","Others"};
    private String type,amount,date,time,doc;
    private String idIntent,updateimage;
    private Bitmap bitmappic = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        setspinner();

        getdate();

        gettime();

        getImage();

        getUpdateIntent();

        addexpense();

        cancelimageIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bitmappic = null;
                expense_imageIV.setImageResource(R.drawable.file);
                cancelimageIV.setVisibility(View.INVISIBLE);
            }
        });
        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void getUpdateIntent() {
        idIntent = getIntent().getStringExtra("EXPENSE_ID");
        updateimage = getIntent().getStringExtra("EXPENSE_IMAGE");
        bitmappic = stringToBitmap(updateimage);
        if(idIntent != null){

            int spinnerItemPosition = arrayAdapter.getPosition(getIntent().getStringExtra("EXPENSE_TYPE"));
            expense_typeSP.setSelection(spinnerItemPosition);
            expense_amountET.setText(getIntent().getStringExtra("EXPENSE_AMOUNT"));
            expense_dateBTN.setText(getIntent().getStringExtra("EXPENSE_DATE"));
            expense_timeBTN.setText(getIntent().getStringExtra("EXPENSE_TIME"));

            if(bitmappic != null){
                expense_imageIV.setImageBitmap(bitmappic);
                cancelimageIV.setVisibility(View.VISIBLE);

            }else {
                expense_imageIV.setImageResource(R.drawable.file);
            }
            settileTV.setText("Update "+getIntent().getStringExtra("EXPENSE_TYPE"));
            add_expenseBTN.setText("Update Expense");
        }

    }

    private Bitmap stringToBitmap(String updateimage) {

        try {
            byte [] encodeByte= Base64.decode(updateimage,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }

    private void addexpense() {
        add_expenseBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                type = expense_typeSP.getSelectedItem().toString();
                amount = expense_amountET.getText().toString().trim();
                date = expense_dateBTN.getText().toString().trim();
                time = expense_timeBTN.getText().toString().trim();
                doc = expense_docBTN.getText().toString();


                if(type.equals("Select expense type")){
                    Toast.makeText(MainActivity.this, "Please select an expense type.", Toast.LENGTH_SHORT).show();
                }
                else if(amount.equals("")){
                    Toast.makeText(MainActivity.this, "Enter amount.", Toast.LENGTH_SHORT).show();
                }
                else if(date.equals("")){
                    Toast.makeText(MainActivity.this, "Enter date.", Toast.LENGTH_SHORT).show();
                }

                else {

                    if(idIntent != null){

                        if(bitmappic != null){
                            long resultId = helper.updatedata(idIntent,type,amount,date,time,bitmapToString(bitmappic));

                            if (resultId > 0){
                                Toast.makeText(MainActivity.this, "Updated Successfully.", Toast.LENGTH_SHORT).show();
                                //finish();
                                Intent intent = new Intent(MainActivity.this,ExpenseActivity.class);
                                intent.putExtra("setScreen","1");
                                startActivity(intent);
                                finish();
                            }
                            else {
                                Toast.makeText(MainActivity.this, "Data did not update.", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            long resultId = helper.updatedata(idIntent,type,amount,date,time,null);

                            if (resultId > 0){
                                Toast.makeText(MainActivity.this, "Updated Successfully.", Toast.LENGTH_SHORT).show();
                                //finish();
                                Intent intent = new Intent(MainActivity.this,ExpenseActivity.class);
                                intent.putExtra("setScreen","1");
                                startActivity(intent);
                                finish();
                            }
                            else {
                                Toast.makeText(MainActivity.this, "Data did not update.", Toast.LENGTH_SHORT).show();
                            }
                        }


                    }
                    else {

                        if(bitmappic !=null){
                            long resultId = helper.insertdata(type, amount, date, time, bitmapToString(bitmappic));

                            if(resultId > 0){
                                setResult(RESULT_OK);
                                Toast.makeText(MainActivity.this, "Data inserted Successfully.", Toast.LENGTH_SHORT).show();
                                //finish();
                                Intent intent = new Intent(MainActivity.this,ExpenseActivity.class);
                                intent.putExtra("setScreen","1");
                                startActivity(intent);
                                finish();
                            }
                            else {
                                Toast.makeText(MainActivity.this, "Data did not insert.", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            long resultId = helper.insertdata(type, amount, date, time, null);

                            if(resultId > 0){
                                setResult(RESULT_OK);
                                Toast.makeText(MainActivity.this, "Data inserted Successfully.", Toast.LENGTH_SHORT).show();
                                //finish();
                                Intent intent = new Intent(MainActivity.this,ExpenseActivity.class);
                                intent.putExtra("setScreen","1");
                                startActivity(intent);
                                finish();
                            }
                            else {
                                Toast.makeText(MainActivity.this, "Data did not inserted.", Toast.LENGTH_SHORT).show();
                            }
                        }


                    }




                }


            }
        });
    }

    private String bitmapToString(Bitmap bitmappic) {
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmappic.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String stringImage=Base64.encodeToString(b, Base64.DEFAULT);
        return stringImage;
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

                        expense_timeBTN.setText(timeFormat.format(time).toString());
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
                String[] items = {"Camera","Gallery"};
                builder.setTitle("Choose an action");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0:
                                openCamera();
                                break;
                            case 1:
                                opengallery();
                                break;
                        }
                    }
                });
                Dialog dialog = builder.create();
                dialog.show();

            }
        });
    }

    private void opengallery() {
        Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if (requestCode == 0){
                Bundle bundle = data.getExtras();
                bitmappic = (Bitmap) bundle.get("data");
                expense_imageIV.setImageBitmap(bitmappic);
                cancelimageIV.setVisibility(View.VISIBLE);
            }
            else if(requestCode == 1){
                Uri uri =data.getData();
                try {
                    bitmappic = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                } catch (IOException e) {
                    Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                expense_imageIV.setImageBitmap(bitmappic);
                cancelimageIV.setVisibility(View.VISIBLE);
            }
        }
    }

    private void openCamera() {
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
                    type = adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void init() {
        backIV = findViewById(R.id.backIV);
        cancelimageIV = findViewById(R.id.cancelimageIV);
        expense_amountET = findViewById(R.id.expense_amountET);
        expense_dateBTN = findViewById(R.id.expense_dateET);
        expense_timeBTN = findViewById(R.id.expense_timeET);
        expense_typeSP = findViewById(R.id.expense_typeSP);
        expense_docBTN = findViewById(R.id.expense_docBTN);
        expense_imageIV = findViewById(R.id.expense_imageIV);
        add_expenseBTN = findViewById(R.id.add_expenseBTN);
        settileTV = findViewById(R.id.titleTV);
        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,categories);
        helper = new DatabaseHelper(this);

    }
}

