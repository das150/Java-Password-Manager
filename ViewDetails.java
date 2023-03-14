
// Import Statements
import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Class that is called when user clicks the 'View Details' button in the
 * Password Manager's menu.
 */

public class ViewDetails extends javax.swing.JFrame {

        private javax.swing.JButton openURLButton;
        private javax.swing.JButton showButton;
        private javax.swing.JButton editButton;
        private javax.swing.JButton copyEmailButton;
        private javax.swing.JButton copyUsernameButton;
        private javax.swing.JButton copyPasswordButton;
        private javax.swing.JButton copyNotesButton;
        private javax.swing.JLabel accountNameLabel;
        private javax.swing.JLabel notesLabel;
        private javax.swing.JLabel creationLabel;
        private javax.swing.JLabel renewalPeriodLabel;
        private javax.swing.JLabel renewalDeadlineLabel;
        private javax.swing.JLabel renewalStatusLabel;
        private javax.swing.JLabel urlLabel;
        private javax.swing.JLabel emailLabel;
        private javax.swing.JLabel usernameLabel;
        private javax.swing.JLabel passwordLabel;
        private javax.swing.JPanel expirationPanel;
        private javax.swing.JPanel jPanel;
        private javax.swing.JScrollPane notesScrollPane;
        private javax.swing.JTextArea notesTextArea;
        private javax.swing.JTextField urlField;
        private javax.swing.JTextField usernameField;
        private javax.swing.JTextField passwordField;
        private javax.swing.JTextField emailField;

        /**
         * @param selected - the name of the account to be deleted.
         * @param key      - the master password used for authentication.
         */
        public ViewDetails(String selected, String key) {

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
                setTitle("View Details");
                setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                setLayout(null);
                setVisible(true);
                setIconImage(new ImageIcon(PasswordManager.imageFile).getImage());

                initComponents(selected);
                setLocationRelativeTo(null);
                AddView.Add(selected);
        }

        private void initComponents(String selected) {

                // Variables
                String url = null;
                String email = null;
                String username = null;
                String password = null;
                String notes = null;
                String creation = null;
                String renewalPeriod = null;
                String renewalDate = null;

                try {
                        // Parse the XML database file
                        File xmlFile = new File(PasswordManager.databaseFile);
                        Document database = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlFile);

                        // Retrieve the account elements from the database
                        NodeList elements = database.getDocumentElement().getElementsByTagName("account");

                        // Iterate through the account elements to find the selected account
                        for (int i = 0; i < elements.getLength(); i++) {
                                Element accountElements = (Element) elements.item(i);
                                String name = accountElements.getAttribute("name");

                                if (name.equals(selected)) {
                                        // Retrieve the URL, email, username, password, notes, creation date, renewal
                                        // period,
                                        // and renewal date for the selected account
                                        Element urlElement = (Element) accountElements.getElementsByTagName("url")
                                                        .item(0);
                                        url = AES.decrypt(urlElement.getAttribute("link"));

                                        Element emailElement = (Element) accountElements.getElementsByTagName("email")
                                                        .item(0);
                                        email = AES.decrypt(emailElement.getAttribute("address"));

                                        Element usernameElement = (Element) accountElements.getElementsByTagName("user")
                                                        .item(0);
                                        username = AES.decrypt(usernameElement.getAttribute("user"));

                                        Element passwordElement = (Element) accountElements.getElementsByTagName("pass")
                                                        .item(0);
                                        password = AES.decrypt(passwordElement.getAttribute("pass"));

                                        Element notesElement = (Element) accountElements.getElementsByTagName("note")
                                                        .item(0);
                                        notes = AES.decrypt(notesElement.getAttribute("note"));

                                        Element creationElement = (Element) accountElements
                                                        .getElementsByTagName("creation")
                                                        .item(0);
                                        creation = creationElement.getAttribute("date");

                                        Element periodElement = (Element) accountElements
                                                        .getElementsByTagName("renewal")
                                                        .item(0);
                                        renewalPeriod = periodElement.getAttribute("period");

                                        Element deadlineElement = (Element) accountElements.getElementsByTagName("date")
                                                        .item(0);
                                        renewalDate = deadlineElement.getAttribute("renewal");

                                        break; // Stop iterating once the selected account has been found
                                }
                        }
                } catch (Exception e) {
                        e.printStackTrace();
                }

