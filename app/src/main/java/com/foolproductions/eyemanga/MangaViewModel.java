package com.foolproductions.eyemanga;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.foolproductions.eyemanga.mangaEdenApi.Manga;
import com.foolproductions.eyemanga.mangaEdenApi.MangaEdenService;
import com.foolproductions.eyemanga.mangaEdenApi.MangaEdenURLs;
import com.foolproductions.eyemanga.mangaEdenApi.MangaManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MangaViewModel extends ViewModel {
    MutableLiveData<Manga> manga = new MutableLiveData<>();
    String mangaId;

    public MutableLiveData<Manga> getManga() {
        return manga;
    }

    public MangaViewModel() {
        if (MangaManager.getSelectedManga() != null) {
            setMangaId(MangaManager.getSelectedManga().getI());
        } else {
            //TODO da algum feedback de erro!
        }
    }

    void setMangaId(String mangaId) {
        if (this.mangaId != mangaId) {
            this.mangaId = mangaId;

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(MangaEdenURLs.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            MangaEdenService service = retrofit.create(MangaEdenService.class);
            Call<Manga> mangaCall = service.getManga(mangaId);
            mangaCall.enqueue(new Callback<Manga>() {
                @Override
                public void onResponse(Call<Manga> call, Response<Manga> response) {
                    if (response.isSuccessful()) {
                        manga.setValue(response.body());
                    }
                }

                @Override
                public void onFailure(Call<Manga> call, Throwable t) {
                    //TODO da algum feedback de erro!
                }
            });
        }
    }
}