package br.com.danielsan.dscontacts.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import br.com.danielsan.dscontacts.R;
import br.com.danielsan.dscontacts.MainActivity;
import br.com.danielsan.dscontacts.fragments.OnSectionInteractionListener;
import br.com.danielsan.dscontacts.fragments.OtherFieldsDialog;
import br.com.danielsan.dscontacts.fragments.OtherFieldsDialog.OnOtherFieldsDialogInteractionListener;
import br.com.danielsan.dscontacts.fragments.SectionWithTagFragment;

public class AddContactActivity extends ActionBarActivity
        implements OnSectionInteractionListener, OnOtherFieldsDialogInteractionListener {

    private Spinner mSpnrGroup;
    private Button mBtnAddField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_contact_activity);

        // To use toolbar instead of the ActionBar
//        Toolbar toolbar = (Toolbar) findViewById(R.id.m_action_bar);
//        setSupportActionBar(toolbar);

        LinearLayout lnrLyt = (LinearLayout) View.inflate(this, R.layout.action_bar_add_contact, null);
        View mActDone = lnrLyt.findViewById(R.id.m_act_done);
        View mActCancel = lnrLyt.findViewById(R.id.m_act_cancel);

        mActCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddContactActivity.this, MainActivity.class);
                NavUtils.navigateUpTo(AddContactActivity.this, intent);
            }
        });
        mActDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddContactActivity.this, MainActivity.class);
                NavUtils.navigateUpTo(AddContactActivity.this, intent);
            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(lnrLyt);
        actionBar.setBackgroundDrawable(new ColorDrawable(0xFFEF6C00));
        actionBar.setDisplayOptions(
                ActionBar.DISPLAY_SHOW_CUSTOM, ActionBar.DISPLAY_SHOW_CUSTOM |
                        ActionBar.DISPLAY_SHOW_HOME |
                        ActionBar.DISPLAY_SHOW_TITLE);
        actionBar.setCustomView(lnrLyt,
                new ActionBar.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                )
        );

        mBtnAddField = (Button) findViewById(R.id.m_btn_add_field);
        mBtnAddField.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                OtherFieldsDialog.newInstance().show(getFragmentManager(), "");
            }
        });

        Resources resources = getResources();

        mSpnrGroup = (Spinner) findViewById(R.id.m_spnr_group);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                resources.getStringArray(R.array.field_group));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpnrGroup.setAdapter(adapter);

        if (savedInstanceState == null) {
            addFragment(R.id.m_lnr_lyt_sections,
                    SectionWithTagFragment.newInstance("Phone",
                                                       resources.getStringArray(R.array.field_phone)));
            addFragment(R.id.m_lnr_lyt_sections,
                    SectionWithTagFragment.newInstance("E-mail",
                                                       resources.getStringArray(R.array.field_address_and_email)));
        }
    }

    private void addFragment(int id, Fragment fragment) {
        getSupportFragmentManager().beginTransaction().add(id, fragment).commit();
    }

    @Override
    public void onSectionInteractionListener(Uri uri) {
    }

    @Override
    public void onOtherFieldsDialogInteraction(String type, int tagId) {
        if (tagId == -1) {
            addFragment(R.id.m_lnr_lyt_sections, SectionWithTagFragment.newInstance(type));
        } else {
            addFragment(R.id.m_lnr_lyt_sections,
                        SectionWithTagFragment.newInstance(type,
                                                           getResources()
                                                           .getStringArray(tagId)));
        }
    }
}
