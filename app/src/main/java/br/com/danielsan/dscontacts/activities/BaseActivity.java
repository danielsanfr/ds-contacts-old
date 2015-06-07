package br.com.danielsan.dscontacts.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import br.com.danielsan.dscontacts.util.FragmentsTransaction;

/**
 * Created by daniel on 06/06/15.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected ActionBar mActionBar;
    protected FragmentsTransaction mFragmentsTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (this.getSupportActionBar() != null)
            mActionBar = this.getSupportActionBar();
    }

    public void addActivity(Class<? extends FragmentActivity> activityClass) {
        this.startActivity(new Intent(this, activityClass));
    }

    public void replaceActivity(Class<? extends FragmentActivity> activityClass) {
        this.addActivity(activityClass);
        this.finish();
    }

    @NonNull
    public final FragmentsTransaction getFragmentsTransaction() {
        return mFragmentsTransaction;
    }

    @NonNull
    public ActionBar getBaseActionBar() {
        return mActionBar;
    }

}
