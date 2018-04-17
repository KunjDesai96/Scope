package com.example.kunj.scope;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by kc on 26/03/2018.
 */

class TripsAdapter extends RecyclerView.Adapter<TripsAdapter.MyViewHolder> {

    final Context context;
    final List<TRIP> tripList;

    TripsAdapter(Context context, List<TRIP> tripList) {
        this.context = context;
        this.tripList = tripList;
    }


    @Override
    public TripsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.trip_list_row,parent,false);

        return new TripsAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TripsAdapter.MyViewHolder holder, int position) {
        final TRIP trip = tripList.get(position);
        final Context contain=context.getApplicationContext();
        final String tripname=trip.getTRIP_NAME();
        holder.tripnameTV.setText(trip.getTRIP_NAME());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle b=new Bundle();
                b.putString("tripname",tripname);
                Intent intent = new Intent(context,TripExpenseReportActivity.class);
                intent.putExtras(b);
                System.out.println(tripname);
                context.startActivity(intent);

            }
        });




    }

    @Override
    public int getItemCount() {
        return tripList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tripnameTV;
        public MyViewHolder(View itemView) {
            super(itemView);

            tripnameTV =(TextView)itemView.findViewById(R.id.tripnameTV);

        }
    }
}

