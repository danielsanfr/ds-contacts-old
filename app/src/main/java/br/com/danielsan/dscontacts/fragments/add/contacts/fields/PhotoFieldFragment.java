package br.com.danielsan.dscontacts.fragments.add.contacts.fields;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import br.com.danielsan.dscontacts.R;
import br.com.danielsan.dscontacts.fragments.dialogs.ColorPickerDialogFragment;
import br.com.danielsan.dscontacts.model.Contact;
import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by daniel on 28/06/15.
 */
public class PhotoFieldFragment extends FieldFragment implements ColorPickerDialogFragment.Listener {

    private int mContactColor;

    @Bind(R.id.img_vw_avatar)
    protected ImageView mAvatarImgVw;
    @Bind(R.id.btn_change_photo)
    protected Button mChangePhotoBtn;
    @Bind(R.id.btn_change_color)
    protected Button mChangeColorBtn;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pTitleTxtVw.setText("Photo");
        pTitleImgVw.setImageResource(R.drawable.ic_photo_camera_grey);
    }

    @Override
    protected int contentResId() {
        return R.layout.fragment_field_photo;
    }

    @OnClick(R.id.btn_change_photo)
    protected void changePhotoBtnOnClick(View view) {
    }

    @OnClick(R.id.btn_change_color)
    protected void changeColorBtnOnClick(View view) {
        ColorPickerDialogFragment.newInstance("Contact color")
                                 .setListener(this)
                                 .show(this.getFragmentManager(), "");
    }

    @Override
    public void updatedContact(Contact contact) {
        contact.setPhoto("/a/b/c/d");
        contact.setColor(mContactColor);
    }

    @Override
    public void onInputColor(Integer color) {
        mContactColor = color;
    }

}
