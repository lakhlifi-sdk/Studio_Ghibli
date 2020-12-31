package com.lakhlifi.studio_ghibli;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lakhlifi.studio_ghibli.Models.Film;

import java.util.ArrayList;
import java.util.List;

public class Mabase extends SQLiteOpenHelper {
    
    private static final String TABLE_FILM = "films";
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String DIRECTOR = "director";
    private static final String PRODUCER = "producer";
    private static final String RELEASE_DATE = "release_date";
    private static final String RT_SCORE = "rt_score";
    private static final String URL = "url";

    private static final int DATABASE_VERSION = 1;
    public Mabase(Context context) {
        super(context, "films_db", null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_FILM_TABLE = "CREATE TABLE " + TABLE_FILM +
                "(" +
                ID + " INTEGER PRIMARY KEY," +
                TITLE + " TEXT," +
                DESCRIPTION + " TEXT," +
                DIRECTOR + " TEXT," +
                PRODUCER + " TEXT," +
                RELEASE_DATE + " TEXT," +
                RT_SCORE + " TEXT," +
                URL + " TEXT" +
                ")";
        db.execSQL(CREATE_FILM_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FILM);
        onCreate(db);

    }

    public void addFilm(Film film){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID, film.getId());
        values.put(TITLE, film.getTitle());
        values.put(DESCRIPTION, film.getDescription());
        values.put(DIRECTOR, film.getDirector());
        values.put(PRODUCER, film.getProducer());
        values.put(RELEASE_DATE, film.getRelease_date());
        values.put(RT_SCORE, film.getRt_score());
        values.put(URL, film.getUrl());
        db.insert(TABLE_FILM, null, values);
    }

    public List<Film> getAllFilms() {
        List<Film> filmList = new ArrayList<Film>();
        // Select All Query  
        String selectQuery = "SELECT  * FROM " + TABLE_FILM;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list  
        if (cursor.moveToFirst()) {
            do {
                Film film = new Film();
                film.setId(cursor.getString(0));
                film.setTitle(cursor.getString(1));
                film.setDescription(cursor.getString(2));//DIRECTOR
                film.setDirector(cursor.getString(3));
                film.setProducer(cursor.getString(4));
                film.setRelease_date(cursor.getString(5));
                film.setRt_score(cursor.getString(6));
                film.setUrl(cursor.getString(7));
                // Adding film to list  
                filmList.add(film);
            } while (cursor.moveToNext());
        }

        // return film list
        System.out.println("Film List->"+filmList);
        return filmList;
    }
}
