package br.com.danielsan.dscontacts.activities;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.danielsan.dscontacts.R;
import br.com.danielsan.dscontacts.custom.CancelDoneActionBar;
import br.com.danielsan.dscontacts.custom.CancelDoneActionBar.OnCancelDoneActionBarListener;
import br.com.danielsan.dscontacts.fragments.GroupSectionFragment;
import br.com.danielsan.dscontacts.fragments.NameSectionFragment;
import br.com.danielsan.dscontacts.fragments.OnSectionInteractionListener;
import br.com.danielsan.dscontacts.fragments.dialogs.OtherFieldsDialog;
import br.com.danielsan.dscontacts.fragments.dialogs.OtherFieldsDialog.OnOtherFieldsDialogInteractionListener;
import br.com.danielsan.dscontacts.fragments.SectionWithTagFragment;

public class AddContactActivity extends ActionBarActivity
        implements OnSectionInteractionListener,
        OnOtherFieldsDialogInteractionListener,
        OnCancelDoneActionBarListener {

    private Button mBtnAddField;
    private List<String> mOtherFieldsTitles;
    private List<Integer> mOtherFieldsTagsId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        // To use toolbar instead of the ActionBar
//        Toolbar toolbar = (Toolbar) findViewById(R.id.m_action_bar);
//        setSupportActionBar(toolbar);

        new CancelDoneActionBar(this);

        mBtnAddField = (Button) findViewById(R.id.m_btn_add_field);
        mBtnAddField.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                OtherFieldsDialog.newInstance((ArrayList<String>) mOtherFieldsTitles).show(getSupportFragmentManager(), "");
            }
        });

        Resources resources = getResources();

        TypedArray typedArray = resources.obtainTypedArray(R.array.other_fields_tags);
        mOtherFieldsTitles = new ArrayList<>(Arrays.asList(resources.getStringArray(R.array.other_fields_titles)));
        mOtherFieldsTagsId = new ArrayList<>();
        int length = typedArray.length();
        for (int i = 0; i < length; i++) {
            mOtherFieldsTagsId.add(typedArray.getResourceId(i, -1));
        }
        typedArray.recycle();

        addFragment(R.id.frm_lyt_group_section, GroupSectionFragment.newInstance());

        addFragment(NameSectionFragment.newInstance());
        addFragment(SectionWithTagFragment.newInstance(getResources().getString(R.string.phone),
                                                       resources.getStringArray(R.array.field_phone)));
        addFragment(SectionWithTagFragment.newInstance(getResources().getString(R.string.email),
                                                       resources.getStringArray(R.array.field_address_and_email)));
    }

    private void addFragment(Fragment fragment) {
        this.addFragment(R.id.m_lnr_lyt_sections, fragment);
    }

    private void addFragment(int id, Fragment fragment) {
        getSupportFragmentManager().beginTransaction().add(id, fragment).commit();
    }

    @Override
    public void onSectionInteractionListener(Uri uri) {
    }

    @Override
    public void onOtherFieldsDialogInteraction(String type, int position) {
        mOtherFieldsTitles.remove(position);
        int tagId = mOtherFieldsTagsId.remove(position);
        if (tagId == -1) {
            addFragment(SectionWithTagFragment.newInstance(type));
        } else {
            addFragment(SectionWithTagFragment.newInstance(type, getResources()
                                                                 .getStringArray(tagId)));
        }
        if (mOtherFieldsTagsId.size() == 0) {
            mBtnAddField.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDoneActionBarClicked() {
        onCancelActionBarClicked();
    }

    @Override
    public void onCancelActionBarClicked() {
        this.finish();
    }

}
