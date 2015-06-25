package br.com.danielsan.dscontacts.adapters;

import android.content.Context;
import android.database.MatrixCursor;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AlphabetIndexer;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import br.com.danielsan.dscontacts.R;
import br.com.danielsan.dscontacts.adapters.holder.ContactsViewHolder;

/**
 * Created by daniel on 05/06/15.
 */
public class ContactsAdapter extends BaseAdapter implements SectionIndexer {

    private final Context mContext;
    private AlphabetIndexer mAlphabetIndexer;

    // list of data items
    public List<ListData> mDataList = Arrays.asList(
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
        Collections.sort(mDataList);
        MatrixCursor matrixCursor = new MatrixCursor(new String[] { "_id", "item" });
        for (int i = 0, size = mDataList.size(); i < size; ++i) {
            matrixCursor.addRow(new Object[] { i, mDataList.get(i).data });
        }
        mAlphabetIndexer = new AlphabetIndexer(matrixCursor, 1, " ABCDEFGHIJKLMNOPQRSTUVWXYZ");
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
    public Object[] getSections() {
        return mAlphabetIndexer.getSections();
    }

    @Override
    public int getPositionForSection(int i) {
        return mAlphabetIndexer.getPositionForSection(i);
    }

    @Override
    public int getSectionForPosition(int i) {
        return mAlphabetIndexer.getSectionForPosition(i);
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

    public static class ListData implements Comparable<ListData> {

        public String data;

        public boolean isChecked;

        public ListData(String data) {
            this.data = data;
        }

        public void setChecked(boolean isChecked) {
            this.isChecked = isChecked;
        }

        @Override
        public int compareTo(@NonNull ListData listData) {
            return data.compareTo(listData.data);
        }
    }

}
