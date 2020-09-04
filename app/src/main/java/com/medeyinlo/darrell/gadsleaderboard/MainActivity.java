package com.medeyinlo.darrell.gadsleaderboard;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.medeyinlo.darrell.gadsleaderboard.api.LeaderboardApiService;
import com.medeyinlo.darrell.gadsleaderboard.ui.main.SectionsPagerAdapter;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final String BASE_URL = "https://gadsapi.herokuapp.com";
    private Retrofit mRetrofit;
    public static LeaderboardApiService mLeaderboardApiService;

//    TODO: Debug and fix skill fragment not showing RecyclerView and items when loaded;
//    TODO: Debug RecyclerView item list not having spacing and elevation when displayed in live app;
//    TODO: Review launcher icon to remove white border that shows in phone app drawer.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        connectAndGetData();

        Button submitBtn = findViewById(R.id.submit_btn);

        submitBtn.setOnClickListener(view -> startActivity(new Intent(
                getApplicationContext(), SubmitActivity.class)));
    }

    private void connectAndGetData() {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        mLeaderboardApiService = mRetrofit.create(LeaderboardApiService.class);
    }

}