                // Initialize GUI components
                jPanel = new javax.swing.JPanel();
                accountNameLabel = new javax.swing.JLabel();
                urlField = new javax.swing.JTextField();
                openURLButton = new javax.swing.JButton();
                usernameField = new javax.swing.JTextField();
                copyUsernameButton = new javax.swing.JButton();
                passwordField = new javax.swing.JTextField();
                copyPasswordButton = new javax.swing.JButton();
                showButton = new javax.swing.JButton();
                editButton = new javax.swing.JButton();
                copyNotesButton = new javax.swing.JButton();
                notesScrollPane = new javax.swing.JScrollPane();
                notesTextArea = new javax.swing.JTextArea();
                creationLabel = new javax.swing.JLabel();
                emailField = new javax.swing.JTextField();
                copyEmailButton = new javax.swing.JButton();
                expirationPanel = new javax.swing.JPanel();
                renewalPeriodLabel = new javax.swing.JLabel();
                renewalDeadlineLabel = new javax.swing.JLabel();
                renewalStatusLabel = new javax.swing.JLabel();
                urlLabel = new javax.swing.JLabel();
                emailLabel = new javax.swing.JLabel();
                usernameLabel = new javax.swing.JLabel();
                passwordLabel = new javax.swing.JLabel();
                notesLabel = new javax.swing.JLabel();

                // If renewal period is 0, hide the expiration panel
                if (renewalPeriod.equals("0")) {
                        expirationPanel.setVisible(false);
                }

                // Set the account name label
                accountNameLabel.setFont(new java.awt.Font("Segoe UI", 1, 24));
                accountNameLabel.setText(selected);

