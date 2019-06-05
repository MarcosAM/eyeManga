package com.foolproductions.eyemanga.mangaActivity;

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

    private MutableLiveData<Manga> manga = new MutableLiveData<>();

    public MangaViewModel() {
        if (MangaManager.getSelectedManga() != null) {
            fetchManga(MangaManager.getSelectedManga().getI());
        }
    }

    private void fetchManga(String mangaId) {
        if (mangaId != null) {
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
                }
            });
        }
    }

    public MutableLiveData<Manga> getManga() {
        return manga;
    }
}