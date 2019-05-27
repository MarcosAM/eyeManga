package com.foolproductions.eyemanga.historicDatabase;

import java.util.List;

public interface IHistoricDAO {

    boolean save(ReadingHistoric historic);

    boolean delete(ReadingHistoric historic);

    List<ReadingHistoric> getList();
}
