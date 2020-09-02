package com.medeyinlo.darrell.gadsleaderboard.api;

import com.google.gson.annotations.SerializedName;

import java.net.URL;

public class SkillLearner {
    @SerializedName("name")
    private String name;
    @SerializedName("score")
    private String score;
    @SerializedName("country")
    private String country;
    @SerializedName("badgeUrl")
    private URL badgeUrl;

    public SkillLearner(String name, String score, String country, URL badgeUrl) {
        this.name = name;
        this.score = score;
        this.country = country;
        this.badgeUrl = badgeUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public URL getBadgeUrl() {
        return badgeUrl;
    }

    public void setBadgeUrl(URL badgeUrl) {
        this.badgeUrl = badgeUrl;
    }
}
