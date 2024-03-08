package slogo.model.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import slogo.model.api.exception.XmlException;

public class XMLConfigurationTest {

  private XmlConfiguration xmlConfig;

  @BeforeEach
  void setup() {
    xmlConfig = new XmlConfiguration();
  }

  @Test
  void testLoadHelpFileCreatesCorrectMap() {
    String helpFile = "data/commands/command_help_basic.xml";
    String randomCommand = "Name: random\nParameter(s): max: The upwards, exclusive bound";
    String randomCommandHelp = randomCommand + "\nAlias: rand\nDescription: "
        + "Generates random non-negative number strictly less than max"
        + "\nExample: rand 10 (could return 4)\nReturn: "
        + "random non-negative number strictly less than max\n";
    int numCommands = 58;
    try {
      Map<String, String> helpDocMap = xmlConfig.loadHelpFile(helpFile);

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
      xmlConfig.loadHelpFile("nonexistent.file");
    } catch (XmlException e) {
      assertEquals(e.getMessage(), "Error parsing nonexistent.file");
    }
  }

  @Test
  void testLoadSessionCreatesCorrectSession() {
    try {
      List<String> expectedCommands = Arrays.asList(
          "setxy -300 0",
          "repeat 8 [forward 50 repeat 3  [fd 50 rt 120 ]]",
          "setxy -300 0",
          "repeat 8 [forward 50 repeat 3  [fd 50 lt 120 ]]"
      );

      List<String> executedCommands = xmlConfig.loadSessionFromFile(
          "data/examples/loops/doubleTriangle.slogo");

      assertEquals(expectedCommands, executedCommands);
    } catch (XmlException e) {
      fail();
    }
  }

  @Test
  void testLoadSessionThrowsXMLExceptionForNonexistentFile() {
    try {
      xmlConfig.loadSession("nonexistent.file");
    } catch (XmlException e) {
      assertEquals(e.getMessage(), "Error parsing nonexistent.file");
    }
  }

  @Test
  void testSaveSession() {
    //TODO: implement this
  }
}
