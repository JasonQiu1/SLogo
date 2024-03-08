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
  private Session session;

  @BeforeEach
  void setup() {
    xmlConfig = new XmlConfiguration();
    session = new Session();
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
      xmlConfig.loadSessionFromFile("nonexistent.file");
    } catch (XmlException e) {
      assertEquals(e.getMessage(), "Error parsing nonexistent.file");
    }
  }

  @Test
  void testSaveSessionCreatesCorrectFile() {
    List<String> commands = Arrays.asList("fd 50", "rt 45");
    String filePath = "doc/tests/saveSessionTest";
    try {
      session.run(commands.get(0));
      session.run(commands.get(1));
      xmlConfig.saveSession(session, filePath);

      assertEquals(xmlConfig.loadSessionFromFile(filePath + ".slogo"), commands);
    } catch (XmlException e) {
      fail();
    }
  }

  @Test
  void testSaveSessionThrowsXMLExceptionForNonexistentFile() {
    try {
      xmlConfig.saveSession(session, "nonexistent.file");
    } catch (XmlException e) {
      assertEquals(e.getMessage(), "Error parsing nonexistent.file");
    }
  }
}

