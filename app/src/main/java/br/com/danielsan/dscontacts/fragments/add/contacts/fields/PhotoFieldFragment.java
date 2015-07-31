package br.com.danielsan.dscontacts.fragments.add.contacts.fields;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    private static final String BASE_FILENAME = "avatar_%s";
    private static final String IMAGE_FORMAT = ".jpg";

    private File mAvatarFile;
    private String mPhotoPath;
    private int mContactColor;
    private Bitmap mAvatarBitmap;
    private boolean mDeleteAvatarFile;

    @Bind(R.id.img_vw_avatar)
    protected ImageView mAvatarImgVw;
    @Bind(R.id.btn_change_photo)
    protected Button mChangePhotoBtn;
    @Bind(R.id.btn_change_color)
    protected Button mChangeColorBtn;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mDeleteAvatarFile = true;
        try {
            mAvatarFile = this.createImageFile();
        } catch (IOException e) {
//            e.printStackTrace();
            return;
        }

        pTitleTxtVw.setText("Photo");
        pTitleImgVw.setImageResource(R.drawable.ic_photo_camera_grey);
    }

    @Override
    protected int contentResId() {
        return R.layout.fragment_field_photo;
    }

    @OnClick(R.id.btn_change_photo)
    protected void changePhotoBtnOnClick(View view) {
        if (mAvatarFile == null)
            return;

        // Galley app
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        // Others Galley app
        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        // Camera app
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mAvatarFile));

        // Join all intents
        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] { pickIntent, cameraIntent });

        this.startActivityForResult(chooserIntent, PICK_PHOTO_FOR_AVATAR);

    }

    @OnClick(R.id.btn_change_color)
    protected void changeColorBtnOnClick(View view) {
        ColorPickerDialogFragment.newInstance("Contact color")
                                 .setListener(this)
                                 .show(this.getFragmentManager(), "");
    }

    @Override
    public void updatedContact(Contact contact) {
        this.saveAvatarBitmap();
        contact.setPhoto(mPhotoPath);
        contact.setColor(mContactColor);
        mDeleteAvatarFile = false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//            Log.d("========", String.valueOf(requestCode) + " - " + String.valueOf(resultCode) + "===================");
            Log.d("========", String.valueOf(requestCode) + " - " + String.valueOf(resultCode) + " - " + String.valueOf(data)+ " - " + String.valueOf(mAvatarFile));
        if (requestCode == PICK_PHOTO_FOR_AVATAR && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                mAvatarBitmap = BitmapFactory.decodeFile(mPhotoPath);
            } else {
                InputStream inputStream;
                try {
                    inputStream = this.getActivity()
                                      .getContentResolver()
                                      .openInputStream(data.getData());
                    mAvatarBitmap = BitmapFactory.decodeStream(inputStream);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

            if (mAvatarBitmap != null)
                mAvatarImgVw.setImageBitmap(mAvatarBitmap);





//            try {
//            Log.d("========", String.valueOf(requestCode) + " - " + String.valueOf(resultCode) + " - " + String.valueOf(data.getData()));
//            if (data.getExtras() != null)
//                Log.d("========", String.valueOf(requestCode) + " - " + String.valueOf(resultCode) + " - " + String.valueOf(data.getExtras()));
//                mAvatarImgVw.setImageURI(data.getData());
//                InputStream inputStream = this.getActivity().getContentResolver().openInputStream(data.getData());
//                mAvatarImgVw.setImageBitmap(BitmapFactory.decodeStream(inputStream));
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAvatarFile != null && mDeleteAvatarFile)
            mAvatarFile.delete();
    }

    // http://developer.android.com/training/camera/photobasics.html
    private File createImageFile() throws IOException {
        File storageDir = this.getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        String imageFileName = String.format(BASE_FILENAME, new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()));
        File image = File.createTempFile(imageFileName, IMAGE_FORMAT, storageDir);

        mPhotoPath = image.getAbsolutePath();

        return image;
    }

    private void saveAvatarBitmap() {
        if (mAvatarBitmap == null || mAvatarFile == null)
            return;

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(mAvatarFile);
            mAvatarBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onInputColor(Integer color) {
        mContactColor = color;
    }

}
