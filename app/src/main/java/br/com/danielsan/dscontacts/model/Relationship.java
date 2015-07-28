package br.com.danielsan.dscontacts.model;

import com.activeandroid.annotation.Table;

import br.com.danielsan.dscontacts.model.base.FieldWithTag;

/**
 * Created by daniel on 27/07/15.
 */
@Table(name = "relationship")
public class Relationship extends FieldWithTag {

    public Relationship(Contact contact) {
        super(contact);
    }

}
