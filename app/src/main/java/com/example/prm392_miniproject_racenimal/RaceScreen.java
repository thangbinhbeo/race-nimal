package com.example.prm392_miniproject_racenimal;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class RaceScreen extends AppCompatActivity {
    TextView txtlogo;

    SeekBar seekBarTien;
    SeekBar seekBarBao;
    SeekBar seekBarManh;
    SeekBar seekBarTung;
    SeekBar seekBarBinh;

    private ConstraintLayout mainLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.race_game);

        txtlogo = (TextView) findViewById(R.id.textView16);

        seekBarBao = (SeekBar) findViewById(R.id.seekBarBao);
        seekBarTien = (SeekBar) findViewById(R.id.seekBarTien);
        seekBarManh = (SeekBar) findViewById(R.id.seekBarManh);
        seekBarTung = (SeekBar) findViewById(R.id.seekBarTung);
        seekBarBinh = (SeekBar) findViewById(R.id.seekBarBinh);

        mainLayout = (ConstraintLayout) findViewById(R.id.mainLayout);

        Shader textShader = new LinearGradient(
                0, 0, 0, txtlogo.getTextSize(),
                new int[]{
                        Color.parseColor("#FF5733"),
                        Color.parseColor("#FFC300"),
                        Color.parseColor("#DAF7A6")
                },
                null, Shader.TileMode.CLAMP);

        txtlogo.getPaint().setShader(textShader);

        changeBackground();
    }

    private void changeBackground() {
        mainLayout.setBackgroundResource(R.drawable.background_after);
    }
}
