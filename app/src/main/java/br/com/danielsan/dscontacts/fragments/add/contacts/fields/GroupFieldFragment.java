package br.com.danielsan.dscontacts.fragments.add.contacts.fields;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import br.com.danielsan.dscontacts.R;
import br.com.danielsan.dscontacts.adapters.FieldSpinnerAdapter;
import br.com.danielsan.dscontacts.model.Contact;
import butterknife.Bind;

/**
 * Created by daniel on 28/06/15.
 */
public class GroupFieldFragment extends FieldFragment implements AdapterView.OnItemSelectedListener {

    @Bind(R.id.spnr_group)
    protected Spinner mGroupSpnr;

    public static GroupFieldFragment newInstance() {
        GroupFieldFragment fragment = new GroupFieldFragment();
        fragment.setArguments(FieldFragment.makeBaseBundle(R.string.group, R.drawable.ic_group_grey));
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FieldSpinnerAdapter.configureAdapter(this.getActivity(), mGroupSpnr, R.array.field_group);
    }

    @Override
    protected int contentResId() {
        return R.layout.fragment_field_group;
    }

    @Override
    public void updatedContact(Contact contact) {
        contact.setGroup((String) mGroupSpnr.getSelectedItem());
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) { }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) { }

}
