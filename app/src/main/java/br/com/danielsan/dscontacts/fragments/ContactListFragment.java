package br.com.danielsan.dscontacts.fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.Arrays;
import java.util.List;

import br.com.danielsan.dscontacts.R;
import br.com.danielsan.dscontacts.adapters.ContactsAdapter;

/**
 * Created by daniel on 05/06/15.
 */
public class ContactListFragment extends ListFragment {

    public static ContactListFragment newInstance() {
        return new ContactListFragment();
    }

    public ContactListFragment() {}

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.setListAdapter(new ContactsAdapter(this.getActivity()));
        this.getListView().setBackgroundColor(this.getResources().getColor(R.color.window_background));
    }
}
