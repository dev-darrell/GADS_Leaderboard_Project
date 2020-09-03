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

//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    public TopLearnersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TopLearnersFragment.
     */

    public static TopLearnersFragment newInstance() {
        TopLearnersFragment fragment = new TopLearnersFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
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
        loadDataFromApi();
        return view;
    }

    private void loadDataFromApi() {
        Call<List<HourLearner>> call = MainActivity.mLeaderboardApiService.hourLearners();

        call.enqueue(new Callback<List<HourLearner>>() {
            @Override
            public void onResponse(Call<List<HourLearner>> call, Response<List<HourLearner>> response) {
                List<HourLearner> hourLearners = response.body();
                TopLearnersFragment.mHourRecyclerView.setAdapter(new HourRecyclerViewAdapter(hourLearners));
            }

            @Override
            public void onFailure(Call<List<HourLearner>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString(), t);
            }
        });
    }

    private void loadRecyclerView(View view) {
        mHourRecyclerView = view.findViewById(R.id.top_hours_recyclerview);
        mHourRecyclerView.setHasFixedSize(true);
        mHourRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}