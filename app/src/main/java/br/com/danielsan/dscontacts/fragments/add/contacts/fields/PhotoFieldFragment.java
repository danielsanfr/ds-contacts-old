package br.com.danielsan.dscontacts.fragments.add.contacts.fields;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.io.IOException;

import br.com.danielsan.dscontacts.R;
import br.com.danielsan.dscontacts.fragments.dialogs.ColorPickerDialogFragment;
import br.com.danielsan.dscontacts.model.Contact;
import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by daniel on 28/06/15.
 */
public class PhotoFieldFragment extends FieldFragment implements ColorPickerDialogFragment.Listener {

    private static final int PICK_PHOTO_FOR_AVATAR = 1;
    private static final String BASE_FILENAME = "%savatar";
    private static final String IMAGE_FORMAT = ".jpg";

    private File mAvatarFile;
    private File mAvatarTempFile;
    private String mPhotoPath;
    private int mContactColor;
    private boolean mDeleteAvatarFile;

    @Bind(R.id.img_vw_avatar)
    protected ImageView mAvatarImgVw;
    @Bind(R.id.img_vw_clear_photo)
    protected ImageView mClearPhotoImgVw;

    public static PhotoFieldFragment newInstance() {
        PhotoFieldFragment fragment = new PhotoFieldFragment();
        fragment.setArguments(FieldFragment.makeBaseBundle(R.string.photo, R.drawable.ic_photo_camera_grey));
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mDeleteAvatarFile = true;
        try {
            mAvatarFile = this.createImageFile();
            mAvatarTempFile = this.createImageFile("temp_");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected int contentResId() {
        return R.layout.fragment_field_photo;
    }

    @OnClick(R.id.img_vw_clear_photo)
    protected void clearPhotoImgVwOnClick(View view) {
        mDeleteAvatarFile = true;
        mAvatarImgVw.setImageResource(R.drawable.ic_person_white);
        mClearPhotoImgVw.setVisibility(View.INVISIBLE);
    }

    @OnClick(R.id.btn_change_photo)
    protected void changePhotoBtnOnClick(View view) {
        if (mAvatarFile == null || mAvatarTempFile == null)
            return;

        // Galley app
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        // Others Galley app
        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        // Camera app
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mAvatarTempFile));

        // Join all intents
        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] { pickIntent, cameraIntent });

        this.startActivityForResult(chooserIntent, PICK_PHOTO_FOR_AVATAR);

    }

    @OnClick(R.id.btn_change_color)
    protected void changeColorBtnOnClick(View view) {
        ColorPickerDialogFragment.newInstance(R.string.contact_color)
                                 .setListener(this)
                                 .show(this.getFragmentManager(), "");
    }

    @Override
    public void updatedContact(Contact contact) {
        contact.setPicture(mDeleteAvatarFile ? "" : mPhotoPath);
        contact.setColor(mContactColor);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PICK_PHOTO_FOR_AVATAR) {
                beginCrop(data);
            } else if (requestCode == Crop.REQUEST_CROP) {
                handleCrop(data);
            }
        }
    }

    private void beginCrop(Intent data) {
        Uri source;
        Uri destination = Uri.fromFile(mAvatarFile);
        if (data == null)
            source = Uri.fromFile(mAvatarTempFile);
        else
            source = data.getData();

        this.startActivityForResult(Crop.of(source, destination).asSquare().getIntent(this.getActivity()), Crop.REQUEST_CROP);
    }

    private void handleCrop(Intent data) {
        if (!mDeleteAvatarFile)
            mAvatarImgVw.setImageURI(null);
        mDeleteAvatarFile = false;
        mAvatarImgVw.setImageURI(Crop.getOutput(data));
        mClearPhotoImgVw.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
            mAvatarTempFile.delete();
        if (mAvatarFile != null && mDeleteAvatarFile)
            mAvatarFile.delete();
    }
    private File createImageFile() throws IOException {
        return this.createImageFile("");
    }

    // http://developer.android.com/training/camera/photobasics.html
    private File createImageFile(@NonNull String complement) throws IOException {
        File storageDir = this.getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        String imageFileName = String.format(BASE_FILENAME, complement);
        File image = File.createTempFile(imageFileName, IMAGE_FORMAT, storageDir);

        mPhotoPath = image.getAbsolutePath();

        return image;
    }

    @Override
    public void onInputColor(Integer color) {
        mContactColor = color;
    }

}
