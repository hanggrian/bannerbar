package com.hendraanggrian.material.bannerbar.test;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class TestActivity extends AppCompatActivity {
    public FrameLayout frameLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        setSupportActionBar(findViewById(R.id.toolbar));
        frameLayout = findViewById(R.id.frameLayout);
    }
}
