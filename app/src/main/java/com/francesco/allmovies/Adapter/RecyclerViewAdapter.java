package com.francesco.allmovies.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.francesco.allmovies.Activity.MovieDetails;
import com.francesco.allmovies.Model.Movie;
import com.francesco.allmovies.R;

import java.util.List;

import static com.francesco.allmovies.Activity.MainActivity.TAG;

public class RecyclerViewAdapter extends RecyclerView.Adapter <RecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<Movie> movies;
    RecyclerViewClickListener clickListener;
    private static String imageBaseUrl ="https://image.tmdb.org/t/p/w500";

    public RecyclerViewAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.cardview_locandina, viewGroup,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.movie_title.setText(movies.get(i).getMovie_title());
        Log.d(TAG, "onBindViewHolder: movie_title:"+ movies.get(i).getMovie_title());
        String posterURL = imageBaseUrl+(movies.get(i).getMovie_poster());
        Log.d(TAG, "onBindViewHolder: posterURL: "+posterURL);
        final ProgressBar progressBar = viewHolder.progressBar;

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round);
        Glide.with(context).load(posterURL).placeholder(R.drawable.ic_load).apply(options).into(viewHolder.movie_image);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {

        private ImageView movie_image;
        private TextView movie_title;
        private ProgressBar progressBar;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            movie_image = (ImageView) itemView.findViewById(R.id.movie_image);
            movie_title = (TextView) itemView.findViewById(R.id.movie_title);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if (pos !=  RecyclerView.NO_POSITION){
                        Movie clickedMovie = movies.get(pos);
                        Intent intent = new Intent(context, MovieDetails.class);
                        intent.putExtra("MOVIE_POSTER", movies.get(pos).getMovie_poster());
                        intent.putExtra("MOVIE_PLOT", movies.get(pos).getMovie_plot());
                        intent.putExtra("MOVIE_TITLE", movies.get(pos).getMovie_title());
                        intent.putExtra("MOVIE_YEAR", movies.get(pos).getMovie_year());
                        Log.d(TAG, "onClick: PASSING DATA TO ITEM CLICKED" + movies.get(pos).getMovie_poster() );
                        Log.d(TAG, "onClick: PASSING DATA TO ITEM CLICKED title:" + movies.get(pos).getMovie_title() );


                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        Toast.makeText(view.getContext(), "Cliked on "+ clickedMovie.getMovie_title(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }



    }
}
