
// Import Statements
import java.awt.GridBagLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.UIManager;

/**
 * A GUI prompts user for the master password (generate your own at
 * https://bcrypt-generator.com/). CHANGING THE MASTER PASSWORD (text, not
 * bcrypt) REMOVES ACCESS TO THE ACCOUNTS.
 * -- If the user enters the correct password, the prompt is replaced by the
 * Password Manager's main menu.
 * -- If the user enters an incorrect password, an error message is displayed
 * and the password field is cleared.
 * -- The user has three attempts. Once they reach this limit, the window
 * closes.
 */

public class PasswordManager extends JFrame {
    // Constants
    public static final String masterPassword = "$2a$12$u/EVN0egor43./KIk6zw6.ON0pOrLyV6B2WT6zFEaeObDbEAjqpEK"; // Password:
                                                                                                                // master
                                                                                                                // --
                                                                                                                // Generate
                                                                                                                // your
                                                                                                                // own:
                                                                                                                // https://bcrypt-generator.com/
    public static final String databaseFile = "accounts.xml";
    public static final String rockYouFile = "rockyou.txt";
    public static final String imageFile = "icon.png";

    // Variables
    private int loginAttempts = 0;
    private JLabel label;
    private JPasswordField passwordField;

    // Main Method
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); // Windows theme
        } catch (Exception e) {
            e.printStackTrace();
        }

        PasswordManager prompt = new PasswordManager();
        prompt.setTitle("Password Manager");
        prompt.setSize(500, 300);
        prompt.setDefaultCloseOperation(EXIT_ON_CLOSE);
        prompt.setLocationRelativeTo(null);
        prompt.setLayout(new GridBagLayout());
        prompt.setVisible(true);
        prompt.setIconImage(new ImageIcon(imageFile).getImage());
    }

    // Constructor
    private PasswordManager() {
        // Create and add components
        label = new JLabel("Master Password:     ");
        add(label);
        passwordField = new JPasswordField(20);
        add(passwordField);

        // Listen for password entry
        passwordField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Retrieve entered password
                char[] password = passwordField.getPassword();
                String passwordString = new String(password);

                // Check if password is correct
                if (org.mindrot.jbcrypt.BCrypt.checkpw(passwordString, PasswordManager.masterPassword)) { // http://www.mindrot.org/projects/jBCrypt/
                    // Hande correct password: send password to AES, dispose window, and open
                    // PasswordManagerMenu
                    AES key = new AES();
                    try {
                        key.setKey(passwordString);
                    } catch (Exception b) {
                        b.printStackTrace();
                    }

                    for (Window window : Window.getWindows()) {
                        window.dispose();
                    }
                    new PasswordManagerMenu(masterPassword);
                } else {
                    // Handle incorrect password
                    loginAttempts++;
                    if (loginAttempts == 3) { // Discourages brute-forcing
                        dispose();
                        JOptionPane.showOptionDialog(null,
                                "Whoops! You've had too many failed login attempts.",
                                "Incorrect Master Password",
                                JOptionPane.DEFAULT_OPTION,
                                JOptionPane.ERROR_MESSAGE, null, new String[] { "OK" }, null);
                        return;
                    }
                    String attempts = "attempts";
                    if (loginAttempts == 2) {
                        attempts = "attempt";
                    }
                    JOptionPane.showOptionDialog(null,
                            "Incorrect Master Password! You have " + (3 - loginAttempts) + " " + attempts
                                    + " remaining.",
                            "Incorrect Master Password",
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.ERROR_MESSAGE, null, new String[] { "OK" }, null);
                    passwordField.setText("");
                }

            }
        });
    }
}