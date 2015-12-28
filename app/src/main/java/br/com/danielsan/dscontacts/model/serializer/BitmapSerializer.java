package br.com.danielsan.dscontacts.model.serializer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.activeandroid.serializer.TypeSerializer;

import java.io.ByteArrayOutputStream;

/**
 * Created by daniel on 31/07/15.
 */
public class BitmapSerializer extends TypeSerializer {

    @Override
    public Class<?> getDeserializedType() {
        return Bitmap.class;
    }

    @Override
    public Class<?> getSerializedType() {
        return byte[].class;
    }

    @Override
    public Object serialize(Object data) {
        if (data == null || !(data instanceof byte[]))
            return null;

        byte[] bitmapData = (byte[]) data;
        return BitmapFactory.decodeByteArray(bitmapData , 0, bitmapData.length);
    }

    @Override
    public Object deserialize(Object data) {
        if (data == null || !(data instanceof Bitmap))
            return null;

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ((Bitmap) data).compress(Bitmap.CompressFormat.JPEG, 100, bos);
        return bos.toByteArray();
    }

}
