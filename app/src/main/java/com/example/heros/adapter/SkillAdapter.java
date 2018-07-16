package com.example.heros.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.heros.R;
import com.example.heros.bean.Skill;

import java.io.InputStream;
import java.util.List;

/**
 * Created by DELL on 2018/7/11.
 */

public class SkillAdapter extends BaseAdapter {
    List<Skill> skills = null;
    Context context = null;

    public SkillAdapter(List<Skill> skills, Context context) {
        this.skills = skills;
        this.context = context;
    }

    @Override
    public int getCount() {
        return skills.size();
    }

    @Override
    public Skill getItem(int position) {
        return skills.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.skill_item, null);
        try {
            ImageView ivSkill=(ImageView) view.findViewById(R.id.image_Skill);
            Skill skill = getItem(position);
            String sikllPath = skill.getSkillPath();
            InputStream is = context.getAssets().open(sikllPath);
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            ivSkill.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }
}
