package com.medeyinlo.darrell.gadsleaderboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.medeyinlo.darrell.gadsleaderboard.adapter.HourRecyclerViewAdapter;
import com.medeyinlo.darrell.gadsleaderboard.api.HourLearner;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TopLearnersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TopLearnersFragment extends Fragment {
    private static final String TAG = "TopLearnersFragment";
    public static RecyclerView mHourRecyclerView;
    private List<HourLearner> mHourLearners;

    public TopLearnersFragment() {
        // Required empty public constructor
    }


    public static TopLearnersFragment newInstance() {
        TopLearnersFragment fragment = new TopLearnersFragment();

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
        View view = inflater.inflate(R.layout.fragment_top_learners, container, false);

        loadRecyclerView(view);

        Call<List<HourLearner>> call = MainActivity.mLeaderboardApiService.hourLearners();

        call.enqueue(new Callback<List<HourLearner>>() {
            @Override
            public void onResponse(Call<List<HourLearner>> call, Response<List<HourLearner>> response) {
                mHourLearners = response.body();
                HourRecyclerViewAdapter recyclerViewAdapter = new HourRecyclerViewAdapter(mHourLearners);
                mHourRecyclerView.setAdapter(recyclerViewAdapter);
            }

            @Override
            public void onFailure(Call<List<HourLearner>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString(), t);
            }
        });
        return view;
    }


    private void loadRecyclerView(View view) {
        mHourRecyclerView = view.findViewById(R.id.top_hours_recyclerview);
        mHourRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}