package org.junit.rules;

import java.io.File;
import java.io.IOException;

public class TemporaryFolder extends ExternalResource {
  private File folder;
  
  protected void before() throws Throwable {
    create();
  }
  
  protected void after() {
    delete();
  }
  
  public void create() throws IOException {
    this.folder = newFolder();
  }
  
  public File newFile(String fileName) throws IOException {
    File file = new File(getRoot(), fileName);
    file.createNewFile();
    return file;
  }
  
  public File newFile() throws IOException {
    return File.createTempFile("junit", null, this.folder);
  }
  
  public File newFolder(String... folderNames) {
    File file = getRoot();
    for (String folderName : folderNames) {
      file = new File(file, folderName);
      file.mkdir();
    } 
    return file;
  }
  
  public File newFolder() throws IOException {
    File createdFolder = File.createTempFile("junit", "", this.folder);
    createdFolder.delete();
    createdFolder.mkdir();
    return createdFolder;
  }
  
  public File getRoot() {
    if (this.folder == null)
      throw new IllegalStateException("the temporary folder has not yet been created"); 
    return this.folder;
  }
  
  public void delete() {
    recursiveDelete(this.folder);
  }
  
  private void recursiveDelete(File file) {
    File[] files = file.listFiles();
    if (files != null)
      for (File each : files)
        recursiveDelete(each);  
    file.delete();
  }
}
