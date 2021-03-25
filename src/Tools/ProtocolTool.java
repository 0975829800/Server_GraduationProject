package Tools;

import java.nio.ByteBuffer;

public class ProtocolTool {
  public int protocol;
  public byte[] data;
  public ProtocolTool(int protocol, byte[] data){
    this.data = data;
    this.protocol = protocol;
  }

  //cut data to protocol and else data
  public static ProtocolTool ProtocolTrim(byte[] buf){
    int protocol = ByteBuffer.wrap(buf,0,4).getInt();
    byte[] data = new byte[buf.length-4];
    System.arraycopy(buf,4,data,0, data.length);
    return new ProtocolTool(protocol,data);
  }

  //Protocol define
  public static final int LOGIN     = 1;
  public static final int REGISTER  = 2;
}
