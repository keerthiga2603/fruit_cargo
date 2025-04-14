import javax.xml.parsers.*;
import javax.xml.xpath.*;
import org.w3c.dom.*;
import java.io.*;

public class XPathDemo {
    public static void main(String[] args) {
        try {
            File xmlFile = new File("farmer_product.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);

            XPath xPath = XPathFactory.newInstance().newXPath();

            String farmerName = xPath.evaluate("/Data/Farmer[1]/Name", doc);
            String productRate = xPath.evaluate("/Data/Product[1]/Rate", doc);

            System.out.println("First Farmer's Name: " + farmerName);
            System.out.println("First Product Rate: " + productRate);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
