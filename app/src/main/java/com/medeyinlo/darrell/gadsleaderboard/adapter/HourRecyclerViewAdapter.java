package com.medeyinlo.darrell.gadsleaderboard.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.medeyinlo.darrell.gadsleaderboard.R;
import com.medeyinlo.darrell.gadsleaderboard.api.HourLearner;

import java.util.List;

public class HourRecyclerViewAdapter extends RecyclerView.Adapter<HourRecyclerViewAdapter.ViewHolder> {
    private List<HourLearner> mHourLearners;

    public HourRecyclerViewAdapter(List<HourLearner> hourLearners) {
        mHourLearners = hourLearners;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.top_learner_item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(mHourLearners.get(position));
    }

    @Override
    public int getItemCount() {
        return mHourLearners.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mLearnerName;
        private TextView mHourDetails;
        private ImageView mImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mLearnerName = itemView.findViewById(R.id.hour_name_tv);
            mHourDetails = itemView.findViewById(R.id.hour_details_tv);
            mImageView = itemView.findViewById(R.id.hour_img_view);
        }

        public void bindData(HourLearner hourLearner) {
            String name = hourLearner.getName();
            String details = hourLearner.getHours() + " learning hours, " +
                    hourLearner.getCountry();
//            URL imageUrl = hourLearner.getBadgeUrl();

            mLearnerName.setText(name);
            mHourDetails.setText(details);
        }
    }
}
