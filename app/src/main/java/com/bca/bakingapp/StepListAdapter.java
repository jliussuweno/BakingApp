package com.bca.bakingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StepListAdapter extends RecyclerView.Adapter<StepListAdapter.StepViewHolder> {

    private final LayoutInflater mInflater;
    private List<Step> mSteps;
    private StepCallback callback = null;

    StepListAdapter(Context context){
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.step_item, parent, false);
        return new StepViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
        if (mSteps != null){
            Step current = mSteps.get(position);
            holder.nameItemView.setText(current.getIdStep() + ". " + current.getDescription());
            holder.getView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (callback != null){
                        callback.stepPressed(current);
                    }
                }
            });
        } else {
            holder.nameItemView.setText("No Task");
        }
    }

    void setmSteps(List<Step> steps){
        mSteps = steps;
        notifyDataSetChanged();
    }

    void setCallback(StepCallback mCallback) {
        callback = mCallback;
    }
    @Override
    public int getItemCount() {
        if (mSteps != null)
            return mSteps.size();
        else return 0;
    }

    class StepViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameItemView;
        private final View parent;


        private StepViewHolder(View itemView){
            super(itemView);
            parent = itemView;
            nameItemView = itemView.findViewById(R.id.textViewStepName);
        }

        private View getView(){
            return parent;
        }
    }

}
