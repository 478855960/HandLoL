package com.example.heros;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class LogoAnimActivity extends Activity {
    ImageView imgAnim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo_anim);

        imgAnim = (ImageView) findViewById(R.id.imageView_Anim);
        //将动画文件转换成一个动画对象
        Animation anim = AnimationUtils.loadAnimation(this,
                R.anim.logo_anim);
        //设置动画
        imgAnim.setAnimation(anim);

        // 监听对话对象的事件，动画事件结束，实现跳转
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(LogoAnimActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
