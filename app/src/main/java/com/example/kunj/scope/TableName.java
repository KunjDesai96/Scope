package com.example.kunj.scope;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class TableName extends AppCompatActivity {

    EditText recordName,columnCount;
    Button tableNext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_name);

        recordName = (EditText) findViewById(R.id.recordName);
        tableNext = (Button) findViewById(R.id.TableNext);

        tableNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(TableName.this,ColumnNames.class);
                startActivity(intent);
            }
        });


    }
}
