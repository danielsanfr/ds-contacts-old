package br.com.danielsan.dscontacts.activities;

import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.danielsan.dscontacts.fragments.ContactListFragment;
import br.com.danielsan.dscontacts.fragments.MainFragment;
import br.com.danielsan.dscontacts.R;
import butterknife.ButterKnife;
import butterknife.Bind;

public class MainActivity extends BaseActivity implements OnNavigationItemSelectedListener {

    private CharSequence mTitle;
    private MainFragment mMainFragment;
    private SearchViewController mSrcVwCltl;
    private ActionBarDrawerToggle mDrawerToggle;

    @Bind(R.id.drw_lyt_main)
    protected DrawerLayout mDrawerLayout;
    @Bind(R.id.nvgt_vw)
    protected NavigationView mNavigationView;
    @Bind(R.id.frm_lyt_drawer)
    protected FrameLayout mDrawerFrmLyt;
    @Bind(R.id.txt_vw_user_name)
    protected TextView mUserNameTextView;
    @Bind(R.id.txt_vw_user_email)
    protected TextView mUserEmailTextView;
    @Bind(R.id.img_vw_avatar)
    protected ImageView mAvatarImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        mTitle = this.getTitle();
        mSrcVwCltl = new SearchViewController();
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                                                  R.string.navigation_drawer_open,
                                                  R.string.navigation_drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                supportInvalidateOptionsMenu();
            }
        };

        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        // enable status bar tint
        this.getSystemBarTintManager().setStatusBarTintEnabled(true);
        // enable navigation bar tint
        this.getSystemBarTintManager().setNavigationBarTintEnabled(true);

        mNavigationView.setNavigationItemSelectedListener(this);
        mUserNameTextView.setText("Daniel San");
        mUserEmailTextView.setText("daniel.samrocha@gmail.com");
        mAvatarImageView.setImageResource(R.drawable.img_avatar);

        mMainFragment = new MainFragment();
        this.replaceFragment(mMainFragment);
    }

    @Override
    protected int getContentViewRes() {
        return R.layout.activity_main;
    }

    @Override
    protected int getBaseContainerId() {
        return R.id.container;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        menuItem.setChecked(true);
        mDrawerLayout.closeDrawer(mDrawerFrmLyt);
        return true;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (mMainFragment != null)
            mMainFragment.setCurrentColor();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mDrawerLayout.isDrawerOpen(mDrawerFrmLyt))
            this.getMenuInflater().inflate(R.menu.global, menu);
        else
            this.getMenuInflater().inflate(R.menu.main, menu);

        mSrcVwCltl.setSearchViewMenu(menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item))
            return true;

        switch (item.getItemId()) {
            case R.id.menu_create_group:
                Snackbar.make(mMainFragment.getView(), "Your message", Snackbar.LENGTH_SHORT).show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void changeColor(Integer newColor) {
        this.getSystemBarTintManager().setTintColor(newColor);
        mActionBar.setBackgroundDrawable(new ColorDrawable(newColor));
    }

    private class SearchViewController
            implements MenuItemCompat.OnActionExpandListener,
            SearchView.OnQueryTextListener,
            SearchView.OnSuggestionListener {

        public void setSearchViewMenu(Menu menu) {
            MenuItem menuItem = menu.findItem(R.id.menu_search);
            if (menuItem != null) {
                SearchView searchView = (SearchView) menuItem.getActionView();
                searchView.setQueryHint(getString(R.string.find_contacts) + "...");
                searchView.setOnQueryTextListener(this);
                searchView.setOnSuggestionListener(this);
                MenuItemCompat.setOnActionExpandListener(menuItem, this);
            }
        }

        @Override
        public boolean onMenuItemActionExpand(MenuItem menuItem) {
            addFragment(new ContactListFragment());
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            return true;
        }

        @Override
        public boolean onMenuItemActionCollapse(MenuItem menuItem) {
            removeFragment();
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            return true;
        }

        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            return false;
        }

        @Override
        public boolean onSuggestionSelect(int position) {
            return false;
        }

        @Override
        public boolean onSuggestionClick(int position) {
            return false;
        }
    }

}
