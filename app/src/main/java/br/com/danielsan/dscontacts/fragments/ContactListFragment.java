package br.com.danielsan.dscontacts.fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        this.setListAdapter(new ContactsAdapter(this.getActivity()));

        return view;
    }

}
