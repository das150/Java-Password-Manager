
// Import Statements
import java.security.SecureRandom;
import javax.swing.JOptionPane;

/**
 * Class that is called when user clicks the 'Generate' button next the password
 * field.
 */

public class GeneratePW {

    /**
     * Generates a random password for the given account name.
     * 
     * @param accountName - the name of the account for which to generate a password
     * @return - the generated password with the account name prepended
     */
    public static String Generate(String accountName) {
        if (accountName.isEmpty()) {
            JOptionPane.showOptionDialog(null,
                    "Please provide an account name/title to use this feature", "No Account Name Provided",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.ERROR_MESSAGE, null, new String[] { "OK" }, null);
            return "";
        }

        SecureRandom random = new SecureRandom();
        String letters = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String symbols = "!@#$^*()_-=+~?/.,";
        String all = letters + letters.toUpperCase() + numbers + symbols;

        // Create random string of characters
        StringBuilder password = new StringBuilder();
        while (password.length() < 26) {
            password.append(all.charAt(random.nextInt(all.length())));
        }

        // Insert one lowercase letter, one uppercase letter, one number, and one symbol
        // into the string
        password.insert(random.nextInt(password.length()), letters.charAt(random.nextInt(letters.length())));
        password.insert(random.nextInt(password.length()),
                letters.toUpperCase().charAt(random.nextInt(letters.length())));
        password.insert(random.nextInt(password.length()), numbers.charAt(random.nextInt(numbers.length())));
        password.insert(random.nextInt(password.length()), symbols.charAt(random.nextInt(symbols.length())));

        return accountName + password.toString();
    }
}