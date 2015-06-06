package br.com.danielsan.dscontacts.fragments.base;

import android.app.Activity;
import android.support.v4.app.Fragment;

import br.com.danielsan.dscontacts.activities.BaseActivity;
import br.com.danielsan.dscontacts.util.FragmentsTransaction;

/**
 * Created by daniel on 06/06/15.
 */
public class BFragment extends Fragment {

    protected BaseActivity mBaseActivity;

    public BFragment() {}

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mBaseActivity = (BaseActivity) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must extend BaseActivity.");
        }
    }
}
