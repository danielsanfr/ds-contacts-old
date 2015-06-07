package br.com.danielsan.dscontacts.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import br.com.danielsan.dscontacts.R;
import br.com.danielsan.dscontacts.fragments.dialogs.EditTextDialogFragment;
import br.com.danielsan.dscontacts.fragments.dialogs.EditTextDialogFragment.OnEditTextDialogListener;

public class GroupSectionFragment extends Fragment implements OnItemSelectedListener, OnEditTextDialogListener {

    private Spinner mSpnrGroup;
    private ArrayList<String> mGroups;
    private ArrayAdapter<String> mSpnrGroupAdapter;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment GroupSectionFragment.
     */
    public static GroupSectionFragment newInstance() {
        return new GroupSectionFragment();
    }

    public GroupSectionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_section, container, false);

        mGroups = new ArrayList<>();
        mGroups.addAll(Arrays.asList(getResources().getStringArray(R.array.field_group)));

        mSpnrGroup = (Spinner) view.findViewById(R.id.m_spnr_group);
        mSpnrGroupAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item,
                                               mGroups);
        mSpnrGroupAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpnrGroup.setOnItemSelectedListener(this);
        mSpnrGroup.setAdapter(mSpnrGroupAdapter);

        return view;
    }

    @Override
    public void onEditTextListenerInteraction(String text) {
        mSpnrGroupAdapter.insert(text, mSpnrGroupAdapter.getCount() - 1);
        mSpnrGroup.setSelection(mSpnrGroupAdapter.getCount() - 2, true);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String group = ((TextView) view).getText().toString();
        if (group.equals(getResources().getString(R.string.create_new_group))) {
            EditTextDialogFragment editTextDialogFragment =
            EditTextDialogFragment.newInstance(group);
            editTextDialogFragment.setOnEditTextDialogListener(this);
            editTextDialogFragment.show(getFragmentManager(), "");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) { }
}
