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

import com.medeyinlo.darrell.gadsleaderboard.adapter.HourRecyclerViewAdapter;
import com.medeyinlo.darrell.gadsleaderboard.api.HourLearner;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopLearnersFragment extends Fragment {
    private static final String TAG = "TopLearnersFragment";
    public static RecyclerView mHourRecyclerView;
    private List<HourLearner> mHourLearners;
    private HourRecyclerViewAdapter mHourAdapter;

    public TopLearnersFragment() {
        // Required empty public constructor
    }


    public static TopLearnersFragment newInstance() {

        return new TopLearnersFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_top_learners, container, false);

        loadRecyclerView(view);

        loadDataList(view);

        return view;
    }

    private void loadDataList(View view) {
        ProgressBar hourProgress = view.findViewById(R.id.hour_prog_bar);
        hourProgress.setVisibility(View.VISIBLE);
        Call<List<HourLearner>> call = MainActivity.mLeaderboardApiService.hourLearners();

        call.enqueue(new Callback<List<HourLearner>>() {
            @Override
            public void onResponse(@NonNull Call<List<HourLearner>> call, @NonNull Response<List<HourLearner>> response) {
                hourProgress.setVisibility(View.GONE);
                mHourLearners = response.body();
                mHourAdapter.changeDataList(mHourLearners);
            }

            @Override
            public void onFailure(@NonNull Call<List<HourLearner>> call, @NonNull Throwable t) {
                hourProgress.setVisibility(View.GONE);
                Log.e(TAG, "onFailure: " + t.toString(), t);
            }
        });
    }


    private void loadRecyclerView(View view) {
        mHourRecyclerView = view.findViewById(R.id.top_hours_recyclerview);
        mHourRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mHourAdapter = new HourRecyclerViewAdapter(mHourLearners);
        mHourRecyclerView.setAdapter(mHourAdapter);
    }
}