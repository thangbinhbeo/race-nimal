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

        edPassword = (EditText) findViewById(R.id.editTextPassword);
        txtTitle = (TextView) findViewById(R.id.textViewLogo);
        eyeIcon = (ImageView) findViewById(R.id.iconEye);

        Intent intent = getIntent();
        Account user = intent.getParcelableExtra("user");

        if (user != null) {
            AccountManager.getInstance().getAccountList().add(user);
        }

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
                // Xóa listener để tránh gọi nhiều lần
                txtTitle.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                // Tạo LinearGradient để áp dụng cho văn bản
                Shader shader = new LinearGradient(
                        0, 0, 0, txtTitle.getHeight(),  // Gradient theo chiều dọc (180 độ)
                        new int[] {
                                Color.parseColor("#A9C9FF"),  // Màu bắt đầu
                                Color.parseColor("#FFBBEC")   // Màu kết thúc
                        },
                        null,
                        Shader.TileMode.CLAMP  // Kiểu gradient
                );

                // Áp dụng shader cho paint của TextView
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
            for (Account account : AccountManager.getInstance().getAccountList()) {
                if (account.getUsername().equals(userName) && account.getPassword().equals(password)) {
                    isAuthenticated = true;
                    user = account;
                    break;
                }
            }
            if (isAuthenticated) {
                Intent intent = new Intent(LoginScreen.this, RaceScreen.class);
                intent.putExtra("user", user);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void signUp() {
        Intent intent = new Intent(this, SignupScreen.class);
        startActivity(intent);
        finish();
    }
}
