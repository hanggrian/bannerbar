package com.hendraanggrian.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hendraanggrian.errorview.R;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public class ErrorView extends LinearLayout {

    @NonNull private final ImageView imageView;
    @NonNull private final TextView textViewTitle;
    @NonNull private final TextView textViewSubtitle;
    @NonNull private final Button button;

    private int emptyLogo;
    @Nullable private String emptyTitle;
    @Nullable private String emptySubtitle;
    @Nullable private String emptyButtonText;

    public ErrorView(Context context) {
        this(context, null);
    }

    public ErrorView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ErrorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        (((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE))).inflate(R.layout.errorview, this, true);
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
        imageView = (ImageView) findViewById(R.id.imageview_errorview);
        textViewTitle = (TextView) findViewById(R.id.textview_errorview_title);
        textViewSubtitle = (TextView) findViewById(R.id.textview_errorview_subtitle);
        button = (Button) findViewById(R.id.button_errorview);

        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ErrorView, 0, 0);
        try {
            emptyLogo = array.getResourceId(R.styleable.ErrorView_emptyLogo, -1);
            emptyTitle = array.getString(R.styleable.ErrorView_emptyTitle);
            emptySubtitle = array.getString(R.styleable.ErrorView_emptySubtitle);
            emptyButtonText = array.getString(R.styleable.ErrorView_emptyButtonText);
        } finally {
            array.recycle();
        }

        setImage(imageView, emptyLogo);
        setText(textViewTitle, emptyTitle);
        setText(textViewSubtitle, emptySubtitle);
        setText(button, emptyButtonText);
    }

    public void showEmpty(boolean show) {
        setVisibility(show ? VISIBLE : GONE);
        if (show) {
            setImage(imageView, emptyLogo);
            setText(textViewTitle, emptyTitle);
            setText(textViewSubtitle, emptySubtitle);
            setText(button, emptyButtonText);
        }
    }

    public void registerRecyclerView(@NonNull final RecyclerView.Adapter<?> adapter) {
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                onItemRangeInserted(0, 0);
                onItemRangeRemoved(0, 0);
            }

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                if (adapter.getItemCount() > 0)
                    showEmpty(false);
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                if (adapter.getItemCount() == 0)
                    showEmpty(true);
            }
        });
    }

    private static void setImage(@NonNull ImageView imageView, int logo) {
        if (logo != -1) {
            imageView.setImageResource(logo);
            if (imageView.getVisibility() != VISIBLE)
                imageView.setVisibility(VISIBLE);
        } else if (imageView.getVisibility() != GONE) {
            imageView.setVisibility(GONE);
        }
    }

    private static void setText(@NonNull TextView textView, @Nullable String text) {
        if (!TextUtils.isEmpty(text)) {
            textView.setText(text);
            if (textView.getVisibility() != VISIBLE)
                textView.setVisibility(VISIBLE);
        } else if (textView.getVisibility() != GONE) {
            textView.setVisibility(GONE);
        }
    }

    public enum Mode {
        ERROR, EMPTY
    }
}