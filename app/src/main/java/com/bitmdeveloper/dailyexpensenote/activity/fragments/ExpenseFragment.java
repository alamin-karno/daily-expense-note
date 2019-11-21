package com.bitmdeveloper.dailyexpensenote.activity.fragments;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.bitmdeveloper.dailyexpensenote.activity.activity.ExpenseActivity;
import com.bitmdeveloper.dailyexpensenote.activity.activity.MainActivity;
import com.bitmdeveloper.dailyexpensenote.activity.adapter.ExpenseAdapter;
import com.bitmdeveloper.dailyexpensenote.activity.database.DatabaseHelper;
import com.bitmdeveloper.dailyexpensenote.activity.model_class.Expense;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
    public static DatabaseHelper helper;

    private TextView fromDateTV,toDateTV;
    private String fromdate;
    private FloatingActionButton favicon;
    public ExpenseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_expense, container, false);

        init(view);

        getexpense();

        fabscroll();

        getFromDate();

        getToDate();

        getdata();

        notifyRecyclerView();

       // Filtering();

        return view;
    }


    private void notifyRecyclerView() {
        adapter = new ExpenseAdapter(expenseList,getContext());
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

    private void Filtering() {
        expensetype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    getdata();
                    notifyRecyclerView();
                }
                else if(position == 1){
                    Cursor cursor = helper.getSpecificData("SELECT * FROM expense WHERE expense_type = 'Breakfast'");
                   // setData(cursor);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setData(Cursor cursor) {
        expenseList.clear();
        while (cursor.moveToNext()){
            String expense_id = cursor.getString(cursor.getColumnIndex(helper.COL_ID));
            String expense_type = cursor.getString(cursor.getColumnIndex(helper.COL_TYPE));
            String expense_amount = cursor.getString(cursor.getColumnIndex(helper.COL_AMOUNT));
            String expense_date = cursor.getString(cursor.getColumnIndex(helper.COL_DATE));
            String expense_time = cursor.getString(cursor.getColumnIndex(helper.COL_TIME));
            String expese_doc = cursor.getString(cursor.getColumnIndex(helper.COL_DOC));

            expenseList.add(new Expense(expense_id,expense_type,expense_amount,expense_date,expense_time,expese_doc));
        }
        notifyRecyclerView();
    }


    private void fabscroll() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy>0){
                    favicon.hide();
                }
                else if(dy<0){
                    favicon.show();
                }
            }
        });
    }

    private void getexpense() {
        favicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),MainActivity.class);
                startActivity(intent);
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

    private void getdata() {
        expenseList.clear();
        Cursor cursor = helper.showData();
        while (cursor.moveToNext()){
            String expense_id = cursor.getString(cursor.getColumnIndex(helper.COL_ID));
            String expense_type = cursor.getString(cursor.getColumnIndex(helper.COL_TYPE));
            String expense_amount = cursor.getString(cursor.getColumnIndex(helper.COL_AMOUNT));
            String expense_date = cursor.getString(cursor.getColumnIndex(helper.COL_DATE));
            String expense_time = cursor.getString(cursor.getColumnIndex(helper.COL_TIME));
            String expese_doc = cursor.getString(cursor.getColumnIndex(helper.COL_DOC));

            expenseList.add(new Expense(expense_id,expense_type,expense_amount,expense_date,expense_time,expese_doc));

        }
        notifyRecyclerView();
    }


    private void init(View view) {
        favicon = view.findViewById(R.id.favicon);
        expenseList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.expenseRecycleView);
        helper = new DatabaseHelper(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));




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
