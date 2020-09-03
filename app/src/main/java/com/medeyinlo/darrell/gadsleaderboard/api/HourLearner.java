package com.medeyinlo.darrell.gadsleaderboard.api;

import com.google.gson.annotations.SerializedName;

import java.net.URL;

public class HourLearner {
    @SerializedName("name")
    private String name;
    @SerializedName("hours")
    private int hours;
    @SerializedName("country")
    private String country;
    @SerializedName("badgeUrl")
    private URL badgeUrl;

    public HourLearner(String name, int hours, String country, URL badgeUrl) {
        this.name = name;
        this.hours = hours;
        this.country = country;
        this.badgeUrl = badgeUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
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
