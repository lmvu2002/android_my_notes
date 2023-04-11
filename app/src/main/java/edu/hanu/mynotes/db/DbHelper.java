package edu.hanu.mynotes.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import edu.hanu.mynotes.MainActivity;

public class DbHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "notes.db";
    public static final int DB_VERSION = 1;

    public DbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    public DbHelper(@Nullable Context context,@Nullable String DB_NAME,@Nullable SQLiteDatabase.CursorFactory  factory,@Nullable int DB_VERSION) {
        super(context, DB_NAME, factory, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //create tables
        String sql = "CREATE TABLE notes (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "content TEXT NOT NULL" +
                ")";
        db.execSQL(sql);
        //seed demo data
        sql = "INSERT INTO notes (content) VALUES('Example note')";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //production: alter table
        String sql = "ALTER TABLE notes " +
                "ADD newtable";
        db.execSQL(sql);
        //development: drop tables & recreate
        sql = "DROP TABLE IF EXISTS notes";
        db.execSQL(sql);
    }
}
