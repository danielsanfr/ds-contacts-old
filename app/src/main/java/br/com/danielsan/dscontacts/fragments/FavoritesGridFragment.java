package br.com.danielsan.dscontacts.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import br.com.danielsan.dscontacts.R;
import br.com.danielsan.dscontacts.activities.ContactInfoActivity;
import br.com.danielsan.dscontacts.adapters.FavoritesAdapter;
import br.com.danielsan.dscontacts.fragments.base.BFragment;
import butterknife.ButterKnife;
import butterknife.Bind;
import butterknife.OnItemClick;

/**
 * Created by daniel on 07/06/15.
 */
public class FavoritesGridFragment extends BFragment implements FavoritesAdapter.Listener {

    @Bind(R.id.grd_vw_favorites)
    protected GridView mFavoritesGridView;

    public static FavoritesGridFragment newInstance() {
        return new FavoritesGridFragment();
    }

    public FavoritesGridFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        ButterKnife.bind(this, view);

        mFavoritesGridView.setAdapter(new FavoritesAdapter(this));

        return view;
    }

    @OnItemClick(R.id.grd_vw_favorites)
    protected void favoritesGridViewOnClick() {
        mBaseActivity.addActivity(ContactInfoActivity.class);
    }

    @Override
    public void onItemClick(int position, FavoritesAdapter.Item item) {
        Log.d("========", String.valueOf(position) + " <=> " + String.valueOf(item));
    }
}
