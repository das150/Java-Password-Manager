
// Import Statements
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.ListSelectionModel;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * The Password Manager's menu.
 */

public class PasswordManagerMenu extends JFrame {

    private final DefaultListModel<String> accountListModel = new DefaultListModel<>();

    // GUI components
    private javax.swing.JButton viewDetailsButton;
    private javax.swing.JButton updatePasswordButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.JButton addAccountButton;
    private javax.swing.JButton checkPasswordButton;
    private javax.swing.JLabel accountTitleLabel;
    private javax.swing.JLabel accountDirectionsLabel;
    private javax.swing.JLabel filterListLabel;
    private javax.swing.JLabel alternateLabel;
    private javax.swing.JList<String> listAccounts;
    private javax.swing.JScrollPane listScrollPane;
    private javax.swing.JTextField searchField;

    // Constructor for PasswordManagerMenu
    public PasswordManagerMenu(String key) {
        // Verify correct master password
        if (!key.equals(PasswordManager.masterPassword)) {
            System.out.println("Proper Authentication Not Provided!");
            return;
        }

        // Initialize the JFrame
        setTitle("Password Manager Menu");
        setSize(500, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setVisible(true);
        setIconImage(new ImageIcon(PasswordManager.imageFile).getImage());

        initComponents();
        setLocationRelativeTo(null);
        getAccounts().forEach(accountListModel::addElement);
    }

    // Initialize GUI components
    private void initComponents() {
        listScrollPane = new javax.swing.JScrollPane();
        listAccounts = new javax.swing.JList<>();
        accountTitleLabel = new javax.swing.JLabel();
        accountDirectionsLabel = new javax.swing.JLabel();
        filterListLabel = new javax.swing.JLabel();
        alternateLabel = new javax.swing.JLabel();
        viewDetailsButton = new javax.swing.JButton();
        updatePasswordButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        addAccountButton = new javax.swing.JButton();
        checkPasswordButton = new javax.swing.JButton();
        searchField = new javax.swing.JTextField();

        // Set component properties and add event listeners
        accountTitleLabel.setFont(new java.awt.Font("Segoe UI", 1, 24));
        accountTitleLabel.setText("Accounts");

        accountDirectionsLabel.setText("Select an account to perform an action:");

        listAccounts.setModel(accountListModel);
        listAccounts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        listScrollPane.setViewportView(listAccounts);

        filterListLabel.setFont(new java.awt.Font("Segoe UI", 1, 14));
        filterListLabel.setText("Filter List:");

        searchField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchFieldKeyReleased(evt);
            }
        });

        viewDetailsButton.setText("View Details");
        viewDetailsButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                viewDetailsButtonMouseClicked(evt);
            }
        });

        updatePasswordButton.setText("Update Password");
        updatePasswordButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                updatePasswordButtonMouseClicked(evt);
            }
        });

        deleteButton.setText("Delete");
        deleteButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deleteButtonMouseClicked(evt);
            }
        });

        alternateLabel.setText("Alternatively, you can add an account to this list or run a basic password scan:");

        addAccountButton.setText("Add Account");
        addAccountButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addAccountButtonMouseClicked(evt);
            }
        });

        checkPasswordButton.setText("Scan Passwords");
        checkPasswordButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                checkPasswordButtonMouseClicked(evt);
            }
        });

        // Set component layout and add to JFrame (made using Apache NetBeans IDE 16)
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(addAccountButton, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        147,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(checkPasswordButton,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 147,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(alternateLabel)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(filterListLabel)
                                                .addGap(18, 18, 18)
                                                .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        266,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(accountDirectionsLabel)
                                        .addComponent(accountTitleLabel)
                                        .addComponent(listScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 705,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(viewDetailsButton, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        147,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(updatePasswordButton,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 147,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 147,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(31, Short.MAX_VALUE)));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addComponent(accountTitleLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(accountDirectionsLabel)
                                .addGap(21, 21, 21)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(filterListLabel)
                                        .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, 35,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(15, 15, 15)
                                .addComponent(listScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(viewDetailsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 33,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(updatePasswordButton, javax.swing.GroupLayout.PREFERRED_SIZE, 33,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 33,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(35, 35, 35)
                                .addComponent(alternateLabel)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(addAccountButton, javax.swing.GroupLayout.PREFERRED_SIZE, 33,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(checkPasswordButton, javax.swing.GroupLayout.PREFERRED_SIZE, 33,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(37, Short.MAX_VALUE)));
        pack();
    }

    // Get the list of accounts
    private List<String> getAccounts() {
        List<String> accounts = new ArrayList<>();

        try {
            // Parse the XML file
            File xmlFile = new File(PasswordManager.databaseFile);
            Document database = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlFile);

            // Form a list of the account elements
            NodeList accountList = database.getElementsByTagName("account");

            // Create a map of account names and usage counts
            HashMap<String, Integer> accountMap = new HashMap<>();

            // Run for every account element
            for (int a = 0; a < accountList.getLength(); a++) {
                Node accountNode = accountList.item(a);
                Element accountElement = (Element) accountNode;

                String accountName = accountElement.getAttribute("name");
                NodeList accountDetails = accountNode.getChildNodes();

                // Run for every child node of the account element
                for (int b = 0; b < accountDetails.getLength(); b++) {
                    Element usecountElement = (Element) accountDetails.item(b);

                    // Make sure there is a value for the count attribute
                    if (usecountElement.hasAttribute("count")) {
                        // Add the account name and usage count to the map
                        int accountUse = Integer.parseInt(usecountElement.getAttribute("count"));
                        accountMap.put(accountName, accountUse);
                    }
                }
            }

            // Sort map by usage count
            List<Map.Entry<String, Integer>> accountDisplayList = new ArrayList<>(accountMap.entrySet());
            accountDisplayList.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

            // Add the account names to the list to display
            for (Map.Entry<String, Integer> accountEntry : accountDisplayList) {
                accounts.add(accountEntry.getKey());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accounts;
    }

    // Add accounts containing search term to filtered list model
    private void searchFilter(String searchTerm) {
        DefaultListModel<String> filteredAccounts = new DefaultListModel<>();
        getAccounts().stream().filter(account -> account.toUpperCase().contains(searchTerm.toUpperCase()))
                .forEach(filteredAccounts::addElement);
        listAccounts.setModel(filteredAccounts);
    }

    // Event listener for searchField
    private void searchFieldKeyReleased(java.awt.event.KeyEvent evt) {
        searchFilter(searchField.getText());
    }

    // Event listener for viewDetailsButton
    private void viewDetailsButtonMouseClicked(java.awt.event.MouseEvent evt) {
        ViewDetails info = new ViewDetails(listAccounts.getSelectedValue(), PasswordManager.masterPassword);

        // Update order of accounts in list (based on new viewcount)
        info.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                searchFilter(searchField.getText());
            }
        });
    }

    // Event listener for updatePasswordButton
    private void updatePasswordButtonMouseClicked(java.awt.event.MouseEvent evt) {
        new UpdatePassword(listAccounts.getSelectedValue(), PasswordManager.masterPassword);
    }

    // Event listener for deleteButton
    private void deleteButtonMouseClicked(java.awt.event.MouseEvent evt) {
        DeleteAccount.Delete(listAccounts.getSelectedValue(), PasswordManager.masterPassword);

        // Update accounts in list
        searchFilter(searchField.getText());
    }

    // Event listener for addAccountButton
    private void addAccountButtonMouseClicked(java.awt.event.MouseEvent evt) {
        AddAccount add = new AddAccount(PasswordManager.masterPassword);

        // Update accounts in list
        add.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                searchFilter(searchField.getText());
            }
        });
    }

    // Event listener for checkPasswordButton
    private void checkPasswordButtonMouseClicked(java.awt.event.MouseEvent evt) {
        new CheckPasswords(PasswordManager.masterPassword);
    }
}