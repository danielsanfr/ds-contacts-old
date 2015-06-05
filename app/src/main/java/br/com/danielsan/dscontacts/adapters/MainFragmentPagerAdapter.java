package br.com.danielsan.dscontacts.adapters;

import android.support.annotation.ColorRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
    @ColorRes
    public static final int[] PAGE_COLORS = { R.color.orange_500, R.color.blue_500, R.color.green_500 };

    private MainActivity mMainActivitya;

    public MainFragmentPagerAdapter(MainActivity mainActivity) {
        super(mainActivity.getSupportFragmentManager());
        mMainActivitya = mainActivity;
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

    @ColorRes
    public int getColor(int position) {
        return PAGE_COLORS[position];
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {
        mMainActivitya.changeColor(PAGE_COLORS[position]);
    }

    @Override
    public void onPageScrollStateChanged(int state) {}
}
