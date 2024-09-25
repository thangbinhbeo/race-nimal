package com.example.prm392_miniproject_racenimal;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginScreen extends AppCompatActivity {
    private EditText edPassword;
    private ImageView eyeIcon;
    private TextView txtTitle;
    private final boolean[] isPasswordVisible = {false};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_game);

        edPassword = (EditText) findViewById(R.id.editTextPassword);
        txtTitle = (TextView) findViewById(R.id.textViewLogo);
        eyeIcon = (ImageView) findViewById(R.id.iconEye);

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
}
