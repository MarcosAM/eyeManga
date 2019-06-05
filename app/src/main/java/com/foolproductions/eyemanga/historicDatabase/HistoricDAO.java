package com.foolproductions.eyemanga.historicDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.foolproductions.eyemanga.mangaEdenApi.MangaListItem;

import java.util.ArrayList;
import java.util.List;

public class HistoricDAO implements IHistoricDAO {

    private SQLiteDatabase read;
    private SQLiteDatabase write;

    public HistoricDAO(Context context) {
        DBHelper helper = new DBHelper(context);

        this.read = helper.getReadableDatabase();
        this.write = helper.getWritableDatabase();
    }

    @Override
    public boolean save(MangaListItem manga) {

        if (checkIfExists(manga.getI())) {
            try {
                write.update(DBHelper.TABLE_MANGAS, convertMangaListItemToContentValues(manga), DBHelper.ID_COLUMN + "=?", new String[]{manga.getI()});
                Log.i("Debbuging", "Deu certo atualizar o histórico");
            } catch (Exception e) {
                Log.i("Debbuging", "Falha ao atualizar o histórico");
                return false;
            }
        } else {
            try {
                write.insert(DBHelper.TABLE_MANGAS, null, convertMangaListItemToContentValues(manga));
                Log.i("Debbuging", "Deu certo salvar o histórico");
            } catch (Exception e) {
                Log.i("Debbuging", "Falha ao salvar o histórico novo");
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean delete(String mangaId) {
        try {
            write.delete(DBHelper.TABLE_MANGAS, DBHelper.ID_COLUMN + "=?", new String[]{mangaId});
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<MangaListItem> getList() {
        List<MangaListItem> mangas = new ArrayList<>();

        String sql = "SELECT * FROM " + DBHelper.TABLE_MANGAS + " ;";
        Cursor cursor = read.rawQuery(sql, null);

        while (cursor.moveToNext()) {
            MangaListItem manga = new MangaListItem();

            //TODO refactor this.... and...
            manga.setI(cursor.getString(cursor.getColumnIndex(DBHelper.ID_COLUMN)));
            manga.setT(cursor.getString(cursor.getColumnIndex(DBHelper.TITLE_COLUMN)));
            manga.setIm(cursor.getString(cursor.getColumnIndex(DBHelper.IMAGE_COLUMN)));
            manga.setChapterId(cursor.getString(cursor.getColumnIndex(DBHelper.CHAPTER_COLUMN)));
            manga.setPage(cursor.getInt(cursor.getColumnIndex(DBHelper.PAGE_COLUMN)));

            mangas.add(manga);
        }

        cursor.close();

        return mangas;
    }

    public MangaListItem getManga(String mangaId) {
        MangaListItem manga = null;
        Cursor cursor = read.rawQuery("SELECT * FROM " + DBHelper.TABLE_MANGAS + " WHERE " + DBHelper.ID_COLUMN + "=? ;", new String[]{mangaId});
        while (cursor.moveToNext()) {
            manga = new MangaListItem();

            //TODO... this
            manga.setI(cursor.getString(cursor.getColumnIndex(DBHelper.ID_COLUMN)));
            manga.setT(cursor.getString(cursor.getColumnIndex(DBHelper.TITLE_COLUMN)));
            manga.setIm(cursor.getString(cursor.getColumnIndex(DBHelper.IMAGE_COLUMN)));
            manga.setChapterId(cursor.getString(cursor.getColumnIndex(DBHelper.CHAPTER_COLUMN)));
            manga.setPage(cursor.getInt(cursor.getColumnIndex(DBHelper.PAGE_COLUMN)));
        }
        cursor.close();
        return manga;
    }

    private boolean checkIfExists(String mangaId) {
        long count = DatabaseUtils.queryNumEntries(read, DBHelper.TABLE_MANGAS, "id = ?", new String[]{mangaId});
        return count > 0;
    }

    private ContentValues convertMangaListItemToContentValues(MangaListItem manga) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.ID_COLUMN, manga.getI());
        values.put(DBHelper.TITLE_COLUMN, manga.getT());
        values.put(DBHelper.IMAGE_COLUMN, manga.getIm());
        values.put(DBHelper.CHAPTER_COLUMN, manga.getChapterId());
        values.put(DBHelper.PAGE_COLUMN, manga.getPage());

        return values;
    }
}
