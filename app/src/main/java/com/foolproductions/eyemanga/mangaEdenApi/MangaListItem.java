package com.foolproductions.eyemanga.mangaEdenApi;

import java.util.List;

public class MangaListItem {

    private String t;
    private String im;
    private String i;
    private List<String> c;

    private String chapterId;
    private int page;

    public MangaListItem() {
    }

    public MangaListItem(String id, String title, String image, String chapterId, int page) {
        i = id;
        t = title;
        im = image;
        this.chapterId = chapterId;
        this.page = page;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

    public String getIm() {
        return im;
    }

    public void setIm(String im) {
        this.im = im;
    }

    public String getI() {
        return i;
    }

    public void setI(String i) {
        this.i = i;
    }

    public List<String> getC() {
        return c;
    }

    public void setC(List<String> c) {
        this.c = c;
    }


    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}