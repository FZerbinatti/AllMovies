package com.francesco.allmovies.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.francesco.allmovies.Model.Movie;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class DatabaseHelper extends SQLiteOpenHelper {

    static final String TABLE_MOVIES = "MOVIES";
    private static final String DB_NAME = "Movie_LocalDB";
    private static final int DB_VERSION = 1;


    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null,DB_VERSION);
    }


    private void createTables(SQLiteDatabase db){
        Log.d(TAG, "createTables: ");
        //creating tables
        String CREATE_TABLE_MOVIES = "CREATE TABLE " + TABLE_MOVIES +
                "(" + "ID" + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "MOVIE_TITLE" + " TEXT ,  "
                + "MOVIE_YEAR" + " TEXT , "
                + "MOVIE_DESCRIPTION" + " TEXT , "
                + "MOVIE_FAVOURITE" + " TEXT , "
                + "MOVIE_POSTER" + " TEXT   );";

        db.execSQL(CREATE_TABLE_MOVIES);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        createTables(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIES);
        onCreate(sqLiteDatabase);
    }


    public void addMovie ( Movie movie ){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MOVIE_TITLE", movie.getMovie_title());
        values.put("MOVIE_YEAR", movie.getMovie_year());
        values.put("MOVIE_DESCRIPTION", movie.getMovie_plot());
        values.put("MOVIE_POSTER", movie.getMovie_poster());
        values.put("MOVIE_FAVOURITE", 0);


        db.insert(TABLE_MOVIES, null, values);
        db.close();
    }

    public void updateFavourite(Boolean favourite, String movie_name){
        SQLiteDatabase db = this.getWritableDatabase();
        String bool ="0";
        ContentValues cv = new ContentValues();
        if (favourite){
            bool = "1";
        }else {
            bool = "0";
        }
        cv.put("MOVIE_FAVOURITE", bool);
        db.update(TABLE_MOVIES, cv, "MOVIE_TITLE" + "= ?", new String[] {movie_name});
        db.close();
        Log.d(TAG, "updateFavourite: now movie parameter for facourite is  "+ bool);
    }

    public Boolean getFavourite ( String movie_title){
        SQLiteDatabase db = this.getReadableDatabase();
        String fav="0";
        //                                 0
        String selectQuery = "SELECT  MOVIE_FAVOURITE FROM MOVIES WHERE MOVIE_TITLE = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String []{movie_title});
        if (cursor.moveToFirst()) {
            do {
                fav = cursor.getString(0);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        if (fav.equals("0")){
            Log.d(TAG, "getFavourite: " + movie_title + " is NOT a fav");
            return false;
        }else {
            Log.d(TAG, "getFavourite: " + movie_title + " is a fav");
            return true;
        }


    }


    public void deleteMovie (String movie_title){
        SQLiteDatabase db = this.getWritableDatabase();
        String table = "TABLE_MOVIES";
        String whereClause = "MOVIE_TITLE=?";
        String[] whereArgs = new String[] { String.valueOf(movie_title) };
        db.delete(table, whereClause, whereArgs);
    }


    public ArrayList<Movie> getOfflineMovies() {
        Log.d(TAG, "getOrdini: entered query");

        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList <Movie> listMovies = new ArrayList<Movie>();
        //                                 0           1              2                    3
        String selectQuery = "SELECT  MOVIE_TITLE,  MOVIE_YEAR, MOVIE_DESCRIPTION, MOVIE_POSTER FROM MOVIES";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Movie movie = new Movie(cursor.getString(3), cursor.getString(2), cursor.getString(1), cursor.getString(0));
                listMovies.add(movie);
                Log.d(TAG, "getMovies: movie: "+movie);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listMovies;

    }

    public Integer getTotalOfflineMovies (){
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, "MOVIES");
        db.close();
        Integer i = (int) (long) count;

        return i;
    }

    public boolean movieExist(String movie_name) {
        Log.d(TAG, "movieExist: entered query");

        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM MOVIES WHERE MOVIE_TITLE = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String []{movie_name});
        if (cursor.moveToFirst()) {
            do {
                return  true;
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return false;
    }

    public ArrayList<Movie> searchMovies(String movie_searched) {
        Log.d(TAG, "searchMovies: entered query");

        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList <Movie> listMovies = new ArrayList<Movie>();
        //                                 0           1              2                    3
       // String selectQuery = "SELECT  MOVIE_TITLE,  MOVIE_YEAR, MOVIE_DESCRIPTION, MOVIE_POSTER FROM MOVIES";


        Cursor cursor = db.query(TABLE_MOVIES, new String[] {"MOVIE_TITLE", "MOVIE_YEAR", "MOVIE_DESCRIPTION", "MOVIE_POSTER" },
                "MOVIE_TITLE" + " LIKE ?", new String[] {"%" + movie_searched + "%"},
                null, null, null);



        if (cursor.moveToFirst()) {
            do {
                Movie movie = new Movie(cursor.getString(3), cursor.getString(2), cursor.getString(1), cursor.getString(0));
                listMovies.add(movie);
                Log.d(TAG, "getMovies: movie: "+movie);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return listMovies;

    }


    public ArrayList<Movie> getFavouriteMovies() {
        Log.d(TAG, "getOrdini: entered query");

        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList <Movie> listMovies = new ArrayList<Movie>();
        //                                 0           1              2                    3
        String selectQuery = "SELECT MOVIE_TITLE,  MOVIE_YEAR, MOVIE_DESCRIPTION, MOVIE_POSTER FROM MOVIES WHERE MOVIE_FAVOURITE = ?";

        Cursor cursor = db.rawQuery(selectQuery, new String []{"1"});
        if (cursor.moveToFirst()) {
            do {
                Movie movie = new Movie(cursor.getString(3), cursor.getString(2), cursor.getString(1), cursor.getString(0));
                listMovies.add(movie);
                Log.d(TAG, "getMovies: movie: "+movie);
            } while (cursor.moveToNext());
        }
        Log.d(TAG, "getFavouriteMovies: listMovies.size " +listMovies.size());
        cursor.close();
        db.close();
        return listMovies;

    }





}
