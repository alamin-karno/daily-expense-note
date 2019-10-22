package com.bitmdeveloper.dailyexpensenote.activity.fragments;


import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
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
import com.bitmdeveloper.dailyexpensenote.activity.adapter.ExpenseAdapter;
import com.bitmdeveloper.dailyexpensenote.activity.database.DatabaseHelper;
import com.bitmdeveloper.dailyexpensenote.activity.model_class.Expense;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExpenseFragment extends Fragment {
    List<Expense> expenseList;
    private Spinner expensetype;
    private String[] expensetypeList={"Select expense type","Breakfast","Lunch","Dinner","Transport Cost","Medicine","Phone Bill","Others"};
    private ArrayAdapter<String> arrayAdapter;
    private RecyclerView recyclerView;
    private ExpenseAdapter adapter;
    private DatabaseHelper helper;

    private TextView fromDateTV,toDateTV;
    private String fromdate;

    public ExpenseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_expense, container, false);
        init(view);

        getFromDate();

        getToDate();

        getdata();

        setToRecyclerView();

        getSpinner();

        return view;
    }

    private void getSpinner() {
        expensetype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                if(pos == 0){
                    getdata();
                    setToRecyclerView();
                }
                else if(pos == 1){
                    Cursor cursor = helper.getSpecificData("SELECT * FROM expense WHERE expense_type = 'Breakfast'");
                    setData(cursor);
                }
                else if(pos == 2){
                    Cursor cursor = helper.getSpecificData("SELECT * FROM expense WHERE expense_type = 'Lunch'");
                    setData(cursor);
                }
                else if(pos == 3){
                    Cursor cursor = helper.getSpecificData("SELECT * FROM expense WHERE expense_type = 'Dinner'");
                    setData(cursor);
                }
                else if(pos == 4){
                    Cursor cursor = helper.getSpecificData("SELECT * FROM expense WHERE expense_type = 'Transport Cost'");
                    setData(cursor);
                }
                else if(pos == 5){
                    Cursor cursor = helper.getSpecificData("SELECT * FROM expense WHERE expense_type = 'Medicine'");
                    setData(cursor);
                }
                else if(pos == 6){
                    Cursor cursor = helper.getSpecificData("SELECT * FROM expense WHERE expense_type = 'Phone Bill'");
                    setData(cursor);
                }
                else if(pos == 7){
                    Cursor cursor = helper.getSpecificData("SELECT * FROM expense WHERE expense_type = 'Others'");
                    setData(cursor);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setData(Cursor cursor) {
        expenseList.clear();
        while (cursor.moveToNext()){
            String expense_id = cursor.getString(0);
            String expense_type = cursor.getString(1);
            String expense_date = cursor.getString(2);
            String expense_amount = cursor.getString(3);
            String expense_time = cursor.getString(4);
            String expese_doc = cursor.getString(5);

            expenseList.add(new Expense(expense_id,expense_type,expense_date,expense_amount,expense_time,expese_doc));
        }
        setToRecyclerView();
    }

    private void setToRecyclerView() {
        adapter = new ExpenseAdapter(expenseList,getContext());
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

    private void getToDate() {
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

    private void getdata() {
        expenseList.clear();
        Cursor cursor = helper.getData();

        while (cursor.moveToNext()){
            String expense_id = cursor.getString(0);
            String expense_type = cursor.getString(1);
            String expense_date = cursor.getString(2);
            String expense_amount = cursor.getString(3);
            String expense_time = cursor.getString(4);
            String expese_doc = cursor.getString(5);

            expenseList.add(new Expense(expense_id,expense_type,expense_date,expense_amount,expense_time,expese_doc));
        }
    }


    private void init(View view) {

        expenseList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.expenseRecycleView);
        helper = new DatabaseHelper(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ExpenseAdapter(expenseList,getContext());
        recyclerView.setAdapter(adapter);

        setSpinner(view);

        fromDateTV = view.findViewById(R.id.fromdateTV);
        toDateTV = view.findViewById(R.id.todateTV);

    }

    private void setSpinner(View view) {
        expensetype = view.findViewById(R.id.expense_typeSP);
        arrayAdapter = new ArrayAdapter<>(getActivity(),R.layout.support_simple_spinner_dropdown_item,expensetypeList);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        expensetype.setAdapter(arrayAdapter);
    }


}
