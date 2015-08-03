package br.com.danielsan.dscontacts.fragments.add.contacts.fields;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.danielsan.dscontacts.R;
import br.com.danielsan.dscontacts.fragments.base.BFragment;
import br.com.danielsan.dscontacts.model.Contact;
import butterknife.ButterKnife;
import butterknife.Bind;

/**
 * Created by daniel on 28/06/15.
 */
public abstract class FieldFragment extends BFragment {

    protected static final String TITLE_RES = "title_res";
    protected static final String TITLE_IMAGE_RES = "title_image_res";

    @Bind(R.id.txt_vw_field_title)
    protected TextView pTitleTxtVw;
    @Bind(R.id.img_vw_field_title)
    protected ImageView pTitleImgVw;
    @Bind(R.id.cd_vw_field_content)
    protected CardView pContentCdVw;

    @StringRes
    protected int pTitleRes;
    @DrawableRes
    protected int pTitleImageRes;

    protected static Bundle makeBaseBundle(@StringRes int titleRes, @DrawableRes int imageTitleRes) {
        Bundle bundle = new Bundle();
        bundle.putInt(TITLE_RES, titleRes);
        bundle.putInt(TITLE_IMAGE_RES, imageTitleRes);
        return bundle;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = this.getArguments();
        if (arguments == null || !arguments.containsKey(TITLE_RES) || !arguments.containsKey(TITLE_IMAGE_RES))
            throw new RuntimeException(this.toString() + " must use the bundle made by the method FieldFragment.makeBaseBundle");

        pTitleRes = arguments.getInt(TITLE_RES);
        pTitleImageRes = arguments.getInt(TITLE_IMAGE_RES);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_field, container, false);

        pContentCdVw = ButterKnife.findById(view, R.id.cd_vw_field_content);
        View.inflate(pContentCdVw.getContext(), this.contentResId(), pContentCdVw);

        ButterKnife.bind(this, view);

        pTitleTxtVw.setText(pTitleRes);
        pTitleImgVw.setImageResource(pTitleImageRes);

        return view;
    }

    @StringRes
    public int getTitleRes() {
        return pTitleRes;
    }

    @DrawableRes
    public int getTitleImageRes() {
        return pTitleImageRes;
    }

    @LayoutRes
    protected abstract int contentResId();

    public abstract void updatedContact(Contact contact);

}
