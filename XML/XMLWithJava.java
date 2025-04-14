import javax.xml.parsers.*;
import org.w3c.dom.*;
import java.io.*;

public class XMLWithJava {
    public static void main(String[] args) {
        try {
            File file = new File("farmer_product.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList farmers = doc.getElementsByTagName("Farmer");
            NodeList products = doc.getElementsByTagName("Product");

            System.out.println("=== Farmer Details ===");
            for (int i = 0; i < farmers.getLength(); i++) {
                Element f = (Element) farmers.item(i);
                System.out.println("ID: " + f.getElementsByTagName("FarmerID").item(0).getTextContent());
                System.out.println("Name: " + f.getElementsByTagName("Name").item(0).getTextContent());
                System.out.println("Location: " + f.getElementsByTagName("Location").item(0).getTextContent());
            }

            System.out.println("\n=== Product Details ===");
            for (int i = 0; i < products.getLength(); i++) {
                Element p = (Element) products.item(i);
                System.out.println("ProductID: " + p.getElementsByTagName("ProductID").item(0).getTextContent());
                System.out.println("FarmerID: " + p.getElementsByTagName("FarmerID").item(0).getTextContent());
                System.out.println("Rate: " + p.getElementsByTagName("Rate").item(0).getTextContent());
                System.out.println("Quantity: " + p.getElementsByTagName("Quantity").item(0).getTextContent());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
