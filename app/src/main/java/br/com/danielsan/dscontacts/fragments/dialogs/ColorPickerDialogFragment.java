package br.com.danielsan.dscontacts.fragments.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.LinearLayout;

import com.larswerkman.holocolorpicker.ColorPicker;

/**
 * Created by daniel on 31/07/15.
 */
public class ColorPickerDialogFragment extends DialogFragment implements DialogInterface.OnClickListener {

    private static final String TITLE = "title";
    private static final String TITLE_RES = "title_res";

    private Listener mListener;
    private ColorPicker mClrPkr;
    private CharSequence mTitle;

    public static ColorPickerDialogFragment newInstance(@StringRes int titleRes) {
        ColorPickerDialogFragment fragment = new ColorPickerDialogFragment();
        Bundle args = new Bundle();
        args.putInt(TITLE_RES, titleRes);
        fragment.setArguments(args);
        return fragment;
    }

    public static ColorPickerDialogFragment newInstance(String title) {
        ColorPickerDialogFragment fragment = new ColorPickerDialogFragment();
        Bundle args = new Bundle();
        args.putString(TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    public ColorPickerDialogFragment setListener(ColorPickerDialogFragment.Listener listener) {
        mListener = listener;
        return this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            if (bundle.containsKey(TITLE))
                mTitle = bundle.getString(TITLE, "");
            else if (bundle.containsKey(TITLE_RES))
                mTitle = this.getString(bundle.getInt(TITLE_RES));
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mClrPkr = new ColorPicker(this.getActivity());
        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(25, 10, 25, 10);

        linearLayout.addView(mClrPkr, layoutParams);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle(mTitle);
        alertDialogBuilder.setCancelable(true);
        alertDialogBuilder.setView(linearLayout);
        alertDialogBuilder.setPositiveButton(getResources().getString(android.R.string.ok), this);
        alertDialogBuilder.setNegativeButton(getResources().getString(android.R.string.cancel), null);
        return alertDialogBuilder.create();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        if (mListener != null)
            mListener.onInputColor(mClrPkr.getColor());
    }

    public interface Listener {
        void onInputColor(Integer color);
    }

}
