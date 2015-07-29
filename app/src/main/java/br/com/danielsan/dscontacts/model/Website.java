package br.com.danielsan.dscontacts.model;

import android.support.annotation.ArrayRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import com.activeandroid.annotation.Table;

import br.com.danielsan.dscontacts.R;
import br.com.danielsan.dscontacts.model.base.Field;

/**
 * Created by daniel on 27/07/15.
 */
@Table(name = "website", id = "_id")
public class Website extends Field {

    @StringRes
    @Override
    public int getTitleRes() {
        return R.string.website;
    }

    @DrawableRes
    @Override
    public int getImageTitleRes() {
        return R.drawable.ic_web_grey;
    }

    @ArrayRes
    @Override
    public int getTagsRes() {
        return -1;
    }

}
