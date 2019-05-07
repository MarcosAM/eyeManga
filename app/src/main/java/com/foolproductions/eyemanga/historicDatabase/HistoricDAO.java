package com.foolproductions.eyemanga.historicDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class HistoricDAO implements IHistoricDAO {

    SQLiteDatabase read;
    SQLiteDatabase write;

    public HistoricDAO(Context context) {
        DBHelper helper = new DBHelper(context);

        this.read = helper.getReadableDatabase();
        this.write = helper.getWritableDatabase();
    }

    @Override
    public boolean save(ReadingHistoric historic) {

        try {
            write.insert(DBHelper.TABLE_MANGAS, null, convertHistoricToContentValues(historic));
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    @Override
    public boolean update(ReadingHistoric historic) {

        try {
            write.update(DBHelper.TABLE_MANGAS, convertHistoricToContentValues(historic), DBHelper.ID_COLUMN + "=?", new String[]{historic.getId()});
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    @Override
    public boolean delete(ReadingHistoric historic) {
        //TODO provavelmente deletar isso pq eu não acho que eu vá precisar, talvez,,, talvez eu deixe o usuário deletar históricos
        return false;
    }

    @Override
    public List<ReadingHistoric> getList() {
        List<ReadingHistoric> historics = new ArrayList<>();

        String sql = "SELECT * FROM " + DBHelper.TABLE_MANGAS + " ;";
        Cursor cursor = read.rawQuery(sql, null);

        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            ReadingHistoric historic = new ReadingHistoric();
            //TODO criar um construtor para colocar isso "automaticamente"
            //TODO alterar o evento de clique dos Recycler Views, deixar o REcycler VIew cuidar disso com o AddItemClickListener
            //TODO transformar manga em Serializeble...talvez...
            //TODO criar um método para pegar um Reading Historic em específico

            historic.setId(cursor.getString(cursor.getColumnIndex(DBHelper.ID_COLUMN)));
            historic.setChapterId(cursor.getString(cursor.getColumnIndex(DBHelper.CHAPTER_COLUMN)));
            historic.setPage(cursor.getInt(cursor.getColumnIndex(DBHelper.PAGE_COLUMN)));

            historics.add(historic);
        }

        cursor.close();

        return historics;
    }

    ContentValues convertHistoricToContentValues(ReadingHistoric historic) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.ID_COLUMN, historic.getId());
        values.put(DBHelper.CHAPTER_COLUMN, historic.getChapterId());
        values.put(DBHelper.PAGE_COLUMN, historic.getPage());

        return values;
    }
}
