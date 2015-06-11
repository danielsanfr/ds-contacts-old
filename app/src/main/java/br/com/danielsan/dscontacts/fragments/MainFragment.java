package br.com.danielsan.dscontacts.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.getbase.floatingactionbutton.FloatingActionButton;

import br.com.danielsan.dscontacts.R;
import br.com.danielsan.dscontacts.activities.AddContactActivity;
import br.com.danielsan.dscontacts.activities.MainActivity;
import br.com.danielsan.dscontacts.adapters.MainFragmentPagerAdapter;
import br.com.danielsan.dscontacts.fragments.base.MasterFragment;
import br.com.danielsan.dscontacts.misc.fab.FabHidden;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by daniel on 06/06/15.
 */
public class MainFragment extends MasterFragment {

    public static final String POSITION = "position";

    @InjectView(R.id.vw_pgr_main)
    protected ViewPager mMainViewPager;
    @InjectView(R.id.fab_add_contact)
    protected FloatingActionButton mFabAddContact;
    @InjectView(R.id.pg_sld_tab_stp_main)
    protected PagerSlidingTabStrip mPagerSlidingTabStrip;

    private FabHidden mFabHidden;

    public static MainFragment newInstance(int position) {
        MainFragment fragment = new MainFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(POSITION, position);
        fragment.setArguments(bundle);
        return fragment;
    }

    public MainFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmnet_main, container, false);
        ButterKnife.inject(this, view);

//        mFabHidden = new FabHidden(mFabAddContact, mLstVw);
        mMainViewPager.setAdapter(new MainFragmentPagerAdapter(this));
        mPagerSlidingTabStrip.setViewPager(mMainViewPager);
        mPagerSlidingTabStrip.setOnPageChangeListener((MainFragmentPagerAdapter) mMainViewPager.getAdapter());

        Bundle arguments = this.getArguments();
        if (arguments != null && arguments.containsKey(POSITION))
            mMainViewPager.setCurrentItem(arguments.getInt(POSITION));
        else
            mMainViewPager.setCurrentItem(0);

        this.setCurrentColor();

        return view;
    }

    @OnClick(R.id.fab_add_contact)
    protected void mFabAddContactOnClick() {
        mBaseActivity.addActivity(AddContactActivity.class);
    }

    public void setCurrentColor() {
        int position = mMainViewPager.getCurrentItem();
        MainFragmentPagerAdapter mainFragmentPagerAdapter = (MainFragmentPagerAdapter) mMainViewPager.getAdapter();

        this.changeColor(mainFragmentPagerAdapter.getColor(position));
        this.changFabPressedColor(mainFragmentPagerAdapter.getPressedColor(position));
    }

    public void changFabPressedColor(Integer newColor) {
        mFabAddContact.setColorPressed(newColor);
    }

    public void changeColor(Integer newColor) {
        mFabAddContact.setColorNormal(newColor);
        mPagerSlidingTabStrip.setBackgroundColor(newColor);
        ((MainActivity) mBaseActivity).changeColor(newColor);
    }

}