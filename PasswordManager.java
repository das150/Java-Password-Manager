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

/*
 * A GUI prompts user for the master password (which in this case is turtle, an unsecure password that is only used for an example).
 * -- If the user enters the correct password, the prompt is replaced by the Password Manager's main menu.
 * -- If the user enters an incorrect password, an error message is displayed and the password field is cleared.
 * -- The user has five attempts. Once they reach this limit, the window closes.
 */

public class PasswordManager extends JFrame {
    public static final String masterPassword = "$2a$10$kASzua7EUyRa4d8CWY7GxOZruDo0/jMJjQyp8WjwYPlaYU95fQi.e"; // turtle
    private static final String imageFile = "icon.png";
    private int loginAttempts = 0;

    private JLabel label;
    private JPasswordField passwordField;

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

    private PasswordManager() {
        label = new JLabel("Master Password:     ");
        add(label);

        passwordField = new JPasswordField(20);
        add(passwordField);

        // - On user enter - //
        passwordField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                char[] password = passwordField.getPassword();
                String passwordString = new String(password);

                if (org.mindrot.jbcrypt.BCrypt.checkpw(passwordString, PasswordManager.masterPassword)) { // http://www.mindrot.org/projects/jBCrypt/
                    for (Window window : Window.getWindows()) {
                        window.dispose();
                    }
                    new PasswordManagerMenu(masterPassword);

                } else {
                    loginAttempts++;
                    if (loginAttempts == 5) { // Discourages brute-forcing
                        dispose();
                        JOptionPane.showOptionDialog(null,
                                "Whoops! You've had too many failed login attempts.",
                                "Incorrect Master Password",
                                JOptionPane.DEFAULT_OPTION,
                                JOptionPane.ERROR_MESSAGE, null, new String[] { "OK" }, null);
                        return;
                    }

                    String attempts = "attempts";
                    if (loginAttempts == 4) {
                        attempts = "attempt";
                    }

                    JOptionPane.showOptionDialog(null,
                            "Incorrect Master Password! You have " + (5 - loginAttempts) + " " + attempts
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