package com.foolproductions.eyemanga.readActivity;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.foolproductions.eyemanga.historicDatabase.HistoricDAO;
import com.foolproductions.eyemanga.mangaEdenApi.Chapter;
import com.foolproductions.eyemanga.mangaEdenApi.Manga;
import com.foolproductions.eyemanga.mangaEdenApi.MangaEdenService;
import com.foolproductions.eyemanga.mangaEdenApi.MangaEdenURLs;
import com.foolproductions.eyemanga.mangaEdenApi.MangaListItem;
import com.foolproductions.eyemanga.mangaEdenApi.MangaManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class ReadViewModel extends ViewModel {
    private MutableLiveData<Chapter> chapter = new MutableLiveData<>();
    private String chapterId;
    private Manga manga;
    private MangaEdenService service;
    private HistoricDAO historicDAO;

    MutableLiveData<Chapter> getChapter() {
        return chapter;
    }

    void initIfNecessery(Context context, Manga manga, String chapterId) {
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
                MangaListItem mangaListItem = historicDAO.getManga(MangaManager.getSelectedManga().getI());
                if (mangaListItem == null) {
                    Log.i("Debbuging", "Comecei a ler do início!");
                    chapterId = manga.getChapters().get(manga.getChapters().size() - 1).get(3);
                } else {
                    Log.i("Debbuging", "Comecei a ler do histórico!");
                    chapterId = mangaListItem.getChapterId();
                }
            } else {
                Log.i("Debbuging", "Comecei a ler a partir de um capítulo!");
            }
            setChapterId(chapterId);
        }
    }

    boolean hasNextChapter() {
        return !manga.getChapters().get(0).get(3).equals(chapterId);
    }

    boolean goToNextChapter() {
        if (hasNextChapter()) {
            int chapterIndex = -1;
            for (List<String> chapter : manga.getChapters()) {
                if (chapter.get(3).equals(chapterId)) {
                    chapterIndex = manga.getChapters().indexOf(chapter);
                    break;
                }
            }
            if (chapterIndex < 0) {
                return false;
            } else {
                setChapterId(manga.getChapters().get(chapterIndex - 1).get(3));
                return true;
            }
        }

        return false;
    }

    int getStartingPage() {
        List<MangaListItem> historics = historicDAO.getList();
        for (MangaListItem historic : historics) {
            if (historic.getChapterId().equals(chapterId)) {
                return historic.getPage();
            }
        }

        return 0;
    }

    private void setChapterId(String chapterId) {
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

    String getChapterId() {
        return chapterId;
    }

    void setManga(Manga manga) {
        this.manga = manga;
    }

    HistoricDAO getHistoricDAO() {
        return historicDAO;
    }
}