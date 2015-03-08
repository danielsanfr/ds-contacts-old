package br.com.danielsan.dscontacts.custom;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import br.com.danielsan.dscontacts.R;

public class CancelDoneActionBar {
    private Activity mActivity;
    private OnCancelDoneActionBarListener mListener;

    public CancelDoneActionBar(Activity activity) {
        mActivity = activity;
        setListener();

        LinearLayout lnrLyt = (LinearLayout) View.inflate(mActivity, R.layout.action_bar_add_contact, null);
        View mActDone = lnrLyt.findViewById(R.id.m_act_done);
        View mActCancel = lnrLyt.findViewById(R.id.m_act_cancel);
        mActDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onDoneActionBarClicked();
            }
        });
        mActCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onCancelActionBarClicked();
            }
        });

        configureActionBar(lnrLyt);
    }

    private void setListener() {
        try {
            mListener = (OnCancelDoneActionBarListener) mActivity;
        } catch (ClassCastException e) {
            throw new ClassCastException(mActivity.toString()
                    + " must implement OnCancelDoneActionBarListener");
        }
    }

    private void configureActionBar(View view) {
        ActionBar actionBar = ((ActionBarActivity) mActivity).getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(0xFFEF6C00));
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM,
                                    ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME
                                    | ActionBar.DISPLAY_SHOW_TITLE);
        actionBar.setCustomView(view,
                                new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                                           ViewGroup.LayoutParams.MATCH_PARENT));
    }

    public interface OnCancelDoneActionBarListener {
        public void onDoneActionBarClicked();
        public void onCancelActionBarClicked();
    }
}
