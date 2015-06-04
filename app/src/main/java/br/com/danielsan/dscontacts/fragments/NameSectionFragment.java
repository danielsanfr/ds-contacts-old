package br.com.danielsan.dscontacts.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.andexert.expandablelayout.library.ExpandableLayout;

import java.util.ArrayList;
import java.util.List;

import br.com.danielsan.dscontacts.R;

public class NameSectionFragment extends Fragment implements OnClickListener {

    private boolean mNameChanged = false;

    private ExpandableLayout mExpdbLytNameSection;
    private ImageView mImgVwExpandName;
    private EditText mEdtTxtName;
    private EditText mEdtTxtFirstName;
    private EditText mEdtTxtMiddleName;
    private EditText mEdtTxtLastName;
    private EditText mEdtTxtOrganization;
    private EditText mEdtTxtTitle;

    private List<EditText> mEdtTxtNameParts;

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
        mEdtTxtName          = (EditText) view.findViewById(R.id.m_edt_txt_name);
        mEdtTxtFirstName     = (EditText) view.findViewById(R.id.m_edt_txt_first_name);
        mEdtTxtMiddleName    = (EditText) view.findViewById(R.id.m_edt_txt_middle_name);
        mEdtTxtLastName      = (EditText) view.findViewById(R.id.m_edt_txt_last_name);
        mEdtTxtOrganization  = (EditText) view.findViewById(R.id.m_edt_txt_organization);
        mEdtTxtTitle         = (EditText) view.findViewById(R.id.m_edt_txt_title);

        ImageButton mImgBtnPhoto = (ImageButton) view.findViewById(R.id.m_img_btn_photo);
        Button mBtnAddOrganization = (Button) view.findViewById(R.id.m_btn_add_organization);

        mEdtTxtNameParts = new ArrayList<>();
        mEdtTxtNameParts.add(mEdtTxtFirstName);
        mEdtTxtNameParts.add(mEdtTxtMiddleName);
        mEdtTxtNameParts.add(mEdtTxtLastName);

        mEdtTxtTitle.setVisibility(View.GONE);
        mEdtTxtOrganization.setVisibility(View.GONE);

        mEdtTxtName.addTextChangedListener(mEdtTxtNameTextWatcher);
        mEdtTxtFirstName.addTextChangedListener(mEdtTxtNamePartsTextWatcher);
        mEdtTxtMiddleName.addTextChangedListener(mEdtTxtNamePartsTextWatcher);
        mEdtTxtLastName.addTextChangedListener(mEdtTxtNamePartsTextWatcher);

        mImgVwExpandName.setOnClickListener(this);
        mExpdbLytNameSection.getContentLayout().setOnClickListener(null);

        mImgBtnPhoto.setOnClickListener(mImgBtnPhotoOnClickListener);
        mBtnAddOrganization.setOnClickListener(mBtnAddOrganizationOnClickListener);

        return view;
    }

    private TextWatcher mEdtTxtNamePartsTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mNameChanged = false;
        }
        @Override
        public void afterTextChanged(Editable s) { }
    };

    private TextWatcher mEdtTxtNameTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mNameChanged = true;
        }
        @Override
        public void afterTextChanged(Editable s) { }
    };

    private OnClickListener mImgBtnPhotoOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
        }
    };

    private OnClickListener mBtnAddOrganizationOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            view.setVisibility(View.GONE);
            mEdtTxtTitle.setVisibility(View.VISIBLE);
            mEdtTxtOrganization.setVisibility(View.VISIBLE);
            mEdtTxtOrganization.requestFocus();
        }
    };

    @Override
    public void onClick(View view) {
        if (mExpdbLytNameSection.isOpened()) {
            mExpdbLytNameSection.hide();
            mEdtTxtName.setVisibility(View.VISIBLE);
            mImgVwExpandName.setImageResource(R.drawable.ic_expand_more_grey600);
            buildHeaderName();
        } else {
            mExpdbLytNameSection.show();
            mEdtTxtName.setVisibility(View.INVISIBLE);
            mImgVwExpandName.setImageResource(R.drawable.ic_expand_less_grey600);
            buildContentNames();
        }
    }

    private void buildHeaderName() {
        String headerName = mEdtTxtFirstName.getText().toString().trim();
        headerName += " " + mEdtTxtMiddleName.getText().toString().trim();
        headerName = headerName.trim();
        headerName += " " +  mEdtTxtLastName.getText().toString().trim();

        mEdtTxtName.setText(headerName.trim());
        mNameChanged = false;
    }

    private void buildContentNames() {
        if (!mNameChanged) {
            return;
        }
        for (EditText editTex : mEdtTxtNameParts) {
            editTex.setText("");
        }

        String[] contentNames = mEdtTxtName.getText().toString().split(" ");
        int length = contentNames.length;
        switch (length) {
            case 0:
                break;
            case 1:
                mEdtTxtFirstName.setText(contentNames[0]);
                break;
            case 2:
                mEdtTxtFirstName.setText(contentNames[0]);
                mEdtTxtMiddleName.setText(contentNames[1]);
                break;
            default: {
                mEdtTxtFirstName.setText(contentNames[0]);
                mEdtTxtLastName.setText(contentNames[length - 1]);
                String middleName = "";
                for (int i = 1; i < length - 1; i++) {
                    middleName += contentNames[i] + " ";
                }
                mEdtTxtMiddleName.setText(middleName.trim());
            }
        }
    }
}
