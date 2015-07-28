package br.com.danielsan.dscontacts.model;

import com.activeandroid.annotation.Table;

import br.com.danielsan.dscontacts.model.base.Field;

/**
 * Created by daniel on 27/07/15.
 */
@Table(name = "website")
public class Website extends Field {

    public Website(Contact contact) {
        super(contact);
    }

}
