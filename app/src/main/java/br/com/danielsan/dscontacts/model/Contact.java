package br.com.danielsan.dscontacts.model;

import android.util.Pair;

import java.util.List;

/**
 * Created by daniel on 14/02/15.
 */
public class Contact {
    private Name mName;
    private String mGroup;
    private String mCompany;
    private String mJobTitle;
    private List<Pair<Integer, String>> mPhones;
    private List<Pair<String, String>> mEmails;
    private List<Pair<String, String>> mAddress;
    private List<Pair<String, String>> mSpecialDates;

    public Contact(Name name) {
        mName = name;
    }

    public Contact(Name name, String group, String company, String jobTitle,
                   List<Pair<Integer, String>> phones, List<Pair<String, String>> emails,
                   List<Pair<String, String>> address, List<Pair<String, String>> specialDates) {
        mName = name;
        mGroup = group;
        mCompany = company;
        mJobTitle = jobTitle;
        mPhones = phones;
        mEmails = emails;
        mAddress = address;
        mSpecialDates = specialDates;
    }

    public Name getName() {
        return mName;
    }

    public void setName(Name name) {
        mName = name;
    }

    public String getGroup() {
        return mGroup;
    }

    public void setGroup(String group) {
        mGroup = group;
    }

    public String getCompany() {
        return mCompany;
    }

    public void setCompany(String company) {
        mCompany = company;
    }

    public String getJobTitle() {
        return mJobTitle;
    }

    public void setJobTitle(String jobTitle) {
        mJobTitle = jobTitle;
    }

    public List<Pair<Integer, String>> getPhones() {
        return mPhones;
    }

    public void setPhones(List<Pair<Integer, String>> phones) {
        mPhones = phones;
    }

    public List<Pair<String, String>> getEmails() {
        return mEmails;
    }

    public void setEmails(List<Pair<String, String>> emails) {
        mEmails = emails;
    }

    public List<Pair<String, String>> getAddress() {
        return mAddress;
    }

    public void setAddress(List<Pair<String, String>> address) {
        mAddress = address;
    }

    public List<Pair<String, String>> getSpecialDates() {
        return mSpecialDates;
    }

    public void setSpecialDates(List<Pair<String, String>> specialDates) {
        mSpecialDates = specialDates;
    }
}
