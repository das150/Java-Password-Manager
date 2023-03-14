
// Import Statements
import java.awt.Color;
import java.io.File;
import java.time.LocalDate;
import java.time.Period;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.plaf.metal.MetalProgressBarUI;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Class that is called when the user clicks the 'Update Password' button in the
 * Password Manager's menu.
 */

public class UpdatePassword extends javax.swing.JFrame {

    private javax.swing.JButton generateButton;
    private javax.swing.JButton updatePasswordButton;
    private javax.swing.JLabel accountNameLabel;
    private javax.swing.JLabel confirmLabel;
    private javax.swing.JLabel warningLabel1;
    private javax.swing.JLabel warningLabel2;
    private javax.swing.JLabel newLabel;
    private javax.swing.JPasswordField confirmPasswordField;
    private javax.swing.JPasswordField newPasswordField;
    private javax.swing.JProgressBar scoreBar;

    /**
     * Constructs an UpdatePassword jFrame.
     *
     * @param selected - the selected account name
     * 
     * @param key      - the master password
     */
    public UpdatePassword(String selected, String key) {

        // Verify correct master password
        if (!key.equals(PasswordManager.masterPassword)) {
            System.out.println("Proper Authentication Not Provided!");
            return;
        }

        // Verify the user selected an account
        if (selected == null) {
            JOptionPane.showOptionDialog(null,
                    "You need to select an account to perform that action.", "No Account Selected!",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.ERROR_MESSAGE, null, new String[] { "OK" }, null);
            return; // return as no action can be performed without an account selected
        }

        // Initialize the JFrame
        setTitle("Update Password");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);
        setVisible(true);
        setIconImage(new ImageIcon(PasswordManager.imageFile).getImage());

