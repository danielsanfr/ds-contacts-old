package br.com.danielsan.dscontacts.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import br.com.danielsan.dscontacts.R;

public class GroupSectionFragment extends Fragment {

    private Spinner mSpnrGroup;
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
        View view = inflater.inflate(R.layout.group_section_fragment, container, false);

        mSpnrGroup = (Spinner) view.findViewById(R.id.m_spnr_group);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.field_group));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpnrGroup.setAdapter(adapter);

        return view;
    }
}
