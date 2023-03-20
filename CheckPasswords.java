
// Import Statements
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;
import javax.swing.ImageIcon;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Class that is called when user clicks the 'Scan Passwords' button in the
 * Password Manager's main menu.
 * The scan checks for password re-use, expired passwords, low password scores,
 * and common passwords.
 */

public class CheckPasswords extends javax.swing.JFrame {

    // GUI components
    private javax.swing.JButton closeButton;
    private javax.swing.JLabel passwordScanTitleLabel;
    private javax.swing.JLabel currentProcessLabel;
    private javax.swing.JLabel percentLabel;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JScrollPane updatesScrollPane;
    private javax.swing.JTextArea updates;

    /**
     * @param key - the master password used for authentication.
     */

    public CheckPasswords(String key) {

        // Verify correct master password
        if (!key.equals(PasswordManager.masterPassword)) {
            System.out.println("Proper Authentication Not Provided!");
            return;
        }

        // Initialize the JFrame
        setTitle("Password Scan");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);
        setVisible(true);
        setIconImage(new ImageIcon(PasswordManager.imageFile).getImage());

        initComponents();
        setLocationRelativeTo(null);
        worker.start(); // Start password scan
    }

    private void initComponents() {

        // Initialize GUI components
        passwordScanTitleLabel = new javax.swing.JLabel();
        currentProcessLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();
        percentLabel = new javax.swing.JLabel();
        updatesScrollPane = new javax.swing.JScrollPane();
        updates = new javax.swing.JTextArea();
        closeButton = new javax.swing.JButton();

        // Set component properties and add event listener to close button
        passwordScanTitleLabel.setFont(new java.awt.Font("Segoe UI", 1, 24));
        passwordScanTitleLabel.setText("Password Scan");

        currentProcessLabel.setFont(new java.awt.Font("sansserif", 1, 12));
        currentProcessLabel.setText("Checking password reuse...");

        progressBar.setValue(0);
        percentLabel.setText("0% Complete!");

        updates.setEditable(false);
        updates.setBackground(new java.awt.Color(255, 255, 255));
        updates.setColumns(20);
        updates.setLineWrap(true);
        updates.setRows(5);
        updates.setWrapStyleWord(true);
        updatesScrollPane.setViewportView(updates);

        closeButton.setText("Cancel");
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });

        // Set component layout and add to JFrame (made using Apache NetBeans IDE 16)
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 102,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(updatesScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        360,
                                                        Short.MAX_VALUE)
                                                .addComponent(percentLabel)
                                                .addComponent(currentProcessLabel)
                                                .addComponent(passwordScanTitleLabel)
                                                .addComponent(progressBar, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addContainerGap(25, Short.MAX_VALUE)));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(passwordScanTitleLabel)
                                .addGap(18, 18, 18)
                                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 21,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(14, 14, 14)
                                .addComponent(percentLabel)
                                .addGap(18, 18, 18)
                                .addComponent(currentProcessLabel)
                                .addGap(18, 18, 18)
                                .addComponent(updatesScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 210,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 42,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(25, Short.MAX_VALUE)));
        pack();
    }

    // When the 'Cancel'/'Close' button is clicked, dispose of the window
    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {
        dispose();
    }

    // Scans the database for password reuse and expiration
    // Creates a new thread because scanning is CPU-intensive
    private Thread worker = new Thread(new Runnable() {
        @Override
        public void run() {
            // Get the database file
            File xmlFile = new File(PasswordManager.databaseFile);

            // - Check for password re-use - //
            try {
                // Create a set to hold the passwords
                Set<String> accountSet = new HashSet<>();
                // Parse the database file
                Document database = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlFile);
                // Get a list of password elements
                NodeList passwordList = database.getElementsByTagName("pass");
                // Get the password for each element and add it to the set
                for (int i = 0; i < passwordList.getLength(); i++) {
                    String password = passwordList.item(i).getAttributes().getNamedItem("pass").getNodeValue();
                    accountSet.add(password);
                }
                // Check if any passwords are the same
                if (accountSet.size() < passwordList.getLength()) { // If no duplicates they will be equal
                    updates.append(
                            "You are re-using passwords! Try to use strong and unique passwords for each of your accounts.\n--");
                    updates.setCaretPosition(updates.getDocument().getLength());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Update the progress bar and labels
            progressBar.setValue(10);
            percentLabel.setText("10% Complete!");
            currentProcessLabel.setText("Checking password renewal dates...");

            // Check for expired passwords
            try {
                // Parse the database file
                Document database = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlFile);

                // Get lists of renewal dates, renewal period elements, and account elements
                NodeList renewalDateList = database.getElementsByTagName("date");
                NodeList renewalPeriodList = database.getElementsByTagName("renewal");
                NodeList accountList = database.getDocumentElement().getElementsByTagName("account");

                // For each account, check the renewal date
                double counter = 0;
                for (int i = 0; i < accountList.getLength(); i++) {
                    counter++;

                    // Update the progress bar + label
                    int amountIncrease = (int) ((double) (counter / accountList.getLength()) * 10);
                    int newAmount = 10 + amountIncrease;
                    progressBar.setValue(newAmount);
                    percentLabel.setText(newAmount + "% Complete!");

                    // Get the current account element
                    Element account = (Element) accountList.item(i);
                    String accountName = account.getAttribute("name");

                    // Ignore accounts that don't have a renewal period
                    var period = renewalPeriodList.item(i).getAttributes().getNamedItem("period").getNodeValue();
                    if (period.equals("0")) {
                        continue;
                    }

                    // Find the number of days between the current date and the renewal deadline
                    LocalDate dateNow = LocalDate.now();
                    var renewal = renewalDateList.item(i).getAttributes().getNamedItem("renewal").getNodeValue();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate dateRenew = LocalDate.parse(renewal, formatter);
                    long daysBetween = ChronoUnit.DAYS.between(dateNow, dateRenew);

                    // Display a message if the renewal deadline has passed or if it's time to
                    // update the password
                    if (dateNow.isAfter(dateRenew)) {
                        updates.append("\nYou have passed your renewal deadline for '" + accountName
                                + "'. A password reset is overdue.\n--");
                        updates.setCaretPosition(updates.getDocument().getLength());
                    } else if (daysBetween <= 7) {
                        updates.append("\nIt's time to update your password for '" + accountName + "'.\n--");
                        updates.setCaretPosition(updates.getDocument().getLength());
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                // Parse the XML file
                Document database = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlFile);

                // Get a list of all the accounts
                NodeList accountList = database.getElementsByTagName("account");

                // Iterate over each account in the list
                double counter = 0;
                for (int i = 0; i < accountList.getLength(); i++) {
                    counter++;

                    // Get the current account
                    Node account = accountList.item(i);
                    String accountName = account.getAttributes().getNamedItem("name").getNodeValue();

                    // Get the password for the current account
                    String password = accountList.item(i).getChildNodes().item(4).getAttributes()
                            .getNamedItem("pass")
                            .getNodeValue();

                    try {
                        // Set the current process label
                        currentProcessLabel.setText(accountName + ": Scoring password...");

                        // Decrypt the password
                        String userPassword = AES.decrypt(password);
                        int passwordScore = 0;

                        // Score the password
                        // +2 points for each character
                        for (int p = 0; p < userPassword.length(); p++) {
                            if (passwordScore > 48) { // cap at 48 points
                            } else {
                                passwordScore += 2;
                            }
                        }
                        // +10 points for three+ lowercase letters, +2 points for one
                        if (userPassword.matches(".*[0-9]{3}.*")) {
                            passwordScore += 10;
                        } else if (userPassword.matches(".*[0-9].*")) {
                            passwordScore += 2;
                        }
                        // +10 points for three+ digits, +2 points for one
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

                        // If the password score is high, skip to the next account
                        if (passwordScore >= 82) {
                            int amountIncrease = (int) ((double) (counter / accountList.getLength()) * 80);
                            int newAmount = 20 + amountIncrease;
                            progressBar.setValue(newAmount);
                            percentLabel.setText(newAmount + "% Complete!");
                            continue;
                        }
                        // If the password score is low, add an update to the text area
                        else if (passwordScore <= 65) {
                            updates.append("\nThe password for '" + accountName + "' received a password score of "
                                    + passwordScore + "/100, a low score. We recommend changing this password.\n--");
                            updates.setCaretPosition(updates.getDocument().getLength());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    // Check with rockyou.txt wordlist
                    try (BufferedReader reader = new BufferedReader(
                            new FileReader(PasswordManager.rockYouFile))) {
                        // Set the current process label
                        currentProcessLabel.setText(accountName + ": Checking rockyou.txt wordlist...");
                        String line;
                        String userPassword = AES.decrypt(password);
                        // Check if decrypted password matches line in wordlist
                        while ((line = reader.readLine()) != null) {
                            // Display warning message if password is found
                            if (line.trim().equals(userPassword)) {
                                updates.append("\n'" + accountName + "'"
                                        + " was found to be using a common password! Hackers could figure it out in seconds.\n--");
                                updates.setCaretPosition(updates.getDocument().getLength());
                                break;
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // Update progress bar + label with new percentage
                    int amountIncrease = (int) ((double) (counter / accountList.getLength()) * 80);
                    int newAmount = 20 + amountIncrease;
                    progressBar.setValue(newAmount);
                    percentLabel.setText(newAmount + "% Complete!");

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Update the process label and button text to show completion
            currentProcessLabel.setText("Results are below.");
            closeButton.setText("Close");

            // If no issue messages are displayed, show positive message
            if (updates.getText().equals("")) {
                updates.setText("Your passwords appear to be secure!");
                progressBar.setValue(100);
                percentLabel.setText("100% Complete!");
            }
        }
    });
}
