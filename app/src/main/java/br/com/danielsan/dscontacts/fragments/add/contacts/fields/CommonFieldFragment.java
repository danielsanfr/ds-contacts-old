package br.com.danielsan.dscontacts.fragments.add.contacts.fields;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import br.com.danielsan.dscontacts.R;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.Optional;

/**
 * Created by daniel on 28/06/15.
 */
public class CommonFieldFragment extends FieldFragment implements View.OnClickListener {

    protected static final String TITLE = "title";
    protected static final String IMAGE_TITLE = "image_title";

    private ViewGroup mViewGroup;
    private LayoutInflater mLayoutInflater;
    protected SparseArray<View> pSubFieldViews;

    @StringRes
    protected int pTitleRes;
    @DrawableRes
    protected int pImageTitleRes;

    @Optional
    @InjectView(R.id.lnr_lyt_fields_container)
    protected LinearLayout pFieldsContainerLnrLyt;

    public static CommonFieldFragment newInstance(@StringRes int titleRes, @DrawableRes int imageTitleRes) {
        CommonFieldFragment fragment = new CommonFieldFragment();
        fragment.setArguments(CommonFieldFragment.buildBundle(titleRes, imageTitleRes));
        return fragment;
    }

    protected static Bundle buildBundle(@StringRes int titleRes, @DrawableRes int imageTitleRes) {
        Bundle bundle = new Bundle();
        bundle.putInt(TITLE, titleRes);
        bundle.putInt(IMAGE_TITLE, imageTitleRes);
        return bundle;
    }

    public CommonFieldFragment() {}

    @LayoutRes
    protected int getSubFieldLayoutRes() {
        return R.layout.component_contact_subfield;
    }

    @Override
    protected int contentResId() {
        return R.layout.fragment_base_field;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pSubFieldViews = new SparseArray<>();
        Bundle bundle = this.getArguments();
        if (bundle != null && bundle.containsKey(TITLE)) {
            pTitleRes = bundle.getInt(TITLE);
            pImageTitleRes = bundle.getInt(IMAGE_TITLE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mViewGroup = container;
        mLayoutInflater = inflater;
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, view);
        this.addSubFieldOnClick(view.findViewById(R.id.btn_add_sub_field));
        pTitleTxtVw.setText(pTitleRes);
        pTitleImgVw.setImageResource(pImageTitleRes);
    }

    @Optional
    @Nullable
    @OnClick(R.id.btn_add_sub_field)
    protected void addSubFieldOnClick(View view) {
        View subView = mLayoutInflater.inflate(this.getSubFieldLayoutRes(), mViewGroup, false);

        EditText edtTxtItem = (EditText) subView.findViewById(R.id.edt_txt_item);
        ImageView imgVwRemove = (ImageView) subView.findViewById(R.id.img_vw_remove);

        imgVwRemove.setTag(pSubFieldViews.size());
        imgVwRemove.setOnClickListener(this);

        edtTxtItem.setHint(pTitleRes);

        pSubFieldViews.append(pSubFieldViews.size(), subView);

        pFieldsContainerLnrLyt.addView(subView);
    }

    @Override
    public void onClick(View view) {
        if (pSubFieldViews.size() > 1) {
            pSubFieldViews.delete((int) view.getTag());
            pFieldsContainerLnrLyt.removeView((View) view.getParent());
        }
    }
}
