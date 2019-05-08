package com.foolproductions.eyemanga.historicDatabase;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    static final int VERSION = 1;
    public static final String DB_NAME = "DB_HISTORIC";
    public static final String TABLE_MANGAS = "mangas";

    public static final String ID_COLUMN = "id";
    public static final String CHAPTER_COLUMN = "chapterId";
    public static final String PAGE_COLUMN = "page";

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_MANGAS + " ( " + ID_COLUMN + " TEXT PRIMARY KEY," + CHAPTER_COLUMN + " TEXT, " + PAGE_COLUMN + " INT(3))";

        try {
            sqLiteDatabase.execSQL(sql);
        } catch (Exception e) {
            //TODO lidar com isso aqui de alguma forma
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
