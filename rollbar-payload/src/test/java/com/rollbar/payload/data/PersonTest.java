package com.rollbar.payload.data;

import com.rollbar.testing.GetAndSet;
import com.rollbar.testing.TestThat;
import com.rollbar.utilities.ArgumentNullException;
import com.rollbar.utilities.InvalidLengthException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class PersonTest {

    Person p;

    @Before
    public void setUp() throws Exception {
        p = new Person("userid-123", "billy-bob", "bbob@gmail.com");
    }

    @Test
    public void constructorWorks() throws Exception {
        Person person = null;
        try {
            person = new Person("hello-id");
        } catch (ArgumentNullException e) {
            fail("Id isn't null");
        }
        assertEquals("hello-id", person.id());
        assertNull(person.username());
        assertNull(person.email());
    }

    @Test(expected = ArgumentNullException.class)
    public void testIdNull() throws Exception {
        p.id(null);
    }

    @Test
    public void testId() throws Exception {
        TestThat.getAndSetWorks(p, "1234abcd", "efgh5678", new GetAndSet<Person, String>() {
            public String get(Person item) {
                return item.id();
            }

            public Person set(Person item, String val) {
                try {
                    return item.id(val);
                } catch (ArgumentNullException e) {
                    fail("Nothign is null");
                }
                return null;
            }
        });
    }

    @Test(expected = InvalidLengthException.class)
    public void testUsernameTooLong() throws Exception {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < 100; i++) builder.append("abcdefghijklmnopqrstuvwxyz");
        p.username(builder.toString());
    }

    @Test
    public void testUsername() throws Exception {
        TestThat.getAndSetWorks(p, "1234abcd", "efgh5678", new GetAndSet<Person, String>() {
            public String get(Person item) {
                return item.username();
            }

            public Person set(Person item, String val) {
                try {
                    return item.username(val);
                } catch (InvalidLengthException e) {
                    fail("Neither is wrong");
                }
                return null;
            }
        });
    }

    @Test
    public void testEmail() throws Exception {
        TestThat.getAndSetWorks(p, "1234abcd", "efgh5678", new GetAndSet<Person, String>() {
            public String get(Person item) {
                return item.email();
            }

            public Person set(Person item, String val) {
                return item.email(val);
            }
        });
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPutReserved() throws Exception {
        p.put(Person.EMAIL, "host-name");
    }

    @Test
    public void testGetPut() throws Exception {
        TestThat.getAndSetWorks(p, 1024, 16, new GetAndSet<Person, Integer>() {
            public Integer get(Person item) {
                return (Integer) item.get("extra");
            }

            public Person set(Person item, Integer val) {
                return item.put("extra", val);
            }
        });
    }

    @Test
    public void testPutAfterSet() {
        Person person = p.email("bbob+fun@gmail.com")
            .put("extra", "fun");
        assertEquals("bbob+fun@gmail.com", person.email());
        assertEquals("fun", person.get("extra"));
    }

    @Test
    public void testKeys() throws Exception {
        Person person = p.id("id")
            .username("username")
            .put("extra", 15);
        ArrayList<String> keys = new ArrayList<String>(person.keys(true));
        java.util.Collections.sort(keys);
        String[] actual = keys.toArray(new String[keys.size()]);
        String[] expected = new String[] { "extra" };
        assertArrayEquals(expected, actual);

        keys =  new ArrayList<String>(person.keys(false));
        java.util.Collections.sort(keys);
        actual = keys.toArray(new String[keys.size()]);
        expected = new String[] { Person.EMAIL, "extra", Person.ID, Person.USERNAME };
        assertArrayEquals(expected, actual);
    }
}