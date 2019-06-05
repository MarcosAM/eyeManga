package com.foolproductions.eyemanga.historicDatabase;

import com.foolproductions.eyemanga.mangaEdenApi.MangaListItem;

import java.util.List;

public interface IHistoricDAO {

    boolean save(MangaListItem historic);

    boolean delete(String mangaId);

    List<MangaListItem> getList();
}
