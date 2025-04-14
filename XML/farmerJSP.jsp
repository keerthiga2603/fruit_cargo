<%@ page import="javax.xml.parsers.*, org.w3c.dom.*, java.io.*" %>
<html>
<body>
<h2>Farmer & Product Info</h2>
<%
    File file = new File(application.getRealPath("farmer_product.xml"));
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    Document doc = builder.parse(file);
    doc.getDocumentElement().normalize();

    NodeList farmers = doc.getElementsByTagName("Farmer");
    NodeList products = doc.getElementsByTagName("Product");

    out.println("<h3>Farmers</h3>");
    for (int i = 0; i < farmers.getLength(); i++) {
        Element f = (Element) farmers.item(i);
        out.println("<p>ID: " + f.getElementsByTagName("FarmerID").item(0).getTextContent() + "<br>");
        out.println("Name: " + f.getElementsByTagName("Name").item(0).getTextContent() + "<br>");
        out.println("Location: " + f.getElementsByTagName("Location").item(0).getTextContent() + "</p>");
    }

    out.println("<h3>Products</h3>");
    for (int i = 0; i < products.getLength(); i++) {
        Element p = (Element) products.item(i);
        out.println("<p>ProductID: " + p.getElementsByTagName("ProductID").item(0).getTextContent() + "<br>");
        out.println("FarmerID: " + p.getElementsByTagName("FarmerID").item(0).getTextContent() + "<br>");
        out.println("Rate: " + p.getElementsByTagName("Rate").item(0).getTextContent() + "<br>");
        out.println("Quantity: " + p.getElementsByTagName("Quantity").item(0).getTextContent() + "</p>");
    }
%>
</body>
</html>
