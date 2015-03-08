package br.com.danielsan.dscontacts.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Map;

import br.com.danielsan.dscontacts.R;

public class SectionWithTagFragment extends SimpleSectionFragment {
    protected static final String ARG_TAGS = "tags";

    protected String[] mTags;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param title Parameter 1.
     * @param tags Parameter 2.
     * @return A new instance of fragment SectionFragment.
     */
    public static SimpleSectionFragment newInstance(String title, String[] tags) {
        SimpleSectionFragment fragment = new SectionWithTagFragment();
        Bundle args = buildBundle(title);
        args.putStringArray(ARG_TAGS, tags);
        fragment.setArguments(args);
        return fragment;
    }

    public SectionWithTagFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTags = getArguments().getStringArray(ARG_TAGS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);


        return view;
    }

    @Override
    protected View addContactItem() {
        View view = super.addContactItem(R.layout.section_contact_item_with_tag);
        Integer counterSectionItems = mCounterSectionItems - 1;
        Map<Integer, View> sectionItem = mSectionItems.remove(counterSectionItems);

        Spinner spnrTag = (Spinner) view.findViewById(R.id.spnr_tag);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, mTags);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnrTag.setAdapter(adapter);

        sectionItem.put(R.id.spnr_tag, spnrTag);
        mSectionItems.put(counterSectionItems, sectionItem);

        return view;
    }
}
