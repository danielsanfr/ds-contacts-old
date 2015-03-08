package br.com.danielsan.dscontacts.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    private List<String> mOtherFieldsTitles;
    private List<Integer> mOtherFieldsTagsId;

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
                OtherFieldsDialog.newInstance((ArrayList<String>) mOtherFieldsTitles).show(getFragmentManager(), "");
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

        mSpnrGroup = (Spinner) findViewById(R.id.m_spnr_group);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                resources.getStringArray(R.array.field_group));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpnrGroup.setAdapter(adapter);

        if (savedInstanceState == null) {
            addFragment(SectionWithTagFragment.newInstance(getResources().getString(R.string.phone),
                                                           resources.getStringArray(R.array.field_phone)));
            addFragment(SectionWithTagFragment.newInstance(getResources().getString(R.string.email),
                                                           resources.getStringArray(R.array.field_address_and_email)));
        }
    }

    private void addFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().add(R.id.m_lnr_lyt_sections, fragment).commit();
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
    }
}
