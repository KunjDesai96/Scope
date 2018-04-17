package com.example.kunj.scope;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by kc on 27/03/2018.
 */

class TripExpenseAdapter extends RecyclerView.Adapter<TripExpenseAdapter.MyViewHolder> {

    final Context context;
    final List<TripExpense> tripExpenses;

    TripExpenseAdapter(Context context, List<TripExpense> tripExpenses) {
        this.context = context;
        this.tripExpenses = tripExpenses;
    }

    @Override
    public TripExpenseAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.tripexpense_list_row,parent,false);

        return new TripExpenseAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TripExpenseAdapter.MyViewHolder holder, int position) {

        final TripExpense objTE = tripExpenses.get(position);
        holder.tripnameTv.setText(objTE.getP_EXPENSE_NAME());
        holder.tripDateTv.setText(objTE.getP_EXPENSE_DATE());
        holder.tripAmountTv.setText(objTE.getP_EXPENSE_AMOUNT());
        int id = context.getResources().getIdentifier(objTE.getP_EXPENSE_CATEGORY(),"mipmap",context.getPackageName());
        // System.out.println(id);
        holder.tripImageview.setImageResource(id);



    }

    @Override
    public int getItemCount() {
        return tripExpenses.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView tripImageview;
        TextView tripnameTv,tripDateTv,tripAmountTv;

        public MyViewHolder(View itemView) {
            super(itemView);

            tripImageview = (ImageView)itemView.findViewById(R.id.tripexpense_row_image);
            tripnameTv = (TextView)itemView.findViewById(R.id.tripexpense_row_ename);
            tripDateTv = (TextView)itemView.findViewById(R.id.tripexpense_row_date);
            tripAmountTv = (TextView)itemView.findViewById(R.id.tripexpense_row_amount);

        }
    }
}
