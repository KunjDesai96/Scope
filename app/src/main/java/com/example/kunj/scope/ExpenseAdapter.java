package com.example.kunj.scope;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by kunj on 11/27/2017.
 */

public class ExpenseAdapter extends BaseAdapter {

    public final HomePage homePage;
    public final List<Expense> expenses;

    public ExpenseAdapter(HomePage homePage, List<Expense> expenses) {

    this.homePage = homePage;
    this.expenses = expenses;

    }

    @Override
    public int getCount() {
        return expenses.size();
    }

    @Override
    public Object getItem(int i) {
        return expenses.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view  = LayoutInflater.from(homePage).inflate(R.layout.list_row,viewGroup,false);
        TextView exname = (TextView)view.findViewById(R.id.expensename);
        TextView excat = (TextView)view.findViewById(R.id.expensecategory);
        TextView amount = (TextView)view.findViewById(R.id.amount);
        TextView date = (TextView) view.findViewById(R.id.date);
        ImageView categoryImage= (ImageView) view.findViewById(R.id.categoryImage);

        Expense expense  = expenses.get(i);

        //Example


        String categoryString = expense.getCategory();
        //Toast.makeText(homePage,categoryString,Toast.LENGTH_LONG).show();
        String imagefile = categoryString.toLowerCase();
        int resource = view.getResources().getIdentifier(imagefile, "drawable", homePage.getPackageName());
        categoryImage.setImageResource(resource);

        exname.setText(expense.getDescription());
        excat.setText(expense.getCategory());
        amount.setText(expense.getAmount());
        date.setText(expense.getDate());
        return view;
    }

    public void addExpense(Expense expense) {

        expenses.add(expense);
    }

    public void delete(Expense ex) {

        expenses.remove(ex);
    }
}
