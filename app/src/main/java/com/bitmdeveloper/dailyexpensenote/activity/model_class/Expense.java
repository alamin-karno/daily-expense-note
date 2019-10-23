package com.bitmdeveloper.dailyexpensenote.activity.model_class;

public class Expense {
    private String id, expense_type,expense_amount,expense_date,expenseTime,expenseImage;
    public Expense() {
    }

    public Expense(String id,String expense_type, String expense_amount, String expense_date, String expenseTime, String expenseImage) {
        this.id = id;
        this.expense_type = expense_type;
        this.expense_amount = expense_amount;
        this.expense_date = expense_date;
        this.expenseTime = expenseTime;
        this.expenseImage = expenseImage;
    }

    public String getId() {

        return id;
    }


    public String getExpense_type() {
        return expense_type;
    }

    public String getExpense_amount() {
        return expense_amount;
    }

    public String getExpense_date() {
        return expense_date;
    }

    public String getExpenseTime() {
        return expenseTime;
    }

    public String getExpenseImage() {
        return expenseImage;
    }
}
