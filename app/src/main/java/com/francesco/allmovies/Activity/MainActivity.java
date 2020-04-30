package com.francesco.allmovies.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.francesco.allmovies.API.Client;
import com.francesco.allmovies.API.Service;
import com.francesco.allmovies.BuildConfig;
import com.francesco.allmovies.Database.DatabaseHelper;
import com.francesco.allmovies.Model.Movie;
import com.francesco.allmovies.Model.MoviesResponse;
import com.francesco.allmovies.R;
import com.francesco.allmovies.Adapter.RecyclerViewAdapter;
import com.francesco.allmovies.Adapter.RecyclerViewClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private List<Movie> movieList;
    private static final int REQUEST_CODE_SPEACH_INPUT = 1000;
    ProgressDialog pd;
    DatabaseHelper mDatabaseHelper;
    ImageButton button_microphone, button_check;
    public static final String TAG ="MainActivity: ";
    EditText editText_search_movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDatabaseHelper = new DatabaseHelper(getApplicationContext());
        button_microphone = (ImageButton) findViewById(R.id.button_microphone);
        button_check = (ImageButton) findViewById(R.id.button_check);
        editText_search_movie = (EditText) findViewById(R.id.editText_search_movie);

        hideSoftKeyboard();
        initViews();

        button_microphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Voice2Text();
            }
        });

        editText_search_movie.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                button_microphone.setVisibility(View.GONE);
                button_check.setVisibility(View.VISIBLE);
                return false;
            }
        });

        button_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard();
                // cerca tra i tutoli qualcosa che contiene la parola in edittext e fetcha il movie in una lista
                ArrayList<Movie> searchedMovieList =  new ArrayList<>();
                searchedMovieList = mDatabaseHelper.searchMovies(editText_search_movie.getText().toString());


                recyclerView.setAdapter(new RecyclerViewAdapter(getApplicationContext(), searchedMovieList));
                recyclerView.smoothScrollToPosition(0);

            }
        });
    }

    // implementa SQLITE per salvare i dati dei film fetchati
    // implementa la ricerca sui film nel database locale



    private void initViews(){

        recyclerView = findViewById(R.id.main_recyclerview);
        movieList = new ArrayList<>();

        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                ArrayList<String> images_paths = null;
                // open the Full screen image display
                Intent intent = new Intent(MainActivity.this, MovieDetails.class);
                // passa solamente una lista dei path delle immagini
                for (int i=0; i<movieList.size(); i++){
                    images_paths.add(movieList.get(i).getMovie_poster());
                }

                intent.putExtra("MOVIE_PHOTO", "") ;
                startActivity(intent);


            }
        };


        adapter = new RecyclerViewAdapter(this, movieList);

        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        }

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        loadJSON();
    }

    private void hideSoftKeyboard() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

    }

    public Activity getActivity(){
        Context context = this;
        while (context instanceof ContextWrapper){
            if(context instanceof Activity){
                return (Activity) context;
            }
            context = ((ContextWrapper)context).getBaseContext();

        }
        return  null;
    }

    private void loadJSON() {

        if (!isNetworkAvailable()) {
            Log.d(TAG, "loadJSON: network NOT available");
            int offlineMoviesNumber = mDatabaseHelper.getTotalOfflineMovies();
            if (offlineMoviesNumber==0){
                Log.d(TAG, "loadJSON: NO OFFLINE MOVIES");
                Toast.makeText(MainActivity.this, "Nessun Film in offline mode", Toast.LENGTH_SHORT).show();
            }else {
                Log.d(TAG, "loadJSON: LOADING OFFLINE MOVIES");
                //carica i film offline
                ArrayList<Movie> offlineMovies = mDatabaseHelper.getOfflineMovies();
                recyclerView.setAdapter(new RecyclerViewAdapter(getApplicationContext(), offlineMovies));
                recyclerView.smoothScrollToPosition(0);
            }
        } else {

            pd = new ProgressDialog(this);
            pd.setMessage("Fetching  movies");
            pd.setCancelable(false);
            pd.show();
            try {


                Log.d(TAG, "loadJSON: DISPOSITIVO ONLINE");
                if (BuildConfig.MyTheMovieDBApiToken.isEmpty()) {
                    Log.d(TAG, "loadJSON: NO API KEY");
                    pd.dismiss();
                    return;
                }

                Client Client = new Client();
                Service apiService = Client.getClient().create(Service.class);

                Call<MoviesResponse> call = apiService.getPopularMovies(BuildConfig.MyTheMovieDBApiToken);
                Log.d(TAG, "loadJSON: call " + call);
                Log.d(TAG, "loadJSON: MyTheMovieDBApiToken :" + BuildConfig.MyTheMovieDBApiToken);
                call.enqueue(new Callback<MoviesResponse>() {
                    @Override
                    public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                        List<Movie> movies = response.body().getResults();
                        // controlla se ogni titolo esiste gia sul db locale, se non esiste inseriscilo
                        for (int i =0; i<movies.size(); i++){
                            String movie_name = movies.get(i).getMovie_title();
                            if (!mDatabaseHelper.movieExist(movie_name)){
                                mDatabaseHelper.addMovie(movies.get(i));
                            }
                        }
                        recyclerView.setAdapter(new RecyclerViewAdapter(getApplicationContext(), movies));
                        recyclerView.smoothScrollToPosition(0);
                        pd.dismiss();
                    }

                    @Override
                    public void onFailure(Call<MoviesResponse> call, Throwable t) {
                        Log.d(TAG, "onFailure: error fetching data ");
                    }
                });
            } catch (Exception e) {
                Log.d(TAG, "loadJSON: Error" + e.getMessage());
            }

        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void Voice2Text (){

        Intent intent_voice = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent_voice.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent_voice.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent_voice.putExtra(RecognizerIntent.EXTRA_PROMPT, "Movie title?");


        try {
            startActivityForResult(intent_voice, REQUEST_CODE_SPEACH_INPUT);
        }catch (Exception e){
            Log.d(TAG, "onClick: "+ e.getMessage());
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case REQUEST_CODE_SPEACH_INPUT:{
                if (resultCode == RESULT_OK && data != null ) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String description = editText_search_movie.getText().toString();

                    if (description.equals("") || description.equals(" ") || description.isEmpty() ){
                        editText_search_movie.setText(result.get(0));
                    }else{
                        editText_search_movie.setText(result.get(0));
                    }



                }
            }
        }

        button_check.setVisibility(View.VISIBLE);
        button_microphone.setVisibility(View.GONE);
    }


}
