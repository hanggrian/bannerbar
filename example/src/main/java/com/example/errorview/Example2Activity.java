package com.example.errorview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.hendraanggrian.widget.ErrorView;

import butterknife.BindView;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public class Example2Activity extends BaseActivity {

    @BindView(R.id.toolbar_example2) Toolbar toolbar;
    @BindView(R.id.errorview_example2) ErrorView errorView;

    @Override
    public int getContentView() {
        return R.layout.activity_example2;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        errorView.setAction("Retry", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Example2Activity.this, "Clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
