package br.com.danielsan.dscontacts.activities;

import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.os.Handler;
import android.support.annotation.ArrayRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.EditText;
import android.widget.ImageView;

import com.andexert.expandablelayout.library.ExpandableLayout;
import com.r0adkll.slidr.Slidr;

import java.util.ArrayList;
import java.util.List;

import br.com.danielsan.dscontacts.R;
import br.com.danielsan.dscontacts.fragments.add.contacts.fields.CommonFieldFragment;
import br.com.danielsan.dscontacts.fragments.add.contacts.fields.GroupFieldFragment;
import br.com.danielsan.dscontacts.fragments.add.contacts.fields.PhotoFieldFragment;
import br.com.danielsan.dscontacts.fragments.add.contacts.fields.WithTagsFieldFragment;
import br.com.danielsan.dscontacts.fragments.add.contacts.fields.WorkFieldFragment;
import br.com.danielsan.dscontacts.fragments.dialogs.OtherFieldsDialog;
import br.com.danielsan.dscontacts.model.Name;
import br.com.danielsan.dscontacts.model.serializer.NameSerializer;
import br.com.danielsan.dscontacts.util.FragmentsTransaction;
import br.com.danielsan.dscontacts.widgets.util.SimpleTextWatcher;
import butterknife.ButterKnife;
import butterknife.Bind;
import butterknife.OnClick;

public class AddContactActivity extends BaseActivity
        implements OtherFieldsDialog.Listener {

    private List<Trio> mFields;
    private String mDefaultTitle;
    private boolean mNameChanged = false;
    private ArrayList<String> mFieldTitles;
    private NameEdtTxtDelayRunnable mNameEdtTxtDelayRunnable;
    private final Handler mNameEdtTxtDelayHandler = new Handler();
    private final RotateAnimation mRotateAnimationLeft = new RotateAnimation(0f, -180f,
                                                                             RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                                                                             RotateAnimation.RELATIVE_TO_SELF, 0.5f);
    private final RotateAnimation mRotateAnimationRight = new RotateAnimation(-180f, 0f,
                                                                              RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                                                                              RotateAnimation.RELATIVE_TO_SELF, 0.5f);

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_add_contact);
        ButterKnife.bind(this);
        Slidr.attach(this);

        mDefaultTitle = this.getString(R.string.title_activity_add_contact);

        mRotateAnimationLeft.setDuration(200);
        mRotateAnimationRight.setDuration(200);
        mRotateAnimationLeft.setFillAfter(true);
        mRotateAnimationRight.setFillAfter(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);

        mNameInfoExpdbLyt.setOnClickListener(null);
        mClpsngTlbrLyt.setTitle(mDefaultTitle);
        mClpsngTlbrLyt.setBackgroundColor(this.getResources().getColor(R.color.orange_500));
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

        mFields = new ArrayList<>(9);
        mFieldTitles = new ArrayList<>(9);
        TypedArray typedArray = this.getResources().obtainTypedArray(R.array.fields);
        for (int i = 0, length = typedArray.length(); i < length; i+=3) {
            Trio trio = new Trio();
            trio.title = typedArray.getResourceId(i, -1);
            trio.imageTitle = typedArray.getResourceId(i + 1, -1);
            trio.tags = typedArray.getResourceId(i + 2, -1);
            mFields.add(trio);
            mFieldTitles.add(this.getString(trio.title));
        }
        typedArray.recycle();

        this.addFragment(new PhotoFieldFragment());
        this.addField(0);
        this.addField(0);
        FragmentsTransaction.add(this, R.id.frm_lyt_field_group, new GroupFieldFragment());
        this.findViewById(R.id.btn_add_organization).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setVisibility(View.GONE);
                FragmentsTransaction.add(AddContactActivity.this, R.id.frm_lyt_field_work, new WorkFieldFragment());
            }
        });
    }

    @Override
    protected int getMasterContainer() {
        return R.id.lnr_lyt_fields;
    }

    @OnClick(R.id.flt_act_btn_add_field)
    protected void addFieldFltActBtnOnClick(View view) {
        OtherFieldsDialog.newInstance(mFieldTitles).show(getSupportFragmentManager(), "");
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
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_confirm:
                break;
            case R.id.menu_favorite:
                item.setChecked(!item.isChecked());
                item.setIcon((item.isChecked()) ? R.drawable.ic_favorite_white : R.drawable.ic_favorite_border_white);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSelection(OtherFieldsDialog dialog, View view, int position, CharSequence text) {
        this.addField(position);
    }

    private void addField(int index) {
        Trio trio = mFields.get(index);
        if (trio.tags == -1) {
            this.addFragment(CommonFieldFragment.newInstance(trio.title, trio.imageTitle));
        } else {
            this.addFragment(WithTagsFieldFragment.newInstance(trio.title, trio.imageTitle, trio.tags));
        }
        mFields.remove(index);
        mFieldTitles.remove(index);
        if (mFields.size() == 0) {
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) mAddFieldFltActBtn.getLayoutParams();
            layoutParams.setBehavior(null);
            mAddFieldFltActBtn.setLayoutParams(layoutParams);
            mAddFieldFltActBtn.setVisibility(View.GONE);
        }
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

    private static class Trio {
        @StringRes
        public int title;
        @DrawableRes
        public int imageTitle;
        @ArrayRes
        public int tags;
    }

}
