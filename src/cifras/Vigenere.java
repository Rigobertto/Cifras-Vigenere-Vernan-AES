package cifras;

public class Vigenere {

    public static String codificar(String message, String key) {
        String encrypted = "";
        int keyIndex = 0;
        for (int i = 0; i < message.length(); i++) {
            char c = message.charAt(i);
            if (Character.isLetter(c)) {
                // Encontra o índice da letra da chave a ser usada
                char keyChar = key.charAt(keyIndex % key.length());
                int keyOffset = Character.isUpperCase(keyChar) ? 'A' : 'a';
                int keyIndexOffset = keyChar - keyOffset;

                // Encontra o índice da letra da mensagem a ser usada
                int messageOffset = Character.isUpperCase(c) ? 'A' : 'a';
                int messageIndex = c - messageOffset;

                // Aplica o algoritmo de Vigenère para encontrar o índice da letra criptografada
                int encryptedIndex = (messageIndex + keyIndexOffset) % 26;

                // Adiciona a letra criptografada à mensagem criptografada
                encrypted += (char) (encryptedIndex + messageOffset);

                keyIndex++;
            } else {
                // Mantém o caractere não-letra na mensagem criptografada
                encrypted += c;
            }
        }
        return encrypted;
    }

    // Função para descriptografar uma mensagem usando a chave especificada
    public static String decodificar(String encrypted, String key) {
        String decrypted = "";
        int keyIndex = 0;
        for (int i = 0; i < encrypted.length(); i++) {
            char c = encrypted.charAt(i);
            if (Character.isLetter(c)) {
                // Encontra o índice da letra da chave a ser usada
                char keyChar = key.charAt(keyIndex % key.length());
                int keyOffset = Character.isUpperCase(keyChar) ? 'A' : 'a';
                int keyIndexOffset = keyChar - keyOffset;

                // Encontra o índice da letra da mensagem criptografada a ser usada
                int encryptedOffset = Character.isUpperCase(c) ? 'A' : 'a';
                int encryptedIndex = c - encryptedOffset;

                // Aplica o algoritmo de Vigenère inverso para encontrar o índice da letra original
                int originalIndex = (encryptedIndex - keyIndexOffset + 26) % 26;

                // Adiciona a letra original à mensagem descriptografada
                decrypted += (char) (originalIndex + encryptedOffset);

                keyIndex++;
            } else {
                // Mantém o caractere não-letra na mensagem descriptografada
                decrypted += c;
            }
        }
        return decrypted;
    }
}