package br.com.danielsan.dscontacts.model.serializer;

import com.activeandroid.serializer.TypeSerializer;

import br.com.danielsan.dscontacts.model.Name;

/**
 * Created by daniel on 27/07/15.
 */
public class NameSerializer extends TypeSerializer {

    @Override
    public Class<?> getDeserializedType() {
        return Name.class;
    }

    @Override
    public Class<?> getSerializedType() {
        return String.class;
    }

    @Override
    public String serialize(Object data) {
        if (data == null || !(data instanceof Name))
            return null;

        Name name = (Name) data;
        return this.serialize(name.getName(), name.getMiddleName(), name.getLastName());
    }

    @Override
    public Name deserialize(Object data) {
        if (data == null || !(data instanceof String))
            return null;

        Name name = new Name();
        String[] names = ((String) data).split(" ");
        int length = names.length;
        switch (length) {
            case 1:
                name.setName(names[0]);
                break;
            case 2:
                name.setName(names[0]);
                name.setMiddleName(names[1]);
                break;
            default: {
                name.setName(names[0]);
                name.setLastName(names[length - 1]);
                String middleName = "";
                for (int i = 1; i < length - 1; i++) {
                    middleName += names[i] + " ";
                }
                name.setMiddleName(middleName.trim());
            }
        }

        return name;
    }

    public String serialize(String firstName, String middleName, String lastName) {
        if (firstName == null)
            firstName = "";
        if (middleName == null)
            middleName = "";
        if (lastName == null)
            lastName = "";

        String name = firstName.trim();
        name += " " + middleName.trim();
        name = name.trim();
        name += " " +  lastName.trim();

        return  name.trim();
    }

}
