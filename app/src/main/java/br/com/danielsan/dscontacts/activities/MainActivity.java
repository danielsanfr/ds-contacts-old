package br.com.danielsan.dscontacts.activities;

import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;

import br.com.danielsan.dscontacts.fragments.MainFragment;
import br.com.danielsan.dscontacts.fragments.NavigationDrawerFragment;
import br.com.danielsan.dscontacts.R;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity
        implements  NavigationDrawerFragment.NavigationDrawerCallbacks {

    private CharSequence mTitle;
    private MainFragment mMainFragment;
    private NavigationDrawerFragment mNavigationDrawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        mTitle = this.getTitle();

        // enable status bar tint
        this.getSystemBarTintManager().setStatusBarTintEnabled(true);
        // enable navigation bar tint
        this.getSystemBarTintManager().setNavigationBarTintEnabled(true);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                this.getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp((DrawerLayout) this.findViewById(R.id.drawer_layout));

        mMainFragment = new MainFragment();
        this.replaceFragment(mMainFragment);
    }

    @Override
    protected int getMasterContainer() {
        return R.id.container;
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (mMainFragment != null)
            mMainFragment.setCurrentColor();
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
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
            this.getMenuInflater().inflate(R.menu.main, menu);
            this.restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //noinspection SimplifiableIfStatement
            case R.id.mn_create_group:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void changeColor(Integer newColor) {
        this.getSystemBarTintManager().setTintColor(newColor);
        mActionBar.setBackgroundDrawable(new ColorDrawable(newColor));
    }

}
