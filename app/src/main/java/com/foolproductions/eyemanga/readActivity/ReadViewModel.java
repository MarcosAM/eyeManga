package com.foolproductions.eyemanga.readActivity;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.foolproductions.eyemanga.historicDatabase.HistoricDAO;
import com.foolproductions.eyemanga.historicDatabase.ReadingHistoric;
import com.foolproductions.eyemanga.mangaEdenApi.Chapter;
import com.foolproductions.eyemanga.mangaEdenApi.Manga;
import com.foolproductions.eyemanga.mangaEdenApi.MangaEdenService;
import com.foolproductions.eyemanga.mangaEdenApi.MangaEdenURLs;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReadViewModel extends ViewModel {
    MutableLiveData<Chapter> chapter = new MutableLiveData<>();
    String chapterId;
    Manga manga;
    MangaEdenService service;
    HistoricDAO historicDAO;

    public MutableLiveData<Chapter> getChapter() {
        return chapter;
    }

    public void initIfNecessery(Context context, Manga manga, String mangaId, String chapterId) {
        if (historicDAO == null) {
            historicDAO = new HistoricDAO(context);
        }

        if (service == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(MangaEdenURLs.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            service = retrofit.create(MangaEdenService.class);
        }

        if (this.manga == null) {
            setManga(manga);
        }

        if (this.chapterId == null) {
            if (chapterId == null) {
                ReadingHistoric historic = historicDAO.getManga(mangaId);
                if (historic == null) {
                    Log.i("Debbuging", "Comecei a ler do início!");
                    chapterId = manga.getChapters().get(manga.getChapters().size() - 1).get(3);
                } else {
                    Log.i("Debbuging", "Comecei a ler do histórico!");
                    chapterId = historic.getChapterId();
                }
            } else {
                Log.i("Debbuging", "Comecei a ler a partir de um capítulo!");
            }
            setChapterId(chapterId);
        }
    }

    public boolean hasNextChapter() {
        //TODO outra parte do código que está pegando o mágico número 3
        Log.i("Debbuging", "O código do último capítulo é :" + manga.getChapters().get(0).get(3) + " o código to atual é: " + chapterId);
        return !manga.getChapters().get(0).get(3).equals(chapterId);
    }

    public boolean goToNextChapter() {
        if (hasNextChapter()) {
            int chapterIndex = -1;
            for (List<String> chapter : manga.getChapters()) {
                //TODO mais uma vez o 3 mágico!
                if (chapter.get(3).equals(chapterId)) {
                    chapterIndex = manga.getChapters().indexOf(chapter);
                    break;
                }
            }
            if (chapterIndex < 0) {
                return false;
            } else {
                //TODO mais uma vez o 3 mágico!
                setChapterId(manga.getChapters().get(chapterIndex - 1).get(3));
                return true;
            }
        }

        return false;
    }

    public int getStartingPage() {
        List<ReadingHistoric> historics = historicDAO.getList();
        for (ReadingHistoric historic : historics) {
            if (historic.getChapterId().equals(chapterId)) {
                return historic.getPage();
            }
        }

        return 0;
    }

    void setChapterId(String chapterId) {
        this.chapterId = chapterId;
        Call<Chapter> chapterCall = service.getChapter(chapterId);
        chapterCall.enqueue(new Callback<Chapter>() {
            @Override
            public void onResponse(Call<Chapter> call, Response<Chapter> response) {
                if (response.isSuccessful()) {
                    chapter.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Chapter> call, Throwable t) {

            }
        });
    }

    public String getChapterId() {
        return chapterId;
    }

    void setManga(Manga manga) {
        this.manga = manga;
    }

    public HistoricDAO getHistoricDAO() {
        return historicDAO;
    }
}