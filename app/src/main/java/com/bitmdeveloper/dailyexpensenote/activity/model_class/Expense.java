package com.bitmdeveloper.dailyexpensenote.activity.model_class;

public class Expense {
    private String expense_type,expense_date,expense_amount;

    public Expense() {
    }

    public Expense(String expense_type, String expense_date, String expense_amount) {
        this.expense_type = expense_type;
        this.expense_date = expense_date;
        this.expense_amount = expense_amount;
    }

    public String getExpense_type() {
        return expense_type;
    }

    public String getExpense_date() {
        return expense_date;
    }

    public String getExpense_amount() {
        return expense_amount;
    }
}
