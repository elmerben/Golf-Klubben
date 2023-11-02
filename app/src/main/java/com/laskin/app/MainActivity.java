package com.laskin.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button pelaaGolfiaPainike = findViewById(R.id.aloita);
        pelaaGolfiaPainike.setOnClickListener(new View.OnClickListener() {
                                                  public void onClick(View v) {

                                                      Intent intent = new Intent(MainActivity.this, pelaaGolfia.class);
                                                      startActivity(intent);
                                                  }

                                              });
        mediaPlayer = MediaPlayer.create(this, R.raw.ranx);

        Button tarkastelePainike = findViewById(R.id.tarkastele);
        tarkastelePainike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MainActivity.this, kierrosTarkastelu.class);
                startActivity(intent2);
            }
        });

    }






    public void playMusic() {
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }



}