package com.francesco.allmovies.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.francesco.allmovies.R;

public class MovieDetails extends AppCompatActivity {

    TextView movie_title, movie_description, movie_year;
    ImageView movie_poster;
    private static String imageBaseUrl ="https://image.tmdb.org/t/p/w500";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        movie_poster = (ImageView) findViewById(R.id.movie_poster);
        movie_title = (TextView) findViewById(R.id.movie_title);
        movie_description = (TextView) findViewById(R.id.movie_description);
        movie_year = (TextView) findViewById(R.id.movie_year);

        Intent intent = getIntent();
        if (intent.hasExtra("MOVIE_TITLE")){
            String extra_poster_path = getIntent().getStringExtra("MOVIE_POSTER");
            String extra_poster_title = getIntent().getStringExtra("MOVIE_TITLE");
            String extra_poster_description = getIntent().getStringExtra("MOVIE_PLOT");
            String extra_poster_year = getIntent().getStringExtra("MOVIE_YEAR");

            Glide.with(this).load(imageBaseUrl+extra_poster_path).placeholder(R.drawable.ic_load).into(movie_poster);
            movie_title.setText(extra_poster_title);
            movie_description.setText(extra_poster_description);
            movie_year.setText(extra_poster_year);



        }else{
            Toast.makeText(getApplicationContext(), "NO API DATA", Toast.LENGTH_SHORT).show();
        }

    }
}
