package cifras;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES {
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
    public static byte[] KEY_BYTES;
    public final String key_Tamanho = "123456789012345678901234";
    private static final byte[] IV_BYTES = "abcdefghijklmnop".getBytes();

    public static byte[] codificar(byte[] input) throws Exception {
        SecretKeySpec key = new SecretKeySpec(KEY_BYTES, "AES");
        IvParameterSpec iv = new IvParameterSpec(IV_BYTES);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        return cipher.doFinal(input);
    }

    public byte[] decodificar(byte[] input) throws Exception {
        SecretKeySpec key = new SecretKeySpec(KEY_BYTES, "AES");
        IvParameterSpec iv = new IvParameterSpec(IV_BYTES);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        return cipher.doFinal(input);
    }

    public String ajustarChave(String strMenor, int tamanho) {
        StringBuilder sb = new StringBuilder();
        while (sb.length() < tamanho) {
            sb.append(strMenor);
        }
        return sb.toString().substring(0, tamanho);
    }

}
