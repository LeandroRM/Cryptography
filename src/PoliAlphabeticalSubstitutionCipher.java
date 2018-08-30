import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Implements a poli alphabetic substitution cipher, that is a basic method of encryption to provide
 * security.
 * </br>
 * A substitution cipher is a method of encrypting by which units of plaintext are replaced with ciphertext
 *
 */
public class PoliAlphabeticalSubstitutionCipher {

    private static final String[] ALPHABET_1;
    private static final String[] ALPHABET_2;

    private final String encryptedMessage;
    private final HashMap<String, String> cryptMap1;
    private final HashMap<String, String> cryptMap2;

    static {
        ALPHABET_1 = new String[] {
                "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
                "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
        };
        ALPHABET_2 = new String[] {
                "Z", "Y", "X", "W", "V", "U", "T", "S", "R", "Q", "P", "O", "N",
                "M", "L", "K", "J", "I", "H", "G", "F", "E", "D", "C", "B", "A"
        };
    }

    public PoliAlphabeticalSubstitutionCipher(String message) {
        message = message.toUpperCase();
        cryptMap1 = new HashMap<>();
        cryptMap2 = new HashMap<>();
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

            if (!Arrays.stream(ALPHABET_1).anyMatch((character) -> character.equals(c))) {
                partialEncryptedMessage.append(c);
                continue; //Actual character is not present in alphabet
            }

            //Already finished the process
            if ((cryptMap1.size() == ALPHABET_1.length) && (cryptMap2.size() == ALPHABET_2.length)) {
                break;
            }

            boolean par = (i + 1) % 2 == 0;
            HashMap<String, String> actualMap = par ? cryptMap2 : cryptMap1;
            String[] alphabet = par ? ALPHABET_2 : ALPHABET_1;

            //Already checked this character
            if (actualMap.containsKey(c)) {
                partialEncryptedMessage.append(actualMap.get(c));
                continue;
            }

            //Set the character that will replace
            String replaceChar = alphabet[actualMap.size()];
            actualMap.put(c, replaceChar);
            partialEncryptedMessage.append(replaceChar);
        }
        return partialEncryptedMessage.toString();
    }

    private String decrypt(String message) {
        StringBuilder partialDecryptedMessage = new StringBuilder();

        for (int i = 0; i < message.length(); i++) {
            String c = String.valueOf(message.charAt(i));

            if (!Arrays.stream(ALPHABET_1).anyMatch((character) -> character.equals(c))) {
                partialDecryptedMessage.append(c);
                continue; //Actual character is not present in alphabet
            }
            boolean par = (i + 1) % 2 == 0;
            HashMap<String, String> actualMap = par ? cryptMap2 : cryptMap1;
            String originalChar = getOriginalChar(c, actualMap);
            partialDecryptedMessage.append(originalChar);
        }

        //Full decrypted message
        return partialDecryptedMessage.toString();
    }

    /**
     * Get the original char in cryptMap1
     * @param c
     * @return
     */
    private String getOriginalChar(String c, HashMap<String, String> actualMap) {
        for (String key : actualMap.keySet()) {
            if (actualMap.get(key).equals(c)) {
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

