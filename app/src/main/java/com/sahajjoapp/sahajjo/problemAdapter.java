package com.sahajjoapp.sahajjo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class problemAdapter extends RecyclerView.Adapter<problemAdapter.ViewHolder> {

    Context context;
    ArrayList<Problem> problemArrayList;

    public problemAdapter(Context context, ArrayList<Problem> problemArrayList) {
        this.context = context;
        this.problemArrayList = problemArrayList;
    }

    @NonNull
    @Override
    public problemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.problems, parent, false);
        return new problemAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull problemAdapter.ViewHolder holder, int position) {
        Problem problem = problemArrayList.get(position);
        holder.problemTitle.setText( "Problem: " + problem.getProblemTitle());
        holder.problemDescription.setText("Details: " +problem.getProblemDescription());
        holder.problemLocation.setText("Location: " +problem.getProblemLocation());
    }

    @Override
    public int getItemCount() {
        return problemArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView problemTitle, problemDescription, problemLocation;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            problemTitle = itemView.findViewById(R.id.problemTitle);
            problemDescription = itemView.findViewById(R.id.problemDescription);
            problemLocation = itemView.findViewById(R.id.problemLocation);
        }
    }
}
