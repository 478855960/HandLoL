package com.example.heros.media;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

import com.example.heros.R;

/**
 * 控制音乐
 * Created by DELL on 2018/7/10.
 */

public class HeroMedia {
    public MediaPlayer player;

    public HeroMedia(Context context) {
        player = MediaPlayer.create(context, R.raw.music);
        player.setOnErrorListener(new MediaPlayer.OnErrorListener() {

            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Log.i("TAG", "出错了" + what + "----" + extra);
                return false;
            }
        });
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                // 该方法被回调的时候，说明歌曲演唱完毕，可以做一些资源的回收操作

            }
        });
    }

    // 播放歌曲
    public void start() {
        if (player != null) {
            player.start();
        }
    }

    // 关闭歌曲
    public void pause() {
        if (player != null && player.isPlaying()) {
            player.pause();
        }
    }

    public void stop() {
        if (player != null) {
            player.stop();
            player.release();
            player = null;// 置空的目的：变成空对象有助于垃圾处理器释放内存
        }
    }
}
