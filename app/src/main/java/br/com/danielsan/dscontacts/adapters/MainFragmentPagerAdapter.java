package br.com.danielsan.dscontacts.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import br.com.danielsan.dscontacts.fragments.ContactListFragment;

/**
 * Created by daniel on 05/06/15.
 */
public class MainFragmentPagerAdapter extends FragmentPagerAdapter {

    private static final String[] TITLES = { "Favorites", "Contacts", "Groups" };

    public MainFragmentPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
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

}
