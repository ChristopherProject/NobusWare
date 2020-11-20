package QuarantineAPI.system.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import QuarantineAPI.system.HippoFile;
import QuarantineAPI.system.encrypt.Encrypter;
import QuarantineAPI.system.exception.HippoFileException;
import QuarantineAPI.system.object.HippoFileObject;

public final class HippoFileService {
  public static HippoFile getFile(String path, Encrypter... encrypters) throws IOException, HippoFileException {
    return getFile(new File(path), encrypters);
  }
  
  public static HippoFile getFile(File path, Encrypter... encrypters) throws IOException, HippoFileException {
    ArrayList<String> lines = new ArrayList<>();
    if (encrypters.length > 0) {
      StringBuilder content = new StringBuilder();
      for (String line : Files.readAllLines(path.toPath()))
        content.append(line).append("\n"); 
      String decrypt = "";
      for (Encrypter encrypter : encrypters)
        decrypt = encrypter.decrypt(content.toString()); 
      lines.addAll(Arrays.asList(decrypt.split("\n")));
    } else {
      lines.addAll(Files.readAllLines(path.toPath()));
    } 
    String[] dirs = path.getAbsolutePath().split("/");
    String fileName = "";
    StringBuilder pathBuilder = new StringBuilder();
    for (String dir : dirs) {
      if (dir.endsWith(".encryptedprop")) {
        fileName = dir.substring(0, dir.length() - 6);
      } else {
        pathBuilder.append(dir).append("/");
      } 
    } 
    HippoFile hippoFile = (new HippoFile()).setName(fileName).setDestination(pathBuilder.toString());
    for (String line : lines) {
      StringBuilder nameBuilder = new StringBuilder();
      int startObject = 0;
      for (char name : line.toCharArray()) {
        if (name == '{')
          break; 
        nameBuilder.append(name);
        startObject++;
      } 
      HippoFileObject hippoFileObject = new HippoFileObject(nameBuilder.toString());
      int subElements = 0;
      for (int i = startObject; i < line.length(); i++) {
        char character = line.charAt(i);
        if (character == '{')
          subElements++; 
        if (character == '}') {
          if (subElements == 0)
            break; 
          subElements--;
        } 
      } 
      ArrayList<Character> elementList = new ArrayList<>();
      for (int j = startObject + 1; j < line.length(); j++) {
        char character = line.charAt(j);
        elementList.add(Character.valueOf(character));
      } 
      char[] elementChars = new char[elementList.size() - 1];
      for (int k = 0; k < elementList.size() - 1; k++)
        elementChars[k] = ((Character)elementList.get(k)).charValue(); 
      hippoFileObject.scanElements(elementChars);
      hippoFile.addObject(hippoFileObject);
    } 
    return hippoFile;
  }
  
  public static String convertToText(HippoFile hippoFile) {
    StringBuilder content = new StringBuilder();
    for (HippoFileObject object : hippoFile.getContent())
      content.append(object.getContent()); 
    return content.toString();
  }
  
  public static HashMap<Integer, Character> getEncryptedMap(String content) {
    HashMap<Integer, Character> encryptedMap = new HashMap<>();
    for (int i = 0; i < content.length(); i++)
      encryptedMap.put(Integer.valueOf(i), Character.valueOf(content.charAt(i))); 
    return encryptedMap;
  }
}
