// 5. The user selects an invalid turtle image
public void handleTurtleSelection(File turtleFile) {
  try {
    Image turtleImage = getTurtleImage(turtleFile);
    passTurtleImage(turtleImage);
  }
  catch(Exception e) {
    throw new FileNotFoundException("Not correct turtle input", e);
  }
}

// 6. The user resizes the window
public void resizeWindow(Stage stage, Page previousPage, int resize) {
  GeneralPage newPage = new GraphicsPage(stage);
  newPage.setPage(previousPage.getPage());
  Scene modifiedScene = new Scene(newPage.getPage(), resize, resize);
  stage.setScene(modifiedScene);
  stage.show();
}
// 7. The user loads a previously saved file
public void getSavedFile(File previouslySavedFile) {
  try {
    XMLConfig savedConfig = getXMLConfig(previouslySavedFile);
    passConfig(savedConfig);
  }
  catch(Exception e) {
    throw new FileNotFoundException("File configuration not found", e);
  }
}

// 8. The user creates a new save file
public void createSavedFile(Data dataToBeSaved) {
  XMLConfig newConfig = new XMLConfig();
  for(Item data : dataToBeSaved) {
    if(data != null) {
      newConfig.append(data);
    }
  }
  saveNewConfig(newConfig);
}