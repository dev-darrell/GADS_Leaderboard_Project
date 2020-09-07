package com.medeyinlo.darrell.gadsleaderboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.medeyinlo.darrell.gadsleaderboard.adapter.SkillRecyclerViewAdapter;
import com.medeyinlo.darrell.gadsleaderboard.api.SkillLearner;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TopSkillFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TopSkillFragment extends Fragment {
    private static final String TAG = "TopSkillFragment";
    public static RecyclerView mSkillRecyclerView;
    private List<SkillLearner> mSkillLearners = null;
    private SkillRecyclerViewAdapter mSkillAdapter;

    public TopSkillFragment() {
        // Required empty public constructor
    }


    public static TopSkillFragment newInstance() {
        TopSkillFragment fragment = new TopSkillFragment();
        return fragment;
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

        Call<List<SkillLearner>> skillCall = MainActivity.mLeaderboardApiService.skillLearners();

        skillCall.enqueue(new Callback<List<SkillLearner>>() {
            @Override
            public void onResponse(Call<List<SkillLearner>> call, Response<List<SkillLearner>> response) {
                mSkillLearners = response.body();
                Log.d(TAG, "onResponse: Changing Adapter Values");
                mSkillAdapter.changeDataList(mSkillLearners);
            }

            @Override
            public void onFailure(Call<List<SkillLearner>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString(), t);
            }
        });

        return view;
    }

    private void loadRecyclerViewLayout(View view) {
        mSkillRecyclerView = view.findViewById(R.id.recyclerview_top_skills);
        Log.d(TAG, "loadRecyclerViewLayout: Adding Adapter");
        mSkillAdapter = new SkillRecyclerViewAdapter(mSkillLearners);
        mSkillRecyclerView.setAdapter(mSkillAdapter);
        mSkillRecyclerView.setHasFixedSize(true);
        mSkillRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}
