package com.foolproductions.eyemanga.mangaEdenApi;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MangaEdenService {

    @GET(MangaEdenURLs.MANGA_LIST_URL)
    public Call<MangaList> getMangaList();

}