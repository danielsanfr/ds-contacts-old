package br.com.danielsan.dscontacts.misc.fab;

import android.animation.ObjectAnimator;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.AbsListView;

import com.getbase.floatingactionbutton.FloatingActionsMenu;

import br.com.danielsan.dscontacts.R;

/**
 * Created by daniel on 04/06/15.
 */
public class FabHidden implements AbsListView.OnScrollListener, ScrollDirectionListener {

    private static final int TRANSLATE_DURATION_MILLIS = 400;

    private View mViewToHide;
    private AbsListView mAbsListView;

    private boolean mVisible;

    private int mLastScrollY;
    private final int mScrollThreshold;
    private int mPreviousFirstVisibleItem;

    private AbsListView.OnScrollListener mOnScrollListener;
    private ScrollDirectionListener mScrollDirectionListener;

    private final Interpolator mInterpolator = new AccelerateDecelerateInterpolator();

    public FabHidden(@NonNull View view, @NonNull AbsListView absListView){
        this(view, absListView, null, null);
    }

    public FabHidden(@NonNull View view, @NonNull AbsListView absListView,
                     @NonNull ScrollDirectionListener scrollDirectionListener) {
        this(view, absListView, scrollDirectionListener, null);
    }

    public FabHidden(@NonNull View view, @NonNull AbsListView absListView,
                     @NonNull AbsListView.OnScrollListener onScrollListener) {
        this(view, absListView, null, onScrollListener);
    }

    public FabHidden(@NonNull View view, @NonNull AbsListView absListView,
                     ScrollDirectionListener scrollDirectionListener,
                     AbsListView.OnScrollListener onScrollListener) {
        mVisible = true;
        mViewToHide = view;
        mAbsListView = absListView;
        mOnScrollListener = onScrollListener;
        mScrollDirectionListener = scrollDirectionListener;
        mScrollThreshold = mViewToHide.getResources().getDimensionPixelOffset(R.dimen.fab_scroll_threshold);
        mAbsListView.setOnScrollListener(this);
    }

    public void show() {
        this.show(true);
    }

    public void hide() {
        this.hide(true);
    }

    public void show(boolean animate) {
        this.toggle(true, animate, false);
    }

    public void hide(boolean animate) {
        this.toggle(false, animate, false);
    }

    private void toggle(final boolean visible, final boolean animate, final boolean force) {
        if (mVisible != visible || force) {
            mVisible = visible;
            int height = mViewToHide.getHeight();
            if (height == 0 && !force) {
                ViewTreeObserver viewTreeObserver = mViewToHide.getViewTreeObserver();
                if (viewTreeObserver.isAlive()) {
                    viewTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                        @Override
                        public boolean onPreDraw() {
                            ViewTreeObserver currnteViewTreeObserver = mViewToHide.getViewTreeObserver();
                            if (currnteViewTreeObserver.isAlive())
                                currnteViewTreeObserver.removeOnPreDrawListener(this);
                            toggle(visible, animate, true);
                            return true;
                        }
                    });
                    return;
                }
            }

            if (mViewToHide instanceof FloatingActionsMenu)
                ((FloatingActionsMenu) mViewToHide).collapse();

            int translationY = visible ? 0 : height + this.getMarginBottom();
            if (animate) {
                ObjectAnimator translationYObjectAnimator = ObjectAnimator.ofFloat(mViewToHide, "translationY", translationY);
                translationYObjectAnimator.setInterpolator(mInterpolator);
                translationYObjectAnimator.setDuration(TRANSLATE_DURATION_MILLIS);
                translationYObjectAnimator.start();
            } else {
                ObjectAnimator translationYObjectAnimator = ObjectAnimator.ofFloat(mViewToHide, "translationY", translationY);
                translationYObjectAnimator.start();
            }
            // On pre-Honeycomb a translated view is still clickable, so we need to disable clicks manually
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB)
                mViewToHide.setClickable(visible);
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (mOnScrollListener != null)
            mOnScrollListener.onScrollStateChanged(view, scrollState);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (mOnScrollListener != null)
            mOnScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        if(totalItemCount == 0)
            return;

        if (firstVisibleItem == mPreviousFirstVisibleItem) {
            int newScrollY = this.getTopItemScrollY();
            if (Math.abs(mLastScrollY - newScrollY) > mScrollThreshold) {
                if (mLastScrollY > newScrollY)
                    this.onScrollUp();
                else
                    this.onScrollDown();
            }
            mLastScrollY = newScrollY;
        } else {
            if (firstVisibleItem > mPreviousFirstVisibleItem)
                this.onScrollUp();
            else
                this.onScrollDown();

            mLastScrollY = getTopItemScrollY();
            mPreviousFirstVisibleItem = firstVisibleItem;
        }
    }

    @Override
    public void onScrollUp() {
        if (mScrollDirectionListener != null)
            mScrollDirectionListener.onScrollUp();
        this.hide();
    }

    @Override
    public void onScrollDown() {
        if (mScrollDirectionListener != null)
            mScrollDirectionListener.onScrollDown();
        this.show();
    }

    private int getMarginBottom() {
        int marginBottom = 0;
        final ViewGroup.LayoutParams layoutParams = mViewToHide.getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams)
            marginBottom = ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
        return marginBottom;
    }

    private int getTopItemScrollY() {
        if (mAbsListView.getChildAt(0) == null)
            return 0;
        return mAbsListView.getChildAt(0).getTop();
    }

}
