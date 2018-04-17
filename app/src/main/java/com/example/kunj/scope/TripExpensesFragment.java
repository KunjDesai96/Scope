package com.example.kunj.scope;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
//import static nl.qbusict.cupboard.CupboardFactory.cupboard;
/**
 * A simple {@link Fragment} subclass.
 */
public class TripExpensesFragment extends Fragment {

    RecyclerView tripExpenserView;
    FloatingActionButton fab;
    Context context = getContext();
    String TripExpenseAdd_URL = "http://scopeapp.000webhostapp.com/scope/expense.php";
    String TripExpenseRetrive_URL="http://scopeapp.000webhostapp.com/scope/expenseData.php";
    List<TripExpense> tripExpenses;
    TripExpenseAdapter tripAdapter;
    String tripexpense_name, tripexpense_amount, tripexpense_category, tripexpense_date;
    private DatePicker datePicker;
    private Calendar myCalendar;
    private int year, month, day;
    EditText tripexpense_nameET, tripexpense_amountET, tripexpense_dateET;
    Spinner tripexpense_categorySPN;
    Button tripexpense_addBTN, tripexpense_cancelBTN;

    //Intent intent;
    //Bundle extra=intent.getExtras();
    //String tripname=extra.getString("tripname");


    public TripExpensesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trip_expenses, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final String spinner_category[] = {"Category", "food", "entertainment", "shopping", "travel"};

