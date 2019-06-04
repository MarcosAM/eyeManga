package com.foolproductions.eyemanga.historicDatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    static final int VERSION = 1;
    static final String DB_NAME = "DB_HISTORIC";
    static final String TABLE_MANGAS = "mangas";

    static final String ID_COLUMN = "id";
    static final String TITLE_COLUMN = "title";
    static final String IMAGE_COLUMN = "image";
    static final String CHAPTER_COLUMN = "chapterId";
    static final String PAGE_COLUMN = "page";

    DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_MANGAS + " ( " + ID_COLUMN + " TEXT PRIMARY KEY, " + TITLE_COLUMN + " TEXT, " + IMAGE_COLUMN + " TEXT, " + CHAPTER_COLUMN + " TEXT, " + PAGE_COLUMN + " INT(3))";

        try {
            sqLiteDatabase.execSQL(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}