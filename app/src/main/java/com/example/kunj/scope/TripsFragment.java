package com.example.kunj.scope;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TripsFragment extends Fragment {
    FloatingActionButton fab;
    RecyclerView tripsRview;
    List<TRIP> tripList;
    TripsAdapter tripsAdapter;
    String Trips_URL="http://scopeapp.000webhostapp.com/scope/tripname.php";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trips, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Context context=getContext();
        fab=(FloatingActionButton)view.findViewById(R.id.fab_tripFragment);

        tripsRview = (RecyclerView)view.findViewById(R.id.triprecyclerview);

        //final CoordinatorLayout cordinateLayout = (CoordinatorLayout) view.findViewById(R.id.coordinate_personalFragment);
        tripList = new ArrayList<>();

        /*tripList.add(new TRIP("Bombay"));
        tripList.add(new TRIP("laher"));
        tripList.add(new TRIP("everest"));
        tripList.add(new TRIP("jambu"));
        tripList.add(new TRIP("Kashmir"));
        tripList.add(new TRIP("tavi"));*/

        retriveTrips();




        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(view.getContext(),CreateTripActivity.class);
                startActivity(intent);

            }
        });
    }

    private void retriveTrips() {

        final Context context=getContext();
        StringRequest request = new StringRequest(Request.Method.POST, Trips_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    //Toast.makeText(context,response,Toast.LENGTH_LONG).show();
                    System.out.println(response+"json");

                    JSONArray jsonArray =new JSONArray(response);
                    int count=0;
                    while (count<jsonArray.length()){
                        JSONObject jsonObject=jsonArray.getJSONObject(count);
                        String tripnamedb = jsonObject.getString("tripname");
                        // Toast.makeText(context,"INdata",Toast.LENGTH_LONG).show();
                        System.out.println(tripnamedb+"trips");
                        tripList.add(new TRIP(tripnamedb));
                        count++;
                    }

                    tripsAdapter = new TripsAdapter(getContext(),tripList);
                    tripsRview.setAdapter(tripsAdapter);

                    LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                    tripsRview.setLayoutManager(manager);

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
}
