package br.com.danielsan.dscontacts.model;

import com.activeandroid.annotation.Table;

import br.com.danielsan.dscontacts.model.base.Field;

/**
 * Created by daniel on 27/07/15.
 */
@Table(name = "note")
public class Note extends Field {

    public Note(Contact contact) {
        super(contact);
    }

}