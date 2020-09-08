package com.medeyinlo.darrell.gadsleaderboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.medeyinlo.darrell.gadsleaderboard.adapter.SkillRecyclerViewAdapter;
import com.medeyinlo.darrell.gadsleaderboard.api.SkillLearner;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopSkillFragment extends Fragment {
    private static final String TAG = "TopSkillFragment";
    public static RecyclerView mSkillRecyclerView;
    private List<SkillLearner> mSkillLearners = null;
    private SkillRecyclerViewAdapter mSkillAdapter;

    public TopSkillFragment() {
        // Required empty public constructor
    }


    public static TopSkillFragment newInstance() {
        return new TopSkillFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_top_skill, container, false);

        loadRecyclerViewLayout(view);

        loadDataList(view);

        return view;
    }

    private void loadDataList(View view) {
        ProgressBar skillProgress = view.findViewById(R.id.skill_prog_bar);
        skillProgress.setVisibility(View.VISIBLE);

        Call<List<SkillLearner>> skillCall = MainActivity.mLeaderboardApiService.skillLearners();

        skillCall.enqueue(new Callback<List<SkillLearner>>() {
            @Override
            public void onResponse(@NonNull Call<List<SkillLearner>> call, @NonNull Response<List<SkillLearner>> response) {
                skillProgress.setVisibility(View.GONE);
                mSkillLearners = response.body();
                mSkillAdapter.changeDataList(mSkillLearners);
            }

            @Override
            public void onFailure(@NonNull Call<List<SkillLearner>> call, @NonNull Throwable t) {
                skillProgress.setVisibility(View.GONE);
                Log.e(TAG, "onFailure: " + t.toString(), t);
            }
        });
    }

    private void loadRecyclerViewLayout(View view) {
        mSkillRecyclerView = view.findViewById(R.id.recyclerview_top_skills);
        mSkillRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mSkillAdapter = new SkillRecyclerViewAdapter(mSkillLearners, getContext());
        mSkillRecyclerView.setAdapter(mSkillAdapter);
    }
}
