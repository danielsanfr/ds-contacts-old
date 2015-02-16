package br.com.danielsan.dscontacts.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import br.com.danielsan.dscontacts.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SectionContactFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SectionContactFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SectionContactFragment extends Fragment {
    private static final String ARG_TITLE = "title";
    private static final String ARG_TAGS = "tags";
    private static final String ARG_ITEM = "item";

    private String mTitle;
    private List<String> mTags;
    private int mContactItem;

    private OnFragmentInteractionListener mListener;

    private LayoutInflater mLayoutInflater;
    private ViewGroup mViewGroup;
    private LinearLayout mLnrLytContainer;
    private ImageView mImgVwAddNew;

    public enum Type {
        Default,
        Email,
        Date
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param title Parameter 1.
     * @param tags Parameter 2.
     * @return A new instance of fragment SectionFragment.
     */
    public static SectionContactFragment newInstance(String title, ArrayList<String> tags) {
        return newInstance(title, tags, Type.Default);
    }

    public static SectionContactFragment newInstance(String title, ArrayList<String> tags, Type type) {
        SectionContactFragment fragment = new SectionContactFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putStringArrayList(ARG_TAGS, tags);

        switch (type) {
            case Email:
                args.putInt(ARG_ITEM, R.layout.contact_item_email);
                break;
            case Date:
                args.putInt(ARG_ITEM, R.layout.contact_item_date);
                break;
            case Default:
            default:
                args.putInt(ARG_ITEM, R.layout.contact_item);
        }

        fragment.setArguments(args);
        return fragment;
    }

    public SectionContactFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = getArguments().getString(ARG_TITLE);
            mTags = getArguments().getStringArrayList(ARG_TAGS);
            mContactItem = getArguments().getInt(ARG_ITEM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mLayoutInflater = inflater;
        mViewGroup = container;

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.section_contact_fragment, container, false);

        TextView mTxtVwTitle = (TextView) view.findViewById(R.id.m_txt_vw_title);
        mImgVwAddNew = (ImageView) view.findViewById(R.id.m_img_vw_add_new);
        mLnrLytContainer = (LinearLayout) view.findViewById(R.id.m_lnr_lyt_container);

        mTxtVwTitle.setText(mTitle);

        mImgVwAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View viewChild = mLayoutInflater.inflate(mContactItem, mViewGroup, false);

                EditText edtTxtItem = (EditText) viewChild.findViewById(R.id.m_edt_txt_item);
                edtTxtItem.setHint(mTitle);

                Spinner spnrTag = (Spinner) viewChild.findViewById(R.id.m_spnr_tag);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_spinner_item, mTags);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnrTag.setAdapter(adapter);

                ImageView imgBtnClear = (ImageView) viewChild.findViewById(R.id.m_img_btn_clear);
                imgBtnClear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                });

                mLnrLytContainer.addView(viewChild);
            }
        });

        View viewChild = inflater.inflate(mContactItem, container, false);

        EditText edtTxtItem = (EditText) viewChild.findViewById(R.id.m_edt_txt_item);
        edtTxtItem.setHint(mTitle);

        Spinner spnrTag = (Spinner) viewChild.findViewById(R.id.m_spnr_tag);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, mTags);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnrTag.setAdapter(adapter);

        ImageView imgBtnClear = (ImageView) viewChild.findViewById(R.id.m_img_btn_clear);
        imgBtnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        mLnrLytContainer.addView(viewChild);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
