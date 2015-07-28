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

import java.lang.reflect.InvocationTargetException;

import br.com.danielsan.dscontacts.R;
import br.com.danielsan.dscontacts.model.Contact;
import br.com.danielsan.dscontacts.model.base.Field;
import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by daniel on 28/06/15.
 */
public class CommonFieldFragment extends FieldFragment implements View.OnClickListener {

    protected static final String TITLE = "title";
    protected static final String IMAGE_TITLE = "image_title";

    private ViewGroup mViewGroup;
    private LayoutInflater mLayoutInflater;
    protected SparseArray<View> pSubFieldViews;
    protected Class<? extends Field> mFieldClass;

    @StringRes
    protected int pTitleRes;
    @DrawableRes
    protected int pImageTitleRes;

    @Nullable
    @Bind(R.id.lnr_lyt_fields_container)
    protected LinearLayout pFieldsContainerLnrLyt;

    public static CommonFieldFragment newInstance(Field field) {
        CommonFieldFragment fragment = new CommonFieldFragment();
        fragment.setFieldClass(field.getClass());
        fragment.setArguments(CommonFieldFragment.buildBundle(field.getTitleRes(), field.getImageTitleRes()));
        return fragment;
    }

    protected static Bundle buildBundle(@StringRes int titleRes, @DrawableRes int imageTitleRes) {
        Bundle bundle = new Bundle();
        bundle.putInt(TITLE, titleRes);
        bundle.putInt(IMAGE_TITLE, imageTitleRes);
        return bundle;
    }

    public CommonFieldFragment() {}

    public void setFieldClass(Class<? extends Field> fieldClass) {
        mFieldClass = fieldClass;
    }

    @LayoutRes
    protected int getSubFieldLayoutRes() {
        return R.layout.component_contact_subfield;
    }

    @Override
    protected int contentResId() {
        return R.layout.fragment_base_field;
    }

    @Override
    protected void updatedContact(Contact contact) {
        for (int index = 0, size = pSubFieldViews.size(); index < size ; ++index) {
            try {
                Field field = mFieldClass.getDeclaredConstructor(Contact.class).newInstance(contact);
                this.updateField(field, pSubFieldViews.get(pSubFieldViews.keyAt(index)));
                contact.addField(field);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (java.lang.InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    protected void updateField(Field field, View view) {
        field.setContent(((EditText) view.findViewById(R.id.edt_txt_item)).getText().toString());
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
        this.addSubFieldOnClick(view.findViewById(R.id.btn_add_sub_field));
        pTitleTxtVw.setText(pTitleRes);
        pTitleImgVw.setImageResource(pImageTitleRes);
    }

    @Nullable
    @OnClick(R.id.btn_add_sub_field)
    protected void addSubFieldOnClick(View view) {
        View subView = mLayoutInflater.inflate(this.getSubFieldLayoutRes(), mViewGroup, false);

        EditText edtTxtItem = (EditText) subView.findViewById(R.id.edt_txt_item);
        ImageView imgVwRemove = (ImageView) subView.findViewById(R.id.img_vw_remove);

        edtTxtItem.setHint(pTitleRes);
        imgVwRemove.setTag(pSubFieldViews.size());
        imgVwRemove.setOnClickListener(this);

        pSubFieldViews.append(pSubFieldViews.size(), subView);

        if (pFieldsContainerLnrLyt != null)
            pFieldsContainerLnrLyt.addView(subView);
    }

    @Override
    public void onClick(View view) {
        if (pSubFieldViews.size() > 1 && pFieldsContainerLnrLyt != null) {
            pSubFieldViews.delete((int) view.getTag());
            pFieldsContainerLnrLyt.removeView((View) view.getParent());
        }
    }

}
