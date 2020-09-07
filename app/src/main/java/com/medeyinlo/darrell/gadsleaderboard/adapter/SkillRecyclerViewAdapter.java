package com.medeyinlo.darrell.gadsleaderboard.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.medeyinlo.darrell.gadsleaderboard.R;
import com.medeyinlo.darrell.gadsleaderboard.api.SkillLearner;

import java.net.URL;
import java.util.List;

public class SkillRecyclerViewAdapter extends RecyclerView.Adapter<SkillRecyclerViewAdapter.ViewHolder> {
    private List<SkillLearner> mSkillLearners;

    public SkillRecyclerViewAdapter(List<SkillLearner> skillLearners) {
        mSkillLearners = skillLearners;
    }

    public void changeDataList(List<SkillLearner> newSkillLearners) {
        if (mSkillLearners != null) {
            mSkillLearners.clear();
        }
        mSkillLearners = newSkillLearners;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.top_skill_item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(mSkillLearners.get(position));
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mLearnerName;
        private TextView mSkillDetails;
        private TextView mBadgeImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mLearnerName = itemView.findViewById(R.id.skill_name_tv);
            mSkillDetails = itemView.findViewById(R.id.skill_details_tv);
            mBadgeImg = itemView.findViewById(R.id.skill_img_view);
        }

        public void bindData(SkillLearner skillLearner) {
            String name = skillLearner.getName();
            String details = skillLearner.getScore() + " Skill IQ Score, " + skillLearner.getCountry();
            URL badgeUrl = skillLearner.getBadgeUrl();

            mLearnerName.setText(name);
            mSkillDetails.setText(details);
        }
    }
}
