package com.example.prm392_miniproject_racenimal;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RechargeActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    Button btnNap;
    Button btnReturn;
    Intent intentfromHomePage;
    Intent intentFromThisPage;
    EditText addMoney;
    TextView RemainMoney;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recharge);

        btnNap = findViewById(R.id.buttonNap);
        btnReturn = findViewById(R.id.buttonReturn);
        intentfromHomePage = getIntent();
        intentFromThisPage = new Intent(RechargeActivity.this, MainActivity.class);
        addMoney = findViewById(R.id.editTextAddMoney);

//        sotienconlai = findViewById(R.id.price)
//
//        String remainmoney = intentfromHomePage.getStringExtra("remain");
//        RemainMoney.setText();


        mediaPlayer = MediaPlayer.create(this, R.raw.bgm);
        mediaPlayer.setLooping(false);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.start();
            }
        });
        mediaPlayer.start();

        btnNap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intentFromThisPage);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }

    }
}
