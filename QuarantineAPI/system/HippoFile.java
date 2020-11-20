package QuarantineAPI.system;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

import QuarantineAPI.system.encrypt.Encrypter;
import QuarantineAPI.system.exception.HippoFileException;
import QuarantineAPI.system.object.HippoFileObject;
import QuarantineAPI.system.service.HippoFileService;

public final class HippoFile {
  private String name;
  
  private File destination;
  
  private File path;
  
  private final CopyOnWriteArrayList<HippoFileObject> content = new CopyOnWriteArrayList<>();
  
  public HippoFile setName(String name) throws IOException {
    this.name = name;
    if (this.destination != null) {
      this.path = new File(this.destination, name + ".encryptedprop");
      if (!this.path.exists()) {
        System.out.println(this.path);
        if (!this.path.createNewFile())
          throw new IOException("Creation of encryptedprop file failed!"); 
      } 
    } 
    return this;
  }
  
  public HippoFile setDestination(String destination) throws IOException {
    this.destination = new File(destination);
    if (!this.destination.exists() && 
      !this.destination.mkdirs())
      throw new IOException("Creation of the encryptedprop file destination failed!"); 
    if (this.name != null) {
      this.path = new File(destination, this.name + ".encryptedprop");
      if (!this.path.exists() && 
        !this.path.createNewFile())
        throw new IOException("Creation of encryptedprop file failed!"); 
    } 
    return this;
  }
  
  public HippoFile addObject(HippoFileObject hippoFileObject) {
    this.content.add(hippoFileObject);
    return this;
  }
  
  public HippoFile save() throws IOException {
    FileWriter fileWriter = new FileWriter(this.path);
    for (HippoFileObject hippoFileObject : this.content)
      fileWriter.write(hippoFileObject.getContent()); 
    fileWriter.close();
    return this;
  }
  
  public void clear() throws IOException {
    FileWriter fileWriter = new FileWriter(getPath());
    fileWriter.close();
  }
  
  public boolean isEmpty() throws IOException {
    FileReader fileReader = new FileReader(this.path);
    int read = fileReader.read();
    fileReader.close();
    return (read == -1);
  }
  
  public HippoFile encrypt() throws IOException {
    return encrypt(new Encrypter[] { Encrypter.standard() });
  }
  
  public HippoFile encrypt(Encrypter... encrypters) throws IOException {
    clear();
    String content = HippoFileService.convertToText(this);
    FileWriter fileWriter = new FileWriter(this.path);
    for (Encrypter encrypter : encrypters)
      fileWriter.write(encrypter.encrypt(content)); 
    fileWriter.close();
    return this;
  }
  
  public HippoFileObject getObject(String object) throws HippoFileException {
    for (HippoFileObject hippoFileObject : this.content) {
      if (hippoFileObject.getName().equalsIgnoreCase(object))
        return hippoFileObject; 
    } 
    throw new HippoFileException("An exception was thrown whilst finding object!\n\nDetails:\nFile Name: " + this.name + "\nObject Name: " + object + "\nFound: null\n\nMaybe the object does not exist?");
  }
  
  public String getName() {
    return this.name;
  }
  
  public File getDestination() {
    return this.destination;
  }
  
  public File getPath() {
    return this.path;
  }
  
  public CopyOnWriteArrayList<HippoFileObject> getContent() {
    return this.content;
  }
}
