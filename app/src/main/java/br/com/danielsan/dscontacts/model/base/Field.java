package br.com.danielsan.dscontacts.model.base;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import br.com.danielsan.dscontacts.model.Contact;

/**
 * Created by daniel on 27/07/15.
 */
@Table(name = "field")
public abstract class Field extends Model implements Comparable<Object> {

    @Column(name = "contact")
    protected Contact mContact;
    @Column(name = "content")
    protected String mContent;

    public Field(Contact contact) {
        super();
        mContact = contact;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    @Override
    public int compareTo(Object object) {
        if (object instanceof Field)
            return mContent.compareTo(((Field) object).getContent());
        else if (object instanceof String)
            return mContent.compareTo((String) object);

        return -1;
    }

}
