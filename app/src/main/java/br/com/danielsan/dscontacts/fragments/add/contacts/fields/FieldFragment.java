package br.com.danielsan.dscontacts.fragments.add.contacts.fields;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.danielsan.dscontacts.R;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by daniel on 28/06/15.
 */
public abstract class FieldFragment extends Fragment {

    @InjectView(R.id.txt_vw_field_title)
    protected TextView pTitleTxtVw;
    @InjectView(R.id.img_vw_field_title)
    protected ImageView pTitleImgVw;
    @InjectView(R.id.cd_vw_field_content)
    protected CardView pContentCdVw;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_field, container, false);
        ButterKnife.inject(this, view);

        View.inflate(pContentCdVw.getContext(), this.contentResId(), pContentCdVw);

        return view;
    }

    @LayoutRes
    protected abstract int contentResId();

}
