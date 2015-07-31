package br.com.danielsan.dscontacts.fragments.add.contacts.fields;

import android.os.Bundle;
import android.support.annotation.ArrayRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Spinner;

import br.com.danielsan.dscontacts.R;
import br.com.danielsan.dscontacts.adapters.FieldSpinnerAdapter;
import br.com.danielsan.dscontacts.model.base.Field;
import br.com.danielsan.dscontacts.model.base.FieldWithTag;

/**
 * Created by daniel on 28/06/15.
 */
public class WithTagsFieldFragment extends CommonFieldFragment {

    protected static final String TAGS = "tags";

    protected String[] pTags;
    protected Spinner pCurrentSpinner;

    @ArrayRes
    protected int pTagsRes;

    public static WithTagsFieldFragment newInstance(Field field) {
        WithTagsFieldFragment fragment = new WithTagsFieldFragment();
        Bundle bundle = CommonFieldFragment.buildBundle(field);
        bundle.putInt(TAGS, field.getTagsRes());
        fragment.setArguments(bundle);
        return fragment;
    }

    public WithTagsFieldFragment() {}

    @Override
    protected int getSubFieldLayoutRes() {
        return R.layout.component_contact_subfield_with_tag;
    }

    @Override
    protected void updateField(Field field, View view) {
        super.updateField(field, view);
        if (field instanceof FieldWithTag) {
            FieldWithTag fieldWithTag = (FieldWithTag) field;
            fieldWithTag.setTag((String) ((Spinner) view.findViewById(R.id.spnr_tag)).getSelectedItem());
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pTags = null;
        pCurrentSpinner = null;

        Bundle bundle = this.getArguments();
        if (bundle != null && bundle.containsKey(TAGS)) {
            pTagsRes = bundle.getInt(TAGS);
            pTags = this.getResources().getStringArray(pTagsRes);
        }
    }

    @Override
    protected void addSubFieldOnClick(View view) {
        super.addSubFieldOnClick(view);

        FieldSpinnerAdapter.configureAdapter(this.getActivity(),
                                             (Spinner) pSubFieldViews.get(pSubFieldCounter - 1).findViewById(R.id.spnr_tag),
                                             pTagsRes);
    }

}