package com.medeyinlo.darrell.gadsleaderboard.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.medeyinlo.darrell.gadsleaderboard.R;
import com.medeyinlo.darrell.gadsleaderboard.api.SkillLearner;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SkillRecyclerViewAdapter extends RecyclerView.Adapter<SkillRecyclerViewAdapter.ViewHolder> {
    private List<SkillLearner> mSkillLearners;
    private Context mContext;

    public SkillRecyclerViewAdapter(List<SkillLearner> skillLearners, Context context) {
        mSkillLearners = skillLearners;
        mContext = context;
    }

    public void changeDataList(List<SkillLearner> newSkillLearners) {
        if (mSkillLearners != null) {
            mSkillLearners.clear();
        }
        mSkillLearners = newSkillLearners;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.top_skill_item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(mSkillLearners.get(position));
    }

    @Override
    public int getItemCount() {
        if (mSkillLearners != null) {
            return mSkillLearners.size();
        } else return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mLearnerName;
        private TextView mSkillDetails;
        private ImageView mBadgeImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mLearnerName = itemView.findViewById(R.id.skill_name_tv);
            mSkillDetails = itemView.findViewById(R.id.skill_details_tv);
            mBadgeImg = itemView.findViewById(R.id.skill_img_view);
        }

        public void bindData(SkillLearner skillLearner) {

            String name = skillLearner.getName();
            String details = skillLearner.getScore() + " Skill IQ Score, " + skillLearner.getCountry();
            String badgeUrl = skillLearner.getBadgeUrl();

            mLearnerName.setText(name);
            mSkillDetails.setText(details);

            Picasso.get()
                    .load(badgeUrl)
                    .into(mBadgeImg);
        }
    }
}
