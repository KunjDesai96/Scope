package com.example.kunj.scope;

/**
 * Created by kunj on 10/8/2017.
 */

public class Expense {
    public final String category;
    public final String description;
    public final String amount;
    public final String date;

    public Expense(String category, String description, String amount, String date) {
        this.category = category;
        this.description = description;
        this.amount = amount;
        this.date = date;
    }



    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }
}
