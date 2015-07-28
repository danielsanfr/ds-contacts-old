package br.com.danielsan.dscontacts.fragments.add.contacts.fields;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        pTitleTxtVw.setText("Work");
        pTitleImgVw.setImageResource(R.drawable.ic_work_grey);

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected int contentResId() {
        return R.layout.fragment_field_work;
    }

    @Override
    protected void updatedContact(Contact contact) {
        contact.setCompany(mOrganizationEdtTxt.getText().toString());
        contact.setJobTitle(mTitleEdtTxt.getText().toString());
    }

}
