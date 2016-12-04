package com.example.mkass.evson;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import entities.Evenement;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private final List<Evenement> evenements = new ArrayList<Evenement>();

    @Override
    public int getItemCount() {
        return evenements.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Evenement ev = evenements.get(position);
        holder.display(ev);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        public MyViewHolder(final View itemView) {
            super(itemView);

        }

        public void display(Evenement ev) {

        }
    }

}