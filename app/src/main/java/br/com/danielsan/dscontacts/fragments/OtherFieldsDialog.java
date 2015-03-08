package br.com.danielsan.dscontacts.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ArrayAdapter;

import br.com.danielsan.dscontacts.R;

/**
* A simple {@link android.app.Fragment} subclass.
* Activities that contain this fragment must implement the
* {@link OtherFieldsDialog.OnOtherFieldsDialogInteractionListener} interface
* to handle interaction events.
* Use the {@link OtherFieldsDialog#newInstance} factory method to
* create an instance of this fragment.
*/
public class OtherFieldsDialog extends DialogFragment implements OnClickListener{
    private OnOtherFieldsDialogInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment OtherFieldsDialog.
     */
    public static OtherFieldsDialog newInstance() {
         return new OtherFieldsDialog();
    }

    public OtherFieldsDialog() {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        ListView listView = new ListView(getActivity());

        listView.setAdapter(new OtherFieldsAdapter(getActivity(), this));

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("Add other field");
        alertDialogBuilder.setCancelable(true);
        alertDialogBuilder.setNegativeButton("Cancel", null);
        alertDialogBuilder.setView(listView);
        return alertDialogBuilder.create();
    }

    @Override
    public void onClick(View view) {
        if (mListener != null) {
            TextView textView = (TextView) view;
            mListener.onOtherFieldsDialogInteraction(textView.getText().toString());
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnOtherFieldsDialogInteractionListener) activity;
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

    private static class OtherFieldsAdapter extends ArrayAdapter<String> {

        private OnClickListener mOnClickListener;

        public OtherFieldsAdapter(Activity activity, OnClickListener onClickListener) {
            super(activity, android.R.layout.simple_list_item_activated_1,
                  android.R.id.text1, activity.getResources().getStringArray(R.array.other_fields));
            mOnClickListener = onClickListener;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = (TextView) super.getView(position, convertView, parent);
            textView.setOnClickListener(mOnClickListener);
            return  textView;
        }
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
    public interface OnOtherFieldsDialogInteractionListener {
        public void onOtherFieldsDialogInteraction(String type);
    }

}