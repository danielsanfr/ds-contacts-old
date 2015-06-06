package br.com.danielsan.dscontacts.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;

import com.astuetz.PagerSlidingTabStrip;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import br.com.danielsan.dscontacts.fragments.NavigationDrawerFragment;
import br.com.danielsan.dscontacts.R;
import br.com.danielsan.dscontacts.adapters.MainFragmentPagerAdapter;
import br.com.danielsan.dscontacts.misc.fab.FabHidden;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    @InjectView(R.id.vw_pgr_main)
    protected ViewPager mMainViewPager;
    @InjectView(R.id.fab_add_contact)
    protected FloatingActionButton mFabAddContact;
    @InjectView(R.id.pg_sld_tab_stp_main)
    protected PagerSlidingTabStrip mPagerSlidingTabStrip;

    private CharSequence mTitle;
    private FabHidden mFabHidden;
    private ActionBar mActionBar;
    private Drawable mLastBackgrouDrawable;
    private SystemBarTintManager mSystemBarTintManager;
    private NavigationDrawerFragment mNavigationDrawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ButterKnife.inject(this);

        mTitle = this.getTitle();
        mActionBar = this.getSupportActionBar();
        if (mActionBar != null)
            mActionBar.setElevation(0);

        // create our manager instance after the content view is set
        mSystemBarTintManager = new SystemBarTintManager(this);
        // enable status bar tint
        mSystemBarTintManager.setStatusBarTintEnabled(true);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                this.getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        mMainViewPager.setAdapter(new MainFragmentPagerAdapter(this));
        mPagerSlidingTabStrip.setViewPager(mMainViewPager);
        mPagerSlidingTabStrip.setOnPageChangeListener((MainFragmentPagerAdapter) mMainViewPager.getAdapter());
        mMainViewPager.setCurrentItem(1);






//        mFabHidden = new FabHidden(mFabAddContact, mLstVw);
        mFabAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddContactActivity.class));
            }
        });

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.changeColor(((MainFragmentPagerAdapter) mMainViewPager.getAdapter()).getColor(mMainViewPager.getCurrentItem()));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        mActionBar.setDisplayShowTitleEnabled(true);
        mActionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            //noinspection SimplifiableIfStatement
            case R.id.action_settings:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void changeColor(Integer newColor) {
        mSystemBarTintManager.setTintColor(newColor);
        mPagerSlidingTabStrip.setBackgroundColor(newColor);
        mLastBackgrouDrawable = new ColorDrawable(newColor);
        mActionBar.setBackgroundDrawable(mLastBackgrouDrawable);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.main_fragment, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
