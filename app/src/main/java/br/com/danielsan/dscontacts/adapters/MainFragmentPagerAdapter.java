package br.com.danielsan.dscontacts.adapters;

import android.animation.ArgbEvaluator;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import br.com.danielsan.dscontacts.activities.MainActivity;
import br.com.danielsan.dscontacts.R;
import br.com.danielsan.dscontacts.fragments.ContactListFragment;
import br.com.danielsan.dscontacts.fragments.FavoritesGridFragment;
import br.com.danielsan.dscontacts.fragments.MainFragment;

/**
 * Created by daniel on 05/06/15.
 */
public class MainFragmentPagerAdapter extends FragmentPagerAdapter
        implements ViewPager.OnPageChangeListener {

    private static final String[] TITLES = { "Favorites", "Contacts", "Groups" };

    private Integer[] mPageColors;
    private Integer[] mPressedColors;
    private MainFragment mMainFragment;
    private ArgbEvaluator mArgbEvaluator;

    public MainFragmentPagerAdapter(MainFragment mainFragment) {
        super(mainFragment.getActivity().getSupportFragmentManager());
        mMainFragment = mainFragment;

        mArgbEvaluator = new ArgbEvaluator();
        Resources resources = mMainFragment.getResources();
        mPageColors = new Integer[] { resources.getColor(R.color.blue_500),
                                      resources.getColor(R.color.green_500),
                                      resources.getColor(R.color.orange_500) };
        mPressedColors = new Integer[] { resources.getColor(R.color.blue_300),
                                         resources.getColor(R.color.green_300),
                                         resources.getColor(R.color.orange_300) };
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FavoritesGridFragment();
            case 1:
            case 2:
            default:
                return new ContactListFragment();
        }
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

    public Integer getPressedColor(int position) {
        return mPressedColors[position];
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        // Thanks: http://kubaspatny.github.io/2014/09/18/viewpager-background-transition/
        /**
         * Thanks: http://kubaspatny.github.io/2014/09/18/viewpager-background-transition/
         *         https://github.com/kubaspatny/viewpagerbackgroundanimation
         */
        if(position < (this.getCount() -1) && position < (mPageColors.length - 1))
            mMainFragment.changeColor((Integer) mArgbEvaluator.evaluate(positionOffset, mPageColors[position], mPageColors[position + 1]));
        else
            // the last page color
            mMainFragment.changeColor(mPageColors[mPageColors.length - 1]);
    }

    @Override
    public void onPageSelected(int position) {
        mMainFragment.changFabPressedColor(mPressedColors[position]);
    }

    @Override
    public void onPageScrollStateChanged(int state) {}
}
