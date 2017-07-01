package com.hendraanggrian.errorview.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public class InstrumentedActivity extends AppCompatActivity {

    public ProgressBar progressBar;
    public FrameLayout frameLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instrumented);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
    }
}