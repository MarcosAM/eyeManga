package com.foolproductions.eyemanga.mangaEdenApi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MangaEdenService {

    @GET(MangaEdenURLs.MANGA_LIST_URL)
    public Call<MangaList> getMangaList();

    @GET(MangaEdenURLs.MANGA_URL)
    public Call<Manga> getManga(@Path(MangaEdenURLs.MANGA_PATH) String mangaId);

}