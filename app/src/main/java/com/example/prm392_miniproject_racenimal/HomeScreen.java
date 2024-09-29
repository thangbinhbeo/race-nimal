package com.example.prm392_miniproject_racenimal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class HomeScreen extends AppCompatActivity {

    private TextView txtUsername;
    private TextView money;
    private ImageView addMoneyButton;
    private TextView textViewBetNow;
    private TextView textViewLogOut;

    Account user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_game);

        txtUsername = findViewById(R.id.txtUsername);
        money = findViewById(R.id.money);
        addMoneyButton = findViewById(R.id.addMoneyButton);
        textViewBetNow = findViewById(R.id.textViewBetNow);
        textViewLogOut = findViewById(R.id.textViewLogOut);

        Intent intent = getIntent();

        user = intent.getParcelableExtra("user");

        user = AccountManager.getInstance(this).getAccount(user);

        txtUsername.setText("" + user.getUsername());

        money.setText("$" + AccountManager.getInstance(this).getAccount(user).getBudget());

        addMoneyButton.setOnClickListener(v -> addMoney(user));

        textViewLogOut.setOnClickListener(v -> logout());

        textViewBetNow.setOnClickListener(v -> goToRaceScreen());
    }

    private void addMoney(Account user) {
        Intent intent = new Intent(HomeScreen.this, WalletActivity.class);
        intent.putExtra("user", user);
        intent.putExtra("activity", "homeScreen");
        startActivity(intent);
        finish();
    }

    private void goToRaceScreen() {
        Intent intent = new Intent(this, RaceScreen.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }

    private void logout() {
        Intent intent = new Intent(this, LoginScreen.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }
}
