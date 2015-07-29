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
@Table(name = Relationship.TABLE_NAME, id = "_id")
public class Relationship extends FieldWithTag {

    public static final String TABLE_NAME = BASE_TABLE_NAME + "relationship";

    @StringRes
    @Override
    public int getTitleRes() {
        return R.string.relationship;
    }

    @DrawableRes
    @Override
    public int getImageTitleRes() {
        return R.drawable.ic_group_grey;
    }

    @ArrayRes
    @Override
    public int getTagsRes() {
        return R.array.field_relationship;
    }

}
