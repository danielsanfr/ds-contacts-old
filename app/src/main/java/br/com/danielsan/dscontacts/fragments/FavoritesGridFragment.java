package br.com.danielsan.dscontacts.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import br.com.danielsan.dscontacts.R;
import br.com.danielsan.dscontacts.adapters.FavoritesAdapter;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

/**
 * Created by daniel on 07/06/15.
 */
public class FavoritesGridFragment extends Fragment {

    public static FavoritesGridFragment newInstance() {
        return new FavoritesGridFragment();
    }

    public FavoritesGridFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        GridView gridView = (GridView) inflater.inflate(R.layout.fragment_favorites, container, false);
        ButterKnife.inject(this, gridView);

        gridView.setAdapter(new FavoritesAdapter());


        return gridView;
    }

    @OnItemClick(R.id.grd_vw_favorites)
    protected void favoritesGridViewOnClick() {
    }

}
