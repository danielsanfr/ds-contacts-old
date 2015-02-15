package br.com.danielsan.dscontacts.activities;

import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.ArrayList;

import br.com.danielsan.dscontacts.MainActivity;
import br.com.danielsan.dscontacts.R;
import br.com.danielsan.dscontacts.fragments.SectionContactFragment;

public class AddContactActivity extends ActionBarActivity
        implements SectionContactFragment.OnFragmentInteractionListener {

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

        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
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

        ArrayList<String> groups = new ArrayList<>();
        groups.add("Frineds");
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
            ArrayList<String> emailTags = new ArrayList<String>();
            ArrayList<String> addressTags = new ArrayList<String>();
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

            emailTags.add("Home");
            emailTags.add("Job");
            emailTags.add("Other");
            emailTags.add("Custom");

            addressTags.add("Home");
            addressTags.add("Job");
            addressTags.add("Other");
            addressTags.add("Custom");

            specialDatesTags.add("Birthday");
            specialDatesTags.add("Commemorative Date");
            specialDatesTags.add("Other");
            specialDatesTags.add("Custom");

            addFragment(R.id.m_frm_lyt_1, SectionContactFragment.newInstance("Phone", phoneTags));
            addFragment(R.id.m_frm_lyt_2, SectionContactFragment.newInstance("E-mail", emailTags, SectionContactFragment.Type.Email));
            addFragment(R.id.m_frm_lyt_3, SectionContactFragment.newInstance("Address", addressTags));
            addFragment(R.id.m_frm_lyt_4, SectionContactFragment.newInstance("Special dates", specialDatesTags, SectionContactFragment.Type.Date));
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.add_contact, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, MainActivity.class);
                NavUtils.navigateUpTo(this, intent);
                break;
            case R.id.action_create_group:
                break;
            //noinspection SimplifiableIfStatement
            case R.id.action_settings:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void addFragment(int id, Fragment fragment) {
        getSupportFragmentManager().beginTransaction().add(id, fragment).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.add_contact_fragment, container, false);
            return rootView;
        }
    }
}
