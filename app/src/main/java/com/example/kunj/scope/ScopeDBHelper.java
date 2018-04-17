package com.example.kunj.scope;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kunj on 10/8/2017.
 */

public class ScopeDBHelper {

    public  static final String DATABASE_NAME = "scope.db";
    public  static final int DATABASE_VERSION = 1;
    public static final String TABLE_EXPENSE = "expense_table";
   // public static final String TABLE_ATTENDANCE = "attendance_table";

    public static final String COLUMN_ID= "ID";
    public static final String COLUMN_CATEGORY= "CATEGORY";
    public static final String COLUMN_DESCRIPTION= "DESCRIPTION";
    public static final String COLUMN_AMOUNT= "AMOUNT";
    public static final String COLUMN_DATE= "DATE";

    public  final String CREATE_TABLE= "create table "+TABLE_EXPENSE+" ( "+COLUMN_ID+ " text, "+COLUMN_CATEGORY+" text , "+COLUMN_DESCRIPTION+" text, " +COLUMN_AMOUNT+" text , "+COLUMN_DATE+" date );";


    public Context context;
    ScopeOpenHelper scopeOpenHelper;
    public static SQLiteDatabase db;

    public ScopeDBHelper(Context context) {
        this.context = context;
        scopeOpenHelper = new ScopeOpenHelper(context);
        db = scopeOpenHelper.getWritableDatabase();
    }

    void addExpense(Expense expense){



        ContentValues values = new ContentValues();
        values.put(COLUMN_CATEGORY, expense.getCategory());
        values.put(COLUMN_DESCRIPTION, expense.getDescription());
        values.put(COLUMN_AMOUNT,expense.getAmount());
        values.put(COLUMN_DATE, expense.getDate());

        long res = db.insert(TABLE_EXPENSE,null,values);

        /*if(res == -1)
        {
            Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context,"Success",Toast.LENGTH_SHORT).show();

        }*/

    }

        List<Expense> getallExpenses()
    {
        List<Expense> expenses = new ArrayList<>();
        Cursor cursor = db.query(TABLE_EXPENSE,null,null,null,null,null,null);

        if(cursor != null && cursor.moveToFirst())
        {
            do {
                String category = cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY));
                String description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
                String amount = cursor.getString(cursor.getColumnIndex(COLUMN_AMOUNT));
                String date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE));

                expenses.add(new Expense(category,description,amount,date) );

            }while (cursor.moveToNext());

        }
        return expenses;
    }

    public int deleteExpense(Expense ex) {

        return db.delete(TABLE_EXPENSE,COLUMN_DESCRIPTION+"=?",new String[]{ex.description});
    }


    private  class ScopeOpenHelper extends SQLiteOpenHelper {
        public ScopeOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(CREATE_TABLE);
            //System.out.print(CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }

}
