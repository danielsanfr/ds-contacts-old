package br.com.danielsan.dscontacts.model.base;

import com.activeandroid.annotation.Column;

/**
 * Created by daniel on 27/07/15.
 */
public abstract class FieldWithTag extends Field {

    public enum SortBy {
        Content,
        Tag
    }

    private SortBy mSortBy;

    @Column(name = "tag")
    protected String mTag;

    public void setSortBy(SortBy sortBy) {
        mSortBy = sortBy;
    }

    public String getTag() {
        return mTag;
    }

    public void setTag(String tag) {
        mTag = tag;
    }

    @Override
    public int compareTo(Object object) {
        if (mSortBy == SortBy.Content)
            return super.compareTo(object);

        if (object instanceof FieldWithTag)
            return mTag.compareTo(((FieldWithTag) object).getTag());
        else if (object instanceof String)
            return mTag.compareTo((String) object);

        return -1;
    }

}
