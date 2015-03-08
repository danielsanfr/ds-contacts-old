package br.com.danielsan.dscontacts.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import br.com.danielsan.dscontacts.R;

///**
// * A simple {@link android.app.Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link BlankFragment.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link BlankFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class OtherFieldsDialog extends DialogFragment {
//    private OnFragmentInteractionListener mListener;

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
//        LayoutInflater inflater = getActivity().getLayoutInflater();
//        ListView listView = (ListView) inflater.inflate(R.layout.other_fields_dialog, null);
        ListView listView = new ListView(getActivity());

        listView.setAdapter(new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_activated_1, android.R.id.text1,
                getResources().getStringArray(R.array.other_fields)));

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("Add other field");
        alertDialogBuilder.setCancelable(true);
        alertDialogBuilder.setNegativeButton("Cancel", null);
        alertDialogBuilder.setView(listView);
        return alertDialogBuilder.create();
    }

    //    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        try {
//            mListener = (OnFragmentInteractionListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

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
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        public void onFragmentInteraction(Uri uri);
//    }

}