        final Context context = getActivity();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, spinner_category);


        final CoordinatorLayout coordinatorLayout = (CoordinatorLayout)view.findViewById(R.id.coordinator_tripexpense);
        fab = (FloatingActionButton)view.findViewById(R.id.fab_tripexpenseFragment);
        tripExpenserView = (RecyclerView)view.findViewById(R.id.tripexpenserecyclerview);

        tripExpenses = new ArrayList<>();
         /*tripExpenses.add(new TripExpense("name","food","800","24/3/2017"));
        tripExpenses.add(new TripExpense("name","entertainment","800","24/3/2017"));
        tripExpenses.add(new TripExpense("name","shopping","800","24/3/2017"));
        tripExpenses.add(new TripExpense("name","travel","800","24/3/2017"));
        tripExpenses.add(new TripExpense("name","entertainment","800","24/3/2017"));
        tripExpenses.add(new TripExpense("name","entertainment","800","24/3/2017"));
        */

        showTripExpense();



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.pexpense_popup, null);
                int width = CoordinatorLayout.LayoutParams.MATCH_PARENT;
                int height = CoordinatorLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true;
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
                popupWindow.showAtLocation(coordinatorLayout, Gravity.CENTER, 0, 0);


                tripexpense_nameET = (EditText) popupView.findViewById(R.id.pexpense_nameET);
                tripexpense_amountET = (EditText) popupView.findViewById(R.id.pexpense_amountET);
                tripexpense_categorySPN = (Spinner) popupView.findViewById(R.id.pexpense_categorySPN);
                tripexpense_addBTN = (Button) popupView.findViewById(R.id.pexpense_addBTN);
                tripexpense_dateET = (EditText) popupView.findViewById(R.id.pexpense_dateET);
                tripexpense_cancelBTN = (Button) popupView.findViewById(R.id.pexpense_cancelBTN);

                tripexpense_categorySPN.setAdapter(adapter);

                tripexpense_cancelBTN.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        popupWindow.dismiss();
                        return true;
                    }
                });

                tripexpense_categorySPN.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (i == 0) {
                            tripexpense_categorySPN.requestFocus();
                        } else {
                            tripexpense_category = spinner_category[i];
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                        tripexpense_categorySPN.requestFocus();

                    }
                });


                tripexpense_dateET.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        myCalendar = Calendar.getInstance();
                        new DatePickerDialog(context, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH)).show();

                    }
                });

                tripexpense_addBTN.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tripexpense_name = tripexpense_nameET.getText().toString().trim();
                        tripexpense_amount = tripexpense_amountET.getText().toString().trim();
                        tripexpense_category = tripexpense_categorySPN.getSelectedItem().toString().trim();
                        tripexpense_date = tripexpense_dateET.getText().toString().trim();
                        System.out.println(tripexpense_date + "In addButton call");
                        if (tripexpense_category.equalsIgnoreCase("category")) {
                            setSpinnerError(tripexpense_categorySPN, "Invalid category selected");
                        }
                        else if(tripexpense_name.length()==0){
                            tripexpense_nameET.setError("Enter Trip Expense Name");
                            tripexpense_nameET.requestFocus();
                        }
                        else if(tripexpense_amount.length()==0){
                            tripexpense_amountET.setError("Enter Amount");
                            tripexpense_amountET.requestFocus();
                        }
                        else if(tripexpense_date.length()==0)
                        {
                            tripexpense_dateET.setError("Enter date");
                            tripexpense_dateET.requestFocus();
                        }
                        else {

                            //db = dbHelper.getWritableDatabase();
                            //PersonalExpense data = new PersonalExpense(tripexpense_name, tripexpense_category, tripexpense_amount, pexpense_date);
                            // Long l = cupboard().withDatabase(db).put(data);
                            addTripExpense();
                            popupWindow.dismiss();
                            // Toast.makeText(getActivity(), "expense added", Toast.LENGTH_LONG).show();
                            //System.out.println(data + "  " + l);

                        }
                    }
                });


            }

        });


    }

    private void showTripExpense() {

        final Context context=getContext();
        StringRequest request = new StringRequest(Request.Method.POST, TripExpenseRetrive_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    //Toast.makeText(context,response,Toast.LENGTH_LONG).show();
                    System.out.println(response+"json");

                    JSONArray jsonArray =new JSONArray(response);
                    int count=0;
                    while (count<jsonArray.length()){
                        JSONObject jsonObject=jsonArray.getJSONObject(count);
                        //String tripnamedb = jsonObject.getString("tripname");
                        // Toast.makeText(context,"INdata",Toast.LENGTH_LONG).show();
                        //System.out.println(tripnamedb+"trips");
                        String expensenamedb=jsonObject.getString("exp_name");
                        String categorydb=jsonObject.getString("category");
                        String amountdb = jsonObject.getString("amount");
                        String datedb=jsonObject.getString("exp_date");
                        tripExpenses.add(new TripExpense(expensenamedb,categorydb,amountdb,datedb));
                        count++;
                    }

                    tripAdapter = new TripExpenseAdapter(context,tripExpenses);
                    tripExpenserView.setAdapter(tripAdapter);

                    LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                    tripExpenserView.setLayoutManager(manager);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context,"Newtwork Error!!",Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();


                final Session session = new Session(context);
                String email=session.getEmail();

                params.put("email",email);


                return params;
            }
        };

        Volley.newRequestQueue(context).add(request);

    }

    private void addTripExpense() {

        StringRequest request = new StringRequest(Request.Method.POST, TripExpenseAdd_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //dialog.dismiss();

                try {

                    JSONObject obj = new JSONObject(response);
                    System.out.println("True");
                    System.out.println(response);

                    if(obj.getInt("success")==1)
                    {

                        Toast.makeText(context,obj.getInt("success"),Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(context, HomePage.class);
                        startActivity(intent);

                    }
                    else {

                        Toast.makeText(context,"Invalid",Toast.LENGTH_LONG).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context,"Newtwork Error!!",Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("functionname","expense");

                params.put("email","gagan@gmail.com");

                params.put("exp_name",tripexpense_name);

                params.put("category",tripexpense_category);

                params.put("amount",tripexpense_amount);

                params.put("exp_date",tripexpense_date);

                params.put("tripname","Rishabh");

                return params;
            }
        };

        Volley.newRequestQueue(getContext()).add(request);


    }


    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            updateLabel();
        }
    };


    private void updateLabel() {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        tripexpense_dateET.setText(sdf.format(myCalendar.getTime()));

    }

    private void setSpinnerError(Spinner spinner, String error) {
        View selectedView = spinner.getSelectedView();
        if (selectedView != null && selectedView instanceof TextView) {
            spinner.requestFocus();
            TextView selectedTextView = (TextView) selectedView;
            selectedTextView.setError("error"); // any name of the error will do
            selectedTextView.setTextColor(Color.RED); //text color in which you want your error message to be displayed
            selectedTextView.setText(error); // actual error message
            spinner.performClick(); // to open the spinner list if error is found.

        }




    }
}
