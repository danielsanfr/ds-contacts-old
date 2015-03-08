package br.com.danielsan.dscontacts.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.andexert.expandablelayout.library.ExpandableLayout;

import br.com.danielsan.dscontacts.R;

public class NameSectionFragment extends Fragment implements OnClickListener {

    private ExpandableLayout mExpdbLytNameSection;
    private ImageView mImgVwExpandName;
    private ImageButton mImgBtnPhoto;
    private EditText mEdtTxtHeaderName;
    private EditText mEdtTxtContentName;
    private EditText mEdtTxtNamePrefix;
    private EditText mEdtTxtMiddleName;
    private EditText mEdtTxtLastName;
    private EditText mEdtTxtNameSuffix;
    private Button mBtnAddOrganization;
    private EditText mEdtTxtOrganization;
    private EditText mEdtTxtTitle;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment NameSectionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NameSectionFragment newInstance() {
        return new NameSectionFragment();
    }

    public NameSectionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.name_section_fragment, container, false);

        mExpdbLytNameSection = (ExpandableLayout) view.findViewById(R.id.m_expdb_lyt_name_section);
        mImgVwExpandName     = (ImageView) view.findViewById(R.id.m_img_vw_expand_name);
        mImgBtnPhoto         = (ImageButton) view.findViewById(R.id.m_img_btn_photo);
        mEdtTxtHeaderName    = (EditText) view.findViewById(R.id.m_edt_txt_header_name);
        mEdtTxtContentName   = (EditText) view.findViewById(R.id.m_edt_txt_content_name);
        mEdtTxtNamePrefix    = (EditText) view.findViewById(R.id.m_edt_txt_name_prefix);
        mEdtTxtMiddleName    = (EditText) view.findViewById(R.id.m_edt_txt_middlename);
        mEdtTxtLastName      = (EditText) view.findViewById(R.id.m_edt_txt_lastname);
        mEdtTxtNameSuffix    = (EditText) view.findViewById(R.id.m_edt_txt_name_suffix);
        mBtnAddOrganization  = (Button) view.findViewById(R.id.m_btn_add_organization);
        mEdtTxtOrganization  = (EditText) view.findViewById(R.id.m_edt_txt_organization);
        mEdtTxtTitle         = (EditText) view.findViewById(R.id.m_edt_txt_title);

        mEdtTxtTitle.setVisibility(View.GONE);
        mEdtTxtOrganization.setVisibility(View.GONE);

        mImgVwExpandName.setOnClickListener(this);
        mExpdbLytNameSection.getContentRelativeLayout().setOnClickListener(null);
        mBtnAddOrganization.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setVisibility(View.GONE);
                mEdtTxtTitle.setVisibility(View.VISIBLE);
                mEdtTxtOrganization.setVisibility(View.VISIBLE);
            }
        });

        return view;
    }

    @Override
    public void onClick(View view) {
        if (mExpdbLytNameSection.isOpened()) {
            mExpdbLytNameSection.hide();
            mEdtTxtHeaderName.setVisibility(View.VISIBLE);
            mImgVwExpandName.setImageResource(R.drawable.ic_expand_more_grey600);
        } else {
            mExpdbLytNameSection.show();
            mEdtTxtHeaderName.setVisibility(View.INVISIBLE);
            mImgVwExpandName.setImageResource(R.drawable.ic_expand_less_grey600);
        }
    }
}
