package br.com.danielsan.dscontacts.activities;

import android.net.Uri;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.andexert.expandablelayout.library.ExpandableLayout;
import com.r0adkll.slidr.Slidr;

import java.util.List;

import br.com.danielsan.dscontacts.R;
import br.com.danielsan.dscontacts.custom.CancelDoneActionBar.OnCancelDoneActionBarListener;
import br.com.danielsan.dscontacts.fragments.NameSectionFragment;
import br.com.danielsan.dscontacts.fragments.OnSectionInteractionListener;
import br.com.danielsan.dscontacts.fragments.dialogs.OtherFieldsDialog.OnOtherFieldsDialogInteractionListener;
import br.com.danielsan.dscontacts.fragments.SectionWithTagFragment;
import br.com.danielsan.dscontacts.util.Metrics;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class AddContactActivity extends AppCompatActivity
        implements OnSectionInteractionListener,
        OnOtherFieldsDialogInteractionListener,
        OnCancelDoneActionBarListener {

    private Button mBtnAddField;
    private List<String> mOtherFieldsTitles;
    private List<Integer> mOtherFieldsTagsId;
    final private RotateAnimation mRotateAnimationLeft = new RotateAnimation(0f, -180f,
                                                                             RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                                                                             RotateAnimation.RELATIVE_TO_SELF, 0.5f);
    final private RotateAnimation mRotateAnimationRight = new RotateAnimation(-180f, 0f,
                                                                              RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                                                                              RotateAnimation.RELATIVE_TO_SELF, 0.5f);

    @InjectView(R.id.edt_txt_name)
    protected EditText mNameEdtTxt;
    @InjectView(R.id.edt_txt_first_name)
    protected EditText mFirstNameEdtTxt;
    @InjectView(R.id.edt_txt_middle_name)
    protected EditText mMiddleNameEdtTxt;
    @InjectView(R.id.edt_txt_last_name)
    protected EditText mLastNameEdtTxt;
    @InjectView(R.id.img_vw_expand_name)
    protected ImageView mExpandNameImgVw;
    @InjectView(R.id.expdb_lyt_name_info)
    protected ExpandableLayout mNameInfoExpdbLyt;
    @InjectView(R.id.clpsng_tlbr_lyt)
    protected CollapsingToolbarLayout mClpsngTlbrLyt;
    @InjectView(R.id.app_bar_lyt)
    protected AppBarLayout mAppBarLyt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        ButterKnife.inject(this);
        Slidr.attach(this);

        mRotateAnimationLeft.setDuration(200);
        mRotateAnimationRight.setDuration(200);
        mRotateAnimationLeft.setFillAfter(true);
        mRotateAnimationRight.setFillAfter(true);

        this.setSupportActionBar((Toolbar) this.findViewById(R.id.toolbar));
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mNameInfoExpdbLyt.setOnClickListener(null);
        mClpsngTlbrLyt.setTitle(this.getString(R.string.title_activity_add_contact));
        mClpsngTlbrLyt.setBackgroundColor(this.getResources().getColor(R.color.orange_500));
        mClpsngTlbrLyt.setContentScrimColor(this.getResources().getColor(R.color.orange_500));

        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) mAppBarLyt.getLayoutParams();
        layoutParams.height = Metrics.pxToDp(AddContactActivity.this, 260);
        mAppBarLyt.setLayoutParams(layoutParams);

        // To use toolbar instead of the ActionBar
//        Toolbar toolbar = (Toolbar) findViewById(R.id.m_action_bar);
//        setSupportActionBar(toolbar);

//        new CancelDoneActionBar(this);
//
//        mBtnAddField = (Button) findViewById(R.id.m_btn_add_field);
//        mBtnAddField.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                OtherFieldsDialog.newInstance((ArrayList<String>) mOtherFieldsTitles).show(getSupportFragmentManager(), "");
//            }
//        });
//
//        Resources resources = getResources();
//
//        TypedArray typedArray = resources.obtainTypedArray(R.array.other_fields_tags);
//        mOtherFieldsTitles = new ArrayList<>(Arrays.asList(resources.getStringArray(R.array.other_fields_titles)));
//        mOtherFieldsTagsId = new ArrayList<>();
//        int length = typedArray.length();
//        for (int i = 0; i < length; i++) {
//            mOtherFieldsTagsId.add(typedArray.getResourceId(i, -1));
//        }
//        typedArray.recycle();
//
//        addFragment(R.id.frm_lyt_group_section, GroupSectionFragment.newInstance());
//
//        addFragment(R.id.frm_lyt_name, NameSectionFragment.newInstance());
//        addFragment(SectionWithTagFragment.newInstance(getResources().getString(R.string.phone),
//                                                       resources.getStringArray(R.array.field_phone)));
//        addFragment(SectionWithTagFragment.newInstance(getResources().getString(R.string.email),
//                                                       resources.getStringArray(R.array.field_address_and_email)));
    }

    private void addFragment(Fragment fragment) {
//        this.addFragment(R.id.m_lnr_lyt_sections, fragment);
    }

    private void addFragment(int id, Fragment fragment) {
        getSupportFragmentManager().beginTransaction().add(id, fragment).commit();
    }

    @OnClick(R.id.img_vw_expand_name)
    protected void expandNameImgVwOnClick(View view) {
        RotateAnimation rotateAnimation;
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) mAppBarLyt.getLayoutParams();

        if (mNameInfoExpdbLyt.isOpened()) {
            rotateAnimation = mRotateAnimationRight;
            layoutParams.height = Metrics.pxToDp(AddContactActivity.this, 260);
            mNameInfoExpdbLyt.hide();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mNameEdtTxt.setVisibility(View.VISIBLE);
                }
            }, 150);
//                    buildHeaderName();
        } else {
            rotateAnimation = mRotateAnimationLeft;
            layoutParams.height = Metrics.pxToDp(AddContactActivity.this, 530);
            mNameInfoExpdbLyt.show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mNameEdtTxt.setVisibility(View.GONE);
                }
            }, 100);
//                    buildContentNames();
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
            case R.id.menu_favorite:
                item.setChecked(!item.isChecked());
                item.setIcon((item.isChecked()) ? R.drawable.ic_favorite_white : R.drawable.ic_favorite_border_white);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSectionInteractionListener(Uri uri) {
    }

    @Override
    public void onOtherFieldsDialogInteraction(String type, int position) {
        mOtherFieldsTitles.remove(position);
        int tagId = mOtherFieldsTagsId.remove(position);
        if (tagId == -1) {
            addFragment(SectionWithTagFragment.newInstance(type));
        } else {
            addFragment(SectionWithTagFragment.newInstance(type, getResources()
                                                                 .getStringArray(tagId)));
        }
        if (mOtherFieldsTagsId.size() == 0) {
            mBtnAddField.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDoneActionBarClicked() {
        onCancelActionBarClicked();
    }

    @Override
    public void onCancelActionBarClicked() {
        this.finish();
    }

}
