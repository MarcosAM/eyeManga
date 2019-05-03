package com.foolproductions.eyemanga;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.foolproductions.eyemanga.fragments.ChaptersFragment;
import com.foolproductions.eyemanga.fragments.MangaDetailsFragment;
import com.foolproductions.eyemanga.mangaEdenApi.Manga;
import com.foolproductions.eyemanga.mangaEdenApi.MangaManager;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import org.apache.commons.text.StringEscapeUtils;

public class MangaActivity extends AppCompatActivity {

    public static final String EXTRA_NAME = "id";

    private ViewPager viewPager;
    private SmartTabLayout smartTabLayout;

    private MangaViewModel mangaViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manga);

        viewPager = findViewById(R.id.viewpager);
        smartTabLayout = findViewById(R.id.viewpagertab);

        mangaViewModel = ViewModelProviders.of(this).get(MangaViewModel.class);
        mangaViewModel.setMangaId(getIntent().getStringExtra(EXTRA_NAME));

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(),
                FragmentPagerItems.with(this)
                        //TODO pegar essas strings dos values
                        .add("About", MangaDetailsFragment.class)
                        .add("Chapters", ChaptersFragment.class)
                        .create()
        );

        viewPager.setAdapter(adapter);
        smartTabLayout.setViewPager(viewPager);

        /*MangaManager.setFetchMangaListener(new MangaManager.FetchMangaListener() {
            @Override
            public void onSuccess(Manga manga) {
                //if (tvTitle != null)
                //    tvTitle.setText(StringEscapeUtils.unescapeHtml4(manga.getDescription()));
                //Toast.makeText(MangaActivity.this, "Deu certo baixar o manga!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure() {
            }
        }, mangaId);*/
    }
}