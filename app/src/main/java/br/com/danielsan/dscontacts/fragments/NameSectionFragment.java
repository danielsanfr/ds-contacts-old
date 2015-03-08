package br.com.danielsan.dscontacts.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.andexert.expandablelayout.library.ExpandableLayout;

import br.com.danielsan.dscontacts.R;

public class NameSectionFragment extends Fragment {

    private ExpandableLayout mExpdbLytNameSection;
    private ImageView mImgVwExpandName;

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
        mExpdbLytNameSection.getContentRelativeLayout().setOnClickListener(null);

        mImgVwExpandName = (ImageView) view.findViewById(R.id.m_img_vw_expand_name);
        mImgVwExpandName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mExpdbLytNameSection.isOpened()) {
                    mExpdbLytNameSection.hide();
                    mImgVwExpandName.setImageResource(R.drawable.ic_expand_more_grey600);
                } else {
                    mExpdbLytNameSection.show();
                    mImgVwExpandName.setImageResource(R.drawable.ic_expand_less_grey600);
                }
            }
        });

        return view;
    }
}
