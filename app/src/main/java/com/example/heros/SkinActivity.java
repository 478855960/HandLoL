package com.example.heros;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import com.example.heros.adapter.LogoAdapter;
import com.example.heros.bean.Hero;
import com.example.heros.util.GalleryPageTransformer;
import com.example.heros.util.ImgUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class SkinActivity extends Activity {
    ViewPager skinGallery = null;
    LogoAdapter skinAdapter = null;
    Hero hero = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_skin);
        initUI();
    }

    private void initUI(){
        skinGallery = findViewById(R.id.skin_viewpager);
        initData();
    }

    private void initData(){
        LayoutInflater inflater = LayoutInflater.from(this);
        View logo1 = inflater.inflate(R.layout.logo1, null);
        View logo2 = inflater.inflate(R.layout.logo2, null);
        View logo3 = inflater.inflate(R.layout.logo3, null);
        Intent intent = getIntent();
        hero = (Hero) intent.getSerializableExtra("curHero");
        List<View> logos = new ArrayList<View>();
        String name = hero.getSpath().replaceAll(".png$","");
        name = name.replaceAll("/","");
        logos = getPages(name);
        skinAdapter = new LogoAdapter(logos);
        skinGallery.setAdapter(skinAdapter);
        skinGallery.setPageTransformer(true, new GalleryPageTransformer());

    }

    private List<View> getPages(String name) {
        List<View> pages=new ArrayList<>();
        Field[] fields=R.drawable.class.getDeclaredFields();
        try {
            for (Field field : fields) {
                if (field.getName().startsWith(name)) {
                    ImageView view = new ImageView(this);
                    view.setImageBitmap(ImgUtil.getReverseBitmapById(this, field.getInt(null), 0.5f));
                    pages.add(view);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return pages;
    }
}
