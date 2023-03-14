
// Import Statements
import java.io.File;
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
 * Class that is called when user clicks the 'Delete' button in the Password
 * Manager's menu.
 */

class DeleteAccount {

    /**
     * Method that deletes the selected account from the database.
     *
     * @param selected - the name of the account to be deleted.
     * @param key      - the master password used for authentication.
     */
    static void Delete(String selected, String key) {

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

        // Confirm deletion
        int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure want to delete '" + selected + "'?",
                "Confirm Deletion", JOptionPane.YES_NO_OPTION);

        if (confirmation != JOptionPane.YES_OPTION) {
            return; // return as the user did not confirm the deletion
        }

        try {
            // Parse the XML file
            File xmlFile = new File(PasswordManager.databaseFile);
            Document database = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlFile);

            // Find selected account and delete it
            NodeList accountList = database.getElementsByTagName("account");
            for (int i = 0; i < accountList.getLength(); i++) {
                Element account = (Element) accountList.item(i);
                if (account.getAttribute("name").equals(selected)) {
                    account.getParentNode().removeChild(account);
                    break; // break the loop once the account has been found and deleted
                }
            }

            // Write changes to XML file
            TransformerFactory fileWrite = TransformerFactory.newInstance();
            Transformer newDatabase = fileWrite.newTransformer();
            newDatabase.transform(new DOMSource(database),
                    new StreamResult(new File(PasswordManager.databaseFile)));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}