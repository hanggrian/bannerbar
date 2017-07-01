package com.hendraanggrian.widget;

import android.animation.LayoutTransition;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
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
import com.hendraanggrian.support.utils.view.ViewGroups;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.hendraanggrian.support.utils.view.Views.setVisible;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public class ErrorView extends FrameLayout {

    private static final CharSequence TAG = "com.hendraanggrian.widget.ErrorView";
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

    @NonNull final LayoutParams lpContainer;
    @NonNull final ImageView imageViewBackdrop;
    @NonNull final ImageView imageViewLogo;
    @NonNull final TextView textViewText;
    @NonNull final Button buttonAction;

    @Nullable private ViewGroup targetParent;
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
        this(context, attrs, defStyleAttr, 0);
    }

    public ErrorView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.errorview_layout, this, true);
        lpContainer = (LayoutParams) findViewById(R.id.errorview_container).getLayoutParams();
        imageViewBackdrop = (ImageView) findViewById(R.id.errorview_backdrop);
        imageViewLogo = (ImageView) findViewById(R.id.errorview_logo);
        textViewText = (TextView) findViewById(R.id.errorview_text);
        buttonAction = (Button) findViewById(R.id.errorview_action);
        int backgroundColor = Themes.getColor(context, android.R.attr.windowBackground, -1);
        if (backgroundColor == -1) {
            setBackgroundColor(backgroundColor);
        }
        setClickable(true);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ErrorView, defStyleAttr, defStyleRes);
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
        if (marginLeft != 0 || marginTop != 0 || marginRight != 0 || marginBottom != 0) {
            setContentMargin(marginLeft, marginTop, marginRight, marginBottom);
        }
        a.recycle();
    }

    @NonNull
    public ErrorView setBackdropBitmap(@Nullable Bitmap backdrop) {
        if (setVisible(this.imageViewBackdrop, backdrop != null)) {
            this.imageViewBackdrop.setImageBitmap(backdrop);
        }
        return this;
    }

    @NonNull
    public ErrorView setBackdropUri(@Nullable Uri backdrop) {
        if (setVisible(this.imageViewBackdrop, backdrop != null)) {
            this.imageViewBackdrop.setImageURI(backdrop);
        }
        return this;
    }

    @NonNull
    public ErrorView setBackdropDrawable(@Nullable Drawable backdrop) {
        if (setVisible(this.imageViewBackdrop, backdrop != null)) {
            this.imageViewBackdrop.setImageDrawable(backdrop);
        }
        return this;
    }

    @NonNull
    public ErrorView setBackdropDrawable(@DrawableRes int backdrop) {
        if (setVisible(this.imageViewBackdrop, backdrop > 0)) {
            this.imageViewBackdrop.setImageResource(backdrop);
        }
        return this;
    }

    @NonNull
    public ErrorView setBackdropColor(@ColorInt int color) {
        return setBackdropDrawable(new ColorDrawable(color));
    }

    @NonNull
    public ErrorView setBackdropColorRes(@ColorRes int colorRes) {
        return setBackdropColor(ContextCompat.getColor(getContext(), colorRes));
    }

    @NonNull
    public ErrorView setBackdropColorAttr(@AttrRes int colorAttr) {
        int color = Themes.getColor(getContext(), colorAttr, -1);
        if (color == -1) {
            throw new IllegalArgumentException("color attr not found in current theme.");
        }
        return setBackdropColor(color);
    }

    @NonNull
    public ErrorView setLogoBitmap(@Nullable Bitmap logo) {
        if (setVisible(this.imageViewLogo, logo != null)) {
            this.imageViewLogo.setImageBitmap(logo);
        }
        return this;
    }

    @NonNull
    public ErrorView setLogoUri(@Nullable Uri logo) {
        if (setVisible(this.imageViewLogo, logo != null)) {
            this.imageViewLogo.setImageURI(logo);
        }
        return this;
    }

    @NonNull
    public ErrorView setLogoDrawable(@Nullable Drawable logo) {
        if (setVisible(this.imageViewLogo, logo != null)) {
            this.imageViewLogo.setImageDrawable(logo);
        }
        return this;
    }

    @NonNull
    public ErrorView setLogoDrawable(@DrawableRes int logo) {
        if (setVisible(this.imageViewLogo, logo > 0)) {
            this.imageViewLogo.setImageResource(logo);
        }
        return this;
    }

    @NonNull
    public ErrorView setText(@Nullable CharSequence text) {
        if (setVisible(this.textViewText, !TextUtils.isEmpty(text))) {
            this.textViewText.setText(text);
        }
        return this;
    }

    @NonNull
    public ErrorView setText(@StringRes int text) {
        return setText(getResources().getText(text));
    }

    @NonNull
    @SuppressWarnings("ConstantConditions")
    public <E extends Exception> ErrorView setText(@Nullable E e) {
        if (setVisible(textViewText, e != null)) {
            setText(e.getMessage());
        }
        return this;
    }

    @NonNull
    public ErrorView setTextHttpCode(int httpCode) {
        return setTextHttpCode(HttpErrorCode.valueOf(httpCode));
    }

    @NonNull
    @SuppressWarnings("ConstantConditions")
    public ErrorView setTextHttpCode(@Nullable HttpErrorCode code) {
        if (setVisible(textViewText, code != null)) {
            textViewText.setText(code.toString());
        }
        return this;
    }

    @NonNull
    @SuppressWarnings("deprecation")
    public ErrorView setTextAppearance(@StyleRes int res) {
        if (Build.VERSION.SDK_INT >= 23) {
            textViewText.setTextAppearance(res);
        } else {
            textViewText.setTextAppearance(getContext(), res);
        }
        return this;
    }

    @NonNull
    public ErrorView setTextColor(@ColorInt int color) {
        textViewText.setTextColor(color);
        return this;
    }

    @NonNull
    public ErrorView setTextColorRes(@ColorRes int colorRes) {
        return setTextColor(ContextCompat.getColor(getContext(), colorRes));
    }

    @NonNull
    public ErrorView setTextColorAttr(@AttrRes int colorAttr) {
        int color = Themes.getColor(getContext(), colorAttr, 0);
        if (color == 0) {
            throw new IllegalArgumentException("color attr not found in current theme.");
        }
        return setTextColor(color);
    }

    @NonNull
    public ErrorView setAction(@Nullable CharSequence text, @Nullable final OnClickListener listener) {
        if (setVisible(buttonAction, !TextUtils.isEmpty(text))) {
            buttonAction.setText(text);
            buttonAction.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onClick(ErrorView.this);
                    }
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
        if (Build.VERSION.SDK_INT >= 23) {
            buttonAction.setTextAppearance(res);
        } else {
            buttonAction.setTextAppearance(getContext(), res);
        }
        return this;
    }

    @NonNull
    public ErrorView setActionColor(@ColorInt int color) {
        buttonAction.setTextColor(color);
        return this;
    }

    @NonNull
    public ErrorView setActionColorRes(@ColorRes int colorRes) {
        return setActionColor(ContextCompat.getColor(getContext(), colorRes));
    }

    @NonNull
    public ErrorView setActionColorAttr(@AttrRes int colorAttr) {
        int color = Themes.getColor(getContext(), colorAttr, 0);
        if (color == 0) {
            throw new RuntimeException("color attribute not found!");
        }
        return setActionColor(color);
    }

    @NonNull
    public ErrorView setContentMargin(int left, int top, int right, int bottom) {
        lpContainer.setMargins(left, top, right, bottom);
        return this;
    }

    @NonNull
    public ErrorView setContentMarginLeft(int left) {
        lpContainer.leftMargin = left;
        return this;
    }

    @NonNull
    public ErrorView setContentMarginTop(int top) {
        lpContainer.topMargin = top;
        return this;
    }

    @NonNull
    public ErrorView setContentMarginRight(int right) {
        lpContainer.rightMargin = right;
        return this;
    }

    @NonNull
    public ErrorView setContentMarginBottom(int bottom) {
        lpContainer.bottomMargin = bottom;
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
        if (targetParent == null) {
            throw new IllegalStateException("ErrorView is not created using make()!");
        }
        dismissAll(targetParent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            float elevation = getHighestElevation(targetParent);
            if (elevation > 0) {
                setElevation(elevation);
            }
        }
        targetParent.addView(this);
        if (showListener != null) {
            showListener.onShown(this);
        }
        if (delay > 0) {
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (getContext() instanceof Activity) {
                        if (((Activity) getContext()).isFinishing()) {
                            return;
                        } else if (Build.VERSION.SDK_INT >= 17 && ((Activity) getContext()).isDestroyed()) {
                            return;
                        }
                    }
                    if (ViewGroups.containsView(targetParent, ErrorView.this)) {
                        dismiss(DISMISS_EVENT_TIMEOUT);
                    }
                }
            }, delay);
        }
        return this;
    }

    public void dismiss() {
        dismiss(DISMISS_EVENT_MANUAL);
    }

    private void dismiss(int dismissEvent) {
        if (targetParent == null) {
            throw new IllegalStateException("ErrorView is not created using make()!");
        }
        targetParent.removeView(this);
        if (dismissListener != null) {
            dismissListener.onDismissed(this, dismissEvent);
        }
    }

    public static void dismissAll(@NonNull ViewGroup parent) {
        View child = parent.findViewWithTag(TAG);
        while (child != null && child instanceof ErrorView) {
            ((ErrorView) child).dismiss();
            child = parent.findViewWithTag(TAG);
        }
    }

    @TargetApi(21)
    @RequiresApi(21)
    private static float getHighestElevation(@NonNull ViewGroup parent) {
        float elevation = 0.0f;
        for (int i = 0; i < parent.getChildCount(); i++) {
            float childElevation = parent.getChildAt(i).getElevation();
            if (elevation < childElevation) {
                elevation = childElevation;
            }
        }
        return elevation;
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
        errorView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
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
        errorView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return errorView;
    }

    @NonNull
    @SuppressLint("SwitchIntDef")
    private static ErrorView make(@NonNull ViewGroup parent, @NonNull CharSequence text, @Duration int duration) {
        if (Build.VERSION.SDK_INT >= 11 && parent.getLayoutTransition() == null) {
            parent.setLayoutTransition(new LayoutTransition());
        }
        ErrorView errorView = new ErrorView(parent.getContext());
        errorView.targetParent = parent;
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