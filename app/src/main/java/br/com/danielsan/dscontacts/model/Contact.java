package br.com.danielsan.dscontacts.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

import br.com.danielsan.dscontacts.model.base.Field;
import br.com.danielsan.dscontacts.model.base.FieldWithTag;

/**
 * Created by daniel on 14/02/15.
 */
@Table(name = Contact.TABLE_NAME, id = "_id")
public class Contact extends Model {

    public static final String TABLE_NAME = "contact";

    @Column(name = "name")
    private Name mName;
    @Column(name = "favorite")
    private boolean mFavorite;
    @Column(name = "my_group")
    private String mGroup;
    @Column(name = "organization")
    private String mOrganization;
    @Column(name = "title")
    private String mTitle;
    @Column(name = "picture")
    private String mPicture;
    @Column(name = "color")
    private Integer mColor;

    public Contact() {
        super();
    }

    public Name getName() {
        return mName;
    }

    public void setName(Name name) {
        mName = name;
    }

    public boolean isFavorite() {
        return mFavorite;
    }

    public void setFavorite(boolean mFavorite) {
        this.mFavorite = mFavorite;
    }

    public String getGroup() {
        return mGroup;
    }

    public void setGroup(String group) {
        mGroup = group;
    }

    public String getOrganization() {
        return mOrganization;
    }

    public void setOrganization(String organization) {
        mOrganization = organization;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getPicture() {
        return mPicture;
    }

    public void setPicture(String picture) {
        mPicture = picture;
    }

    public Integer getColor() {
        return mColor;
    }

    public void setColor(Integer color) {
        mColor = color;
    }

    public List<Phone> phones() {
        return this.getMany(Phone.class, TABLE_NAME);
    }

    public List<Email> emails() {
        return this.getMany(Email.class, TABLE_NAME);
    }

    public List<IM> ims() {
        return this.getMany(IM.class, TABLE_NAME);
    }

    public List<Note> notes() {
        return this.getMany(Note.class, TABLE_NAME);
    }

    public List<Nickname> nicknames() {
        return this.getMany(Nickname.class, TABLE_NAME);
    }

    public List<Website> webSites() {
        return this.getMany(Website.class, TABLE_NAME);
    }

    public List<Event> events() {
        return this.getMany(Event.class, TABLE_NAME);
    }

    public List<Address> addresses() {
        return this.getMany(Address.class, TABLE_NAME);
    }

    public List<Relationship> relationships() {
        return this.getMany(Relationship.class, TABLE_NAME);
    }

    public void addPhone(String phone, String tag) {
        this.saveContentAndTag(new Phone(), phone, tag);
    }

    public void addEmail(String email, String tag) {
        this.saveContentAndTag(new Email(), email, tag);
    }

    public void addIM(String im, String tag) {
        this.saveContentAndTag(new IM(), im, tag);
    }

    public void addNote(String note) {
        this.saveContent(new Note(), note);
    }

    public void addNickname(String nickname) {
        this.saveContent(new Nickname(), nickname);
    }

    public void addWebsite(String website) {
        this.saveContent(new Website(), website);
    }

    public void addEvent(String event, String tag) {
        this.saveContentAndTag(new Event(), event, tag);
    }

    public void addAddress(String address, String tag) {
        this.saveContentAndTag(new Address(), address, tag);
    }

    public void addRelationship(String relationship, String tag) {
        this.saveContentAndTag(new Relationship(), relationship, tag);
    }

    private void saveContentAndTag(FieldWithTag fieldWithTag, String content, String tag) {
        fieldWithTag.setTag(tag);
        this.saveContent(fieldWithTag, content);
    }

    private void saveContent(Field field, String content) {
        field.setContent(content);
        field.setContact(this);
        field.save();
    }

}
