package Tools;

import java.nio.ByteBuffer;

public class ToCSharpTool {

  //when want to send to CSharp transform data to byte array

  public static byte[] ToCSharp(int data){
    byte[] trans = new byte[4];
    byte tmp;
    ByteBuffer.wrap(trans,0,4).putInt(data);
    for (int i = 0; i < 2; i++) {
      tmp = trans[i];
      trans[i] = trans[3-i];
      trans[3-i] = tmp;
    }

    return trans;
  }
  public static byte[] ToCSharp(String data){
    return data.getBytes();
  }

  public static byte[] ToCSharp(double data){
    byte[] trans = new byte[8];
    byte tmp;
    ByteBuffer.wrap(trans,0,8).putDouble(data);
    for (int i = 0; i < 4; i++) {
      tmp = trans[i];
      trans[i] = trans[7-i];
      trans[7-i] = tmp;
    }
    return trans;
  }

}
