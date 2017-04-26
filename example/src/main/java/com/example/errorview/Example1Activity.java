package com.example.errorview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.hendraanggrian.widget.ErrorView;

import butterknife.BindView;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public class Example1Activity extends BaseActivity {

    @BindView(R.id.toolbar_example1) Toolbar toolbar;
    @BindView(R.id.errorview_example1) ErrorView errorView;

    @Override
    public int getContentView() {
        return R.layout.activity_example1;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
