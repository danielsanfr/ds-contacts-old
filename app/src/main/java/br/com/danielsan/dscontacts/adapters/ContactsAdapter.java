package br.com.danielsan.dscontacts.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import br.com.danielsan.dscontacts.R;
import br.com.danielsan.dscontacts.adapters.holder.ContactsViewHolder;

/**
 * Created by daniel on 05/06/15.
 */
public class ContactsAdapter extends BaseAdapter {

    private final Context mContext;

    // list of data items
    private List<ListData> mDataList = Arrays.asList(
            new ListData("Iron Man"),
            new ListData("Captain America"),
            new ListData("James Bond"),
            new ListData("Harry Potter"),
            new ListData("Sherlock Holmes"),
            new ListData("Black Widow"),
            new ListData("Hawk Eye"),
            new ListData("Iron Man"),
            new ListData("Guava"),
            new ListData("Tomato"),
            new ListData("Pineapple"),
            new ListData("Strawberry"),
            new ListData("Watermelon"),
            new ListData("Pears"),
            new ListData("Kiwi"),
            new ListData("Plums")
    );

    public ContactsAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public ListData getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ContactsViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.item_contacts, null);
            holder = new ContactsViewHolder(convertView);
        } else
            holder = (ContactsViewHolder) convertView.getTag();

        holder.updateCheckedState(this.getItem(position));

        return convertView;
    }

    public static class ListData {

        public String data;

        public boolean isChecked;

        public ListData(String data) {
            this.data = data;
        }

        public void setChecked(boolean isChecked) {
            this.isChecked = isChecked;
        }
    }

}
