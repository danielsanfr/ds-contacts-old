package br.com.danielsan.dscontacts.model;

import android.support.annotation.ArrayRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import com.activeandroid.annotation.Table;

import br.com.danielsan.dscontacts.R;
import br.com.danielsan.dscontacts.model.base.FieldWithTag;

/**
 * Created by daniel on 27/07/15.
 */
@Table(name = "address", id = "_id")
public class Address extends FieldWithTag {

    @StringRes
    @Override
    public int getTitleRes() {
        return R.string.address;
    }

    @DrawableRes
    @Override
    public int getImageTitleRes() {
        return R.drawable.ic_place_grey;
    }

    @ArrayRes
    @Override
    public int getTagsRes() {
        return R.array.field_address_and_email;
    }

}
