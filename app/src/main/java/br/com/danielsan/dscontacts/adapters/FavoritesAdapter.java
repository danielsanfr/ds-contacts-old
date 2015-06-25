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

    public enum Item {
        Call(0),
        Chat(1),
        Edit(R.id.menu_edit),
        Delete(R.id.menu_delete);
        public int mItem;
        Item(int item) {
            mItem = item;
        }
        public static Item fromInteger(int item) {
        switch(item) {
        case 0:
            return Call;
        case 1:
            return Chat;
        case R.id.menu_edit:
            return Edit;
        case R.id.menu_delete:
            return Delete;
        }
        return null;
    }
    }

    private Listener mListener;

    public FavoritesAdapter(Listener listener) {
        mListener = listener;
    }

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
            favoritesViewHolder = new FavoritesViewHolder(convertView, mListener);
        } else
            favoritesViewHolder = (FavoritesViewHolder) convertView.getTag();

        favoritesViewHolder.updateView(position);

        return convertView;
    }

    public interface Listener {

        void onItemClick(int position, Item item);

    }

}
