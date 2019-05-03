package com.foolproductions.eyemanga;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.foolproductions.eyemanga.mangaEdenApi.Manga;
import com.foolproductions.eyemanga.mangaEdenApi.MangaManager;

import org.apache.commons.text.StringEscapeUtils;

public class MangaActivity extends AppCompatActivity {

    TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manga);

        tvTitle = findViewById(R.id.tvMangaTitle);

        String mangaId = getIntent().getStringExtra("id");
        MangaManager.setFetchMangaListener(new MangaManager.FetchMangaListener() {
            @Override
            public void onSuccess(Manga manga) {
                tvTitle.setText(StringEscapeUtils.unescapeHtml4(manga.getDescription()));
            }

            @Override
            public void onFailure() {

            }
        }, mangaId);
    }
}
