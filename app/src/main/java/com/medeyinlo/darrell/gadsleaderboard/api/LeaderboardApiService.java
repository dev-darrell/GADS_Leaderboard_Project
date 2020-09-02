package com.medeyinlo.darrell.gadsleaderboard.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface LeaderboardApiService {

    @GET("/api/hours")
    Call<List<HourLearner>> HourLearners();

    @GET("/api/skilliq")
    Call<List<SkillLearner>> skillLearners();
}
