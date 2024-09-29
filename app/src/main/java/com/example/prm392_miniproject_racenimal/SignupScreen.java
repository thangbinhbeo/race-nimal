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

public class SignupScreen extends AppCompatActivity {
    private EditText editTextUserName;
    private EditText edPassword;
    private EditText edConfirmPass;
    private ImageView eyeIcon;
    private ImageView eyeIconConfirm;
    private TextView txtTitle;
    private TextView SignInLink;
    private TextView fakeBtnSignUp;
    private final boolean[] isPasswordVisible = {false};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.signup_game);

        edPassword = (EditText) findViewById(R.id.editTextPassword);
        edConfirmPass = (EditText) findViewById(R.id.editTextConfirmPassword);
        txtTitle = (TextView) findViewById(R.id.textViewLogo);
        eyeIcon = (ImageView) findViewById(R.id.iconEye);
        eyeIconConfirm = (ImageView) findViewById(R.id.confirmIconEye);
        editTextUserName = (EditText) findViewById(R.id.editTextUserName);
        SignInLink = (TextView) findViewById(R.id.SignInLink);
        fakeBtnSignUp = findViewById(R.id.fakeButtonSignup);

        SignInLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        fakeBtnSignUp.setOnClickListener(new View.OnClickListener() {
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

        eyeIconConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isPasswordVisible[0]) {
                    edConfirmPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    eyeIconConfirm.setImageResource(R.mipmap.password_eye_close_foreground);
                } else {
                    edConfirmPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    eyeIconConfirm.setImageResource(R.mipmap.password_eye_foreground);
                }

                edConfirmPass.setSelection(edConfirmPass.getText().length());
                isPasswordVisible[0] = !isPasswordVisible[0];
            }
        });
    }

    private void signUp() {
        String userName = editTextUserName.getText().toString();
        String password = edPassword.getText().toString();
        String confirmPassword = edConfirmPass.getText().toString();

        if(!userName.isEmpty() && !password.isEmpty() && password.equals(confirmPassword)) {
            Account user = AccountManager.getInstance(this).getAccount(userName, password);
            if (user == null) {
                user = new Account(userName, password, 100);
                AccountManager.getInstance(this).addAccount(user);
                Intent intent = new Intent(this, LoginScreen.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "User is already existed!!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void signIn() {
        Intent intent = new Intent(this, LoginScreen.class);
        startActivity(intent);
        finish();
    }
}
