package br.com.danielsan.dscontacts.adapters.holder;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import br.com.danielsan.dscontacts.R;
import br.com.danielsan.dscontacts.adapters.ContactsAdapter;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by daniel on 10/06/15.
 */
public class ContactsViewHolder {

    private static final int HIGHLIGHT_COLOR = 0x999be6ff;

    protected View mView;
    @InjectView(R.id.txt_vw_name)
    protected TextView mNameTextView;
    @InjectView(R.id.txt_vw_number)
    protected TextView mNumberTextView;
    @InjectView(R.id.img_vw_check)
    protected ImageView mCheckImageView;
    @InjectView(R.id.img_vw_fist_latter)
    protected ImageView mFirstLatterImageView;

    // declare the color generator and drawable builder
    private ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;
    private TextDrawable.IBuilder mDrawableBuilder;

    public ContactsViewHolder(View view) {
        mView = view;
        mView.setTag(this);
        ButterKnife.inject(this, view);
        mNumberTextView.setText("(82)8711-3754");
        mDrawableBuilder = TextDrawable.builder().round();
    }

    public void updateCheckedState(ContactsAdapter.ListData item) {
        if (item.isChecked) {
            mView.setBackgroundColor(HIGHLIGHT_COLOR);
            mCheckImageView.setVisibility(View.VISIBLE);
            mFirstLatterImageView.setImageDrawable(mDrawableBuilder.build(" ", 0xff616161));
        } else {
            TextDrawable drawable = mDrawableBuilder.build(String.valueOf(item.data.charAt(0)), mColorGenerator.getColor(item.data));
            mView.setBackgroundColor(Color.TRANSPARENT);
            mFirstLatterImageView.setImageDrawable(drawable);
            mCheckImageView.setVisibility(View.GONE);
        }
        mNameTextView.setText(item.data);
        mFirstLatterImageView.setOnClickListener(new ContactItemOnClickListener(item) {
                @Override
                public void onClick(View v) {
                    mListData.setChecked(!mListData.isChecked);
                    updateCheckedState(mListData);
                }
            });
    }

    private static abstract class ContactItemOnClickListener implements View.OnClickListener {

        protected ContactsAdapter.ListData mListData;

        public ContactItemOnClickListener(ContactsAdapter.ListData listData) {
            mListData = listData;
        }

    }

}
