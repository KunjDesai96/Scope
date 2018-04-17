package com.example.kunj.scope;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.example.kunj.scope.R.id.addbtn;
import static com.example.kunj.scope.R.id.clearbtn;
import static com.example.kunj.scope.R.id.data1;
import static com.example.kunj.scope.R.id.textinput_counter;

/**
 * Created by kunj on 1/23/2018.
 */
public class CustomizeFragment extends Fragment {
    Button addbtn;
    EditText data1,data2,data3,data4;
    ListView customlistview;
    CustomScopeDBHelper customScopeDBHelper;
    GenericDTO genericDTO;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.custom_frg,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        customlistview = (ListView) view.findViewById(R.id.customlistview);
        customScopeDBHelper = new CustomScopeDBHelper(getActivity());
        addbtn = (Button) view.findViewById(R.id.addbtn);

        data1 = (EditText) view.findViewById(R.id.data1);
        data2 = (EditText) view.findViewById(R.id.data2);
        data3 = (EditText) view.findViewById(R.id.data3);
        data4 = (EditText) view.findViewById(R.id.data4);
        genericDTO = new GenericDTO();

        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String dataOne = data1.getText().toString();
                final String dataTwo = data2.getText().toString();
                final String dataThree = data3.getText().toString();
                final String dataFour = data4.getText().toString();

                List<String> data = new ArrayList<String>();
                data.add(dataOne);
                data.add(dataTwo);
                data.add(dataThree);
                data.add(dataFour);
                List<String> columnList = new ArrayList<>();
                columnList.add("col1");
                columnList.add("col2");
                columnList.add("col3");
                columnList.add("col4");
                String TABLE_NAME = "custom_expense";
                customScopeDBHelper.getQuery(columnList, TABLE_NAME);

                for(String s:columnList)
                genericDTO.addAttribute(TABLE_NAME,s);


                customScopeDBHelper.addEntry(data,genericDTO);

            }
        });
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              /*  dialog = new Dialog(getActivity());
                dialog.setTitle("Create Record");
                dialog.setContentView(R.layout.layout_dialog);
                Window window = dialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
                dialog.show();


                */



            }
        });
    }
}
