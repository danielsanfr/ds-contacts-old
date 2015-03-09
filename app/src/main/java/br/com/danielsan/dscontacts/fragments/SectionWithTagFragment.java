package br.com.danielsan.dscontacts.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import br.com.danielsan.dscontacts.R;
import br.com.danielsan.dscontacts.fragments.EditTextDialogFragment.OnEditTextDialogListener;

public class SectionWithTagFragment extends SimpleSectionFragment implements OnItemSelectedListener, OnEditTextDialogListener {
    protected static final String ARG_TAGS = "tags";

    private int mTagsSize;
    private ArrayList<String> mTags;
    private ArrayAdapter<String> mSpnrTagAdapter;
    private Spinner mSpnrTag;

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
            mTags = new ArrayList<>();
            mTags.addAll(Arrays.asList(getArguments().getStringArray(ARG_TAGS)));
            mTagsSize = mTags.size();
        }
    }

    @Override
    protected View addContactItem() {
        View view = super.addContactItem(R.layout.section_contact_item_with_tag);
        Integer counterSectionItems = mCounterSectionItems - 1;
        Map<Integer, View> sectionItem = mSectionItems.remove(counterSectionItems);

        mSpnrTag = (Spinner) view.findViewById(R.id.spnr_tag);
        mSpnrTagAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item,
                                             mTags);
        mSpnrTagAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpnrTag.setOnItemSelectedListener(this);
        mSpnrTag.setAdapter(mSpnrTagAdapter);

        sectionItem.put(R.id.spnr_tag, mSpnrTag);
        mSectionItems.put(counterSectionItems, sectionItem);

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String tag = ((TextView) view).getText().toString();
        if (tag.equals(getResources().getString(R.string.custom))) {
            EditTextDialogFragment editTextDialogFragment =
            EditTextDialogFragment.newInstance("Custom label name");
            editTextDialogFragment.setOnEditTextDialogListener(this);
            editTextDialogFragment.show(getFragmentManager(), "");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) { }

    @Override
    public void onEditTextListenerInteraction(String text) {
        if (mTagsSize != mTags.size()) {
            mSpnrTagAdapter.remove(mSpnrTagAdapter.getItem(0));
        }
        mSpnrTagAdapter.insert(text, 0);
        mSpnrTag.setSelection(0, true);
    }
}