                // Set the URL field and open URL button
                urlField.setEditable(false);
                urlField.setText(url);
                openURLButton.setText("Open");
                openURLButton.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                openURLButtonMouseClicked(evt);
                        }
                });

                // Set the username field and copy username button
                usernameField.setEditable(false);
                usernameField.setText(username);
                copyUsernameButton.setText("Copy");
                copyUsernameButton.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                copyUsernameButtonMouseClicked(evt);
                        }
                });

                // Set the password field, show button, and copy password button
                passwordField.setEditable(false);
                String asterisks = "*".repeat(password.length());
                passwordField.setText(asterisks);
                String sendPassword = password;
                showButton.setText("Show");
                showButton.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                showButtonMouseClicked(sendPassword, evt);
                        }
                });
                copyPasswordButton.setText("Copy");
                copyPasswordButton.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                copyPasswordButtonMouseClicked(sendPassword, evt);
                        }
                });

                // Set the notes text area, copy notes button, and edit notes button
                notesTextArea.setEditable(false);
                notesTextArea.setLineWrap(true);
                notesTextArea.setWrapStyleWord(true);
                notesTextArea.setForeground(new java.awt.Color(66, 66, 66));
                notesTextArea.setColumns(20);
                notesTextArea.setRows(5);
                notesTextArea.setText(notes);
                notesScrollPane.setViewportView(notesTextArea);
                copyNotesButton.setText("Copy");
                copyNotesButton.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                copyNotesButtonMouseClicked(evt);
                        }
                });
                editButton.setText("Edit");
                editButton.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                editButtonMouseClicked(selected, evt);
                        }
                });

                // Set the email field and copy email button
                emailField.setEditable(false);
                emailField.setText(email);
                copyEmailButton.setText("Copy");
                copyEmailButton.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                copyEmailButtonMouseClicked(evt);
                        }
                });

                // Set expiration panel
                expirationPanel.setBackground(new java.awt.Color(255, 255, 255));

                // Set labels
                creationLabel.setForeground(new java.awt.Color(102, 102, 102));
                creationLabel.setText("Account created on " + creation);
                renewalPeriodLabel.setFont(new java.awt.Font("sansserif", 1, 12));
                renewalPeriodLabel.setText("This account is set to renew every " + renewalPeriod + " months.");
                renewalDeadlineLabel.setText("The current renewal deadline is " + renewalDate + ".");
                renewalStatusLabel.setFont(new java.awt.Font("sansserif", 1, 12));
                urlLabel.setFont(new java.awt.Font("sansserif", 1, 12));
                urlLabel.setText("URL");
                emailLabel.setFont(new java.awt.Font("sansserif", 1, 12));
                emailLabel.setText("Email");
                usernameLabel.setFont(new java.awt.Font("sansserif", 1, 12));
                usernameLabel.setText("Username");
                passwordLabel.setFont(new java.awt.Font("sansserif", 1, 12));
                passwordLabel.setText("Password");
                notesLabel.setFont(new java.awt.Font("sansserif", 1, 12));
                notesLabel.setText("Notes");

                // Parse the renewal date and the current date
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate DateRenew = LocalDate.parse(renewalDate, formatter);
                LocalDate DateNow = LocalDate.now();

                // Calculate the number of days between the renewal date and the current date
                long daysbetween = ChronoUnit.DAYS.between(DateNow, DateRenew);

                // Check if the current date is after the renewal date
                if (DateNow.isAfter(DateRenew)) {
                        // If so, set the color and text to indicate that the renewal deadline has
                        // passed
                        renewalStatusLabel.setForeground(new java.awt.Color(255, 0, 0));
                        renewalStatusLabel.setText("You have passed this deadline. Please reset your password.");
                } else {
                        // If not, check the number of days between the renewal date and the current
                        // date
                        if (daysbetween <= 7) {
                                // If the number of days is less than or equal to 7, set the color and text to
                                // indicate that it's time to update the password
                                renewalStatusLabel.setForeground(new java.awt.Color(255, 140, 0));
                                renewalStatusLabel.setText("It's time to update your password.");
                        } else {
                                // If the number of days is greater than 7, set the color and text to indicate
                                // that the renewal deadline is more than a week away
                                renewalStatusLabel.setForeground(new java.awt.Color(15, 74, 7));
                                renewalStatusLabel.setText("This deadline is more than a week away.");
                        }
                }

                // Set component layout and add to JFrame (made using Apache NetBeans IDE 16)
                javax.swing.GroupLayout expirationPanelLayout = new javax.swing.GroupLayout(expirationPanel);
                expirationPanel.setLayout(expirationPanelLayout);
                expirationPanelLayout.setHorizontalGroup(
                                expirationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(expirationPanelLayout.createSequentialGroup()
                                                                .addGap(23, 23, 23)
                                                                .addGroup(expirationPanelLayout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(renewalStatusLabel)
                                                                                .addComponent(renewalDeadlineLabel)
                                                                                .addComponent(renewalPeriodLabel))
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)));
                expirationPanelLayout.setVerticalGroup(
                                expirationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(expirationPanelLayout.createSequentialGroup()
                                                                .addGap(17, 17, 17)
                                                                .addComponent(renewalPeriodLabel)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(renewalDeadlineLabel)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(renewalStatusLabel)
                                                                .addContainerGap(19, Short.MAX_VALUE)));

                javax.swing.GroupLayout jPanelLayout = new javax.swing.GroupLayout(jPanel);
                jPanel.setLayout(jPanelLayout);
                jPanelLayout.setHorizontalGroup(
                                jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanelLayout.createSequentialGroup()
                                                                .addGap(20, 20, 20)
                                                                .addGroup(jPanelLayout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(jPanelLayout
                                                                                                .createParallelGroup(
                                                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                                false)
                                                                                                .addGroup(jPanelLayout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                .addGroup(jPanelLayout
                                                                                                                                .createSequentialGroup()
                                                                                                                                .addComponent(creationLabel)
                                                                                                                                .addContainerGap(
                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                Short.MAX_VALUE))
                                                                                                                .addGroup(jPanelLayout
                                                                                                                                .createSequentialGroup()
                                                                                                                                .addGroup(jPanelLayout
                                                                                                                                                .createParallelGroup(
                                                                                                                                                                javax.swing.GroupLayout.Alignment.TRAILING)
                                                                                                                                                .addComponent(expirationPanel,
                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                Short.MAX_VALUE)
                                                                                                                                                .addGroup(jPanelLayout
                                                                                                                                                                .createSequentialGroup()
                                                                                                                                                                .addComponent(notesScrollPane,
                                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                335,
                                                                                                                                                                                Short.MAX_VALUE)
                                                                                                                                                                .addPreferredGap(
                                                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                                                .addGroup(jPanelLayout
                                                                                                                                                                                .createParallelGroup(
                                                                                                                                                                                                javax.swing.GroupLayout.Alignment.TRAILING)
                                                                                                                                                                                .addComponent(editButton,
                                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                                120,
                                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                                                                .addGap(26, 26, 26) ///
                                                                                                                                                                                .addComponent(copyNotesButton,
                                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                                120,
                                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))))
                                                                                                                                .addGap(31, 31, 31)))
                                                                                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                                jPanelLayout
                                                                                                                                .createSequentialGroup()
                                                                                                                                .addComponent(passwordField,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                209,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                .addPreferredGap(
                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                .addComponent(showButton,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                120,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                .addPreferredGap(
                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                .addComponent(copyPasswordButton,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                120,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                                .addGroup(jPanelLayout
                                                                                                .createSequentialGroup()
                                                                                                .addGroup(jPanelLayout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                .addComponent(notesLabel)
                                                                                                                .addComponent(passwordLabel)
                                                                                                                .addComponent(usernameLabel)
                                                                                                                .addComponent(emailLabel)
                                                                                                                .addComponent(urlLabel)
                                                                                                                .addGroup(jPanelLayout
                                                                                                                                .createSequentialGroup()
                                                                                                                                .addComponent(emailField,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                335,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                .addPreferredGap(
                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                .addComponent(copyEmailButton,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                120,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                                .addGroup(jPanelLayout
                                                                                                                                .createSequentialGroup()
                                                                                                                                .addComponent(usernameField,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                335,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                .addPreferredGap(
                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                .addComponent(copyUsernameButton,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                120,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                                .addComponent(accountNameLabel)
                                                                                                                .addGroup(jPanelLayout
                                                                                                                                .createSequentialGroup()
                                                                                                                                .addComponent(urlField,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                335,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                .addPreferredGap(
                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                .addComponent(openURLButton,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                120,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                                                .addContainerGap(31,
                                                                                                                Short.MAX_VALUE)))));
                jPanelLayout.setVerticalGroup(
                                jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanelLayout.createSequentialGroup()
                                                                .addGap(23, 23, 23)
                                                                .addComponent(accountNameLabel)
                                                                .addGap(26, 26, 26)
                                                                .addComponent(urlLabel)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(jPanelLayout
                                                                                .createParallelGroup(
                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                false)
                                                                                .addComponent(openURLButton,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(urlField,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                30,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGap(18, 18, 18)
                                                                .addComponent(emailLabel)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(jPanelLayout
                                                                                .createParallelGroup(
                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                false)
                                                                                .addComponent(copyEmailButton,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(emailField,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                30,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGap(18, 18, 18)
                                                                .addComponent(usernameLabel)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(jPanelLayout
                                                                                .createParallelGroup(
                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                false)
                                                                                .addComponent(copyUsernameButton,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(usernameField,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                30,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGap(18, 18, 18)
                                                                .addComponent(passwordLabel)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(jPanelLayout
                                                                                .createParallelGroup(
                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                false)
                                                                                .addComponent(showButton,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(copyPasswordButton,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(passwordField,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                30,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGap(18, 18, 18)

                                                                .addComponent(notesLabel)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(jPanelLayout
                                                                                .createParallelGroup(
                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                false)
                                                                                .addGroup(jPanelLayout
                                                                                                .createSequentialGroup()
                                                                                                .addGroup(jPanelLayout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                .addComponent(editButton,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                30,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                .addGroup(jPanelLayout
                                                                                                                                .createSequentialGroup()
                                                                                                                                .addGap(35)
                                                                                                                                .addComponent(copyNotesButton,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                30,
                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                                                                .addComponent(notesScrollPane,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                105,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGap(18, 18, 18)

                                                                .addComponent(creationLabel)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(expirationPanel,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addContainerGap(32, Short.MAX_VALUE)));

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                getContentPane().setLayout(layout);
                layout.setHorizontalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGap(0, 518, Short.MAX_VALUE)
                                                .addGroup(layout.createParallelGroup(
                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addGroup(layout.createSequentialGroup()
                                                                                .addGap(0, 0, Short.MAX_VALUE)
                                                                                .addComponent(jPanel,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addGap(0, 0, Short.MAX_VALUE))));
                layout.setVerticalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGap(0, 668, Short.MAX_VALUE)
                                                .addGroup(layout.createParallelGroup(
                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addGroup(layout.createSequentialGroup()
                                                                                .addGap(0, 0, Short.MAX_VALUE)
                                                                                .addComponent(jPanel,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addGap(0, 0, Short.MAX_VALUE))));

                pack();
        }

        // Open the URL in the default web browser
        private void openURLButtonMouseClicked(java.awt.event.MouseEvent evt) {
                // Display an error message if the URL field is empty
                if (urlField.getText().length() == 0) {
                        JOptionPane.showOptionDialog(null,
                                        "Website not found!", "No Url Provided",
                                        JOptionPane.DEFAULT_OPTION,
                                        JOptionPane.ERROR_MESSAGE, null, new String[] { "OK" }, null);
                        return;
                }

                try {
                        // Check if the URL starts with "http://" or "https://"
                        if (urlField.getText().startsWith("https://")
                                        || urlField.getText().startsWith("http://")) {
                                // Open the URL in the default web browser
                                Desktop.getDesktop().browse(URI.create(urlField.getText()));
                        } else {
                                // Prepend "https://" to the URL and open it in the default web browser
                                Desktop.getDesktop().browse(URI.create("https://" + urlField.getText()));
                        }
                } catch (Exception e) {
                        e.printStackTrace();
                }
        }

        // Copy the username to the clipboard
        private void copyUsernameButtonMouseClicked(java.awt.event.MouseEvent evt) {
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(new StringSelection(usernameField.getText()), null);

                copyUsernameButton.setText("Copied!");
                // Schedule a timer to reset the button text to "Copy" after 1.5 seconds
                new java.util.Timer().schedule(
                                new java.util.TimerTask() {
                                        @Override
                                        public void run() {
                                                copyUsernameButton.setText("Copy");
                                        }
                                },
                                1500);
        }

        // Copy the password to the clipboard
        private void copyPasswordButtonMouseClicked(String password, java.awt.event.MouseEvent evt) {
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(new StringSelection(password), null);

                copyPasswordButton.setText("Copied!");
                // Schedule a timer to reset the button text to "Copy" after 1.5 seconds
                new java.util.Timer().schedule(
                                new java.util.TimerTask() {
                                        @Override
                                        public void run() {
                                                copyPasswordButton.setText("Copy");
                                        }
                                },
                                1500);
        }

        // Toggles showing or hiding the password
        private void showButtonMouseClicked(String password, java.awt.event.MouseEvent evt) {
                if (showButton.getText().equals("Show")) {
                        passwordField.setText(password);
                        showButton.setText("Hide");
                } else {
                        String asterisks = "*".repeat(password.length());
                        passwordField.setText(asterisks);
                        showButton.setText("Show");
                }
        }

        // Copy the notes to the clipboard
        private void copyNotesButtonMouseClicked(java.awt.event.MouseEvent evt) {
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(new StringSelection(notesTextArea.getText()), null);

                copyNotesButton.setText("Copied!");

                // Schedule a timer to reset the button text to "Copy" after 1.5 seconds
                new java.util.Timer().schedule(
                                new java.util.TimerTask() {
                                        @Override
                                        public void run() {
                                                copyNotesButton.setText("Copy");
                                        }
                                },
                                1500);
        }

        // Copy the email to the clipboard
        private void copyEmailButtonMouseClicked(java.awt.event.MouseEvent evt) {
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(new StringSelection(emailField.getText()), null);

                copyEmailButton.setText("Copied!");

                // Schedule a timer to reset the button text to "Copy" after 1.5 seconds
                new java.util.Timer().schedule(
                                new java.util.TimerTask() {
                                        @Override
                                        public void run() {
                                                copyEmailButton.setText("Copy");
                                        }
                                },
                                1500);
        }

        // Allows user to edit the notes and updates the database when the "Update"
        // button is clicked
        private void editButtonMouseClicked(String selected, java.awt.event.MouseEvent evt) {
                if (editButton.getText().equals("Edit")) {
                        // Enable editing of notes and change button text to "Update"
                        notesTextArea.setEditable(true);
                        notesTextArea.setForeground(new java.awt.Color(0, 0, 0));
                        notesTextArea.setFocusable(true);
                        notesTextArea.requestFocus();
                        editButton.setText("Update");
                } else {
                        // Disable editing of notes and update the database with the new notes
                        notesTextArea.setEditable(false);
                        notesTextArea.setFocusable(false);
                        try {
                                File xmlFile = new File(PasswordManager.databaseFile);
                                Document database = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                                                .parse(xmlFile);
                                Element root = database.getDocumentElement();
                                NodeList accountList = root.getElementsByTagName("account");
                                Element accountElement = null;
                                // Find the account element with the selected name attribute
                                for (int i = 0; i < accountList.getLength(); i++) {
                                        Element element = (Element) accountList.item(i);
                                        if (element.getAttribute("name").equals(selected)) {
                                                accountElement = element;
                                                break;
                                        }
                                }
                                // Update the note element with the encrypted notes text
                                Element noteElement = (Element) accountElement.getElementsByTagName("note").item(0);
                                noteElement.setAttribute("note", AES.encrypt(notesTextArea.getText()));
                                // Write the updated database to the XML file and update button text
                                Transformer newDatabase = TransformerFactory.newInstance().newTransformer();
                                DOMSource source = new DOMSource(database);
                                StreamResult result = new StreamResult(xmlFile);
                                newDatabase.transform(source, result);
                                editButton.setText("Updated!");
                                // Schedule a timer to reset the button text to "Edit" after 1.5 seconds
                                new java.util.Timer().schedule(
                                                new java.util.TimerTask() {
                                                        @Override
                                                        public void run() {
                                                                editButton.setText("Edit");
                                                                notesTextArea.setFocusable(false);
                                                        }
                                                },
                                                1500);
                                notesTextArea.setForeground(new java.awt.Color(66, 66, 66));
                        } catch (Exception e) {
                                e.printStackTrace();
                                editButton.setText("ERROR");
                        }
                }
        }
}