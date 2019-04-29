package com.foolproductions.eyemanga.mangaEdenApi;

import com.foolproductions.eyemanga.mangaEdenApi.MangaEdenService;
import com.foolproductions.eyemanga.mangaEdenApi.MangaEdenURLs;
import com.foolproductions.eyemanga.mangaEdenApi.MangaList;
import com.foolproductions.eyemanga.mangaEdenApi.MangaListItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MangaManager {

    private static MangaList mangaList;

    public static void initialize(final MangaManagerInitializationListener listiner) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MangaEdenURLs.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MangaEdenService service = retrofit.create(MangaEdenService.class);
        Call<MangaList> mangaListCall = service.getMangaList();
        mangaListCall.enqueue(new Callback<MangaList>() {
            @Override
            public void onResponse(Call<MangaList> call, Response<MangaList> response) {
                if (response.isSuccessful()) {
                    mangaList = response.body();
                    listiner.onSuccess();
                } else {
                    listiner.onFailure();
                }
            }

            @Override
            public void onFailure(Call<MangaList> call, Throwable t) {
                listiner.onFailure();
            }
        });
    }

    public static List<MangaListItem> getMangaListItens() {
        return mangaList.getManga();
    }

    public interface MangaManagerInitializationListener {
        public void onSuccess();

        public void onFailure();
    }
}