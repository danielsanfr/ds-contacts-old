package br.com.danielsan.dscontacts.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import br.com.danielsan.dscontacts.R;
import br.com.danielsan.dscontacts.adapters.holder.DrawerViewHolder;

/**
 * Created by daniel on 06/06/15.
 */
public class DrawerAdapter extends BaseAdapter {

    private final Context mContext;

    public DrawerAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object getItem(int position) {
        switch (position) {
            case 0:
                return mContext.getString(R.string.title_section1);
            case 1:
                return mContext.getString(R.string.title_section2);
            case 2:
            default:
                return mContext.getString(R.string.title_section3);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DrawerViewHolder drawerViewHolder;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.item_drawer, null);
            drawerViewHolder = new DrawerViewHolder(convertView);
        } else
            drawerViewHolder = (DrawerViewHolder) convertView.getTag();

        drawerViewHolder.updateView((String) this.getItem(position));

        return convertView;
    }

}