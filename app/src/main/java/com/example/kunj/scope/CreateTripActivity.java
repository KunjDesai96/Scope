package com.example.kunj.scope;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
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

public class CreateTripActivity extends AppCompatActivity {

    Button cancelBTN, createBTN, addBTN;
    EditText tripnameET, startdateET, enddateET,destinationET,emailET;
    AutoCompleteTextView addtravellerACTV;
    ProgressDialog dialog;

    LinearLayout container;
    String User_URL="http://tripexpense.000webhostapp.com/tripexpense/Users.php";
    String CreatTrip_URL="http://tripexpense.000webhostapp.com/Trials/CreateTrip.php";
    String trip_name, trip_startDate, trip_endDate,destination,email;
    List<String> traveller_TRIP =new ArrayList<>();


    private Calendar myCalendar;
    List<String> travellers_list = new ArrayList<>();
    //private  static  final String traveller_names[]=new String[]{"shital","jay","fatullah","shreyansh","shivani","niyati","dip","priya"};
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trip);


        //code to get Registered Users

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(User_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Toast.makeText(CreateTripActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        int count = 0;
                        while (count < response.length()) {

                            try {

                                JSONObject jsonObject = response.getJSONObject(count);
                                travellers_list.add(jsonObject.getString("email"));
                                count++;

                            } catch (JSONException e) {
                                Toast.makeText(getApplicationContext(), "Error while faching data from Server!!!", Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(CreateTripActivity.this);
        requestQueue.add(jsonArrayRequest);

        // Config.getmInstance(getApplicationContext()).addReq(jsonArrayRequest);
        //end of get users
        /*travellers_list.add("Shital");
        travellers_list.add("jay");
        travellers_list.add("fatullah");
        travellers_list.add("shreyansh");
        travellers_list.add("shivani");
        travellers_list.add("niyati");
        travellers_list.add("dip");
        travellers_list.add("priya");*/

        adapter = new ArrayAdapter<String>(CreateTripActivity.this, android.R.layout.simple_dropdown_item_1line, travellers_list);

        tripnameET = (EditText) findViewById(R.id.tripnameET);
        startdateET = (EditText) findViewById(R.id.startdateET);
        enddateET = (EditText) findViewById(R.id.enddateET);
        destinationET=(EditText)findViewById(R.id.destinationET);
        emailET=(EditText)findViewById(R.id.emailET);
        cancelBTN = (Button) findViewById(R.id.cancelBTN);
        createBTN = (Button) findViewById(R.id.createBTN);
        addBTN = (Button) findViewById(R.id.addBTN);
        addtravellerACTV = (AutoCompleteTextView) findViewById(R.id.addtravellerACTV);
        container = (LinearLayout) findViewById(R.id.container);


        addtravellerACTV.setAdapter(adapter);

        startdateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myCalendar = Calendar.getInstance();
                new DatePickerDialog(CreateTripActivity.this, start_date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        enddateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCalendar = Calendar.getInstance();
                new DatePickerDialog(CreateTripActivity.this, end_date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        addBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String traveller;



                LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View addView = inflater.inflate(R.layout.create_trip_add_traveller_row, null);

                AutoCompleteTextView addtravellerACTVROW = (AutoCompleteTextView) addView.findViewById(R.id.addtravellerACTVROW);
                //addtravellerACTVROW.setAdapter(adapter);
                System.out.println(adapter + "Adapter");
                traveller = addtravellerACTV.getText().toString().trim();


                if (travellers_list.contains(traveller)) {

                    addtravellerACTVROW.setText(traveller);

                    traveller_TRIP.add(traveller);

                    travellers_list.remove(traveller);

                    for (String e : travellers_list) {
                        System.out.println(e + "  traveller");
                    }
                    for(String travellerINTrip : traveller_TRIP)
                    {
                        System.out.println(travellerINTrip + " add");
                    }

                    Button removeBTN = (Button) addView.findViewById(R.id.removeBTN);
                    addtravellerACTV.setText("");

                    final View.OnClickListener thisListener = new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {


                            ((LinearLayout) addView.getParent()).removeView(addView);
                            travellers_list.add(traveller);
                            listAllAddView();

                        }


                    };


                    removeBTN.setOnClickListener(thisListener);
                    container.addView(addView);
                    listAllAddView();
                } else
                    addtravellerACTV.setError("enter valid Traveller");


            }
        });
        cancelBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateTripActivity.this, HomePage.class);
                startActivity(intent);
                finish();
            }
        });
        createBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                trip_name = tripnameET.getText().toString().trim();
                trip_startDate = startdateET.getText().toString().trim();
                trip_endDate = enddateET.getText().toString().trim();
                destination=destinationET.getText().toString().trim();
                email=emailET.getText().toString().trim();
                if (trip_name.length() == 0) {
                    tripnameET.setError("Enter Trip Name");
                    tripnameET.setFocusable(true);
                }
                else if(destination.length()<5)
                {
                    destinationET.setError("enter valid Destination");
                    destinationET.setFocusable(true);
                }
                else{
                    createTrip();

                    //Toast.makeText(CreateTripActivity.this,trip_name +""+trip_startDate + ""+trip_endDate+""+traveller_TRIP,Toast.LENGTH_LONG).show();
                    // Intent intent = new Intent(CreateTripActivity.this,NavigationBarActivity.class);
                    //startActivity(intent);
                    //finish();
                }

            }
        });


    }

    private void createTrip() {

        dialog=new ProgressDialog(CreateTripActivity.this);
        dialog.setTitle("Please Wait");
        dialog.setMessage("Validating data");
        dialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, CreatTrip_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                try {
                    JSONObject object = new JSONObject(response);
                    System.out.println("True");
                    System.out.println(response);
                    Toast.makeText(CreateTripActivity.this,response,Toast.LENGTH_LONG).show();

                    if(object.getInt("success")==1)
                    {

                        Intent intent = new Intent(CreateTripActivity.this,HomePage.class);
                        startActivity(intent);
                        finish();

                    }
                    else{
                        Toast.makeText(CreateTripActivity.this,"Error",Toast.LENGTH_SHORT).show();
                        tripnameET.requestFocus();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("functionname","createtrip");
                params.put("tripname",trip_name);
                params.put("startdate",trip_startDate);
                params.put("enddate",trip_endDate);
                params.put("destination",destination);
                params.put("email",email);

                /*final Session session = new Session(CreateTripActivity.this);
                String emaildb=session.getEmail();
                params.put("email",emaildb);*/
                int count=0;
                for(String s:traveller_TRIP)
                {
                    params.put("travellers["+count+"]",s);
                    count++;
                }
                return params;
            }
        };

        Volley.newRequestQueue(CreateTripActivity.this).add(request);

    }

    private void listAllAddView() {

        int childCount = container.getChildCount();

        for (int i = 0; i < childCount; i++) {

            View thisChild = container.getChildAt(i);

            AutoCompleteTextView childTextView = (AutoCompleteTextView) thisChild.findViewById(R.id.addtravellerACTVROW);
            String childTextViewValue = childTextView.getText().toString();

        }

    }

    DatePickerDialog.OnDateSetListener start_date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            updateLabel_startdate();
        }
    };


    private void updateLabel_startdate() {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        startdateET.setText(sdf.format(myCalendar.getTime()));

    }

    DatePickerDialog.OnDateSetListener end_date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            updateLabel_enddate();
        }
    };


    private void updateLabel_enddate() {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        enddateET.setText(sdf.format(myCalendar.getTime()));

    }
}

