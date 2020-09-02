package com.medeyinlo.darrell.gadsleaderboard;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.medeyinlo.darrell.gadsleaderboard.api.HourLearner;
import com.medeyinlo.darrell.gadsleaderboard.api.LeaderboardApiService;
import com.medeyinlo.darrell.gadsleaderboard.api.SkillLearner;
import com.medeyinlo.darrell.gadsleaderboard.ui.main.SectionsPagerAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final String BASE_URL = "https://gadsapi.herokuapp.com";
    private Retrofit mRetrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        connectAndGetData();

        Button submitBtn = findViewById(R.id.submit_btn);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SubmitActivity.class));
            }
        });
    }

    private void connectAndGetData() {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        LeaderboardApiService leaderboardApiService =
                mRetrofit.create(LeaderboardApiService.class);
        Call<List<HourLearner>> call = leaderboardApiService.HourLearners();

        call.enqueue(new Callback<List<HourLearner>>() {
            @Override
            public void onResponse(Call<List<HourLearner>> call, Response<List<HourLearner>> response) {
                List<HourLearner> hourLearners = response.body();
                TextView textView1 = findViewById(R.id.top_learn_tv);
                textView1.setText(hourLearners.toString());
            }

            @Override
            public void onFailure(Call<List<HourLearner>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString(), t);
            }
        });

        Call<List<SkillLearner>> skillCall = leaderboardApiService.skillLearners();

        skillCall.enqueue(new Callback<List<SkillLearner>>() {
            @Override
            public void onResponse(Call<List<SkillLearner>> call, Response<List<SkillLearner>> response) {
                List<SkillLearner> skillLearners = response.body();
                TextView textView2 = findViewById(R.id.top_skill_tv);
                textView2.setText(skillLearners.toString());
            }

            @Override
            public void onFailure(Call<List<SkillLearner>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString(), t);
            }
        });
    }

}