        initComponents(selected);
        setLocationRelativeTo(null);
    }

    /**
     * Initialize GUI components
     *
     * @param selected - the selected account name
     */
    private void initComponents(String selected) {
        // Initialize GUI components
        scoreBar = new javax.swing.JProgressBar();
        newLabel = new javax.swing.JLabel();
        confirmLabel = new javax.swing.JLabel();
        accountNameLabel = new javax.swing.JLabel();
        warningLabel1 = new javax.swing.JLabel();
        warningLabel2 = new javax.swing.JLabel();
        generateButton = new javax.swing.JButton();
        updatePasswordButton = new javax.swing.JButton();
        confirmPasswordField = new javax.swing.JPasswordField();
        newPasswordField = new javax.swing.JPasswordField();

        // Set component properties and add event listeners
        scoreBar.setUI(new MetalProgressBarUI());
        scoreBar.setMinimum(0);
        scoreBar.setMaximum(100);

        accountNameLabel.setFont(new java.awt.Font("Segoe UI", 1, 24));
        accountNameLabel.setText(selected);

        newLabel.setFont(new java.awt.Font("Segoe UI", 1, 12));
        newLabel.setText("New Password");
        newPasswordField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                newPasswordFieldKeyReleased(evt);
            }
        });

        confirmLabel.setFont(new java.awt.Font("Segoe UI", 1, 12));
        confirmLabel.setText("Confirm New Password");

        generateButton.setText("Generate");
        generateButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                generateButtonMouseClicked(evt, selected);
            }
        });

        warningLabel1.setText("Your previous password will be over-written.");
        warningLabel2.setText("Only continue if you no longer require your old password.");

        updatePasswordButton.setText("Update Password");
        updatePasswordButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                updatePasswordButtonMouseClicked(evt, selected);
            }
        });

        // Set component layout and add to JFrame (made using Apache NetBeans IDE 16)
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(scoreBar, javax.swing.GroupLayout.PREFERRED_SIZE, 227,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(accountNameLabel)
                                        .addComponent(warningLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 345,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(warningLabel2)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(updatePasswordButton,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 334,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING,
                                                                false)
                                                        .addComponent(confirmPasswordField,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                334,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(newLabel)
                                                        .addComponent(confirmLabel)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(newPasswordField,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 227,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(generateButton,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 101,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(accountNameLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(warningLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(warningLabel2)
                                .addGap(30, 30, 30)
                                .addComponent(newLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(generateButton, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(newPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, 32,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(scoreBar, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        8, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(confirmLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(confirmPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, 32,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(updatePasswordButton, javax.swing.GroupLayout.PREFERRED_SIZE, 43,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(27, Short.MAX_VALUE)));

        pack();
    }

    /**
     * Checks the strength of a password and returns a score.
     * 
     * @param userPassword - the password to check
     * 
     * @return - the password score
     */
    private int CheckStrength(String userPassword) {
        int passwordScore = 0;

        // +2 points for each character
        for (int p = 0; p < userPassword.length(); p++) {
            if (passwordScore > 48) { // cap at 48 points
            } else {
                passwordScore += 2;
            }
        }

        // +10 points for three+ digits, +2 points for one
        if (userPassword.matches(".*[0-9]{3}.*")) {
            passwordScore += 10;
        } else if (userPassword.matches(".*[0-9].*")) {
            passwordScore += 2;
        }

        // +10 points for three+ lowercase letters, +2 points for one
        if (userPassword.matches(".*[a-z]{3}.*")) {
            passwordScore += 10;
        } else if (userPassword.matches(".*[a-z].*")) {
            passwordScore += 2;
        }

        // +10 points for three+ uppercase letters, +2 points for one
        if (userPassword.matches(".*[A-Z]{3}.*")) {
            passwordScore += 10;
        } else if (userPassword.matches(".*[A-Z].*")) {
            passwordScore += 2;
        }

        // +20 points for two+ symbols, +2 points for one
        if (userPassword.matches(".*[~!@#$%^&*()_-]{2}.*")) {
            passwordScore += 20;
        } else if (userPassword.matches(".*[~!@#$%^&*()_-].*")) {
            passwordScore += 2;
        }

        // Update the password strength bar
        scoreBar.setValue(passwordScore);

        // Change the color of the bar depending on the score
        if (passwordScore >= 75) {
            scoreBar.setForeground(Color.GREEN);
        } else if (passwordScore >= 45) {
            scoreBar.setForeground(Color.ORANGE);
        } else {
            scoreBar.setForeground(Color.RED);
        }

        return passwordScore;
    }

    /**
     * Generates a password and sets the confirm and new password fields to the
     * generated value.
     * 
     * @param selected - the selected account
     */
    private void generateButtonMouseClicked(java.awt.event.MouseEvent evt, String selected) {
        // Generate the password
        String generatedpw = GeneratePW.Generate(selected);

        // Set the confirm and new password fields to the generated value
        confirmPasswordField.setText(generatedpw);
        newPasswordField.setText(generatedpw);

        // Update the strength of the generated password
        CheckStrength(generatedpw);

        // Update the generate button text
        generateButton.setText("Generated!");

        // Reset the generate button text after 1.5 seconds
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        generateButton.setText("Generate");
                    }
                },
                1500);
    }

    /**
     * Updates the password for the selected account.
     * 
     * @param selected - the selected account
     */
    private void updatePasswordButtonMouseClicked(java.awt.event.MouseEvent evt, String selected) {
        // Check if the new and confirm passwords match
        if ((String.valueOf(newPasswordField.getPassword()))
                .equals(String.valueOf(confirmPasswordField.getPassword()))) {
            // Check if the confirm password field is not empty
            if ((String.valueOf(confirmPasswordField.getPassword())).length() == 0) {
                JOptionPane.showOptionDialog(null,
                        "Password field may not be left blank.", "Error Adding Password",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.ERROR_MESSAGE, null, new String[] { "OK" },
                        null);
            } else {
                // Code for successful submit
                String newAccountPassword = String.valueOf(confirmPasswordField.getPassword());
                String renewalPeriod = "";

                try {
                    // Parse the XML database file
                    File xmlFile = new File(PasswordManager.databaseFile);
                    Document database = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlFile);
                    Element root = database.getDocumentElement();

                    // Find the selected account element in the XML file
                    NodeList accountList = root.getElementsByTagName("account");
                    Element accountElement = null;
                    for (int i = 0; i < accountList.getLength(); i++) {
                        Element element = (Element) accountList.item(i);
                        if (element.getAttribute("name").equals(selected)) {
                            accountElement = element;
                            break;
                        }
                    }

                    // Get the renewal period and calculate the new renewal date
                    Element usecountElement = (Element) accountElement.getElementsByTagName("renewal").item(0);
                    renewalPeriod = usecountElement.getAttribute("period");

                    LocalDate dateNow = LocalDate.now();
                    LocalDate newRenewalDate = dateNow
                            .plus(Period.ofMonths(Integer.parseInt(renewalPeriod)));

                    // Update the account password
                    Element passwordElement = (Element) accountElement.getElementsByTagName("pass").item(0);
                    String oldAccountPassword = passwordElement.getAttribute("pass");
                    if ((AES.decrypt(oldAccountPassword)).equals(newAccountPassword)) {
                        JOptionPane.showOptionDialog(null,
                                "Your new password can not be the same as your old one.", "Error Updating Password",
                                JOptionPane.DEFAULT_OPTION,
                                JOptionPane.ERROR_MESSAGE, null, new String[] { "OK" }, null);

                        return;
                    } else {
                        passwordElement.setAttribute("pass", AES.encrypt(newAccountPassword));
                    }

                    // Update the account renewal date
                    Element renewalElement = (Element) accountElement.getElementsByTagName("date").item(0);
                    renewalElement.setAttribute("renewal", newRenewalDate.toString());

                    // Write the changes to the XML file
                    Transformer newDatabase = TransformerFactory.newInstance().newTransformer();
                    DOMSource source = new DOMSource(database);
                    StreamResult result = new StreamResult(xmlFile);
                    newDatabase.transform(source, result);

                    dispose();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            JOptionPane.showOptionDialog(null,
                    "The entered passwords do not match! Please try again.", "Error Updating Password",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.ERROR_MESSAGE, null, new String[] { "OK" }, null);
        }
    }

    // Call the CheckStrength method to update the password strength indicator
    private void newPasswordFieldKeyReleased(java.awt.event.KeyEvent evt) {
        CheckStrength(String.valueOf(newPasswordField.getPassword()));
    }
}