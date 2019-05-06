package com.foolproductions.eyemanga.readActivity;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.foolproductions.eyemanga.mangaEdenApi.Chapter;
import com.foolproductions.eyemanga.mangaEdenApi.Manga;
import com.foolproductions.eyemanga.mangaEdenApi.MangaEdenService;
import com.foolproductions.eyemanga.mangaEdenApi.MangaEdenURLs;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReadViewModel extends ViewModel {
    MutableLiveData<Chapter> chapter = new MutableLiveData<>();
    String chapterId;

    public MutableLiveData<Chapter> getChapter() {
        return chapter;
    }

    public void setChapterId(String chapterId) {
        if (this.chapterId != chapterId) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(MangaEdenURLs.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            MangaEdenService service = retrofit.create(MangaEdenService.class);
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
    }
}