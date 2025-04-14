import javax.xml.XMLConstants;
import javax.xml.validation.*;
import javax.xml.transform.stream.StreamSource;
import java.io.File;

public class ValidateXMLSchema {
    public static void main(String[] args) {
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File("farmer_product.xsd"));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File("farmer_product.xml")));
            System.out.println("✅ XML is valid against XSD.");
        } catch (Exception e) {
            System.out.println("❌ Validation failed: " + e.getMessage());
        }
    }
}
