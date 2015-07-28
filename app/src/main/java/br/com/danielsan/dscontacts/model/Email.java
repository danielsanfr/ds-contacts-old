package br.com.danielsan.dscontacts.model;

import com.activeandroid.annotation.Table;

import br.com.danielsan.dscontacts.model.base.FieldWithTag;

/**
 * Created by daniel on 27/07/15.
 */
@Table(name = "email")
public class Email extends FieldWithTag {

    public Email(Contact contact) {
        super(contact);
    }

}
