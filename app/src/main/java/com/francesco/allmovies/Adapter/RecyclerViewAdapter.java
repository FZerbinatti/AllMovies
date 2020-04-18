package com.francesco.allmovies.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.francesco.allmovies.Model.Movie;
import com.francesco.allmovies.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter <RecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<Movie> movies;
    RecyclerViewClickListener clickListener;

    public RecyclerViewAdapter(Context context, List<Movie> movies, RecyclerViewClickListener clickListener) {
        this.context = context;
        this.movies = movies;
        this.clickListener = clickListener;
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
        String posterURL = (movies.get(i).getMovie_poster());
        final ProgressBar progressBar = viewHolder.progressBar;

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round);
        Glide.with(context).load(posterURL).apply(options).into(viewHolder.movie_image);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView movie_image;
        private TextView movie_title;
        private ProgressBar progressBar;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            movie_image = (ImageView) itemView.findViewById(R.id.movie_image);
            movie_title = (TextView) itemView.findViewById(R.id.movie_title);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            clickListener.onClick(itemView, getAdapterPosition());

        }
    }
}
