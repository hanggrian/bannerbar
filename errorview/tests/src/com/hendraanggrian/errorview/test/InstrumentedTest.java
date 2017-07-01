package com.hendraanggrian.errorview.test;

import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.hendraanggrian.support.utils.widget.Toasts;
import com.hendraanggrian.widget.ErrorView;

import org.hamcrest.Matcher;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.registerIdlingResources;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InstrumentedTest {

    @Rule
    public ActivityTestRule<InstrumentedActivity> rule = new ActivityTestRule<>(InstrumentedActivity.class);

    public void test1_xml() {
    }

    @Test
    public void test2_simple() {
        onView(withId(R.id.toolbar))
                .perform(setTitle("Here's an errorview"));
        onView(withId(R.id.frameLayout))
                .perform(new ViewAction() {
                    @Override
                    public Matcher<View> getConstraints() {
                        return isAssignableFrom(FrameLayout.class);
                    }

                    @Override
                    public String getDescription() {
                        return FrameLayout.class.getName();
                    }

                    @Override
                    public void perform(UiController uiController, View view) {
                        ErrorView.make(((FrameLayout) view), "No internet connection.", ErrorView.LENGTH_LONG)
                                .setBackdropDrawable(R.drawable.errorview_bg_cloud)
                                .setLogoDrawable(R.drawable.errorview_ic_cloud)
                                .setAction("Retry", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Toasts.showShort(v.getContext(), "Clicked!");
                                    }
                                })
                                .show();
                    }
                }, delay(5000));
    }

    @Test
    public void test3_custom() {
        onView(withId(R.id.toolbar))
                .perform(setTitle("Here's another"));
        onView(withId(R.id.frameLayout))
                .perform(new ViewAction() {
                    @Override
                    public Matcher<View> getConstraints() {
                        return isAssignableFrom(FrameLayout.class);
                    }

                    @Override
                    public String getDescription() {
                        return FrameLayout.class.getName();
                    }

                    @Override
                    public void perform(UiController uiController, final View view) {
                        ErrorView.make(((FrameLayout) view), "No internet connection.", ErrorView.LENGTH_INDEFINITE)
                                .setBackdropDrawable(R.drawable.bg_empty)
                                .setLogoBitmap(null)
                                .setAction(android.R.string.ok, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        view.setVisibility(View.GONE);
                                    }
                                })
                                .show();
                        registerIdlingResources(new ViewVisibilityIdlingResource(view, View.GONE));
                    }
                });
        onView(withId(R.id.frameLayout))
                .perform(new ViewAction() {
                    @Override
                    public Matcher<View> getConstraints() {
                        return isAssignableFrom(FrameLayout.class);
                    }

                    @Override
                    public String getDescription() {
                        return FrameLayout.class.getName();
                    }

                    @Override
                    public void perform(UiController uiController, View view) {
                    }
                });
    }

    @NonNull
    private ViewAction setTitle(@NonNull final String title) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(Toolbar.class);
            }

            @Override
            public String getDescription() {
                return "title to " + title;
            }

            @Override
            public void perform(UiController uiController, View view) {
                ((Toolbar) view).setTitle(title);
            }
        };
    }

    @NonNull
    private ViewAction delay(final long millis) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isDisplayed();
            }

            @Override
            public String getDescription() {
                return "delay for " + millis;
            }

            @Override
            public void perform(UiController uiController, View view) {
                final ProgressBar progressBar = rule.getActivity().progressBar;
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(100);
                new CountDownTimer(millis, 100) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            progressBar.setProgress((int) (progressBar.getMax() * millisUntilFinished / millis), true);
                        } else {
                            progressBar.setProgress((int) (progressBar.getMax() * millisUntilFinished / millis));
                        }
                    }

                    @Override
                    public void onFinish() {
                        progressBar.setVisibility(View.GONE);
                    }
                }.start();
                uiController.loopMainThreadForAtLeast(millis);
            }
        };
    }
}