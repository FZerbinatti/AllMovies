package com.francesco.allmovies.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Movie {

    @SerializedName("poster_path")
    private String movie_poster;
    @SerializedName("adult")
    private boolean adult;
    @SerializedName("overview")
    private String movie_plot;
    @SerializedName("release_date")
    private String movie_year;
    @SerializedName("genre_ids")
    private List<Integer> genreIds = new ArrayList<Integer>();
    @SerializedName("id")
    private Integer id;
    @SerializedName("original_title")
    private String originalTitle;
    @SerializedName("original_language")
    private String originalLanguage;
    @SerializedName("title")
    private String movie_title;
    @SerializedName("backdrop_path")
    private String backdropPath;
    @SerializedName("popularity")
    private Double popularity;
    @SerializedName("vote_count")
    private Integer voteCount;
    @SerializedName("video")
    private Boolean video;
    @SerializedName("vote_average")
    private Double voteAverage;

    public Movie(String movie_title, String movie_year,  String movie_plot, String movie_poster) {
        this.movie_title = movie_title;
        this.movie_year = movie_year;
        this.movie_plot = movie_plot;
        this.movie_poster = movie_poster;
    }

    public String getMovie_poster() {
        return movie_poster;
    }

    public void setMovie_poster(String movie_poster) {
        this.movie_poster = movie_poster;
    }

    public String getMovie_plot() {
        return movie_plot;
    }

    public void setMovie_plot(String movie_plot) {
        this.movie_plot = movie_plot;
    }

    public String getMovie_year() {
        return movie_year;
    }

    public void setMovie_year(String movie_year) {
        this.movie_year = movie_year;
    }

    public String getMovie_title() {
        return movie_title;
    }

    public void setMovie_title(String movie_title) {
        this.movie_title = movie_title;
    }
}
