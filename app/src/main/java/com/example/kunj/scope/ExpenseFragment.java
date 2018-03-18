package com.example.kunj.scope;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by kunj on 1/23/2018.
 */
public class ExpenseFragment extends Fragment implements AdapterView.OnItemLongClickListener {

    Dialog dialog;
    Spinner categorySpinner;
    ArrayAdapter<String> categoryAdapter;
    EditText descriptionEt,amountEt,dateEt;
    Button addbtn,clearbtn,intentbtn;
    ScopeDBHelper scopeDBHelper;
    List<Expense> expenses;
    ListView expenselistview;
    ExpenseAdapter expenseAdapter;
    Calendar myCalendar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.expense_frg,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        expenselistview = (ListView)view.findViewById(R.id.expenselistview);
        scopeDBHelper =  new ScopeDBHelper(getActivity());
        expenses = scopeDBHelper.getallExpenses();

        expenseAdapter = new ExpenseAdapter((HomePage) getActivity(),expenses);
        expenselistview.setAdapter(expenseAdapter);
        expenselistview.setOnItemLongClickListener(this);
        myCalendar = Calendar.getInstance();


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();


            }

        };


        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog = new Dialog(getActivity());
                dialog.setTitle("Add Expense");
                dialog.setContentView(R.layout.layout_dialog);
                Window window = dialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
                dialog.show();

                categorySpinner = (Spinner) dialog.findViewById(R.id.category);
                descriptionEt= (EditText) dialog.findViewById(R.id.description);
                amountEt = (EditText) dialog.findViewById(R.id.amount);
                dateEt = (EditText) dialog.findViewById(R.id.date);
                addbtn = (Button) dialog.findViewById(R.id.addbtn);
                clearbtn = (Button) dialog.findViewById(R.id.clearbtn);

                String items [] ={"Entertainment","Food","Movie","Goods","Fuel","Other"};


                categoryAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,items);
                categorySpinner.setAdapter(categoryAdapter);




                dateEt.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        //        Toast.makeText(HomePage.this,"Clicked",Toast.LENGTH_SHORT).show();

                        // TODO Auto-generated method stub
                        new DatePickerDialog(getActivity(), date, myCalendar
                                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });

                addbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        final String categorystrng = categorySpinner.getSelectedItem().toString();
                        final String descriptionstrng = descriptionEt.getText().toString();
                        final String amountstrng = amountEt.getText().toString();
                        final String datestrng = dateEt.getText().toString();

                        /*if(categorystrng.isEmpty())
                        {
                            categoryEt.setError("Mandatory field");
                            return;
                        }*/
                        if(descriptionstrng.isEmpty())
                        {
                            descriptionEt.setError("Mandatory field");
                            return;
                        }
                        if(amountstrng.isEmpty())
                        {
                            amountEt.setError("Mandatory field");return;
                        }



                        Expense expense = new Expense(categorystrng,descriptionstrng,amountstrng,datestrng);
                        //Toast.makeText(HomePage.this, categorystrng,Toast.LENGTH_SHORT).show();
                        scopeDBHelper.addExpense(expense);
                        expenseAdapter.addExpense(expense);
                        expenseAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });


            }
        });

    }

    private void updateLabel() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dateEt.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

       final Expense ex = expenses.get(i);

        final AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setTitle("Delete Expense");
        dialog.setMessage("Do you want to delete this entry?");


        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


                if(scopeDBHelper.deleteExpense(ex)==1)
                {
                    Toast.makeText(getContext(),"Expense Deleted",Toast.LENGTH_SHORT).show();
                    expenseAdapter.delete(ex);
                    expenseAdapter.notifyDataSetChanged();


                }
                else
                {
                    Toast.makeText(getContext(),"Couldn't delete this entry",Toast.LENGTH_SHORT).show();

                }


            }
        });
        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


            }
        });

        dialog.show();

        return false;
    }
}
