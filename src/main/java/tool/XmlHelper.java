package tool;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class XmlHelper {

  public void updateFile(String text, String tag, String path) {
    try {
      FileOutputStream file = new FileOutputStream(path);
      Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
      Element tagElement = doc.createElement(tag);
      tagElement.setTextContent(text);
      doc.appendChild(tagElement);
      writeXml(doc, file);
    } catch (ParserConfigurationException | TransformerException | FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  public String getElementFromTag(String tag, String path) {
    try {
      File file = new File(path);
      Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
      return doc.getElementsByTagName(tag).item(0).getTextContent();

    } catch (ParserConfigurationException | SAXException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Writes XML data to the provided output stream.
   *
   * @param doc    the XML document to write
   * @param output the output stream to write to
   * @throws TransformerException if an error occurs during transformation
   */
  private static void writeXml(Document doc, OutputStream output) throws TransformerException {
    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    Transformer transformer = transformerFactory.newTransformer();

    transformer.setOutputProperty(OutputKeys.INDENT, "yes");

    DOMSource source = new DOMSource(doc);
    StreamResult result = new StreamResult(output);

    transformer.transform(source, result);
  }
}