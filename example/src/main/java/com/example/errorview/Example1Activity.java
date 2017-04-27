package com.example.errorview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.hendraanggrian.widget.ErrorView;

import butterknife.BindView;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public class Example1Activity extends BaseActivity {

    @BindView(R.id.toolbar_example) Toolbar toolbar;
    @BindView(R.id.framelayout_example) FrameLayout frameLayout;
    private Menu menu;

    @Override
    public int getContentView() {
        return R.layout.activity_example;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.example1, menu);
        this.menu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.item_example1_make:
                ErrorView.make(frameLayout, "No internet connection", getLength())
                        .setAction("Retry", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(Example1Activity.this, "Dismissed.", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
                break;
            default:
                item.setChecked(true);
                break;
        }
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    @ErrorView.Duration
    private int getLength() {
        if (menu.findItem(R.id.item_example1_length_short).isChecked())
            return ErrorView.LENGTH_SHORT;
        else if (menu.findItem(R.id.item_example1_length_long).isChecked())
            return ErrorView.LENGTH_LONG;
        else
            return ErrorView.LENGTH_INDEFINITE;
    }
}