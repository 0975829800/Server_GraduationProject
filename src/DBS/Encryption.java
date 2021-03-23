package DBS;

import java.security.MessageDigest;
import java.util.Base64;

public class Encryption {
  static final String ALGORITHM_SHA = "SHA";
  public static String encryption(String source) throws Exception {
    // SHA加密
    return printBase64(encryptionSHA(source));
  }
  static String printBase64(byte[] out) throws Exception {
//    System.out.println(encodeBase64(out));
    return encodeBase64(out);
  }
  /**
   * SHA加密
   * @param source
   * @return
   * @throws Exception
   */
  static byte[] encryptionSHA(String source) throws Exception {
    MessageDigest md = MessageDigest.getInstance(ALGORITHM_SHA);
    md.update(source.getBytes("UTF-8"));
    return md.digest();
  }

  /**
   * base64編碼
   * @param source
   * @return
   * @throws Exception
   */
  static String encodeBase64(byte[] source) throws Exception{
    Base64.Encoder encode = Base64.getEncoder();
    return new String(encode.encode(source), "UTF-8");
  }
}