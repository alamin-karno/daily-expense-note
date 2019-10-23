package com.bitmdeveloper.dailyexpensenote.activity.fragments;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bitmdeveloper.dailyexpensenote.R;
import com.bitmdeveloper.dailyexpensenote.activity.activity.MainActivity;
import com.bitmdeveloper.dailyexpensenote.activity.database.DatabaseHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {
    private DatabaseHelper helper;

    private Spinner expensetype;
    private String[] expensetypeList={"Select expense type","Breakfast","Lunch","Dinner","Transport Cost","Medicine","Phone Bill","Others"};
    private ArrayAdapter<String> arrayAdapter;

    private TextView fromDateTV,toDateTV,totalExpenseTV;
    private String fromdate;

    public DashboardFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        init(view);

        getFromDate();

        getToDate();

        sumExpenses();

        return view;
    }

    private void sumExpenses() {

        expensetype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0)
                {
                    Cursor cursor = helper.getSpecificData("SELECT SUM (expense_amount) AS total FROM expense");
                    if (cursor.moveToFirst()) {
                        int total = cursor.getInt(cursor.getColumnIndex("total"));
                        totalExpenseTV.setText(String.valueOf(total));
                    }
                }

//                else if(position == 1){
//                    Cursor cursor = helper.getSpecificData("SELECT SUM (expense_amount) AS total FROM expense WHERE expense_type = 'Breakfast'");
//                    if (cursor.moveToFirst()) {
//                        int total = cursor.getInt(cursor.getColumnIndex("total"));
//                        totalExpenseTV.setText(String.valueOf(total));
//                    }
//                }
//

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void getToDate() {
        toDateTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fromdate != null){
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
                            toDateTV.setText(dateFormat.format(date));
                        }
                    };

                    Calendar calendar = Calendar.getInstance();
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH);
                    int day = calendar.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),dateSetListener,year,month,day);
                    datePickerDialog.show();
                }
                else {
                    Toast.makeText(getContext(), "Select from date first", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getFromDate() {
        fromDateTV.setOnClickListener(new View.OnClickListener() {
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
                        fromdate = dateFormat.format(date);
                        fromDateTV.setText(fromdate);
                    }
                };

                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),dateSetListener,year,month,day);
                datePickerDialog.show();
            }
        });
    }

    private void init(View view) {
        helper = new DatabaseHelper(getContext());

        expensetype = view.findViewById(R.id.expense_typeSP);
        arrayAdapter = new ArrayAdapter<>(getActivity(),R.layout.support_simple_spinner_dropdown_item,expensetypeList);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        expensetype.setAdapter(arrayAdapter);

        fromDateTV = view.findViewById(R.id.fromdateTV);
        toDateTV = view.findViewById(R.id.todateTV);
        totalExpenseTV = view.findViewById(R.id.totalExpenseTV);

    }

}
