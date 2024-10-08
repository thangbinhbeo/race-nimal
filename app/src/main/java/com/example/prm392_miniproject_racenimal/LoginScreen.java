package com.example.prm392_miniproject_racenimal;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginScreen extends AppCompatActivity {
    private EditText edPassword;
    private EditText txtUsername;
    private TextView SignUpLink;
    private ImageView eyeIcon;
    private TextView txtTitle;
    private TextView ButtonLogin;
    private final boolean[] isPasswordVisible = {false};


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_game);

        SoundHelper.startBackgroundMusic(this, R.raw.background_login_signup);

        edPassword = (EditText) findViewById(R.id.editTextPassword);
        txtTitle = (TextView) findViewById(R.id.textViewLogo);
        eyeIcon = (ImageView) findViewById(R.id.iconEye);

        txtUsername = (EditText) findViewById(R.id.txtUsername);
        SignUpLink = (TextView) findViewById(R.id.SignUpLink);
        ButtonLogin = findViewById(R.id.ButtonLogin);

        ButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        SignUpLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });

        txtTitle.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                txtTitle.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                Shader shader = new LinearGradient(
                        0, 0, 0, txtTitle.getHeight(),
                        new int[] {
                                Color.parseColor("#A9C9FF"),
                                Color.parseColor("#FFBBEC")
                        },
                        null,
                        Shader.TileMode.CLAMP
                );

                txtTitle.getPaint().setShader(shader);
            }
        });


        eyeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isPasswordVisible[0]) {
                    edPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    eyeIcon.setImageResource(R.mipmap.password_eye_close_foreground);
                } else {
                    edPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    eyeIcon.setImageResource(R.mipmap.password_eye_foreground);
                }

                edPassword.setSelection(edPassword.getText().length());
                isPasswordVisible[0] = !isPasswordVisible[0];
            }
        });
    }

    private void signIn() {
        String userName = txtUsername.getText().toString();
        String password = edPassword.getText().toString();

        boolean isAuthenticated = false;

        if(!userName.isEmpty() && !password.isEmpty()) {
            Account user = null;
            for (Account account : AccountManager.getInstance(this).getAccountList()) {
                if (account.getUsername().equals(userName) && account.getPassword().equals(password)) {
                    isAuthenticated = true;
                    user = account;
                    break;
                }
            }
            if (isAuthenticated) {
                SoundHelper.stopBackgroundMusic();

                Intent intent = new Intent(LoginScreen.this, HomeScreen.class);
                intent.putExtra("user", user);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            } else {
                Toast.makeText(this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void signUp() {
        Intent intent = new Intent(this, SignupScreen.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }

}
