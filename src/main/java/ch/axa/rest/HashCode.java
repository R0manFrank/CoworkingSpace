package ch.axa.rest;

public class HashCode {
    public static String generateHashCode(String input) {
        int hash = 0;
        for (int i = 0; i < input.length(); i++) {
            hash = 31 * hash + input.charAt(i);
        }
        return Integer.toString(hash);
    }
}
