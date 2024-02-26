package slogo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.exception.XmlException;
import slogo.model.XmlConfiguration;

public class XMLConfigurationTest {

  private XmlConfiguration xmlConfig;

  @BeforeEach
  void setup() {
    xmlConfig = new XmlConfiguration();
  }

  @Test
  void testLoadHelpFileCreatesCorrectMap() {
    String helpFile = "data/commands/command_help_basic.xml";
    String randomCommand = "name: random\nParameter(s): max: The upwards, exclusive bound";
    String randomCommandHelp = randomCommand + "\nalias: rand\ndescription: "
        + "Returns random non-negative number strictly less than max\nexample: rand 10\nreturn: "
        + "random non-negative number strictly less than max\n";
    int numCommands = 6;
    try {
      Map<String, String> helpDocMap = xmlConfig.loadHelpFile(helpFile);
      System.out.println(helpDocMap.get(randomCommand));
      System.out.println(randomCommandHelp);
      assertEquals(numCommands, helpDocMap.keySet().size());
      assertTrue(helpDocMap.containsKey(randomCommand));
      assertEquals(helpDocMap.get(randomCommand), randomCommandHelp);
    } catch (XmlException e) {
      fail();
    }
  }

  @Test
  void testLoadHelpFileThrowsXMLExceptionForNonexistentFile() {
    try {
      Map<String, String> helpDocMap = xmlConfig.loadHelpFile("nonexistent.file");
    } catch (XmlException e) {
      assertEquals(e.getMessage(), "Error parsing nonexistent.file");
    }
  }

  @Test
  void testLoadSession() {
  }

  @Test
  void testSaveSession() {
  }
}
