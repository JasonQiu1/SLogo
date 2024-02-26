package slogo.exception;

/**
 * Custom exception class for handling exceptions when parsing XML files
 */
public class XmlException extends Exception {

  private final String fileName;

  /**
   * XMLException Constructor
   *
   * @param fileName The XML file name causing the exception
   */
  public XmlException(String fileName) {
    this.fileName = fileName;
  }

  /**
   * Provides more information on why exception was thrown
   *
   * @return message containing name of exception throwing XML file
   */
  public String getMessage() {
    return "Error parsing " + fileName;
  }
}
