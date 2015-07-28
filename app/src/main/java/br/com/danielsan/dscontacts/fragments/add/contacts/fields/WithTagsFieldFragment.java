package br.com.danielsan.dscontacts.fragments.add.contacts.fields;

import android.os.Bundle;
import android.support.annotation.ArrayRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.danielsan.dscontacts.R;
import br.com.danielsan.dscontacts.fragments.dialogs.EditTextDialogFragment;
import br.com.danielsan.dscontacts.model.base.Field;
import br.com.danielsan.dscontacts.model.base.FieldWithTag;

/**
 * Created by daniel on 28/06/15.
 */
public class WithTagsFieldFragment extends CommonFieldFragment
        implements AdapterView.OnItemSelectedListener,
        EditTextDialogFragment.Listener {

    protected static final String TAGS = "tags";

    protected String[] pTags;
    protected Spinner pCurrentSpinner;

    @ArrayRes
    protected int pTagsRes;

    public static WithTagsFieldFragment newInstance(Field field) {
        WithTagsFieldFragment fragment = new WithTagsFieldFragment();
        Bundle bundle = CommonFieldFragment.buildBundle(field.getTitleRes(), field.getImageTitleRes());
        bundle.putInt(TAGS, field.getTagsRes());
        fragment.setArguments(bundle);
        fragment.setFieldClass(field.getClass());
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

        List<String> tags = new ArrayList<>();
        tags.addAll(Arrays.asList(this.getResources().getStringArray(pTagsRes)));

        Spinner spinner = (Spinner) pSubFieldViews.get(pSubFieldViews.size() - 1).findViewById(R.id.spnr_tag);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, tags);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setOnItemSelectedListener(this);
        spinner.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (view instanceof TextView
            && ((TextView) view).getText().toString().equals(this.getResources().getString(R.string.custom))) {
            if (adapterView instanceof Spinner)
                pCurrentSpinner = (Spinner) adapterView;
            EditTextDialogFragment editTextDialogFragment =
            EditTextDialogFragment.newInstance("Custom label name");
            editTextDialogFragment.setOnEditTextDialogListener(this);
            editTextDialogFragment.show(getFragmentManager(), "");
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}

    @Override
    public void onInputText(CharSequence text) {
        if (pCurrentSpinner == null)
            return;

        ArrayAdapter<String> adapter = (ArrayAdapter<String>) pCurrentSpinner.getAdapter();
        if (pTags.length < adapter.getCount())
            adapter.remove(adapter.getItem(0));

        adapter.insert(text.toString(), 0);
        pCurrentSpinner.setSelection(0);

        pCurrentSpinner = null;
    }

}