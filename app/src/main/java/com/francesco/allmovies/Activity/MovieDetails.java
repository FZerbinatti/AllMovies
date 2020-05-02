package com.francesco.allmovies.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.francesco.allmovies.Database.DatabaseHelper;
import com.francesco.allmovies.R;

public class MovieDetails extends AppCompatActivity {

    TextView movie_title, movie_description, movie_year;
    ImageView movie_poster;
    private static String imageBaseUrl ="https://image.tmdb.org/t/p/w500";
    private ImageButton button_grey_fav, button_red_fav;
    DatabaseHelper mDatabaseHelper;
    private boolean movieFav;
    String extra_poster_path;
    String extra_poster_title;
    String extra_poster_description;
    String extra_poster_year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        mDatabaseHelper = new DatabaseHelper(getApplicationContext());
        movie_poster = (ImageView) findViewById(R.id.movie_poster);
        movie_title = (TextView) findViewById(R.id.movie_title);
        movie_description = (TextView) findViewById(R.id.movie_description);
        movie_year = (TextView) findViewById(R.id.movie_year);
        button_grey_fav = findViewById(R.id.button_addFavourite);
        //button_red_fav = findViewById(R.id.button_addFavouriteRed);
        movieFav=false;





        Intent intent = getIntent();
        if (intent.hasExtra("MOVIE_TITLE")){
            extra_poster_path = getIntent().getStringExtra("MOVIE_POSTER");
            extra_poster_title = getIntent().getStringExtra("MOVIE_TITLE");
             extra_poster_description = getIntent().getStringExtra("MOVIE_PLOT");
            extra_poster_year = getIntent().getStringExtra("MOVIE_YEAR");

            Glide.with(this).load(imageBaseUrl+extra_poster_path).placeholder(R.drawable.ic_load).into(movie_poster);
            movie_title.setText(extra_poster_title);
            movie_description.setText(extra_poster_description);
            movie_year.setText(extra_poster_year);
            movieFav = mDatabaseHelper.getFavourite(extra_poster_title);
            if (movieFav){
                //button_grey_fav.setVisibility(View.INVISIBLE);
                //button_red_fav.setVisibility(View.VISIBLE);
                button_grey_fav.setBackgroundResource(R.drawable.ic_favorite_red);
            }else {
                //button_grey_fav.setVisibility(View.VISIBLE);
                //button_red_fav.setVisibility(View.INVISIBLE);
                button_grey_fav.setBackgroundResource(R.drawable.ic_favorite);
            }



        }else{
            Toast.makeText(getApplicationContext(), "NO API DATA", Toast.LENGTH_SHORT).show();
        }



        button_grey_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (movieFav){
                    button_grey_fav.setBackgroundResource(R.drawable.ic_favorite);
                    mDatabaseHelper.updateFavourite(false, extra_poster_title);
                    movieFav=false;
                    Toast.makeText(MovieDetails.this, "Tolto dai preferiti", Toast.LENGTH_SHORT).show();


                }else {
                    button_grey_fav.setBackgroundResource(R.drawable.ic_favorite_red);
                    mDatabaseHelper.updateFavourite(true, extra_poster_title);
                    movieFav=true;
                    Toast.makeText(MovieDetails.this, "Aggiunti ai preferiti", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
