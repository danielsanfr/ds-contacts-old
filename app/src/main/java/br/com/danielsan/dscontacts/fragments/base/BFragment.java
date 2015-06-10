package br.com.danielsan.dscontacts.fragments.base;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import br.com.danielsan.dscontacts.activities.BaseActivity;
import br.com.danielsan.dscontacts.activities.MainActivity;
import br.com.danielsan.dscontacts.util.FragmentsTransaction;

/**
 * Created by daniel on 06/06/15.
 */
public class BFragment extends Fragment {

    protected BaseActivity mBaseActivity;

    public BFragment() {}

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BFragment.setInsets(mBaseActivity, view);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mBaseActivity = (BaseActivity) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must extend BaseActivity.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mBaseActivity = null;
    }

    public static void setInsets(Activity activity, View view) {
        if (Build.VERSION.SDK_INT != Build.VERSION_CODES.KITKAT)
            return;
        SystemBarTintManager tintManager = new SystemBarTintManager(activity);
        SystemBarTintManager.SystemBarConfig config = tintManager.getConfig();
        view.setPadding(0, config.getPixelInsetTop(true) + 16, config.getPixelInsetRight(), config.getPixelInsetBottom());
    }

}
