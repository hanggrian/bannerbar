package com.hendraanggrian.widget;

import android.animation.LayoutTransition;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.hendraanggrian.errorview.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.hendraanggrian.errorview.VisibilityUtils.setVisible;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public final class ErrorView extends FrameLayout {

    private static final CharSequence TAG = ErrorView.class.getCanonicalName();
    private static final int DELAY_LONG = 3500;
    private static final int DELAY_SHORT = 2000;

    public static final int LENGTH_INDEFINITE = -1;
    public static final int LENGTH_LONG = -2;
    public static final int LENGTH_SHORT = -3;

    private final ViewGroup viewGroup;
    private final ImageView imageViewBackdrop;
    private final ImageView imageViewLogo;
    private final TextView textView;
    private final Button button;
    @Nullable private FrameLayout parent;
    @Nullable private Integer delay;
    @Nullable private OnShowListener showListener;
    @Nullable private OnDismissListener dismissListener;

    public ErrorView(@NonNull Context context) {
        this(context, null);
    }

    public ErrorView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, R.attr.errorViewStyle);
    }

    public ErrorView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ErrorView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs);
        ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.errorview, this, true);
        viewGroup = (ViewGroup) findViewById(R.id.viewgroup_errorview);
        imageViewBackdrop = (ImageView) findViewById(R.id.imageview_errorview_backdrop);
        imageViewLogo = (ImageView) findViewById(R.id.imageview_errorview_logo);
        textView = (TextView) findViewById(R.id.textview_errorview);
        button = (Button) findViewById(R.id.button_errorview);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ErrorView, defStyleAttr, defStyleRes);
        TypedValue v = new TypedValue();
        context.getTheme().resolveAttribute(android.R.attr.windowBackground, v, true);
        try {
            // content
            setBackgroundColor(v.data);
            setBackdrop(a.getResourceId(R.styleable.ErrorView_errorBackdrop, 0));
            setLogo(a.getResourceId(R.styleable.ErrorView_errorLogo, R.drawable.ic_errorview_cloud));
            setText(a.getText(R.styleable.ErrorView_errorText));
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
                setTextAppearance(context, a.getResourceId(R.styleable.ErrorView_errorTextAppearance, R.style.TextAppearance_AppCompat_Medium));
            else
                setTextAppearance(a.getResourceId(R.styleable.ErrorView_errorTextAppearance, R.style.TextAppearance_AppCompat_Medium));
            // positioning
            float marginLeft = a.getDimension(R.styleable.ErrorView_contentMarginLeft, 0);
            float marginTop = a.getDimension(R.styleable.ErrorView_contentMarginTop, 0);
            float marginRight = a.getDimension(R.styleable.ErrorView_contentMarginRight, 0);
            float marginBottom = a.getDimension(R.styleable.ErrorView_contentMarginBottom, 0);
            if (marginLeft != 0 || marginTop != 0 || marginRight != 0 || marginBottom != 0)
                setContentMargin((int) marginLeft, (int) marginTop, (int) marginRight, (int) marginBottom);
        } finally {
            a.recycle();
        }
    }

    @NonNull
    public ErrorView setBackdrop(@Nullable Bitmap backdrop) {
        if (setVisible(imageViewBackdrop, backdrop != null))
            imageViewBackdrop.setImageBitmap(backdrop);
        return this;
    }

    @NonNull
    public ErrorView setBackdrop(@Nullable Uri backdrop) {
        if (setVisible(imageViewBackdrop, backdrop != null))
            imageViewBackdrop.setImageURI(backdrop);
        return this;
    }

    @NonNull
    public ErrorView setBackdrop(@Nullable Drawable backdrop) {
        if (setVisible(imageViewBackdrop, backdrop != null))
            imageViewBackdrop.setImageDrawable(backdrop);
        return this;
    }

    @NonNull
    public ErrorView setBackdrop(@DrawableRes int backdrop) {
        if (setVisible(imageViewBackdrop, backdrop != 0))
            imageViewBackdrop.setImageResource(backdrop);
        return this;
    }

    @NonNull
    public ErrorView setBackdropColor(@ColorInt int color) {
        if (setVisible(imageViewBackdrop, color != 0))
            imageViewBackdrop.setColorFilter(color);
        return this;
    }

    @NonNull
    public ErrorView setBackdropColorRes(@ColorRes int colorRes) {
        return setBackdropColor(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                ? getContext().getColor(colorRes)
                : ContextCompat.getColor(getContext(), colorRes));
    }

    @NonNull
    public ErrorView setLogo(@Nullable Bitmap logo) {
        if (setVisible(imageViewLogo, logo != null))
            imageViewLogo.setImageBitmap(logo);
        return this;
    }

    @NonNull
    public ErrorView setLogo(@Nullable Uri logo) {
        if (setVisible(imageViewLogo, logo != null))
            imageViewLogo.setImageURI(logo);
        return this;
    }

    @NonNull
    public ErrorView setLogo(@Nullable Drawable logo) {
        if (setVisible(imageViewLogo, logo != null))
            imageViewLogo.setImageDrawable(logo);
        return this;
    }

    @NonNull
    public ErrorView setLogo(@DrawableRes int logo) {
        if (setVisible(imageViewLogo, logo != 0))
            imageViewLogo.setImageResource(logo);
        return this;
    }

    @NonNull
    public ErrorView setText(@Nullable CharSequence text) {
        if (setVisible(textView, !TextUtils.isEmpty(text)))
            textView.setText(text);
        return this;
    }

    @NonNull
    public ErrorView setText(@StringRes int text) {
        return setText(getResources().getText(text));
    }

    @NonNull
    @RequiresApi(api = Build.VERSION_CODES.M)
    public ErrorView setTextAppearance(@StyleRes int res) {
        textView.setTextAppearance(res);
        return this;
    }

    @NonNull
    @SuppressWarnings("deprecation")
    public ErrorView setTextAppearance(@NonNull Context context, @StyleRes int res) {
        textView.setTextAppearance(context, res);
        return this;
    }

    @NonNull
    public ErrorView setAction(@Nullable CharSequence text, @Nullable final OnClickListener listener) {
        if (setVisible(button, !TextUtils.isEmpty(text))) {
            button.setText(text);
            button.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.onClick(ErrorView.this);
                    dismiss();
                }
            });
        }
        return this;
    }

    @NonNull
    public ErrorView setAction(@StringRes int text, @Nullable OnClickListener listener) {
        return setAction(getResources().getText(text), listener);
    }

    @NonNull
    public ErrorView setContentMargin(int left, int top, int right, int bottom) {
        ((LayoutParams) viewGroup.getLayoutParams()).setMargins(left, top, right, bottom);
        return this;
    }

    @NonNull
    public ErrorView setContentMarginLeft(int left) {
        ((LayoutParams) viewGroup.getLayoutParams()).leftMargin = left;
        return this;
    }

    @NonNull
    public ErrorView setContentMarginTop(int top) {
        ((LayoutParams) viewGroup.getLayoutParams()).topMargin = top;
        return this;
    }

    @NonNull
    public ErrorView setContentMarginRight(int right) {
        ((LayoutParams) viewGroup.getLayoutParams()).rightMargin = right;
        return this;
    }

    @NonNull
    public ErrorView setContentMarginBottom(int bottom) {
        ((LayoutParams) viewGroup.getLayoutParams()).bottomMargin = bottom;
        return this;
    }

    @NonNull
    public ErrorView setOnShowListener(@NonNull OnShowListener listener) {
        showListener = listener;
        return this;
    }

    @NonNull
    public ErrorView setOnDismissListener(@NonNull OnDismissListener listener) {
        dismissListener = listener;
        return this;
    }

    @NonNull
    public ErrorView show() {
        if (parent != null) {
            dismiss(parent);
            parent.addView(this);
            if (showListener != null)
                showListener.onShown(this);
            if (delay != null) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (parent != null && parent.findViewWithTag(TAG) != null)
                            dismiss();
                    }
                }, delay);
            }
        }
        return this;
    }

    public void dismiss() {
        if (parent != null) {
            parent.removeView(this);
            if (dismissListener != null)
                dismissListener.onDismissed(this);
        }
    }

    public static void dismiss(@NonNull ViewGroup parent) {
        while (parent.findViewWithTag(TAG) != null)
            ((ErrorView) parent.findViewWithTag(TAG)).dismiss();
    }

    @NonNull
    public static ErrorView make(@NonNull FrameLayout parent, @StringRes int text, @Duration int duration) {
        return make(parent, parent.getResources().getString(text), duration);
    }

    @NonNull
    public static ErrorView make(@NonNull final FrameLayout parent, @NonNull CharSequence text, @Duration int duration) {
        if (parent.getLayoutTransition() == null)
            parent.setLayoutTransition(new LayoutTransition());
        ErrorView errorView = new ErrorView(parent.getContext());
        errorView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        errorView.parent = parent;
        errorView.setText(text);
        switch (duration) {
            case LENGTH_INDEFINITE:
                errorView.delay = null;
                break;
            case LENGTH_SHORT:
                errorView.delay = DELAY_SHORT;
                break;
            case LENGTH_LONG:
                errorView.delay = DELAY_LONG;
                break;
        }
        errorView.setTag(TAG);
        return errorView;
    }

    public interface OnShowListener {
        void onShown(@NonNull ErrorView view);
    }

    public interface OnDismissListener {
        void onDismissed(@NonNull ErrorView view);
    }

    @IntDef({LENGTH_INDEFINITE, LENGTH_SHORT, LENGTH_LONG})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Duration {
    }
}