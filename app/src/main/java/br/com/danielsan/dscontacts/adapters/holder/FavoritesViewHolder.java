package br.com.danielsan.dscontacts.adapters.holder;

import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import br.com.danielsan.dscontacts.R;
import br.com.danielsan.dscontacts.adapters.FavoritesAdapter;
import butterknife.ButterKnife;
import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by daniel on 07/06/15.
 */
public class FavoritesViewHolder  implements PopupMenu.OnMenuItemClickListener {

    private int mPosition;
    private PopupMenu mMorePopupMenu;
    private FavoritesAdapter.Listener mListener;

    @Bind(R.id.img_vw_call_favorite)
    protected ImageView mCallFavoriteImgVw;
    @Bind(R.id.img_vw_chat_favorite)
    protected ImageView mChatFavoriteImgVw;
    @Bind(R.id.img_vw_more_favorite)
    protected ImageView mMoreFavoriteImgVw;

    public FavoritesViewHolder(View view, FavoritesAdapter.Listener listener) {
        ButterKnife.bind(this, view);

        mListener      = listener;
        mMorePopupMenu = new PopupMenu(view.getContext(), mMoreFavoriteImgVw);
        mMorePopupMenu.inflate(R.menu.favorite);
        mMorePopupMenu.setOnMenuItemClickListener(this);

        view.setTag(this);
    }

    public void updateView(int position) {
        mPosition = position;
    }

    @OnClick(R.id.img_vw_call_favorite)
    protected void callFavoriteImgVwOnClick(View view) {
        mListener.onItemClick(mPosition, FavoritesAdapter.Item.Call);
    }

    @OnClick(R.id.img_vw_chat_favorite)
    protected void chatFavoriteImgVwOnClick(View view) {
        mListener.onItemClick(mPosition, FavoritesAdapter.Item.Chat);
    }

    @OnClick(R.id.img_vw_more_favorite)
    protected void moreFavoriteImgVwOnClick(View view) {
        mMorePopupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        mListener.onItemClick(mPosition, FavoritesAdapter.Item.fromInteger(item.getItemId()));
        return true;
    }
}
