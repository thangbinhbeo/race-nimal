package com.example.prm392_miniproject_racenimal;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Random;

import pl.droidsonroids.gif.GifImageView;

public class RaceScreen extends AppCompatActivity {
    TextView txtlogo;

    SeekBar seekBarTien;
    SeekBar seekBarBao;
    SeekBar seekBarManh;
    SeekBar seekBarTung;
    SeekBar seekBarBinh;

    EditText betBorse;
    EditText betTat;
    EditText betBhale;
    EditText betMutterfly;
    EditText betTear;

    private TextView btnStart;
    private TextView btnBet;
    private TextView tvBudget;
    private TextView btnAdd;
    private ImageView btnBack;

    private Handler handler = new Handler();
    private Random random = new Random();

    Account user;

    private ConstraintLayout mainLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.race_game);

        txtlogo = (TextView) findViewById(R.id.textView16);

        Intent intent = getIntent();

        user = intent.getParcelableExtra("user");

        user = AccountManager.getInstance(this).getAccount(user);

        if (user == null) {
            Toast.makeText(this, "Null user!", Toast.LENGTH_SHORT).show();
        }

        seekBarBao = findViewById(R.id.seekBarBao);
        seekBarTien = findViewById(R.id.seekBarTien);
        seekBarBinh = findViewById(R.id.seekBarBinh);
        seekBarManh = findViewById(R.id.seekBarManh);
        seekBarTung = findViewById(R.id.seekBarTung);
        btnStart = findViewById(R.id.raceButton);
        btnBet = findViewById(R.id.betButton);
        tvBudget = findViewById(R.id.tvBudget);
        betBorse = findViewById(R.id.editTextNumberBao);
        betTat = findViewById(R.id.editTextNumberTien);
        betBhale = findViewById(R.id.editTextNumberBinh);
        betMutterfly = findViewById(R.id.editTextNumberManh);
        betTear = findViewById(R.id.editTextNumberTung);
        btnAdd = findViewById(R.id.addMore);
        btnBack = findViewById(R.id.btnLogout);

        freezeSeekbar();
        loadBudget();
        setStartButton(btnStart, false);

        btnStart.setOnClickListener(v -> startRace());
        btnBet.setOnClickListener(v -> bet());
        btnAdd.setOnClickListener(v -> addMoney(user));

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

        btnBack.setOnClickListener(v -> back());
    }

    private void freezeSeekbar() {
        seekBarBao.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });

        seekBarTien.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });

        seekBarBinh.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });

        seekBarManh.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });

        seekBarTung.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
    }

    private void addMoney(Account user) {
        Intent intent = new Intent(RaceScreen.this, WalletActivity.class);
        intent.putExtra("user", user);
        intent.putExtra("activity", "raceScreen");
        startActivity(intent);
        finish();
    }

    private void setStartButton(TextView btn, boolean flag) {
        if (flag) {
            btn.setEnabled(flag);
            btn.setBackground(getDrawable(R.drawable.race_button));
        } else {
            btn.setEnabled(false);
            btn.setBackgroundColor(Color.parseColor("#FFBDBDBD"));
        }
    }

    private void setBetButton(TextView btn, boolean flag) {
        if (flag) {
            btn.setEnabled(flag);
            btn.setBackground(getDrawable(R.drawable.bet_button));
        } else {
            btn.setEnabled(false);
            btn.setBackgroundColor(Color.parseColor("#FFBDBDBD"));
        }
    }

    private void changeBackground() {
        mainLayout.setBackgroundResource(R.drawable.background_after);
    }

    private void bet() {
        if (!isBudgetValid()) {
            Toast.makeText(this, "Out of budget!", Toast.LENGTH_SHORT).show();
        } else {
            loadBudget();
            setStartButton(btnStart, true);
            setBetButton(btnBet, false);
            betBorse.setEnabled(false);
            betTat.setEnabled(false);
            betBhale.setEnabled(false);
            betMutterfly.setEnabled(false);
            betTear.setEnabled(false);
        }
    }

    private void resetProgress() {
        seekBarBao.setProgress(0);
        seekBarTien.setProgress(0);
        seekBarBinh.setProgress(0);
        seekBarManh.setProgress(0);
        seekBarTung.setProgress(0);
    }

    private void startRace() {
        resetProgress();
        setStartButton(btnStart, false);
        btnBack.setEnabled(false);
        btnAdd.setEnabled(false);
        animateHorses();
    }

    private void animateHorses() {
        try {
            double betBorseMoney;
            double betTatMoney;
            double betBhaleMoney;
            double betMutterflyMoney;
            double betTearMoney;

            if (!betBorse.getText().toString().isEmpty()) {
                betBorseMoney = Double.parseDouble(betBorse.getText().toString());
            } else {
                betBorseMoney = 0;
            }

            if (!betTat.getText().toString().isEmpty()) {
                betTatMoney = Double.parseDouble(betTat.getText().toString());
            } else {
                betTatMoney = 0;
            }

            if (!betBhale.getText().toString().isEmpty()) {
                betBhaleMoney = Double.parseDouble(betBhale.getText().toString());
            } else {
                betBhaleMoney = 0;
            }

            if (!betMutterfly.getText().toString().isEmpty()) {
                betMutterflyMoney = Double.parseDouble(betMutterfly.getText().toString());
            } else {
                betMutterflyMoney = 0;
            }

            if (!betTear.getText().toString().isEmpty()) {
                betTearMoney = Double.parseDouble(betTear.getText().toString());
            } else {
                betTearMoney = 0;
            }

            final int finishLine = 100; // Finish line at 100%

            new Thread(() -> {
                while (seekBarBao.getProgress() < finishLine && seekBarTien.getProgress() < finishLine &&
                        seekBarBinh.getProgress() < finishLine && seekBarManh.getProgress() < finishLine &&
                        seekBarTung.getProgress() < finishLine) {

                    handler.post(() -> {
                        seekBarBao.setProgress(seekBarBao.getProgress() + random.nextInt(3));
                        seekBarTien.setProgress(seekBarTien.getProgress() + random.nextInt(3));
                        seekBarBinh.setProgress(seekBarBinh.getProgress() + random.nextInt(3));
                        seekBarManh.setProgress(seekBarManh.getProgress() + random.nextInt(3));
                        seekBarTung.setProgress(seekBarTung.getProgress() + random.nextInt(3));
                    });

                    try {
                        Thread.sleep(80); // Delay for animation effect
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                handler.post(() -> {
                    String winnerMessage = "It's a Tie!";
                    String moneyWinMessage = "";
                    boolean isWinnerFound = false;
                    double totalMoney = 0;
                    double winMoney = 0;

                    if (seekBarBao.getProgress() >= finishLine) {
                        winnerMessage = "Borse Wins!";
                        winMoney = betBorseMoney;
                        totalMoney = betBorseMoney - betTatMoney - betBhaleMoney - betMutterflyMoney - betTearMoney;
                        isWinnerFound = true;
                    } else if (seekBarTien.getProgress() >= finishLine) {
                        winnerMessage = "Tat Wins!";
                        winMoney = betTatMoney ;
                        totalMoney = - betBorseMoney + betTatMoney - betBhaleMoney - betMutterflyMoney - betTearMoney;
                        isWinnerFound = true;
                    } else if (seekBarBinh.getProgress() >= finishLine) {
                        winnerMessage = "Bhale Wins!";
                        winMoney = betBhaleMoney;
                        totalMoney = - betBorseMoney - betTatMoney + betBhaleMoney - betMutterflyMoney - betTearMoney;
                        isWinnerFound = true;
                    } else if (seekBarManh.getProgress() >= finishLine) {
                        winnerMessage = "Mutterfly Wins!";
                        winMoney = betMutterflyMoney;
                        totalMoney = - betBorseMoney - betTatMoney - betBhaleMoney + betMutterflyMoney - betTearMoney;
                        isWinnerFound = true;
                    } else if (seekBarTung.getProgress() >= finishLine) {
                        winnerMessage = "Tear Wins!";
                        winMoney = betTearMoney;
                        totalMoney = - betBorseMoney - betTatMoney - betBhaleMoney - betMutterflyMoney + betTearMoney;
                        isWinnerFound = true;
                    }

                    AccountManager.getInstance(this).addMoney(user, winMoney * 2);

                    if (totalMoney < 0) {
                        moneyWinMessage = "You lose " + (-totalMoney) + " USD";
                    } else {
                        moneyWinMessage = "You win " + totalMoney + " USD";
                    }

                    if (!isWinnerFound) {
                        winnerMessage = "It's a Tie!";
                    }

                    showResultDialog(winnerMessage, moneyWinMessage);

                    setBetButton(btnBet, true);
                    setStartButton(btnStart, false);
                    btnBack.setEnabled(true);
                    btnAdd.setEnabled(true);
                    loadBudget();
                    resetProgress();
                });
            }).start();
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void showResultDialog(String message, String moneyWinMessage) {
        View dialogView = getLayoutInflater().inflate(R.layout.result_main, null);

        ImageView winnerImage = dialogView.findViewById(R.id.gifImageView);
        TextView winnerMessage = dialogView.findViewById(R.id.txtScore);
        TextView moneyMessage = dialogView.findViewById(R.id.txtMoney);

        winnerMessage.setText(message);
        moneyMessage.setText(moneyWinMessage);

        if (message.contains("Borse")) {
            winnerImage.setImageResource(R.drawable.bao_horse); // Replace with your image resource
        } else if (message.contains("Tat")) {
            winnerImage.setImageResource(R.drawable.tien_cat); // Replace with your image resource
        } else if (message.contains("Bhale")) {
            winnerImage.setImageResource(R.drawable.binh_whale); // Replace with your image resource
        } else if (message.contains("Mutterfly")) {
            winnerImage.setImageResource(R.drawable.manh_butterfly); // Replace with your image resource
        } else if (message.contains("Tear")) {
            winnerImage.setImageResource(R.drawable.tung_polar); // Replace with your image resource
        } else {
            winnerImage.setImageResource(R.drawable.tivo); // Replace with your image resource
        }

        // Create and show the dialog
        new AlertDialog.Builder(this)
                .setView(dialogView)
                .setPositiveButton("OK", null)
                .show();
    }

    private void loadBudget() {
        tvBudget.setText("$" + AccountManager.getInstance(this).getAccount(user).getBudget());
        betBorse.setEnabled(true);
        betTat.setEnabled(true);
        betBhale.setEnabled(true);
        betMutterfly.setEnabled(true);
        betTear.setEnabled(true);
    }

    private boolean isBudgetValid() {
        double budget = user.getBudget();

        double betBorseMoney = 0;
        double betTatMoney = 0;
        double betBhaleMoney = 0;
        double betMutterflyMoney = 0;
        double betTearMoney = 0;

        // Retrieve bet amounts
        if (!betBorse.getText().toString().isEmpty()) {
            betBorseMoney = Double.parseDouble(betBorse.getText().toString());
        }

        if (!betTat.getText().toString().isEmpty()) {
            betTatMoney = Double.parseDouble(betTat.getText().toString());
        }

        if (!betBhale.getText().toString().isEmpty()) {
            betBhaleMoney = Double.parseDouble(betBhale.getText().toString());
        }

        if (!betMutterfly.getText().toString().isEmpty()) {
            betMutterflyMoney = Double.parseDouble(betMutterfly.getText().toString());
        }

        if (!betTear.getText().toString().isEmpty()) {
            betTearMoney = Double.parseDouble(betTear.getText().toString());
        }

        if (betBorseMoney > budget || betTatMoney > budget ||
                betBhaleMoney > budget || betMutterflyMoney > budget ||
                betTearMoney > budget) {
            return false;
        }

        double totalBet = betBorseMoney + betTatMoney + betBhaleMoney + betMutterflyMoney + betTearMoney;
        if (totalBet > budget) {
            return false;
        }

        AccountManager.getInstance(this).addMoney(user, -totalBet);
        return true;
    }

    private void back() {
        Intent intent = new Intent(this, HomeScreen.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }
}
