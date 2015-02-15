package br.com.danielsan.dscontacts.model;

/**
 * Created by daniel on 14/02/15.
 */
public class Name {
    private String mName;
    private String mNamePrefix;
    private String mMiddleName;
    private String mLastName;
    private String mNameSuffix;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getNamePrefix() {
        return mNamePrefix;
    }

    public void setNamePrefix(String namePrefix) {
        mNamePrefix = namePrefix;
    }

    public String getMiddleName() {
        return mMiddleName;
    }

    public void setMiddleName(String middleName) {
        mMiddleName = middleName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }

    public String getNameSuffix() {
        return mNameSuffix;
    }

    public void setNameSuffix(String nameSuffix) {
        mNameSuffix = nameSuffix;
    }
}
