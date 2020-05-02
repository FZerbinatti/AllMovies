package com.francesco.allmovies.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Movie implements Parcelable {

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
    private Boolean movie_favourite;

    public Movie(String movie_poster, String movie_plot, Boolean movie_favourite, String movie_year, String movie_title) {

        this.movie_poster = movie_poster;
        this.movie_title = movie_title;
        this.movie_favourite = movie_favourite;
        this.movie_plot = movie_plot;
        this.movie_year = movie_year;

    }

    public Movie(String movie_poster, String movie_plot,  String movie_year, String movie_title) {

        this.movie_poster = movie_poster;
        this.movie_title = movie_title;
        this.movie_plot = movie_plot;
        this.movie_year = movie_year;

    }


    public Movie(String movie_poster, boolean adult, String movie_plot, String movie_year, List<Integer> genreIds,
                 Integer id, String originalTitle, String originalLanguage, String movie_title, String backdropPath,
                 Double popularity, Integer voteCount, Boolean video, Double voteAverage) {

        this.movie_poster = movie_poster;
        this.movie_title = movie_title;
        this.movie_plot = movie_plot;
        this.movie_year = movie_year;

        this.adult = adult;
        this.genreIds = genreIds;
        this.id = id;
        this.originalTitle = originalTitle;
        this.originalLanguage = originalLanguage;
        this.backdropPath = backdropPath;
        this.popularity = popularity;
        this.voteCount = voteCount;
        this.video = video;
        this.voteAverage = voteAverage;
    }

    public Movie(){

    }





    public static final Comparator<Movie> BY_NAME_ALPHABETICAL = new Comparator<Movie>() {
        @Override
        public int compare(Movie movie, Movie t1) {

            return movie.originalTitle.compareTo(t1.originalTitle);
        }
    };

    public Boolean getMovie_favourite() {
        return movie_favourite;
    }

    public void setMovie_favourite(Boolean movie_favourite) {
        this.movie_favourite = movie_favourite;
    }

    public String getMovie_poster() {
        return movie_poster;
    }

    public void setMovie_poster(String movie_poster) {
        this.movie_poster = movie_poster;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
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

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getMovie_title() {
        return movie_title;
    }

    public void setMovie_title(String movie_title) {
        this.movie_title = movie_title;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public Boolean getVideo() {
        return video;
    }

    public void setVideo(Boolean video) {
        this.video = video;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public static Comparator<Movie> getByNameAlphabetical() {
        return BY_NAME_ALPHABETICAL;
    }

    public static Creator<Movie> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.movie_poster);
        dest.writeByte(this.adult ? (byte) 1 : (byte) 0);
        dest.writeString(this.movie_plot);
        dest.writeString(this.movie_year);
        dest.writeList(this.genreIds);
        dest.writeValue(this.id);
        dest.writeString(this.originalTitle);
        dest.writeString(this.originalLanguage);
        dest.writeString(this.movie_title);
        dest.writeString(this.backdropPath);
        dest.writeValue(this.popularity);
        dest.writeValue(this.voteCount);
        dest.writeValue(this.video);
        dest.writeValue(this.voteAverage);
    }

    protected Movie(Parcel in) {
        this.movie_poster = in.readString();
        this.adult = in.readByte() != 0;
        this.movie_plot = in.readString();
        this.movie_year = in.readString();
        this.genreIds = new ArrayList<Integer>();
        in.readList(this.genreIds, Integer.class.getClassLoader());
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.originalTitle = in.readString();
        this.originalLanguage = in.readString();
        this.movie_title = in.readString();
        this.backdropPath = in.readString();
        this.popularity = (Double) in.readValue(Double.class.getClassLoader());
        this.voteCount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.video = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.voteAverage = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}