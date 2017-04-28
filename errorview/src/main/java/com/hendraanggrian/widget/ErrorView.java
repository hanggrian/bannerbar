package com.hendraanggrian.widget;

import android.animation.LayoutTransition;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
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
import android.widget.RelativeLayout;
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

    @IntDef({LENGTH_INDEFINITE, LENGTH_SHORT, LENGTH_LONG})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Duration {
    }

    private final LayoutParams containerLayoutParams;
    private final ImageView imageViewBackdrop;
    private final ImageView imageViewLogo;
    private final TextView textView;
    private final Button button;

    @Nullable private ViewGroup parent;
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
        containerLayoutParams = (LayoutParams) findViewById(R.id.viewgroup_errorview).getLayoutParams();
        imageViewBackdrop = (ImageView) findViewById(R.id.imageview_errorview_backdrop);
        imageViewLogo = (ImageView) findViewById(R.id.imageview_errorview_logo);
        textView = (TextView) findViewById(R.id.textview_errorview);
        button = (Button) findViewById(R.id.button_errorview);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ErrorView, defStyleAttr, defStyleRes);
        try {
            // content
            setBackdropDrawable(a.getResourceId(R.styleable.ErrorView_errorBackdrop, 0));
            setLogoDrawable(a.getResourceId(R.styleable.ErrorView_errorLogo, R.drawable.ic_errorview_cloud));
            setText(a.getText(R.styleable.ErrorView_errorText));
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
                setTextAppearance(context, a.getResourceId(R.styleable.ErrorView_errorTextAppearance, R.style.TextAppearance_AppCompat_Medium));
            else
                setTextAppearance(a.getResourceId(R.styleable.ErrorView_errorTextAppearance, R.style.TextAppearance_AppCompat_Medium));
            // positioning
            int marginLeft = (int) a.getDimension(R.styleable.ErrorView_contentMarginLeft, 0);
            int marginTop = (int) a.getDimension(R.styleable.ErrorView_contentMarginTop, 0);
            int marginRight = (int) a.getDimension(R.styleable.ErrorView_contentMarginRight, 0);
            int marginBottom = (int) a.getDimension(R.styleable.ErrorView_contentMarginBottom, 0);
            if (marginLeft != 0 || marginTop != 0 || marginRight != 0 || marginBottom != 0)
                setContentMargin(marginLeft, marginTop, marginRight, marginBottom);
        } finally {
            a.recycle();
        }
    }

    @NonNull
    public ErrorView setBackdropBitmap(@Nullable Bitmap backdrop) {
        if (setVisible(imageViewBackdrop, backdrop != null))
            imageViewBackdrop.setImageBitmap(backdrop);
        return this;
    }

    @NonNull
    public ErrorView setBackdropUri(@Nullable Uri backdrop) {
        if (setVisible(imageViewBackdrop, backdrop != null))
            imageViewBackdrop.setImageURI(backdrop);
        return this;
    }

    @NonNull
    public ErrorView setBackdropDrawable(@Nullable Drawable backdrop) {
        if (setVisible(imageViewBackdrop, backdrop != null))
            imageViewBackdrop.setImageDrawable(backdrop);
        return this;
    }

    @NonNull
    public ErrorView setBackdropDrawable(@DrawableRes int backdrop) {
        if (setVisible(imageViewBackdrop, backdrop != 0))
            imageViewBackdrop.setImageResource(backdrop);
        return this;
    }

    @NonNull
    public ErrorView setBackdropColor(@ColorInt int color) {
        return setBackdropDrawable(new ColorDrawable(color));
    }

    @NonNull
    public ErrorView setBackdropColorRes(@ColorRes int colorRes) {
        return setBackdropColor(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                ? getContext().getColor(colorRes)
                : ContextCompat.getColor(getContext(), colorRes));
    }

    @NonNull
    public ErrorView setBackdropColorAttr(@AttrRes int colorAttr) {
        TypedValue v = new TypedValue();
        getContext().getTheme().resolveAttribute(colorAttr, v, true);
        return setBackdropColor(v.data);
    }

    @NonNull
    public ErrorView setLogoBitmap(@Nullable Bitmap logo) {
        if (setVisible(imageViewLogo, logo != null))
            imageViewLogo.setImageBitmap(logo);
        return this;
    }

    @NonNull
    public ErrorView setLogoUri(@Nullable Uri logo) {
        if (setVisible(imageViewLogo, logo != null))
            imageViewLogo.setImageURI(logo);
        return this;
    }

    @NonNull
    public ErrorView setLogoDrawable(@Nullable Drawable logo) {
        if (setVisible(imageViewLogo, logo != null))
            imageViewLogo.setImageDrawable(logo);
        return this;
    }

    @NonNull
    public ErrorView setLogoDrawable(@DrawableRes int logo) {
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
    public ErrorView setTextColor(@ColorInt int color) {
        textView.setTextColor(color);
        return this;
    }

    @NonNull
    public ErrorView setTextColorRes(@ColorRes int colorRes) {
        return setTextColor(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                ? getContext().getColor(colorRes)
                : ContextCompat.getColor(getContext(), colorRes));
    }

    @NonNull
    public ErrorView setTextColorAttr(@AttrRes int colorAttr) {
        TypedValue v = new TypedValue();
        getContext().getTheme().resolveAttribute(colorAttr, v, true);
        return setTextColor(v.data);
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
    @RequiresApi(api = Build.VERSION_CODES.M)
    public ErrorView setActionAppearance(@StyleRes int res) {
        button.setTextAppearance(res);
        return this;
    }

    @NonNull
    @SuppressWarnings("deprecation")
    public ErrorView setActionAppearance(@NonNull Context context, @StyleRes int res) {
        button.setTextAppearance(context, res);
        return this;
    }

    @NonNull
    public ErrorView setActionColor(@ColorInt int color) {
        button.setTextColor(color);
        return this;
    }

    @NonNull
    public ErrorView setActionColorRes(@ColorRes int colorRes) {
        return setActionColor(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                ? getContext().getColor(colorRes)
                : ContextCompat.getColor(getContext(), colorRes));
    }

    @NonNull
    public ErrorView setActionColorAttr(@AttrRes int colorAttr) {
        TypedValue v = new TypedValue();
        getContext().getTheme().resolveAttribute(colorAttr, v, true);
        return setActionColor(v.data);
    }

    @NonNull
    public ErrorView setContentMargin(int left, int top, int right, int bottom) {
        containerLayoutParams.setMargins(left, top, right, bottom);
        return this;
    }

    @NonNull
    public ErrorView setContentMarginLeft(int left) {
        containerLayoutParams.leftMargin = left;
        return this;
    }

    @NonNull
    public ErrorView setContentMarginTop(int top) {
        containerLayoutParams.topMargin = top;
        return this;
    }

    @NonNull
    public ErrorView setContentMarginRight(int right) {
        containerLayoutParams.rightMargin = right;
        return this;
    }

    @NonNull
    public ErrorView setContentMarginBottom(int bottom) {
        containerLayoutParams.bottomMargin = bottom;
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
            dismissAll(parent);
            parent.addView(this);
            if (showListener != null)
                showListener.onShown(this);
            if (delay != null) {
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (parent != null)
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

    public static void dismissAll(@NonNull ViewGroup parent) {
        View child = parent.findViewWithTag(TAG);
        while (child != null && child instanceof ErrorView) {
            ((ErrorView) child).dismiss();
            child = parent.findViewWithTag(TAG);
        }
    }

    @NonNull
    public static ErrorView make(@NonNull RelativeLayout parent, @StringRes int text, @Duration int duration) {
        return make(parent, parent.getResources().getString(text), duration);
    }

    @NonNull
    public static ErrorView make(@NonNull RelativeLayout parent, @NonNull CharSequence text, @Duration int duration) {
        ErrorView errorView = make((ViewGroup) parent, text, duration);
        errorView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return errorView;
    }

    @NonNull
    public static ErrorView make(@NonNull FrameLayout parent, @StringRes int text, @Duration int duration) {
        return make(parent, parent.getResources().getString(text), duration);
    }

    @NonNull
    public static ErrorView make(@NonNull FrameLayout parent, @NonNull CharSequence text, @Duration int duration) {
        ErrorView errorView = make((ViewGroup) parent, text, duration);
        errorView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return errorView;
    }

    @NonNull
    private static ErrorView make(@NonNull ViewGroup parent, @NonNull CharSequence text, @Duration int duration) {
        if (parent.getLayoutTransition() == null)
            parent.setLayoutTransition(new LayoutTransition());
        ErrorView errorView = new ErrorView(parent.getContext());
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
}