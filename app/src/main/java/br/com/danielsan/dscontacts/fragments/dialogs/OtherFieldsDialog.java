package br.com.danielsan.dscontacts.fragments.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;

import java.util.ArrayList;

/**
* A simple {@link android.app.Fragment} subclass.
* Activities that contain this fragment must implement the
* {@link br.com.danielsan.dscontacts.fragments.dialogs.OtherFieldsDialog.Listener} interface
* to handle interaction events.
* Use the {@link OtherFieldsDialog#newInstance} factory method to
* create an instance of this fragment.
*/
public class OtherFieldsDialog extends DialogFragment implements OnItemClickListener {
    protected static final String TITLES = "titles";

    private Listener mListener;

    private ArrayList<String> mTitles;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param titles Parameter 1.
     * @return A new instance of fragment OtherFieldsDialog.
     */
    public static OtherFieldsDialog newInstance(ArrayList<String> titles) {
        OtherFieldsDialog otherFieldsDialog = new OtherFieldsDialog();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(TITLES, titles);
        otherFieldsDialog.setArguments(bundle);
        return otherFieldsDialog;
    }

    public OtherFieldsDialog() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (this.getArguments() != null) {
            mTitles = this.getArguments().getStringArrayList(TITLES);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        ListView listView = new ListView(getActivity());
        listView.setAdapter(this.createListAdapter(mTitles));
        listView.setOnItemClickListener(this);
        listView.setDividerHeight(0);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("Add other field");
        alertDialogBuilder.setCancelable(true);
        alertDialogBuilder.setNegativeButton("Cancel", null);
        alertDialogBuilder.setView(listView);
        return alertDialogBuilder.create();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        if (mListener != null) {
            CharSequence text = null;
            if (view instanceof TextView)
                text = ((TextView) view).getText();
            mListener.onSelection(this, view, position, text);
        }
        this.dismiss();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (Listener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement Listener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private ArrayAdapter<String> createListAdapter(ArrayList<String> titles) {
        if (titles == null)
            titles = new ArrayList<>();
        return new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1,
                                        android.R.id.text1, titles);
    }

    public interface Listener {
        void onSelection(OtherFieldsDialog dialog, View view, int position, CharSequence text);
    }

}