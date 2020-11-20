package QuarantineAPI.system.encrypt;

import java.util.HashMap;
import java.util.Iterator;

import QuarantineAPI.system.service.HippoFileService;

public enum StandardFileEncryptor implements Encrypter {
  INSTANCE;
  
  public String encrypt(String content) {
    HashMap<Integer, Character> encryptedMap = new HashMap<>();
    StringBuilder encrypted = new StringBuilder();
    for (int index = 0; index < content.length(); index++) {
      char character = content.charAt(index);
      int swapIndex = index + 1;
      if (!encryptedMap.containsKey(Integer.valueOf(index)) && 
        swapIndex < content.length()) {
        char swap = content.charAt(swapIndex);
        encryptedMap.put(Integer.valueOf(swapIndex), Character.valueOf(character));
        encryptedMap.put(Integer.valueOf(index), Character.valueOf(swap));
      } 
    } 
    for (Iterator<Character> iterator = encryptedMap.values().iterator(); iterator.hasNext(); ) {
      char encryptedCharacter = ((Character)iterator.next()).charValue();
      encrypted.append(encryptedCharacter);
    } 
    return encrypted.reverse().toString();
  }
  
  public String decrypt(String content) {
    HashMap<Integer, Character> encryptedMap = HippoFileService.getEncryptedMap(content);
    HashMap<Integer, Character> decryptedMap = new HashMap<>();
    StringBuilder decrypted = new StringBuilder();
    for (int index = 0; index < content.length(); index++) {
      char character = ((Character)encryptedMap.get(Integer.valueOf(index))).charValue();
      int swapIndex = index + 1;
      if (!decryptedMap.containsKey(Integer.valueOf(index)) && 
        swapIndex < content.length()) {
        char swap = ((Character)encryptedMap.get(Integer.valueOf(swapIndex))).charValue();
        decryptedMap.put(Integer.valueOf(swapIndex), Character.valueOf(character));
        decryptedMap.put(Integer.valueOf(index), Character.valueOf(swap));
      } 
    } 
    for (Iterator<Character> iterator = decryptedMap.values().iterator(); iterator.hasNext(); ) {
      char decryptedChar = ((Character)iterator.next()).charValue();
      decrypted.append(decryptedChar);
    } 
    return decrypted.reverse().toString();
  }
}
