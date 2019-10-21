package com.bitmdeveloper.dailyexpensenote.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bitmdeveloper.dailyexpensenote.R;
import com.bitmdeveloper.dailyexpensenote.activity.model_class.Expense;
import com.google.android.material.navigation.NavigationView;

import java.sql.Array;
import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ViewHolder> {
    List<Expense> expenses;
    Context context;

    public ExpenseAdapter(List<Expense> expenses, Context context) {
        this.expenses = expenses;
        this.context = context;
    }

    @NonNull
    @Override
    public ExpenseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_design_expense,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseAdapter.ViewHolder holder, int position) {
        Expense expense = expenses.get(position);
        holder.expense_typeTV.setText(expense.getExpense_type());
        holder.expense_dateTV.setText(expense.getExpense_date());
        holder.expense_amountTV.setText(expense.getExpense_amount());


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context,R.layout.support_simple_spinner_dropdown_item,holder.type);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        holder.update_delete_SP.setAdapter(arrayAdapter);

        holder.update_delete_SP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selecttype = adapterView.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Details View", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView expense_typeTV,expense_dateTV,expense_amountTV;
        private Spinner update_delete_SP;
        private String[] type = {"","Update","Delete"};
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            expense_typeTV = itemView.findViewById(R.id.expense_typeTV);
            expense_dateTV = itemView.findViewById(R.id.expense_dateTV);
            expense_amountTV = itemView.findViewById(R.id.expense_amountTV);
            update_delete_SP = itemView.findViewById(R.id.update_deleteSP);
        }
    }
}
