package br.com.danielsan.dscontacts.misc.fab;

import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.animation.ObjectAnimator;
import android.view.animation.Interpolator;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AbsListView;
import android.support.annotation.NonNull;

import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.getbase.floatingactionbutton.FloatingActionButton;

import br.com.danielsan.dscontacts.R;

public class FloatingActionHidden {

    private static final int TRANSLATE_DURATION_MILLIS = 300;

    private int mScrollThreshold;
    private View mView;
    private boolean mVisible;

    private final Interpolator mInterpolator = new AccelerateDecelerateInterpolator();

    public static FloatingActionHidden builder(@NonNull FloatingActionButton floatingActionsMenu,
                                                     @NonNull AbsListView listView) {
        return builder(floatingActionsMenu, listView, null, null);
    }

    public static FloatingActionHidden builder(@NonNull FloatingActionButton floatingActionsMenu,
                                                     @NonNull AbsListView listView,
                                                     ScrollDirectionListener scrollDirectionListener) {
        return builder(floatingActionsMenu, listView, scrollDirectionListener, null);
    }

    public static FloatingActionHidden builder(@NonNull FloatingActionButton floatingActionsMenu,
                                                     @NonNull AbsListView listView,
                                                     ScrollDirectionListener scrollDirectionListener,
                                                     AbsListView.OnScrollListener onScrollListener) {
        return new FloatingActionHidden(floatingActionsMenu, listView, scrollDirectionListener, onScrollListener);
    }

    public static FloatingActionHidden builder(@NonNull FloatingActionsMenu floatingActionsMenu,
                                                     @NonNull AbsListView listView) {
        return builder(floatingActionsMenu, listView, null, null);
    }

    public static FloatingActionHidden builder(@NonNull FloatingActionsMenu floatingActionsMenu,
                                                     @NonNull AbsListView listView,
                                                     ScrollDirectionListener scrollDirectionListener) {
        return builder(floatingActionsMenu, listView, scrollDirectionListener, null);
    }

    public static FloatingActionHidden builder(@NonNull FloatingActionsMenu floatingActionsMenu,
                                                     @NonNull AbsListView listView,
                                                     ScrollDirectionListener scrollDirectionListener,
                                                     AbsListView.OnScrollListener onScrollListener) {
        return new FloatingActionHidden(floatingActionsMenu, listView, scrollDirectionListener, onScrollListener);
    }

    private FloatingActionHidden(@NonNull View view, @NonNull AbsListView listView,
                                 ScrollDirectionListener scrollDirectionListener,
                                 AbsListView.OnScrollListener onScrollListener) {
        mVisible = true;
        mView = view;
        mScrollThreshold = mView.getResources().getDimensionPixelOffset(R.dimen.fab_scroll_threshold);
        AbsListViewScrollDetectorImpl scrollDetector = new AbsListViewScrollDetectorImpl();
        scrollDetector.setScrollDirectionListener(scrollDirectionListener);
        scrollDetector.setOnScrollListener(onScrollListener);
        scrollDetector.setListView(listView);
        scrollDetector.setScrollThreshold(mScrollThreshold);
        listView.setOnScrollListener(scrollDetector);
    }

    public void show() {
        show(true);
    }

    public void hide() {
        hide(true);
    }

    public void show(boolean animate) {
        toggle(true, animate, false);
    }

    public void hide(boolean animate) {
        toggle(false, animate, false);
    }

    private void toggle(final boolean visible, final boolean animate, boolean force) {
        if (mVisible != visible || force) {
            mVisible = visible;
            int height = mView.getHeight();
            if (height == 0 && !force) {
                ViewTreeObserver vto = mView.getViewTreeObserver();
                if (vto.isAlive()) {
                    vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                        @Override
                        public boolean onPreDraw() {
                            ViewTreeObserver currentVto = mView.getViewTreeObserver();
                            if (currentVto.isAlive()) {
                                currentVto.removeOnPreDrawListener(this);
                            }
                            toggle(visible, animate, true);
                            return true;
                        }
                    });
                    return;
                }
            }

            if (mView instanceof FloatingActionsMenu) {
                ((FloatingActionsMenu) mView).collapse();
            }

            int translationY = visible ? 0 : height + getMarginBottom();
            if (animate) {
                ObjectAnimator translationYObjectAnimator = ObjectAnimator.ofFloat(mView, "translationY", translationY);
                translationYObjectAnimator.setInterpolator(mInterpolator);
                translationYObjectAnimator.setDuration(TRANSLATE_DURATION_MILLIS);
                translationYObjectAnimator.start();
            } else {
                ObjectAnimator translationYObjectAnimator = ObjectAnimator.ofFloat(mView, "translationY", translationY);
                translationYObjectAnimator.start();
            }
            // On pre-Honeycomb a translated view is still clickable, so we need to disable clicks manually
            if (!hasHoneycombApi()) {
                mView.setClickable(visible);
            }
        }
    }

    private int getMarginBottom() {
        int marginBottom = 0;
        final ViewGroup.LayoutParams layoutParams = mView.getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            marginBottom = ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
        }
        return marginBottom;
    }

    private boolean hasHoneycombApi() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    private class AbsListViewScrollDetectorImpl extends AbsListViewScrollDetector {
        private ScrollDirectionListener mScrollDirectionListener;
        private AbsListView.OnScrollListener mOnScrollListener;

        private void setScrollDirectionListener(ScrollDirectionListener scrollDirectionListener) {
            mScrollDirectionListener = scrollDirectionListener;
        }

        public void setOnScrollListener(AbsListView.OnScrollListener onScrollListener) {
            mOnScrollListener = onScrollListener;
        }

        @Override
        public void onScrollDown() {
            show();
            if (mScrollDirectionListener != null) {
                mScrollDirectionListener.onScrollDown();
            }
        }

        @Override
        public void onScrollUp() {
            hide();
            if (mScrollDirectionListener != null) {
                mScrollDirectionListener.onScrollUp();
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                             int totalItemCount) {
            if (mOnScrollListener != null) {
                mOnScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
            }
            super.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (mOnScrollListener != null) {
                mOnScrollListener.onScrollStateChanged(view, scrollState);
            }
            super.onScrollStateChanged(view, scrollState);
        }
    }

}
