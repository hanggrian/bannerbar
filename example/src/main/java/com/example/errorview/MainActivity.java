package com.example.errorview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindViews;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @BindViews({
            R.id.button_main_example1,
            R.id.button_main_example2
    }) Button[] buttons;

    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for (Button button : buttons)
            button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_main_example1:
                startActivity(new Intent(this, Example1Activity.class));
                break;
            case R.id.button_main_example2:
                startActivity(new Intent(this, Example2Activity.class));
                break;
        }
    }
}