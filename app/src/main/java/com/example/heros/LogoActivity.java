package com.example.heros;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.example.heros.adapter.LogoAdapter;

import java.util.ArrayList;
import java.util.List;

public class LogoActivity extends Activity {
    ViewPager vpLogo;
    LogoAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        initialLogos();
    }

    private void initialLogos(){
        vpLogo = (ViewPager) findViewById(R.id.viewPager_Hero);
        LayoutInflater inflater = LayoutInflater.from(this);
        View logo1 = inflater.inflate(R.layout.logo1, null);
        View logo2 = inflater.inflate(R.layout.logo2, null);
        View logo3 = inflater.inflate(R.layout.logo3, null);
        List<View> logos = new ArrayList<View>();
        logos.add(logo1);logos.add(logo2);logos.add(logo3);
        adapter = new LogoAdapter(logos);
        vpLogo.setAdapter(adapter);
        //添加页面滑动变化的监听
        vpLogo.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int index) {
                if(index == 2){
                    //如果滑到最后一页,实现跳转
                    Intent intent = new Intent(LogoActivity.this,
                            LogoAnimActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {}

            @Override
            public void onPageScrollStateChanged(int arg0) {}
        });
    }
}
