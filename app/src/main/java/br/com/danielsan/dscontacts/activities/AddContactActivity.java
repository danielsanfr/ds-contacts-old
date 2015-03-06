package br.com.danielsan.dscontacts.activities;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.ArrayList;

import br.com.danielsan.dscontacts.R;
import br.com.danielsan.dscontacts.MainActivity;
import br.com.danielsan.dscontacts.fragments.OnSectionInteractionListener;
import br.com.danielsan.dscontacts.fragments.SectionWithTagFragment;

public class AddContactActivity extends ActionBarActivity
        implements OnSectionInteractionListener {

    private Spinner mSpnrGroup;

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

        ArrayList<String> groups = new ArrayList<String>();
        groups.add("Friends");
        groups.add("Family");
        groups.add("Coworkers");
        groups.add("Create New group");

        mSpnrGroup = (Spinner) findViewById(R.id.m_spnr_group);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, groups);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpnrGroup.setAdapter(adapter);

        if (savedInstanceState == null) {
            ArrayList<String> phoneTags = new ArrayList<String>();
            ArrayList<String> emailAndAddressTags = new ArrayList<String>();
            ArrayList<String> specialDatesTags = new ArrayList<String>();

            phoneTags.add("Mobile");
            phoneTags.add("Job");
            phoneTags.add("Home");
            phoneTags.add("Principal");
            phoneTags.add("Job Fax");
            phoneTags.add("Home Fax");
            phoneTags.add("Pager");
            phoneTags.add("Other");
            phoneTags.add("Custom");

            emailAndAddressTags.add("Home");
            emailAndAddressTags.add("Job");
            emailAndAddressTags.add("Other");
            emailAndAddressTags.add("Custom");

            specialDatesTags.add("Birthday");
            specialDatesTags.add("Commemorative Date");
            specialDatesTags.add("Other");
            specialDatesTags.add("Custom");

            addFragment(R.id.m_lnr_lyt_sections, SectionWithTagFragment.newInstance("Phone", phoneTags));
            addFragment(R.id.m_lnr_lyt_sections, SectionWithTagFragment.newInstance("E-mail", emailAndAddressTags));
            addFragment(R.id.m_lnr_lyt_sections, SectionWithTagFragment.newInstance("Address", emailAndAddressTags));
            addFragment(R.id.m_lnr_lyt_sections, SectionWithTagFragment.newInstance("Special dates", specialDatesTags));
        }
    }

    @Override
    public void onSectionInteractionListener(Uri uri) {
    }

    private void addFragment(int id, Fragment fragment) {
//        getSupportFragmentManager().beginTransaction().
        getSupportFragmentManager().beginTransaction().add(id, fragment).commit();
    }
}
