package br.com.zup.mvvmarchitecture.util;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class ViewUtils {

    public static void expandView(final View v) {
        expandView(v, null);
    }

    public static void expandView(final View v, final Animation.AnimationListener animationListener) {
        v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();

        v.getLayoutParams().height = 0;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? ViewGroup.LayoutParams.WRAP_CONTENT
                        : (int) (targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms + 100
        a.setDuration((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density) + 100);
        if (animationListener != null) {
            a.setAnimationListener(animationListener);
        }
        v.startAnimation(a);
    }

    public static void collapseView(final View v) {
        collapseView(v, null);
    }

    public static void collapseView(final View v, final Animation.AnimationListener animationListener) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms + 100
        a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density) + 100);
        if (animationListener != null) {
            a.setAnimationListener(animationListener);
        }
        v.startAnimation(a);
    }
}
