package br.com.danielsan.dscontacts.adapters.holder;

import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by daniel on 07/06/15.
 */
public class FavoritesViewHolder {

    public FavoritesViewHolder(View view) {
        ButterKnife.inject(this, view);
        view.setTag(this);
    }

    public void updateView() {
    }

}
