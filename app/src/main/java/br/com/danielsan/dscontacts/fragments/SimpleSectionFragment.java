package br.com.danielsan.dscontacts.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import br.com.danielsan.dscontacts.R;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link br.com.danielsan.dscontacts.fragments.OnSectionInteractionListener} interface
 * to handle interaction events.
 * Use the {@link br.com.danielsan.dscontacts.fragments.SimpleSectionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SimpleSectionFragment extends Fragment {
    protected static final String ARG_TITLE = "title";

    protected String mTitle;

    protected ViewGroup mViewGroup;
    protected LayoutInflater mLayoutInflater;
    protected LinearLayout mLnrLytContainer;
    protected TextView mAddNewTextView;

    protected Integer mCounterSectionItems = 0;
    protected Map<Integer, Map<Integer, View>> mSectionItems;

    protected OnSectionInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param title Parameter 1.
     * @return A new instance of fragment SectionFragment.
     */
    public static SimpleSectionFragment newInstance(String title) {
        SimpleSectionFragment fragment = new SimpleSectionFragment();
        fragment.setArguments(buildBundle(title));
        return fragment;
    }

    protected static Bundle buildBundle(String title) {
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        return args;
    }

    public SimpleSectionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSectionItems = new HashMap<>();
        if (getArguments() != null) {
            mTitle = getArguments().getString(ARG_TITLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mLayoutInflater = inflater;
        mViewGroup = container;

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_section_contact, container, false);

        mLnrLytContainer = (LinearLayout) view.findViewById(R.id.m_lnr_lyt_container);
        mAddNewTextView = (TextView) view.findViewById(R.id.txt_vw_add_new);
        ((TextView) view.findViewById(R.id.txt_vw_title)).setText(mTitle);

        addContactItem();

        mAddNewTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addContactItem();
            }
        });

        return view;
    }

    protected View addContactItem() {
        return addContactItem(R.layout.component_section_contact_item);
    }

    protected View addContactItem(int layout) {
        View view = mLayoutInflater.inflate(layout, mViewGroup, false);

        EditText edtTxtItem = (EditText) view.findViewById(R.id.edt_txt_item);
        ImageView imgVwRemove = (ImageView) view.findViewById(R.id.img_vw_remove);

        Map<Integer, View> sectionItem = new HashMap<>();
        sectionItem.put(layout, view);
        sectionItem.put(R.id.edt_txt_item, edtTxtItem);
        sectionItem.put(R.id.img_vw_remove, imgVwRemove);
        mSectionItems.put(mCounterSectionItems, sectionItem);

        edtTxtItem.setHint(mTitle);
        imgVwRemove.setOnClickListener(new View.OnClickListener() {
            private Integer myKey = mCounterSectionItems;

            @Override
            public void onClick(View view) {
                if (mSectionItems.size() > 1) {
                    mLnrLytContainer.removeView(((View) view.getParent()));
                    mSectionItems.remove(myKey);
                }
            }
        });

        ++mCounterSectionItems;
        mLnrLytContainer.addView(view);

        return  view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onSectionInteractionListener(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnSectionInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnSectionInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
