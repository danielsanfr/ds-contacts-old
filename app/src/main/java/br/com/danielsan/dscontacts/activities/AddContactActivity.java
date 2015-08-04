package br.com.danielsan.dscontacts.activities;

import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.andexert.expandablelayout.library.ExpandableLayout;
import com.r0adkll.slidr.Slidr;

import java.util.ArrayList;

import br.com.danielsan.dscontacts.R;
import br.com.danielsan.dscontacts.fragments.add.contacts.fields.CommonFieldFragment;
import br.com.danielsan.dscontacts.fragments.add.contacts.fields.FieldFragment;
import br.com.danielsan.dscontacts.fragments.add.contacts.fields.GroupFieldFragment;
import br.com.danielsan.dscontacts.fragments.add.contacts.fields.PhotoFieldFragment;
import br.com.danielsan.dscontacts.fragments.add.contacts.fields.WithTagsFieldFragment;
import br.com.danielsan.dscontacts.fragments.add.contacts.fields.WorkFieldFragment;
import br.com.danielsan.dscontacts.fragments.base.BFragment;
import br.com.danielsan.dscontacts.fragments.dialogs.ListDialogFragment;
import br.com.danielsan.dscontacts.model.Contact;
import br.com.danielsan.dscontacts.model.Name;
import br.com.danielsan.dscontacts.model.base.Field;
import br.com.danielsan.dscontacts.model.serializer.NameSerializer;
import br.com.danielsan.dscontacts.util.FragmentsTransaction;
import br.com.danielsan.dscontacts.widgets.util.SimpleTextWatcher;
import butterknife.ButterKnife;
import butterknife.Bind;
import butterknife.OnClick;

