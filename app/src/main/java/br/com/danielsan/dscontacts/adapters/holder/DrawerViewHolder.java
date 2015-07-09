package br.com.danielsan.dscontacts.adapters.holder;

import android.view.View;
import android.widget.TextView;

import br.com.danielsan.dscontacts.R;
import butterknife.ButterKnife;
import butterknife.Bind;

/**
 * Created by daniel on 06/06/15.
 */
public class DrawerViewHolder {

    @Bind(R.id.txt_vw_item_title)
    protected TextView mTxtVwItemTitle;

    public DrawerViewHolder(View view) {
        ButterKnife.bind(this, view);
        view.setTag(this);
    }

    public void updateView(String title) {
        mTxtVwItemTitle.setText(title);
    }

}
