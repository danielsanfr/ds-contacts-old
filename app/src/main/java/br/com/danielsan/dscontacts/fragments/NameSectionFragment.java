package br.com.danielsan.dscontacts.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.danielsan.dscontacts.R;

public class NameSectionFragment extends Fragment {
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment NameSectionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NameSectionFragment newInstance() {
        return new NameSectionFragment();
    }

    public NameSectionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.name_section_fragment, container, false);
    }
}
