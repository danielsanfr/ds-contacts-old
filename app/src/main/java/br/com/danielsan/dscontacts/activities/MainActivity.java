package br.com.danielsan.dscontacts.activities;

import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.danielsan.dscontacts.fragments.MainFragment;
import br.com.danielsan.dscontacts.R;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends BaseActivity implements OnNavigationItemSelectedListener {

    private CharSequence mTitle;
    private MainFragment mMainFragment;
    private ActionBarDrawerToggle mDrawerToggle;

    @InjectView(R.id.drw_lyt_main)
    protected DrawerLayout mDrawerLayout;
    @InjectView(R.id.nvgt_vw)
    protected NavigationView mNavigationView;
    @InjectView(R.id.txt_vw_user_name)
    protected TextView mUserNameTextView;
    @InjectView(R.id.txt_vw_user_email)
    protected TextView mUserEmailTextView;
    @InjectView(R.id.img_vw_avatar)
    protected ImageView mAvatarImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        mTitle = this.getTitle();
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
    protected int getMasterContainer() {
        return R.id.container;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        menuItem.setChecked(true);
        mDrawerLayout.closeDrawer(mNavigationView);
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
        if (mDrawerLayout.isDrawerOpen(mNavigationView))
            this.getMenuInflater().inflate(R.menu.global, menu);
        else
            this.getMenuInflater().inflate(R.menu.main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item))
            return true;
        Snackbar.make(mMainFragment.getView(), "Your message", Snackbar.LENGTH_SHORT).show();
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
