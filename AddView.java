
// Import Statements
import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Class that adds a view to the selected account.
 */

public class AddView {

    /**
     * Add view
     *
     * @param selected - the name of the selected account.
     */
    public static void Add(String selected) {
        try {
            // Parse the XML database file
            Document database = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                    .parse(new File(PasswordManager.databaseFile));

            // Find the account element with the given name
            NodeList accountList = database.getDocumentElement().getElementsByTagName("account");
            Element accountElement = null;
            for (int i = 0; i < accountList.getLength(); i++) {
                Element element = (Element) accountList.item(i);
                if (element.getAttribute("name").equals(selected)) {
                    accountElement = element;
                    break;
                }
            }

            // If the account element is found, increment its usecount attribute by one
            if (accountElement != null) {
                Element usecountElement = (Element) accountElement.getElementsByTagName("usecount").item(0);
                String countString = usecountElement.getAttribute("count");
                int count = Integer.parseInt(countString) + 1;
                usecountElement.setAttribute("count", Integer.toString(count));
            }

            // Write the updated Document object back to the XML database file
            Transformer updates = TransformerFactory.newInstance().newTransformer();
            DOMSource source = new DOMSource(database);
            StreamResult result = new StreamResult(PasswordManager.databaseFile);
            updates.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}