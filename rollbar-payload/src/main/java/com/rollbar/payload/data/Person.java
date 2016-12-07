package com.rollbar.payload.data;

import com.rollbar.utilities.ArgumentNullException;
import com.rollbar.utilities.Extensible;
import com.rollbar.utilities.InvalidLengthException;
import com.rollbar.utilities.JsonSerializable;
import com.rollbar.utilities.Validate;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Represents the user affected by an error
 */
public class Person extends Extensible<Person> implements JsonSerializable {

    public static final String ID = "id";
    public static final String USERNAME = "username";
    public static final String EMAIL = "email";
    private static final Set<String> keys = new HashSet<String>();

    static {
        final String[] keys = new String[]{
            ID,
            USERNAME,
            EMAIL
        };
        Collections.addAll(Person.keys, keys);
    }

    /**
     * Constructor
     * @param members map of custom arguments
     */
    private Person(Map<String, Object> members) {
        super(members);
    }

    /**
     * Constructor
     * @param id the affected user's id
     * @throws ArgumentNullException if {@code id} is null
     */
    public Person(String id) throws ArgumentNullException {
        super(Collections.<String, Object>emptyMap());
        Validate.isNotNullOrWhitespace(id, "id");
        putKnown(ID, id);
    }

    /**
     * Constructor
     * @param id the affected user's id
     * @param username the affected user's username
     * @param email the affected user's email address
     * @throws InvalidLengthException if {@code username} or {@code email} are longer than 255 characters
     * @throws ArgumentNullException if {@code id} is null
     */
    public Person(String id, String username, String email) throws InvalidLengthException, ArgumentNullException {
        this(id, username, email, null);
    }

    /**
     * Constructor
     * @param id the affected user's id
     * @param username the affected user's username
     * @param email the affected user's email address
     * @param members the custom arguments
     * @throws InvalidLengthException if {@code username} or {@code email} are longer than 255 characters
     * @throws ArgumentNullException if {@code id} is null
     */
    public Person(String id, String username, String email, Map<String, Object> members) throws InvalidLengthException, ArgumentNullException {
        super(members);
        Validate.isNotNullOrWhitespace(id, ID);
        putKnown(ID, id);
        if (username != null) {
            Validate.maxLength(username, 255, USERNAME);
        }
        putKnown(USERNAME, username);
        if (email != null) {
            Validate.maxLength(email, 255, EMAIL);
        }
        putKnown(EMAIL, email);
    }

    @Override
    protected Set<String> getKnownMembers() {
        return keys;
    }

    @Override
    public Person copy() {
        return new Person(getMembers());
    }



    /**
     * @return the affected user's id
     */
    public String id() {
        return (String) get(ID);
    }

    /**
     * Set the id on a copy of this Person
     * @param id the new id
     * @return a copy of this person with the new id
     * @throws ArgumentNullException if {@code id} is null
     */
    public Person id(String id) throws ArgumentNullException {
        return new Person(id, username(), email(), getMembers());
    }

    /**
     * @return the affected user's username
     */
    public String username() {
        return (String) get(USERNAME);
    }

    /**
     * Set the username on a copy of this Person
     * @param username the new username
     * @return a copy of this person with the new username
     * @throws InvalidLengthException if {@code username} is longer than 255 characters
     */
    public Person username(String username) throws InvalidLengthException {
        return new Person(id(), username, email(), getMembers());
    }

    /**
     * @return the affected user's email
     */
    public String email() {
        return (String) get(EMAIL);
    }

    /**
     * Set the email on a copy of this Person
     * @param email the new email
     * @return a copy of this person with the new email
     * @throws InvalidLengthException if {@code email} is longer than 255 characters
     */
    public Person email(String email) throws InvalidLengthException {
        return new Person(id(), username(), email, getMembers());
    }

}
