package com.bitmdeveloper.dailyexpensenote.activity.adapter;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bitmdeveloper.dailyexpensenote.R;
import com.bitmdeveloper.dailyexpensenote.activity.activity.MainActivity;
import com.bitmdeveloper.dailyexpensenote.activity.database.DatabaseHelper;
import com.bitmdeveloper.dailyexpensenote.activity.fragments.ExpenseFragment;
import com.bitmdeveloper.dailyexpensenote.activity.model_class.Expense;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;

import java.sql.Array;
import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ViewHolder> {
    List<Expense> expenses;
    Context context;
    private TextView expenseType,expenseAmount,expenseDate,expenseTime;
    private Button showDocumentBtn;
    private static DatabaseHelper databaseHelper;
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
    public void onBindViewHolder(@NonNull final ExpenseAdapter.ViewHolder holder, int position) {
        final Expense expense = expenses.get(position);
        holder.expense_typeTV.setText(expense.getExpense_type());
        holder.expense_dateTV.setText(expense.getExpense_date());
        holder.expense_amountTV.setText(expense.getExpense_amount());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View view1 = LayoutInflater.from(context).inflate(R.layout.itemview_bottom_sheet,null);
                expenseType = view1.findViewById(R.id.expense_TypeTV);
                expenseAmount = view1.findViewById(R.id.expense_AmountTV);
                expenseDate = view1.findViewById(R.id.expense_DateTV);
                expenseTime = view1.findViewById(R.id.expense_TimeTV);
                showDocumentBtn = view1.findViewById(R.id.showDocumentBtnId);

                expenseType.setText(expense.getExpense_type());
                expenseAmount.setText(expense.getExpense_amount()+" BDT");
                expenseDate.setText(expense.getExpense_date());

                if(expense.getExpenseTime().isEmpty()){
                    expenseTime.setText("Time not added.");
                }
                else {
                    expenseTime.setText(expense.getExpenseTime());
                }
                showDocumentBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Dialog dialog = new Dialog(context);
                        View view2 = LayoutInflater.from(context).inflate(R.layout.docview_layout_design,null);
                        dialog.setTitle("Document of "+expense.getExpense_type());
                        dialog.setContentView(view2);

                        ImageView doc_ImageView = view2.findViewById(R.id.imageViewLayout);
                        doc_ImageView.setImageBitmap(stringToBitmap(expense.getExpenseImage()));
                        dialog.show();
                    }
                });
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
                bottomSheetDialog.setContentView(view1);
                bottomSheetDialog.show();
            }
        });

        holder.update_delete_SP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context,view);
                popupMenu.getMenuInflater().inflate(R.menu.update_delect_nav,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.nav_update:
                                Toast.makeText(context, "Update", Toast.LENGTH_SHORT).show();

                            case R.id.nav_delete:
                                Toast.makeText(context, "Delete", Toast.LENGTH_SHORT).show();
                                return true;
                        }
                        return false;
                    }
                });
               popupMenu.show();
            }
        });


    }

    private Bitmap stringToBitmap(String expenseImage) {

    try {
        byte [] eByte = Base64.decode(expenseImage,Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(eByte,0,eByte.length);
        return bitmap;
    }
    catch (Exception e){
        e.getMessage();
        return null;
    }
    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView expense_typeTV,expense_dateTV,expense_amountTV;
        private ImageView update_delete_SP;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            expense_typeTV = itemView.findViewById(R.id.expense_typeTV);
            expense_dateTV = itemView.findViewById(R.id.expense_dateTV);
            expense_amountTV = itemView.findViewById(R.id.expense_amountTV);
            update_delete_SP = itemView.findViewById(R.id.update_deleteSP);
        }
    }
}
