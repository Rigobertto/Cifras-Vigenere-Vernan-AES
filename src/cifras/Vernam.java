package cifras;

import java.util.Random;

public class Vernam {
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final Random RANDOM = new Random();

    public String generateKey(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return sb.toString();
    }

    public static String codificar(String message, String key) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < message.length(); i++) {
            char c = message.charAt(i);
            char k = key.charAt(i);
            if (Character.isLowerCase(c)) {
                int encrypted = (c + k - 2 * 'a') % 26 + 'a';
                sb.append((char) encrypted);
            } else if (Character.isUpperCase(c)) {
                int encrypted = (c + k - 2 * 'A') % 26 + 'A';
                sb.append((char) encrypted);
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static String decodificar(String encrypted, String key) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < encrypted.length(); i++) {
            char c = encrypted.charAt(i);
            char k = key.charAt(i);
            if (Character.isLowerCase(c)) {
                int decrypted = (c - k + 26) % 26 + 'a';
                sb.append((char) decrypted);
            } else if (Character.isUpperCase(c)) {
                int decrypted = (c - k + 26) % 26 + 'A';
                sb.append((char) decrypted);
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