public class AddContactActivity extends BaseActivity
        implements DialogInterface.OnClickListener, BFragment.OnCreationCompleteListener {

    private Menu mMenu;
    private String mDefaultTitle;
    private boolean mNameChanged = false;
    private ArrayList<Field> mFields;
    private ArrayList<String> mFieldTitles;
    private ArrayList<FieldFragment> mFieldFragments;
    private NameEdtTxtDelayRunnable mNameEdtTxtDelayRunnable;
    private final Handler mNameEdtTxtDelayHandler = new Handler();
    private final RotateAnimation mRotateAnimationLeft = new RotateAnimation(0f, -180f,
                                                                             RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                                                                             RotateAnimation.RELATIVE_TO_SELF, 0.5f);
    private final RotateAnimation mRotateAnimationRight = new RotateAnimation(-180f, 0f,
                                                                              RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                                                                              RotateAnimation.RELATIVE_TO_SELF, 0.5f);

    @Bind(R.id.vw_bg_tl_bar)
    protected View mBgTlBarVw;
    @Bind(R.id.edt_txt_name)
    protected EditText mNameEdtTxt;
    @Bind(R.id.edt_txt_first_name)
    protected EditText mFirstNameEdtTxt;
    @Bind(R.id.edt_txt_middle_name)
    protected EditText mMiddleNameEdtTxt;
    @Bind(R.id.edt_txt_last_name)
    protected EditText mLastNameEdtTxt;
    @Bind(R.id.img_vw_expand_name)
    protected ImageView mExpandNameImgVw;
    @Bind(R.id.expdb_lyt_name_info)
    protected ExpandableLayout mNameInfoExpdbLyt;
    @Bind(R.id.clpsng_tlbr_lyt)
    protected CollapsingToolbarLayout mClpsngTlbrLyt;
    @Bind(R.id.app_bar_lyt)
    protected AppBarLayout mAppBarLyt;
    @Bind(R.id.flt_act_btn_add_field)
    protected FloatingActionButton mAddFieldFltActBtn;
    @Bind(R.id.btn_add_organization)
    protected Button mAddOrganizationBtn;

    @Override
    protected void onInitBaseAttributes() {
        super.onInitBaseAttributes();

        mDefaultTitle = this.getString(R.string.title_activity_add_contact);
        mFields = new ArrayList<>(9);
        mFieldTitles = new ArrayList<>(9);
        mFieldFragments = new ArrayList<>();

        String modelPackage = this.getString(R.string.model_package);
        String[] fieldsNames = this.getResources().getStringArray(R.array.fields);
        for (String fieldName : fieldsNames) {
            try {
                Field field = (Field) Class.forName(modelPackage + fieldName).newInstance();
                mFieldTitles.add(this.getString(field.getTitleRes()));
                mFields.add(field);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        Slidr.attach(this);

        mRotateAnimationLeft.setDuration(200);
        mRotateAnimationRight.setDuration(200);
        mRotateAnimationLeft.setFillAfter(true);
        mRotateAnimationRight.setFillAfter(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);

        mNameInfoExpdbLyt.setOnClickListener(null);
        mClpsngTlbrLyt.setTitle(mDefaultTitle);
        mBgTlBarVw.setBackgroundColor(this.getResources().getColor(R.color.orange_500));
        mClpsngTlbrLyt.setContentScrimColor(this.getResources().getColor(R.color.orange_500));
        mAddFieldFltActBtn.setBackgroundTintList(ColorStateList.valueOf(this.getResources().getColor(R.color.orange_500)));

        mNameEdtTxtDelayRunnable = new NameEdtTxtDelayRunnable(mNameEdtTxt);

        SimpleTextWatcher namePartsTextWatcher = new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mNameChanged = false;

                String contactName = buildHeaderName();
                if (contactName.isEmpty())
                    contactName = mDefaultTitle;

                mClpsngTlbrLyt.setTitle(contactName);
            }
        };
        mFirstNameEdtTxt.addTextChangedListener(namePartsTextWatcher);
        mMiddleNameEdtTxt.addTextChangedListener(namePartsTextWatcher);
        mLastNameEdtTxt.addTextChangedListener(namePartsTextWatcher);

        mNameEdtTxt.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mNameChanged = true;

                String name = String.valueOf(s);
                if (name.isEmpty())
                    name = mDefaultTitle;

                mClpsngTlbrLyt.setTitle(name);
            }
        });

        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) mAppBarLyt.getLayoutParams();
        layoutParams.height = (int) this.getResources().getDimension(R.dimen.name_info_collapsing);
        mAppBarLyt.setLayoutParams(layoutParams);

        if (savedInstanceState == null) {
            this.addFragment(PhotoFieldFragment.newInstance());
            this.addField(0);
            this.addField(1);
            FragmentsTransaction.add(this, R.id.frm_lyt_field_group, GroupFieldFragment.newInstance());
        }
    }

    @Override
    protected int getContentViewRes() {
        return R.layout.activity_add_contact;
    }

    @Override
    protected int getBaseContainerId() {
        return R.id.lnr_lyt_fields;
    }

    @OnClick(R.id.btn_add_organization)
    protected void addOrganizationBtnOnClick(View view) {
        FragmentsTransaction.add(AddContactActivity.this, R.id.frm_lyt_field_work, WorkFieldFragment.newInstance());
    }

    @OnClick(R.id.flt_act_btn_add_field)
    protected void addFieldFltActBtnOnClick(View view) {
        ListDialogFragment.newInstance(R.string.add_field, mFieldTitles).show(getSupportFragmentManager(), "");
    }

    @OnClick(R.id.img_vw_expand_name)
    protected void expandNameImgVwOnClick(View view) {
        RotateAnimation rotateAnimation;
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) mAppBarLyt.getLayoutParams();

        if (mNameInfoExpdbLyt.isOpened()) {
            rotateAnimation = mRotateAnimationRight;
            layoutParams.height = (int) this.getResources().getDimension(R.dimen.name_info_collapsing);
            mNameInfoExpdbLyt.hide();
            this.nameEdtTxtChangeVisibility(View.VISIBLE, 150);
            mNameEdtTxt.setText(this.buildHeaderName());
        } else {
            rotateAnimation = mRotateAnimationLeft;
            layoutParams.height = (int) this.getResources().getDimension(R.dimen.name_info_expanded);
            mNameInfoExpdbLyt.show();
            this.nameEdtTxtChangeVisibility(View.GONE, 100);
            this.buildContentNames();
        }
        mExpandNameImgVw.startAnimation(rotateAnimation);
        mAppBarLyt.setLayoutParams(layoutParams);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.add_contact, menu);
        mMenu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
            case R.id.menu_confirm:
                Contact contact = new Contact();
                int i = 0, size = (mFieldFragments.size() > 3) ? 3 : mFieldFragments.size();
                for (; i < size; ++i) {
                    if (mFieldFragments.get(i) instanceof CommonFieldFragment)
                        break;
                    mFieldFragments.get(i).updatedContact(contact);
                }

                if (mMenu != null && mMenu.findItem(R.id.menu_favorite) != null)
                    contact.setFavorite(mMenu.findItem(R.id.menu_favorite).isChecked());
                contact.setName((new NameSerializer()).deserialize(mNameEdtTxt.getText().toString()));
                contact.save();

                for (size = mFieldFragments.size(); i < size; ++i)
                    mFieldFragments.get(i).updatedContact(contact);
                this.finish();
                break;
            case R.id.menu_favorite:
                item.setChecked(!item.isChecked());
                item.setIcon(item.isChecked() ? R.drawable.ic_favorite_white : R.drawable.ic_favorite_border_white);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onComplete(BFragment fragment) {
        if (!(fragment instanceof FieldFragment))
            return;

        FieldFragment fieldFragment = (FieldFragment) fragment;
        if (fieldFragment instanceof CommonFieldFragment)
            mFieldFragments.add(fieldFragment);
        else
            mFieldFragments.add(0, fieldFragment);

        if (fieldFragment instanceof WorkFieldFragment)
            mAddOrganizationBtn.setVisibility(View.GONE);

        int index = mFieldTitles.indexOf(this.getString(fieldFragment.getTitleRes()));
        if (index > -1) {
            mFields.remove(index);
            mFieldTitles.remove(index);
        }

        if (mFields.size() == 0) {
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) mAddFieldFltActBtn.getLayoutParams();
            layoutParams.setBehavior(null);
            mAddFieldFltActBtn.setLayoutParams(layoutParams);
            mAddFieldFltActBtn.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int which) {
        if (which >= 0)
            this.addField(which);
    }

    private void addField(int index) {
        if (mFields.size() == 0)
            return;

        Field field = mFields.get(index);
        FieldFragment fieldFragment;
        if (field.getTagsRes() == -1) {
            fieldFragment = CommonFieldFragment.newInstance(field);
        } else {
            fieldFragment = WithTagsFieldFragment.newInstance(field);
        }
        this.addFragment(fieldFragment);
    }

    private void nameEdtTxtChangeVisibility(int visibility, int delay) {
        mNameEdtTxtDelayRunnable.setVisibility(visibility);
        mNameEdtTxtDelayHandler.postDelayed(mNameEdtTxtDelayRunnable, delay);
    }

    private String buildHeaderName() {
        String headerName = (new NameSerializer()).serialize(mFirstNameEdtTxt.getText().toString().trim(),
                                                             mMiddleNameEdtTxt.getText().toString().trim(),
                                                             mLastNameEdtTxt.getText().toString().trim());

        mNameChanged = false;
        return headerName;
    }

    private void buildContentNames() {
        if (!mNameChanged)
            return;

        mFirstNameEdtTxt.setText("");
        mMiddleNameEdtTxt.setText("");
        mLastNameEdtTxt.setText("");

        Name name = (new NameSerializer()).deserialize(mNameEdtTxt.getText().toString());

        mFirstNameEdtTxt.setText(name.getName());
        mMiddleNameEdtTxt.setText(name.getMiddleName());
        mLastNameEdtTxt.setText(name.getLastName());
    }

    private static class NameEdtTxtDelayRunnable implements Runnable {
        private View mView;
        private int mVisibility = View.VISIBLE;
        public NameEdtTxtDelayRunnable(View view) {
            mView = view;
        }
        public void setVisibility(int visibility) {
            mVisibility = visibility;
        }
        @Override
        public void run() {
            mView.setVisibility(mVisibility);
        }
    }

}
