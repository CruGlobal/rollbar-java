package com.rollbar.utilities;

/**
 * Validates arguments. Throws runtime exceptions when the validation fails.
 */
public final class Validate {

    private Validate() throws InstantiationException {
        throw new InstantiationException("This utility class is not created for instantiation");
    }

    /**
     * Asserts that the String is not null or purely whitespace.
     * @param x the string to validate
     * @param name the name of the argument that's being tested.
     * @throws ArgumentNullException if {@code x} is null or whitespace.
     */
    public static void isNotNullOrWhitespace(String x, String name) throws ArgumentNullException {
        if (x == null || x.trim().isEmpty()) {
            throw new ArgumentNullException(name);
        }
    }

    /**
     * Asserts that the String is not longer than {@code max}.
     * @param x The string to test
     * @param max the maximum length
     * @param name the name of the argument that's being tested.
     * @throws InvalidLengthException if {@code x} is longer than {@code max}
     */
    public static void maxLength(String x, int max, String name) throws InvalidLengthException {
        if (x.length() > max) {
            throw InvalidLengthException.TooLong(name, max);
        }
    }

    /**
     * Asserts that the String is not shorter than {@code max}.
     * @param x The string to test
     * @param min the minimum length
     * @param name the name of the argument that's being tested.
     * @param <T> the type of the array
     * @throws InvalidLengthException if {@code x} is shorter than {@code max}
     */
    public static <T> void minLength(T[] x, int min, String name) throws InvalidLengthException {
        if (x.length < min) {
            throw InvalidLengthException.TooShort(name, min);
        }
    }

    /**
     * Asserts that the argument is not null.
     * This returns the passed-in argument, so that this method can be used in
     * {@code this} and {@code super} constructor expressions.
     *
     * @param data the object to test
     * @param name the name of the argument that's being tested
     * @param <T> the type of the object being tested
     * @return the given object
     * @throws ArgumentNullException if {@code data} is null
     */
    public static <T> T isNotNull(T data, String name) throws ArgumentNullException {
        if (data == null) {
            throw new ArgumentNullException(name);
        }
        return data;
    }
}
