package br.com.danielsan.dscontacts.fragments.add.contacts.fields;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import br.com.danielsan.dscontacts.R;
import br.com.danielsan.dscontacts.model.Contact;
import br.com.danielsan.dscontacts.model.base.Field;
import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by daniel on 28/06/15.
 */
public class CommonFieldFragment extends FieldFragment implements View.OnClickListener {

    protected static final String CLASS = "class";

    protected int pSubFieldCounter;
    private ViewGroup mViewGroup;
    private LayoutInflater mLayoutInflater;
    protected List<Field> pDeleteFields;
    protected SparseArray<View> pSubFieldViews;
    protected Class<? extends Field> pFieldClass;

    @Nullable
    @Bind(R.id.lnr_lyt_fields_container)
    protected LinearLayout pFieldsContainerLnrLyt;

    public static CommonFieldFragment newInstance(Field field) {
        CommonFieldFragment fragment = new CommonFieldFragment();
        fragment.setArguments(CommonFieldFragment.buildBundle(field));
        return fragment;
    }

    protected static Bundle buildBundle(Field field) {
        Bundle bundle = FieldFragment.makeBaseBundle(field.getTitleRes(), field.getImageTitleRes());
        bundle.putSerializable(CLASS, field.getClass());
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
    public void updatedContact(Contact contact) {
        for (Field field : pDeleteFields)
            field.delete();

        View subView;
        for (int index = 0, size = pSubFieldViews.size(); index < size ; ++index) {
            subView = pSubFieldViews.get(pSubFieldViews.keyAt(index));
            Field field = (Field) subView.getTag();
            this.updateField(field, subView);
            field.setContact(contact);
            field.save();
        }
    }

    protected void updateField(Field field, View view) {
        field.setContent(((EditText) view.findViewById(R.id.edt_txt_item)).getText().toString());
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pSubFieldCounter = 0;
        pDeleteFields = new ArrayList<>();
        pSubFieldViews = new SparseArray<>();
        Bundle bundle = this.getArguments();
        if (bundle != null && bundle.containsKey(CLASS))
            pFieldClass = (Class<? extends Field>) bundle.getSerializable(CLASS);
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
        this.addSubFieldOnClick(view.findViewById(R.id.btn_add_sub_field));
        pTitleTxtVw.setText(pTitleRes);
        pTitleImgVw.setImageResource(pTitleImageRes);
    }

    @Nullable
    @OnClick(R.id.btn_add_sub_field)
    protected void addSubFieldOnClick(View view) {
        View subView = mLayoutInflater.inflate(this.getSubFieldLayoutRes(), mViewGroup, false);

        try {
            subView.setTag(pFieldClass.newInstance());
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        EditText edtTxtItem = (EditText) subView.findViewById(R.id.edt_txt_item);
        ImageView imgVwRemove = (ImageView) subView.findViewById(R.id.img_vw_remove);

        edtTxtItem.setHint(pTitleRes);
        imgVwRemove.setTag(pSubFieldCounter);
        imgVwRemove.setOnClickListener(this);

        pSubFieldViews.append(pSubFieldCounter++, subView);

        if (pFieldsContainerLnrLyt != null)
            pFieldsContainerLnrLyt.addView(subView);
    }

    @Override
    public void onClick(View view) {
        if (pSubFieldViews.size() > 1 && pFieldsContainerLnrLyt != null) {
            int key = (int) view.getTag();
            View viewToDelete = pSubFieldViews.get(key);
            Field fieldToDelete = (Field) viewToDelete.getTag();
            if (fieldToDelete != null && fieldToDelete.getId() != null)
                pDeleteFields.add(fieldToDelete);
            pSubFieldViews.delete(key);
            pFieldsContainerLnrLyt.removeView(viewToDelete);
        }
    }

}
