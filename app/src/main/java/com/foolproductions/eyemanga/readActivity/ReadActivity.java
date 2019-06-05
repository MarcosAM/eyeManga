package com.foolproductions.eyemanga.readActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.foolproductions.eyemanga.R;
import com.foolproductions.eyemanga.mangaEdenApi.Chapter;
import com.foolproductions.eyemanga.mangaEdenApi.Manga;
import com.foolproductions.eyemanga.mangaEdenApi.MangaListItem;
import com.foolproductions.eyemanga.mangaEdenApi.MangaManager;

public class ReadActivity extends AppCompatActivity {

    public static final String EXTRA_NAME = "chapterId";

    private RecyclerView recyclerView;
    private Button btnNextChapter;
    private ReadViewModel readViewModel;
    private LinearLayoutManager layoutManager = new LinearLayoutManager(ReadActivity.this);

    private boolean isAtBottom = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        btnNextChapter = findViewById(R.id.btnNextChapter);
        btnNextChapter.setVisibility(View.GONE);
        btnNextChapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (readViewModel.goToNextChapter()) {
                    btnNextChapter.setVisibility(View.GONE);
                } else {
                    //TODO relatar que deu erro!
                }
            }
        });
        recyclerView = findViewById(R.id.rvRead);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                setAtBottom(!recyclerView.canScrollVertically(1));
            }
        });

        readViewModel = ViewModelProviders.of(this).get(ReadViewModel.class);
        readViewModel.initIfNecessery(
                getApplicationContext(),
                (Manga) getIntent().getSerializableExtra("serializedManga"),
                getIntent().getStringExtra(EXTRA_NAME));

        readViewModel.getChapter().observe(this, new Observer<Chapter>() {
            @Override
            public void onChanged(Chapter chapter) {
                ReadRVAdapter adapter = new ReadRVAdapter(chapter.getImages());
                recyclerView.setAdapter(adapter);
                recyclerView.setHasFixedSize(true);
                //TODO ver isso aqui
                recyclerView.scrollToPosition(readViewModel.getStartingPage());
            }
        });
    }

    private void setAtBottom(boolean bottom) {
        //TODO botão sempre estar funcionando no final, mas só mudar as frases de "Next Chapter" para "End"
        //TODO fazer essa definição logo acima tbm, do event listener
        if (isAtBottom == bottom) {
            return;
        }
        isAtBottom = bottom;
        if (bottom) {
            if (readViewModel.hasNextChapter()) {
                btnNextChapter.setVisibility(View.VISIBLE);
            }
        } else {
            btnNextChapter.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        int page = -1;
        try {
            page = layoutManager.findFirstVisibleItemPosition();
        } catch (Exception e) {

        } finally {
            if (page >= 0) {
                MangaListItem historic = new MangaListItem();

                historic.setI(MangaManager.getSelectedManga().getI());
                historic.setT(MangaManager.getSelectedManga().getT());
                historic.setIm(MangaManager.getSelectedManga().getIm());

                historic.setChapterId(readViewModel.getChapterId());
                historic.setPage(page);
                readViewModel.getHistoricDAO().save(historic);
            }
        }
        super.onDestroy();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (hasFocus) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
}
