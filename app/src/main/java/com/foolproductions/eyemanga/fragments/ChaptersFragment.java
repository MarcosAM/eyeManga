package com.foolproductions.eyemanga.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.foolproductions.eyemanga.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChaptersFragment extends Fragment {


    public ChaptersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chapters, container, false);
    }

}
