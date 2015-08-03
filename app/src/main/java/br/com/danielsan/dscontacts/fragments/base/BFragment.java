package br.com.danielsan.dscontacts.fragments.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import br.com.danielsan.dscontacts.activities.BaseActivity;

/**
 * Created by daniel on 06/06/15.
 */
public class BFragment extends Fragment {

    protected BaseActivity mBaseActivity;
    private OnCreationCompleteListener mOnCreationCompleteListener;

    public BFragment() {}

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof OnCreationCompleteListener)
            mOnCreationCompleteListener = (OnCreationCompleteListener) activity;
        else
            mOnCreationCompleteListener = null;

        try {
            mBaseActivity = (BaseActivity) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must extend BaseActivity.");
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mOnCreationCompleteListener != null)
            mOnCreationCompleteListener.onComplete(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mBaseActivity = null;
        mOnCreationCompleteListener = null;
    }

    /**
     * Callbacks interface that notifies the activity that this fragment was created.
     */
    public interface OnCreationCompleteListener {
        /**
         * Called when the fragment was created.
         */
        void onComplete(BFragment fragment);
    }

}
