package com.foolproductions.eyemanga.readActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.foolproductions.eyemanga.R;
import com.foolproductions.eyemanga.mangaEdenApi.Chapter;

public class ReadActivity extends AppCompatActivity {

    public static final String EXTRA_NAME = "id";

    private RecyclerView recyclerView;
    private ReadViewModel readViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        recyclerView = findViewById(R.id.rvRead);

        readViewModel = ViewModelProviders.of(this).get(ReadViewModel.class);
        readViewModel.setChapterId(getIntent().getStringExtra(EXTRA_NAME));
        readViewModel.getChapter().observe(this, new Observer<Chapter>() {
            @Override
            public void onChanged(Chapter chapter) {
                LinearLayoutManager layoutManager = new LinearLayoutManager(ReadActivity.this);
                recyclerView.setLayoutManager(layoutManager);
                ReadRVAdapter adapter = new ReadRVAdapter(chapter.getImages());
                recyclerView.setAdapter(adapter);
                recyclerView.setHasFixedSize(true);
            }
        });
    }

    @Override
    protected void onDestroy() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        //TODO prestar atenção pq pode acabar retornando nulo
        Log.v("Read Activity", "Última página lida foi " + layoutManager.findFirstVisibleItemPosition());
        super.onDestroy();
    }
}
