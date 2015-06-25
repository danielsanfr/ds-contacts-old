package br.com.danielsan.dscontacts.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import br.com.danielsan.dscontacts.R;
import br.com.danielsan.dscontacts.util.FragmentsTransaction;

/**
 * Created by daniel on 06/06/15.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected ActionBar mActionBar;
    private SystemBarTintManager mSystemBarTintManager;
    private FragmentsTransaction mFragmentsTransaction;

    protected void onCreate(Bundle savedInstanceState, @LayoutRes int layoutResID) {
        super.onCreate(savedInstanceState);
        this.setContentView(layoutResID);

        mSystemBarTintManager = new SystemBarTintManager(this);
        mFragmentsTransaction = new FragmentsTransaction(this, this.getMasterContainer());

        if (this.getSupportActionBar() == null)
            this.setSupportActionBar((Toolbar) this.findViewById(R.id.toolbar));

        mActionBar = this.getSupportActionBar();
        mActionBar.setElevation(0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        throw new RuntimeException("You should use the method \"onCreate(Bundle savedInstanceState, @LayoutRes int layoutResID)\" instead of \"onCreate(Bundle savedInstanceState)\"");
    }

    @IdRes
    protected abstract int getMasterContainer();

    public void addActivity(Class<? extends FragmentActivity> activityClass) {
        this.startActivity(new Intent(this, activityClass));
    }

    public void replaceActivity(Class<? extends FragmentActivity> activityClass) {
        this.addActivity(activityClass);
        this.finish();
    }

    public void addFragment(Fragment fragment) {
        mFragmentsTransaction.add(fragment);
    }

    public void removeFragment() {
        mFragmentsTransaction.remove();
    }

    public void replaceFragment(Fragment fragment) {
        mFragmentsTransaction.replace(fragment);
    }

    @NonNull
    public final ActionBar getBaseActionBar() {
        return mActionBar;
    }

    @NonNull
    public final FragmentsTransaction getFragmentsTransaction() {
        return mFragmentsTransaction;
    }

    @NonNull
    public final SystemBarTintManager getSystemBarTintManager() {
        return mSystemBarTintManager;
    }

}
