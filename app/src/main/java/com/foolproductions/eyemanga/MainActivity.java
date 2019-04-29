package com.foolproductions.eyemanga;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.foolproductions.eyemanga.mangaEdenApi.MangaListItem;
import com.foolproductions.eyemanga.mangaEdenApi.MangaManager;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inicializa o Manga Manager!
        MangaManager.initialize(new MangaManager.MangaManagerInitializationListener() {
            @Override
            public void onSuccess() {
                List<MangaListItem> mangaListItens = MangaManager.getMangaListItens();
                for (int i = 0; i < mangaListItens.size(); i++) {
                    initializeRecyclerView();
                }
            }

            @Override
            public void onFailure() {
                Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_LONG).show();
            }
        });

    }

    void initializeRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.rvMangaList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new MangaRVAdapter(MangaManager.getMangaListItens()));
        recyclerView.setHasFixedSize(true);
    }
}
