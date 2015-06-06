package br.com.danielsan.dscontacts.util;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

/**
 * Created by daniel on 06/06/15.
 */
public class FragmentsTransaction {

    @IdRes
    private int mContainerViewId;
    private FragmentActivity mFragmentActivity;

    public FragmentsTransaction(FragmentActivity fragmentActivity, @IdRes int containerViewId) {
        mFragmentActivity = fragmentActivity;
        mContainerViewId = containerViewId;
    }

    public void add(Fragment fragment) {
        FragmentsTransaction.add(mFragmentActivity, mContainerViewId, fragment);
    }

    public void replace(Fragment fragment) {
        FragmentsTransaction.replace(mFragmentActivity, mContainerViewId, fragment);
    }

    public static void add(FragmentActivity fragmentActivity, @IdRes int containerViewId, Fragment fragment) {
        fragmentActivity.getSupportFragmentManager().beginTransaction().add(containerViewId, fragment).commit();
    }

    public static void replace(FragmentActivity fragmentActivity, @IdRes int containerViewId, Fragment fragment) {
        fragmentActivity.getSupportFragmentManager().beginTransaction().replace(containerViewId, fragment).commit();
    }

}
