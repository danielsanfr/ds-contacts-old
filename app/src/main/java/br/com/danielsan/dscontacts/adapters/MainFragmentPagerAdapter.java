package br.com.danielsan.dscontacts.adapters;

import android.animation.ArgbEvaluator;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import br.com.danielsan.dscontacts.MainActivity;
import br.com.danielsan.dscontacts.R;
import br.com.danielsan.dscontacts.fragments.ContactListFragment;

/**
 * Created by daniel on 05/06/15.
 */
public class MainFragmentPagerAdapter extends FragmentPagerAdapter
        implements ViewPager.OnPageChangeListener {

    private static final String[] TITLES = { "Favorites", "Contacts", "Groups" };

    private Integer[] mPageColors;
    private MainActivity mMainActivity;
    private ArgbEvaluator mArgbEvaluator;

    public MainFragmentPagerAdapter(MainActivity mainActivity) {
        super(mainActivity.getSupportFragmentManager());
        mMainActivity = mainActivity;

        mArgbEvaluator = new ArgbEvaluator();
        Resources resources = mMainActivity.getResources();
        mPageColors = new Integer[] { resources.getColor(R.color.orange_500),
                                      resources.getColor(R.color.blue_500),
                                      resources.getColor(R.color.green_500)
                                    };
    }

    @Override
    public Fragment getItem(int position) {
        return new ContactListFragment();
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }

    public Integer getColor(int position) {
        return mPageColors[position];
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        // Thanks: http://kubaspatny.github.io/2014/09/18/viewpager-background-transition/
        /**
         * Thanks: http://kubaspatny.github.io/2014/09/18/viewpager-background-transition/
         *         https://github.com/kubaspatny/viewpagerbackgroundanimation
         */
        if(position < (this.getCount() -1) && position < (mPageColors.length - 1))
            mMainActivity.changeColor((Integer) mArgbEvaluator.evaluate(positionOffset, mPageColors[position], mPageColors[position + 1]));
        else
            // the last page color
            mMainActivity.changeColor(mPageColors[mPageColors.length - 1]);
    }

    @Override
    public void onPageSelected(int position) {}

    @Override
    public void onPageScrollStateChanged(int state) {}
}
