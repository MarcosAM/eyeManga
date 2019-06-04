package com.foolproductions.eyemanga;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.foolproductions.eyemanga.fragments.AboutFragment;
import com.foolproductions.eyemanga.chaptersTab.ChaptersFragment;
import com.foolproductions.eyemanga.mangaEdenApi.MangaListItem;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

public class MangaActivity extends AppCompatActivity {

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

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(),
                FragmentPagerItems.with(this)
                        //TODO pegar essas strings dos values
                        .add("About", AboutFragment.class)
                        .add("Chapters", ChaptersFragment.class)
                        .create()
        );

        viewPager.setAdapter(adapter);
        smartTabLayout.setViewPager(viewPager);
        getSupportActionBar().setElevation(0);
    }
}