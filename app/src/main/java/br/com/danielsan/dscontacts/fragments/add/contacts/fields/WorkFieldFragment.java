package br.com.danielsan.dscontacts.fragments.add.contacts.fields;

import android.widget.EditText;

import br.com.danielsan.dscontacts.R;
import br.com.danielsan.dscontacts.model.Contact;
import butterknife.Bind;

/**
 * Created by daniel on 28/06/15.
 */
public class WorkFieldFragment extends FieldFragment {

    @Bind(R.id.edt_txt_organization)
    protected EditText mOrganizationEdtTxt;
    @Bind(R.id.edt_txt_title)
    protected EditText mTitleEdtTxt;

    public static WorkFieldFragment newInstance() {
        WorkFieldFragment fragment = new WorkFieldFragment();
        fragment.setArguments(FieldFragment.makeBaseBundle(R.string.work, R.drawable.ic_work_grey));
        return fragment;
    }

    @Override
    protected int contentResId() {
        return R.layout.fragment_field_work;
    }

    @Override
    public void updatedContact(Contact contact) {
        contact.setOrganization(mOrganizationEdtTxt.getText().toString());
        contact.setTitle(mTitleEdtTxt.getText().toString());
    }

}
