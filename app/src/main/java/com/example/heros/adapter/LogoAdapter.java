package com.example.heros.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by DELL on 2018/7/9.
 */

public class LogoAdapter extends PagerAdapter {
    List<View> logos = null;
    public LogoAdapter(List<View> logos){
        this.logos = logos;
    }

    @Override
    public int getCount() {
        return logos.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = logos.get(position);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(logos.get(position));
    }
}
