package com.example.myjob;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Splash extends AppCompatActivity {

    private static final int SPLASH_DISPLAY_TIME = 2000; // Thời gian hiển thị màn hình splash (2 giây)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Sử dụng Handler để chuyển đến activity chính sau khi hiển thị màn hình splash trong khoảng thời gian đã định
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(Splash.this,HomeJob.class);
                startActivity(mainIntent);
                finish(); // Đóng màn hình splash sau khi chuyển đến activity chính
            }
        }, SPLASH_DISPLAY_TIME);
    }
}