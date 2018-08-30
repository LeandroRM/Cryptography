import java.util.Arrays;
import java.util.Scanner;

/**
 * Implements Vigenere cipher, that is poli alphabetical method of encryption to provide
 * security.
 * </br>
 * To encrypt, a table of alphabets can be used, termed a Vigenere Table.
 * It has the alphabet written out 26 times in different rows, each alphabet shifted cyclically to the left
 * compared to the previous alphabet, corresponding to the 26 possible Caesar ciphers.
 * At different points in the encryption process, the cipher uses a different alphabet from one of the rows.
 * The alphabet used at each point depends on a repeating keyword.
 *
 */
public class VigenereTableCipher {

    private static final String[] ALPHABET;

    private final String encryptedMessage;
    private final String keyword;

    static {
        ALPHABET = new String[] {
                "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
                "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
        };
    }

    public VigenereTableCipher(String message, String keyword) {
        message = message.toUpperCase();
        this.keyword = keyword.toUpperCase();
        encryptedMessage = encrypt(message);
    }

    /**
     * Encrypt the message using poli alphabetic replace
     *
     * @param message - The message to be encrypted
     * @return        - encrypted message
     */
    private String encrypt(String message) {
        StringBuilder partialEncryptedMessage = new StringBuilder();
        int keywordIndex = 0;

        for (int i = 0; i < message.length(); i++) {
            String c = String.valueOf(message.charAt(i));

            if (!Arrays.stream(ALPHABET).anyMatch((character) -> character.equals(c))) {
                partialEncryptedMessage.append(c);
                continue; //Actual character is not present in alphabet
            }
            //Get actual char at keyword
            String k = String.valueOf(keyword.charAt(keywordIndex++));
            int row = getCharIndex(k);
            int col = getCharIndex(c);

            //Set the character that will replace
            String encryptChar = getEncryptChar(row, col);
            partialEncryptedMessage.append(encryptChar);

            if (keywordIndex == keyword.length()) {
                keywordIndex = 0;
            }
        }
        return partialEncryptedMessage.toString();
    }

    private String decrypt(String message) {
        StringBuilder partialEncryptedMessage = new StringBuilder();
        int keywordIndex = 0;

        for (int i = 0; i < message.length(); i++) {
            String c = String.valueOf(message.charAt(i));

            if (!Arrays.stream(ALPHABET).anyMatch((character) -> character.equals(c))) {
                partialEncryptedMessage.append(c);
                continue; //Actual character is not present in alphabet
            }
            //Get actual char at keyword
            String k = String.valueOf(keyword.charAt(keywordIndex++));
            int keyIndex = getCharIndex(k);
            int charIndex = getCharIndex(c);

            //Set the character that will replace
            String encryptChar = getDecryptChar(keyIndex, charIndex);
            partialEncryptedMessage.append(encryptChar);

            if (keywordIndex == keyword.length()) {
                keywordIndex = 0;
            }
        }
        return partialEncryptedMessage.toString();
    }

    /**
     * Get the reference character at tabula recta with the sum of row and col less 26 (alphabet size) when
     * the sum result is larger or equal to 26.
     * @param keyIndex
     * @param charIndex
     * @return
     */
    private String getEncryptChar(int keyIndex, int charIndex) {
        int index = keyIndex + charIndex;

        if (index >= ALPHABET.length) {
            index -= ALPHABET.length;
        }
        return ALPHABET[index];
    }

    /**
     * Get the reference character at tabula recta with the sum of row and col less 26 (alphabet size) when
     * the sum result is larger or equal to 26.
     * @param keyIndex
     * @param charIndex
     * @return
     */
    private String getDecryptChar(int keyIndex, int charIndex) {
        int index = charIndex - keyIndex;

        if (index < 0) {
            index += ALPHABET.length;
        }
        return ALPHABET[index];
    }

    /**
     * Get the index of the character at the alphabet
     * @param character
     * @return
     */
    private int getCharIndex(String character) {
        for (int i = 0; i < ALPHABET.length; i++) {
            if (ALPHABET[i].equals(character)) {
                return i;
            }
        }
        return -1;
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
        System.out.println("Informe a palavra chave que deseja utilizar");
        String keyword = scanner.nextLine();

        VigenereTableCipher cipher = new VigenereTableCipher(message, keyword);
        System.out.println("Encrypted: " + cipher.getEncryptedMessage());
        System.out.println("Decrypted: " + cipher.getDecryptedMessage());
    }
}

