package com.pallavi.cerebrocovid;

import android.content.Context;
import android.graphics.ColorSpace;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Context mContext;
    private List<Model> mData;

    public MyAdapter(Context mContext, List<Model> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        v = inflater.inflate(R.layout.covid_item, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.loc.setText(mData.get(position).getLoc());
        holder.totalConfirmed.setText(mData.get(position).getTotalConfirmed());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView loc,totalConfirmed;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            loc = itemView.findViewById(R.id.loc);
            totalConfirmed = itemView.findViewById(R.id.totalConfirmed);

        }
    }

    public void filterList(List<Model> filteredlist){
        mData = filteredlist;
        notifyDataSetChanged();
    }

}
