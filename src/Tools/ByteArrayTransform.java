package Tools;

import java.nio.ByteBuffer;

public class ByteArrayTransform {
  public static double ToDouble(byte[] Data, int StartPos){
    byte[] buf = new byte[8];
    System.arraycopy(Data,StartPos,buf,0,8);
    return ByteBuffer.wrap(buf).getDouble();
  }
}
