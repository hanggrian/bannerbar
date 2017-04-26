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
public class Example3Activity extends BaseActivity {

    @BindView(R.id.toolbar_example3) Toolbar toolbar;
    @BindView(R.id.errorview_example3) ErrorView errorView;

    @Override
    public int getContentView() {
        return R.layout.activity_example3;
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
