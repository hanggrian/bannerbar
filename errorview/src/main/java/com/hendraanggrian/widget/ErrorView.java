package com.hendraanggrian.widget;

import android.animation.LayoutTransition;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
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
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hendraanggrian.errorview.HttpErrorCode;
import com.hendraanggrian.errorview.R;
import com.hendraanggrian.support.utils.content.Themes;
import com.hendraanggrian.support.utils.graphics.Drawables;
import com.hendraanggrian.support.utils.view.ViewGroups;
import com.hendraanggrian.support.utils.view.Views;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.hendraanggrian.support.utils.view.Views.setVisible;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public class ErrorView extends FrameLayout {

    private static final CharSequence TAG = ErrorView.class.getCanonicalName();
    private static final int DELAY_LONG = 3500;
    private static final int DELAY_SHORT = 2000;

    @IntDef({DELAY_LONG, DELAY_SHORT})
    @Retention(RetentionPolicy.SOURCE)
    private @interface Delay {
    }

    public static final int LENGTH_INDEFINITE = -1;
    public static final int LENGTH_LONG = -2;
    public static final int LENGTH_SHORT = -3;

    @IntDef({LENGTH_INDEFINITE, LENGTH_LONG, LENGTH_SHORT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Duration {
    }

    public static final int DISMISS_EVENT_ACTION = 1;
    public static final int DISMISS_EVENT_TIMEOUT = 2;
    public static final int DISMISS_EVENT_MANUAL = 3;

    @IntDef({DISMISS_EVENT_ACTION, DISMISS_EVENT_TIMEOUT, DISMISS_EVENT_MANUAL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DismissEvent {
    }

    @NonNull private final LayoutParams containerLayoutParams;
    @NonNull private final ImageView imageViewBackdrop;
    @NonNull private final ImageView imageViewLogo;
    @NonNull private final TextView textView;
    @NonNull private final Button button;

    @Nullable private ViewGroup parent;
    @Delay private int delay;
    @Nullable private OnShowListener showListener;
    @Nullable private OnDismissListener dismissListener;

    public ErrorView(@NonNull Context context) {
        this(context, null);
    }

    public ErrorView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, R.attr.errorViewStyle);
    }

    public ErrorView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        this(context, attrs, defStyleAttr, R.style.ErrorViewStyle);
    }

    public ErrorView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs);
        // setup views
        LayoutInflater.from(context).inflate(R.layout.errorview, this, true);
        setBackgroundColor(Themes.getColor(context, android.R.attr.windowBackground, ContextCompat.getColor(context, android.R.color.transparent)));
        containerLayoutParams = (LayoutParams) findViewById(R.id.viewgroup_errorview).getLayoutParams();
        imageViewBackdrop = Views.findViewById(this, R.id.imageview_errorview_backdrop);
        imageViewLogo = Views.findViewById(this, R.id.imageview_errorview_logo);
        textView = Views.findViewById(this, R.id.textview_errorview);
        button = Views.findViewById(this, R.id.button_errorview);
        // apply styling
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ErrorView, defStyleAttr, defStyleRes);
        try {
            // content
            setBackdropDrawable(a.getDrawable(R.styleable.ErrorView_errorBackdrop));
            setLogoDrawable(a.getDrawable(R.styleable.ErrorView_errorLogo));
            setText(a.getText(R.styleable.ErrorView_errorText));
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
        if (setVisible(imageViewBackdrop, backdrop > 0))
            imageViewBackdrop.setImageResource(backdrop);
        return this;
    }

    @NonNull
    public ErrorView setBackdropColor(@ColorInt int color) {
        return setBackdropDrawable(Drawables.fromColor(color));
    }

    @NonNull
    public ErrorView setBackdropColorRes(@ColorRes int colorRes) {
        return setBackdropDrawable(Drawables.fromColorRes(getContext(), colorRes));
    }

    @NonNull
    public ErrorView setBackdropColorAttr(@AttrRes int colorAttr) {
        return setBackdropDrawable(Drawables.fromColorAttr(getContext(), colorAttr));
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
        if (setVisible(imageViewLogo, logo > 0))
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
    @SuppressWarnings("ConstantConditions")
    public <E extends Exception> ErrorView setText(@Nullable E e) {
        if (setVisible(textView, e != null))
            setText(e.getMessage());
        return this;
    }

    @NonNull
    public ErrorView setTextHttpCode(int httpCode) {
        return setTextHttpCode(HttpErrorCode.valueOf(httpCode));
    }

    @NonNull
    @SuppressWarnings("ConstantConditions")
    public ErrorView setTextHttpCode(@Nullable HttpErrorCode code) {
        if (setVisible(textView, code != null))
            textView.setText(code.toString());
        return this;
    }

    @NonNull
    @SuppressWarnings("deprecation")
    public ErrorView setTextAppearance(@StyleRes int res) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            textView.setTextAppearance(res);
        else
            textView.setTextAppearance(getContext(), res);
        return this;
    }

    @NonNull
    public ErrorView setTextColor(@ColorInt int color) {
        textView.setTextColor(color);
        return this;
    }

    @NonNull
    public ErrorView setTextColorRes(@ColorRes int colorRes) {
        return setTextColor(ContextCompat.getColor(getContext(), colorRes));
    }

    @NonNull
    public ErrorView setTextColorAttr(@AttrRes int colorAttr) {
        return setTextColor(Themes.getColor(getContext(), colorAttr, textView.getCurrentTextColor()));
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
                    dismiss(DISMISS_EVENT_ACTION);
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
    @SuppressWarnings("deprecation")
    public ErrorView setActionAppearance(@StyleRes int res) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            button.setTextAppearance(res);
        else
            button.setTextAppearance(getContext(), res);
        return this;
    }

    @NonNull
    public ErrorView setActionColor(@ColorInt int color) {
        button.setTextColor(color);
        return this;
    }

    @NonNull
    public ErrorView setActionColorRes(@ColorRes int colorRes) {
        return setActionColor(ContextCompat.getColor(getContext(), colorRes));
    }

    @NonNull
    public ErrorView setActionColorAttr(@AttrRes int colorAttr) {
        return setActionColor(Themes.getColor(getContext(), colorAttr, button.getCurrentTextColor()));
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
    public ErrorView setOnShowListener(@Nullable OnShowListener listener) {
        showListener = listener;
        return this;
    }

    @NonNull
    public ErrorView setOnDismissListener(@Nullable OnDismissListener listener) {
        dismissListener = listener;
        return this;
    }

    @NonNull
    public ErrorView show() {
        if (parent == null)
            throw new IllegalStateException("ErrorView is not created using make()!");
        dismissAll(parent);
        parent.addView(this);
        if (showListener != null)
            showListener.onShown(this);
        if (delay > 0)
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (getContext() instanceof Activity)
                        if (((Activity) getContext()).isFinishing())
                            return;
                        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && ((Activity) getContext()).isDestroyed())
                            return;
                    if (ViewGroups.containsView(parent, ErrorView.this))
                        dismiss(DISMISS_EVENT_TIMEOUT);
                }
            }, delay);
        return this;
    }

    public void dismiss() {
        dismiss(DISMISS_EVENT_MANUAL);
    }

    private void dismiss(int dismissEvent) {
        if (parent == null)
            throw new IllegalStateException("ErrorView is not created using make()!");
        parent.removeView(this);
        if (dismissListener != null)
            dismissListener.onDismissed(this, dismissEvent);
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
    public static ErrorView make(@NonNull RelativeLayout parent, @NonNull HttpErrorCode code, @Duration int duration) {
        return make(parent, code.toString(), duration);
    }

    @NonNull
    public static ErrorView make(@NonNull RelativeLayout parent, @NonNull CharSequence text, @Duration int duration) {
        ErrorView errorView = make((ViewGroup) parent, text, duration);
        errorView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroups.MATCH_PARENT, ViewGroups.MATCH_PARENT));
        return errorView;
    }

    @NonNull
    public static ErrorView make(@NonNull FrameLayout parent, @StringRes int text, @Duration int duration) {
        return make(parent, parent.getResources().getString(text), duration);
    }

    @NonNull
    public static ErrorView make(@NonNull FrameLayout parent, @NonNull HttpErrorCode code, @Duration int duration) {
        return make(parent, code.toString(), duration);
    }

    @NonNull
    public static ErrorView make(@NonNull FrameLayout parent, @NonNull CharSequence text, @Duration int duration) {
        ErrorView errorView = make((ViewGroup) parent, text, duration);
        errorView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroups.MATCH_PARENT, ViewGroups.MATCH_PARENT));
        return errorView;
    }

    @NonNull
    @SuppressLint("SwitchIntDef")
    private static ErrorView make(@NonNull ViewGroup parent, @NonNull CharSequence text, @Duration int duration) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB && parent.getLayoutTransition() == null)
            parent.setLayoutTransition(new LayoutTransition());
        ErrorView errorView = new ErrorView(parent.getContext());
        errorView.parent = parent;
        errorView.setText(text);
        switch (duration) {
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
        void onDismissed(@NonNull ErrorView view, @DismissEvent int event);
    }
}