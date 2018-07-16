package com.example.heros;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.app.ActionBar.LayoutParams;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.heros.adapter.SkillAdapter;
import com.example.heros.bean.Hero;
import com.example.heros.bean.Skill;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;

public class HeroMessActivity extends Activity {

    Hero hero = null;
    ImageView ivHeroBlogo = null;
    Button buttonHeroDetial = null;
    Button buttonHeroSkill = null;
    TextView tvHeroDetail = null;
    TextView tvHeroSkill = null;
    FrameLayout flDetial = null;
    LinearLayout llSkill = null;
    Gallery gallerySkill=null;
    SkillAdapter adapter=null;
    Bitmap bitmap=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero_mess);

        getData();// 获取hero
        initialUI();// 初始化控件
        setHeroDetial();// 设置初始化页面
        setHeroSkill();// 设置技能信息
        setListener(); // 监听按钮

    }

    // 显示技能详情
    private void setHeroSkill() {
        // 获得一个技能对象
        Skill skill=hero.getSkills().get(0);
        String skillName= skill.getSkillName();
        String skillDetial = getSkillByPath(skillName);
        tvHeroSkill.setText(skillDetial);
    }
    // 根据路径获取技能详情
    private String getSkillByPath(String url) {
        String text=null;
        BufferedReader reader=null;
        try {
            InputStream is = this.getAssets().open(url);
            reader = new BufferedReader(new InputStreamReader(is));
            StringBuffer sb=new StringBuffer();
            String line=null;
            while ((line=reader.readLine()) != null) {
                sb.append(line);
            }
            text=sb.toString();

        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return text;
    }

    // 初始化控件
    private void initialUI() {

        flDetial = (FrameLayout) findViewById(R.id.framelayout_Detial);
        llSkill = (LinearLayout) findViewById(R.id.linerlayout_Skill);
        ivHeroBlogo = (ImageView) findViewById(R.id.imageview_HeroMess);
        tvHeroDetail = (TextView) findViewById(R.id.textview_Detial);
        tvHeroSkill = (TextView) findViewById(R.id.textview_Skill);
        buttonHeroDetial = (Button) findViewById(R.id.button_Detial);
        buttonHeroSkill = (Button) findViewById(R.id.button_Skill);
        gallerySkill=(Gallery) findViewById(R.id.gallery_Skill);
        adapter = new SkillAdapter(hero.getSkills(), this);
        // 适配数据
        gallerySkill.setAdapter(adapter);
    }

    // 设置初始化页面
    private void setHeroDetial() {

        try {
            String bpath = hero.getBpath();
            InputStream is = this.getAssets().open(bpath);
            bitmap = BitmapFactory.decodeStream(is);
            // 吧bitmap设置到控件上
            ivHeroBlogo.setImageBitmap(bitmap);
            // 获得英雄介绍的详细信息
            String heroPath = hero.getHeroPath();
            String detial = getHeroDetialByPath(heroPath);
            tvHeroDetail.setText(detial);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 根据路径获取英雄信息
    private String getHeroDetialByPath(String path) {
        String text = null;
        BufferedReader reader = null;
        try {
            InputStream is = this.getAssets().open(path);
            reader = new BufferedReader(new InputStreamReader(is));
            StringBuffer sb = new StringBuffer();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            text = sb.toString();
            is.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return text;
    }

    // 监听按钮
    private void setListener() {
        // 英雄详细信息
        buttonHeroDetial.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 设置可视化
                flDetial.setVisibility(View.VISIBLE);
                llSkill.setVisibility(View.GONE);
            }
        });
        // 英雄技能信息
        buttonHeroSkill.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 设置可视化
                flDetial.setVisibility(View.GONE);
                llSkill.setVisibility(View.VISIBLE);
            }
        });
        // 监听点击技能图像
        gallerySkill.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0,
                                    View arg1, int position, long arg3) {
                Skill skill = hero.getSkills().get(position);
                String skillName= skill.getSkillName();
                String skillDetial = getSkillByPath(skillName);
                if(tvHeroSkill.getText() != null){
                    tvHeroSkill.setText(null);
                    tvHeroSkill.setText(skillDetial);
                }else {
                    tvHeroSkill.setText(skillDetial);
                }

            }

        });
        // 弹出popwindow
        ivHeroBlogo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                View view = LayoutInflater.from(HeroMessActivity.this).
                        inflate(R.layout.popwindow_hero, null);
                // 创建一个pop窗口
                final PopupWindow popWindow=new PopupWindow(view,
                        LayoutParams.MATCH_PARENT,
                        LayoutParams.MATCH_PARENT);
                ImageView ivPopWindow=(ImageView) view.findViewById(R.id.imageview_Popwindow);
                ivPopWindow.setScaleType(ImageView.ScaleType.FIT_XY);

                ivPopWindow.setImageBitmap(bitmap);

                // 显示popwindwo窗口
                popWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
                // 点击大图，关闭popwindow窗口
                ivPopWindow.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        popWindow.dismiss();
                    }
                });
            }
        });
    }

    // 获取前一个activity传来的hero
    private void getData() {
        Intent intent = getIntent();
        Serializable s = (Hero) intent.getSerializableExtra("hero");

        if (s instanceof Hero) {
            hero = (Hero) s;
        }
    }
}
