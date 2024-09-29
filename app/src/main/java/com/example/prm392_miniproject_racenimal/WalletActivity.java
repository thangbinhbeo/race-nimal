package com.example.prm392_miniproject_racenimal;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class WalletActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private TextView btnAdd, btnBack;
    private Intent intentfromMain, intentFromThisPage;
    private EditText etEnterAmount;
    private TextView tvWalletBalance;

    private double currentBalance = 0.0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.wallet);

        btnAdd = findViewById(R.id.txtAdd);
        btnBack = findViewById(R.id.txtBack);
        etEnterAmount = findViewById(R.id.et_enter_amount);
        tvWalletBalance = findViewById(R.id.tv_wallet_balance);

        // Lấy số tiền còn lại từ Main
        intentfromMain = getIntent();

        Account user = intentfromMain.getParcelableExtra("user");

        String activity = intentfromMain.getStringExtra("activity");

        if (user == null) {
            Toast.makeText(this, "Null user!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            currentBalance = user.getBudget();
            updateWalletBalance();
        }

        if (!activity.isEmpty()) {
            if (activity.equals("raceScreen")) {
                intentFromThisPage = new Intent(WalletActivity.this, RaceScreen.class);
            } else {
                intentFromThisPage = new Intent(WalletActivity.this, HomeScreen.class);
            }
        }
        // Intent về MainActivity

        mediaPlayer = MediaPlayer.create(this, R.raw.bgm);
        mediaPlayer.setLooping(false);
        mediaPlayer.setOnCompletionListener(mp -> mp.start());
        mediaPlayer.start();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String amountString = etEnterAmount.getText().toString().trim();
                if (!amountString.isEmpty()) {
                    double amount = Double.parseDouble(amountString);
                    if (amount > 0) {
                        AccountManager.getInstance(WalletActivity.this).addMoney(user, amount);

                        currentBalance += amount;
                        updateWalletBalance();

                        Toast.makeText(WalletActivity.this, "Added $" + amount + " to your wallet!", Toast.LENGTH_SHORT).show();

                        intentFromThisPage.putExtra("user", AccountManager.getInstance(WalletActivity.this).getAccount(user));
                    } else {
                        Toast.makeText(WalletActivity.this, "Please enter a positive amount.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(WalletActivity.this, "Please enter an amount.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentFromThisPage.putExtra("user", AccountManager.getInstance(WalletActivity.this).getAccount(user));
                startActivity(intentFromThisPage);
                finish();
            }
        });
    }

    private void updateWalletBalance() {
        tvWalletBalance.setText(String.format("$%.2f", currentBalance));
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
