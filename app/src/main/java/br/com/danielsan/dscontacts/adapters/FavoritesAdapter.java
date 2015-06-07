package br.com.danielsan.dscontacts.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import br.com.danielsan.dscontacts.R;
import br.com.danielsan.dscontacts.adapters.holder.DrawerViewHolder;
import br.com.danielsan.dscontacts.adapters.holder.FavoritesViewHolder;

/**
 * Created by daniel on 07/06/15.
 */
public class FavoritesAdapter extends BaseAdapter {

    @Override
    public int getCount() {
        return 20;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FavoritesViewHolder favoritesViewHolder;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.item_favorites, null);
            favoritesViewHolder = new FavoritesViewHolder(convertView);
        } else
            favoritesViewHolder = (FavoritesViewHolder) convertView.getTag();

        favoritesViewHolder.updateView();

        return convertView;
    }

}
