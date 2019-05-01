package com.foolproductions.eyemanga.mangaEdenApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MangaManager {

    private static MangaList mangaList;
    private static List<String> categories = new ArrayList<>();

    public static void initialize(final MangaManagerInitializationListener listener) {
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
                    fetchCategories();
                    listener.onSuccess();
                } else {
                    listener.onFailure();
                }
            }

            @Override
            public void onFailure(Call<MangaList> call, Throwable t) {
                listener.onFailure();
            }
        });
    }

    static void fetchCategories() {
        List<MangaListItem> mangas = mangaList.getManga();

        for (MangaListItem manga : mangas) {
            for (String category : manga.getC()) {
                if (!categories.contains(category)) {
                    categories.add(category);
                }
            }
        }
    }

    public static List<MangaListItem> getMangaListItens() {
        return mangaList.getManga();
    }

    public static List<String> getCategories() {
        return categories;
    }

    public interface MangaManagerInitializationListener {
        public void onSuccess();

        public void onFailure();
    }
}