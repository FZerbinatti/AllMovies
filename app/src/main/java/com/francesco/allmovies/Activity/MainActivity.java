package com.francesco.allmovies.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.francesco.allmovies.R;
import com.francesco.allmovies.Adapter.RecyclerViewAdapter;
import com.francesco.allmovies.Adapter.RecyclerViewClickListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.main_recyclerview);

        hideSoftKeyboard();


/*        final RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                ArrayList<String> images_paths = null;
                // open the Full screen image display
                Intent intent = new Intent(MainActivity.this, DisplayMovie.class);
                // passa solamente una lista dei path delle immagini
                for (int i=0; i<movieList.size(); i++){
                    images_paths.add(movieList.get(i).getMovie_photo());
                }

                intent.putExtra("MOVIE_PHOTO", movie_photo) ;
                startActivity(intent);


            }
        };

        // moview= lista dei movies fetchati dal db
        RecyclerViewAdapter adapter = new RecyclerViewAdapter( this, null , listener                                                                                                                                                               );
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(adapter);*/


    }

    private void hideSoftKeyboard() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

    }
}
