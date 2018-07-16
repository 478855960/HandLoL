package com.example.heros.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.heros.R;
import com.example.heros.bean.Hero;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by DELL on 2018/7/10.
 */

public class HeroAdapter extends BaseAdapter {
    Context context;
    List<Hero> heros;

    public HeroAdapter(Context context, List<Hero> heros) {
        this.context = context;
        this.heros = heros;
    }

    @Override
    public int getCount() {
        return heros.size();
    }

    @Override
    public Object getItem(int i) {
        return heros.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.hero_item, null);
        try {
            ImageView ivLogo = (ImageView) view.findViewById(R.id.ImageView_LogoItem);
            TextView tvName = (TextView) view.findViewById(R.id.TextView_HeroName);

            Hero hero = heros.get(i);
            InputStream is = context.getAssets().open(hero.getSpath());
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            ivLogo.setImageBitmap(bitmap);
            tvName.setText(hero.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return view;
    }
}
