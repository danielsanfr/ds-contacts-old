package br.com.danielsan.dscontacts.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by daniel on 05/06/15.
 */
public class ContactsAdapter extends BaseAdapter {

    private final Context mContext;

    public ContactsAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return 20;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = new TextView(mContext);
        textView.setText("Testando como isso funciona");
        return textView;
    }
}
