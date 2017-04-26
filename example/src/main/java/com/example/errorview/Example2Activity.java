package com.example.errorview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.hendraanggrian.widget.ErrorView;

import butterknife.BindView;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public class Example2Activity extends BaseActivity {

    @BindView(R.id.toolbar_example) Toolbar toolbar;
    @BindView(R.id.framelayout_example) FrameLayout frameLayout;

    @Override
    public int getContentView() {
        return R.layout.activity_example;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        ErrorView.make(frameLayout, "You have no new emails", ErrorView.LENGTH_INDEFINITE)
                .setBackdrop(R.drawable.bg_empty)
                .setContentMarginBottom((int) getResources().getDimension(R.dimen.example2_content_margin))
                .show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
