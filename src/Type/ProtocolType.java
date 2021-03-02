package Type;

import java.nio.ByteBuffer;

public class ProtocolType {
  public int protocol;
  public byte[] data;
  public ProtocolType(int protocol, byte[] data){
    this.data = data;
    this.protocol = protocol;
  }

  //cut data to protocol and else data
  public static ProtocolType ProtocolTrim(byte[] buf){
    int protocol = ByteBuffer.wrap(buf,0,4).getInt();
    byte[] data = new byte[buf.length-4];
    System.arraycopy(buf,4,data,0, data.length);
    return new ProtocolType(protocol,data);
  }

  //Protocol define
  public static final int LOGIN     = 1;
  public static final int REGISTER  = 2;
}
