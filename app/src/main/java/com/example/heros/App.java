package com.example.heros;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class App extends Activity {

    VideoView video;
    Button play;
    Button pause;
    MediaController mediaController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_layout);
        initUI();

    }

    public void initUI(){
        video = findViewById(R.id.video);
        play = findViewById(R.id.play);
        pause = findViewById(R.id.pause);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                init();
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                video.stopPlayback();
            }
        });
    }

    public void init(){
        mediaController = new MediaController(this);
        String url = "android.resource://" + getPackageName() + "/" + R.raw.search;
        video.setVideoURI(Uri.parse(url));
        video.setMediaController(mediaController);
        mediaController.setMediaPlayer(video);
        video.requestFocus();
        video.start();
    }
}
