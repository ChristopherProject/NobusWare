package QuarantineAPI.system.encrypt;

public interface Encrypter {
  String encrypt(String paramString);
  
  String decrypt(String paramString);
  
  static Encrypter standard() {
    return StandardFileEncryptor.INSTANCE;
  }
}
