package com.bitmdeveloper.dailyexpensenote.activity.fragments;


import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitmdeveloper.dailyexpensenote.R;
import com.bitmdeveloper.dailyexpensenote.activity.adapter.ExpenseAdapter;
import com.bitmdeveloper.dailyexpensenote.activity.database.DatabaseHelper;
import com.bitmdeveloper.dailyexpensenote.activity.model_class.Expense;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExpenseFragment extends Fragment {
    List<Expense> expenseList;
    private RecyclerView recyclerView;
    private ExpenseAdapter adapter;
    private DatabaseHelper helper;
    public ExpenseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_expense, container, false);
        init(view);

        getdta(view);
        return view;
    }

    private void getdta(View view) {
        Cursor cursor = helper.getData();

        while (cursor.moveToNext()){
            String expense_type = cursor.getString(cursor.getColumnIndex(helper.COL_TYPE));
            String expense_date = cursor.getString(cursor.getColumnIndex(helper.COL_DATE));
            String expense_amount = cursor.getString(cursor.getColumnIndex(helper.COL_AMOUNT));

            expenseList.add(new Expense(expense_type,expense_date,expense_amount));
            adapter.notifyDataSetChanged();
        }
    }


    private void init(View view) {
        expenseList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.expenseRecycleView);
        helper = new DatabaseHelper(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ExpenseAdapter(expenseList,getContext());
        recyclerView.setAdapter(adapter);
    }


}
