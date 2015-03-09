package br.com.danielsan.dscontacts.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.app.Fragment;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.content.DialogInterface.OnClickListener;

import br.com.danielsan.dscontacts.R;
/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link br.com.danielsan.dscontacts.fragments.EditTextDialogFragment.OnEditTextDialogListener} interface
 * to handle interaction events.
 * Use the {@link EditTextDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditTextDialogFragment extends DialogFragment implements OnClickListener {
    private static final String ARG_TITLE = "title";

    private String mTitle;
    private EditText mEditText;
    private OnEditTextDialogListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param title Parameter 1.
     * @return A new instance of fragment EditTextDialogFragment.
     */
    public static EditTextDialogFragment newInstance(String title) {
        EditTextDialogFragment fragment = new EditTextDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    public EditTextDialogFragment() {
        // Required empty public constructor
    }

    public void setOnEditTextDialogListener(OnEditTextDialogListener listener) {
        mListener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = getArguments().getString(ARG_TITLE);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mEditText = new EditText(getActivity());
        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(25, 10, 25, 10);

        linearLayout.addView(mEditText, layoutParams);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle(mTitle);
        alertDialogBuilder.setCancelable(true);
        alertDialogBuilder.setView(linearLayout);
        alertDialogBuilder.setPositiveButton(getResources().getString(R.string.ok), this);
        alertDialogBuilder.setNegativeButton(getResources().getString(R.string.cancel), null);
        return alertDialogBuilder.create();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        if (mListener != null) {
            mListener.onEditTextListenerInteraction(mEditText.getText().toString());
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (mListener != null) {
            return;
        }
        try {
            mListener = (OnEditTextDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnEditTextDialogListener {
        public void onEditTextListenerInteraction(String text);
    }

}
