import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Implements a simple alphabetic substitution cipher, that is a basic method of encryption to provide
 * security.
 * </br>
 * A substitution cipher is a method of encrypting by which units of plaintext are replaced with ciphertext
 *
 */
public class SimpleAlphabeticalSubstitutionCipher {

    private static final String[] ALPHABET;

    private final String encryptedMessage;
    private final HashMap<String, String> cryptMap;

    static {
        ALPHABET = new String[] {
                "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
                "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
        };
    }

    public SimpleAlphabeticalSubstitutionCipher(String message) {
        message = message.toUpperCase();
        cryptMap = new HashMap<>();
        encryptedMessage = encrypt(message);
    }

    /**
     * Encrypt the message using alphabetic replace
     *
     * @param message - The message to be encrypted
     * @return        - encrypted message
     */
    private String encrypt(String message) {
        StringBuilder partialEncryptedMessage = new StringBuilder();

        for (int i = 0; i < message.length(); i++) {
            String c = String.valueOf(message.charAt(i));

            //Already finished the process
            if (cryptMap.size() == ALPHABET.length) {
                break;
            }

            //Already checked this character
            if (cryptMap.containsKey(c)) {
                partialEncryptedMessage.append(cryptMap.get(c));
                continue;
            }

            if (!Arrays.stream(ALPHABET).anyMatch((character) -> character.equals(c))) {
                partialEncryptedMessage.append(c);
                continue; //Actual character is not present in alphabet
            }

            //Set the character that will replace
            String replaceChar = ALPHABET[cryptMap.size()];
            cryptMap.put(c, replaceChar);
            partialEncryptedMessage.append(replaceChar);
        }
        return partialEncryptedMessage.toString();
    }

    private String decrypt(String message) {
        StringBuilder partialDecryptedMessage = new StringBuilder();

        for (int i = 0; i < message.length(); i++) {
            String c = String.valueOf(message.charAt(i));

            if (!Arrays.stream(ALPHABET).anyMatch((character) -> character.equals(c))) {
                partialDecryptedMessage.append(c);
                continue; //Actual character is not present in alphabet
            }
            String originalChar = getOriginalChar(c);
            partialDecryptedMessage.append(originalChar);
        }

        //Full decrypted message
        return partialDecryptedMessage.toString();
    }

    /**
     * Get the original char in cryptMap
     * @param c
     * @return
     */
    private String getOriginalChar(String c) {
        for (String key : cryptMap.keySet()) {
            if (cryptMap.get(key).equals(c)) {
                return key;
            }
        }
        return "";
    }

    public String getEncryptedMessage() {
        return encryptedMessage;
    }

    public String getDecryptedMessage() {
        return decrypt(encryptedMessage);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Informe a mensagem que deseja criptografar");
        String message = scanner.nextLine();

        PoliAlphabeticalSubstitutionCipher cipher = new PoliAlphabeticalSubstitutionCipher(message);
        System.out.println("Encrypted: " + cipher.getEncryptedMessage());
        System.out.println("Decrypted: " + cipher.getDecryptedMessage());
    }
}
