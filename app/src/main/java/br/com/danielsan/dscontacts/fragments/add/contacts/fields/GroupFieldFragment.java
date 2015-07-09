package br.com.danielsan.dscontacts.fragments.add.contacts.fields;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import br.com.danielsan.dscontacts.R;

/**
 * Created by daniel on 28/06/15.
 */
public class GroupFieldFragment extends FieldFragment {

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        pTitleTxtVw.setText("Group");
        pTitleImgVw.setImageResource(R.drawable.ic_group_grey);

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected int contentResId() {
        return R.layout.fragment_field_group;
    }

}
