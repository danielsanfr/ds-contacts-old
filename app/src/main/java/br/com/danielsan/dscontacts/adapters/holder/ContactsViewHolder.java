package br.com.danielsan.dscontacts.adapters.holder;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import br.com.danielsan.dscontacts.R;
import br.com.danielsan.dscontacts.adapters.ContactsAdapter;

/**
 * Created by daniel on 10/06/15.
 */
public class ContactsViewHolder {

    private static final int HIGHLIGHT_COLOR = 0x999be6ff;

    private View view;

    private ImageView imageView;

    private TextView textView;

    private ImageView checkIcon;
    // declare the color generator and drawable builder
    private ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;
    private TextDrawable.IBuilder mDrawableBuilder;

    public ContactsViewHolder(View view) {
        this.view = view;
        imageView = (ImageView) view.findViewById(R.id.imageView);
        textView = (TextView) view.findViewById(R.id.textView);
        checkIcon = (ImageView) view.findViewById(R.id.check_icon);
        mDrawableBuilder = TextDrawable.builder().round();
    }

    public void updateCheckedState(final ContactsAdapter.ListData item) {
        if (item.isChecked) {
            imageView.setImageDrawable(mDrawableBuilder.build(" ", 0xff616161));
            view.setBackgroundColor(HIGHLIGHT_COLOR);
            checkIcon.setVisibility(View.VISIBLE);
        } else {
            TextDrawable drawable = mDrawableBuilder.build(String.valueOf(item.data.charAt(0)), mColorGenerator.getColor(item.data));
            imageView.setImageDrawable(drawable);
            view.setBackgroundColor(Color.TRANSPARENT);
            checkIcon.setVisibility(View.GONE);
        }
        textView.setText(item.data);
        imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // when the image is clicked, update the selected state
                    ContactsAdapter.ListData data = item;
                    data.setChecked(!data.isChecked);
                    updateCheckedState(data);
                }
            });
    }

}
