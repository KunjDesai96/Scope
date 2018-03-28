package com.example.kunj.scope;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static android.database.sqlite.SQLiteDatabase.openDatabase;
import static com.example.kunj.scope.ScopeDBHelper.TABLE_EXPENSE;


/**
 * Created by kunj on 2/8/2018.
 */

public class CustomScopeDBHelper {


    public Context context;
    public static SQLiteDatabase db;
    CustomScopeOpenHelper scopeOpenHelper;
    public CustomScopeDBHelper(Context context) {
        this.context = context;
        scopeOpenHelper = new CustomScopeOpenHelper(context);
        db = scopeOpenHelper.getWritableDatabase();

    }


    public static String query;
    public static final String DATABASE_NAME = "scope.db";
    public static final int DATABASE_VERSION = 2;
    String TABLE_NAME;
    public String COLUMN_ID=" ID ";


    public void getQuery(List<String> columnName, String tableName) {
        TABLE_NAME = tableName;
        int i = columnName.size();
        int count = 0;
        StringBuilder sb = new StringBuilder();
        for (String str : columnName) {
            count++;
            if (sb.length() == 0) {
                sb.append("create table if not exists " + TABLE_NAME + "(id integer AUTO_INCREMENT, ");
            }
            if (count != (i)) {
                sb.append(str + " " + "text,");
            } else {
                sb.append(str + " " + "text);");
            }
        }

        query = (sb.toString());

       // System.out.println(query);


    }

    void addEntry(List<String> data,GenericDTO genericDTO)
    {

        List<Object> cols = genericDTO.getAttributeValues("custom_expense");
        System.out.println("Cols:"+cols.size());
        System.out.println("Data"+data.size());
       // List<String> columnName = new ArrayList<>();
        int size = data.size();
        ContentValues values = new ContentValues();
        for(int i =0; i<size;i++)
        {
            //Toast.makeText(context,data.get(i),Toast.LENGTH_SHORT).show();
           values.put(cols.get(i).toString(),data.get(i));
        }
       // db = openDatabase(, null, SQLiteDatabase.OPEN_READWRITE);
        //db = SQLiteDatabase.openDatabase("/data/data/com.example.kunj.scope/databasesscope.db", null,SQLiteDatabase.OPEN_READWRITE);
        long res = db.insert(TABLE_NAME,null,values);
        if(res == -1)
        {
            Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context,"Success",Toast.LENGTH_SHORT).show();

        }

    }

    private  class CustomScopeOpenHelper extends SQLiteOpenHelper {

        public CustomScopeOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);

        }

        @Override
        public void onCreate(SQLiteDatabase db) {
         //  db = SQLiteDatabase.openDatabase(DATABASE_NAME, null,SQLiteDatabase.OPEN_READWRITE);
            db.execSQL(query);
            //System.out.print(CREATE_TABLE);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if(newVersion>oldVersion)
                onCreate(db);

        }
    }
}

