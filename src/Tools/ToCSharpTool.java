package Tools;

import java.nio.ByteBuffer;

public class ToCSharpTool {
  public static byte[] ToCSharp(int data){
    byte[] trans = new byte[4];
    byte tmp;
    ByteBuffer.wrap(trans,0,4).putInt(data);
    tmp = trans[0];
    trans[0] = trans[3];
    trans[3] = tmp;
    tmp = trans[1];
    trans[1] = trans[2];
    trans[2] = tmp;

    return trans;
  }
  public static byte[] ToCSharp(String data){
    return data.getBytes();
  }
}
