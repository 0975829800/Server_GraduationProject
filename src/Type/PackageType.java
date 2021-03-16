package Type;

import ServerMainBody.Server;

import java.nio.ByteBuffer;

public class PackageType {

  //transform Action list to Byte list
  public static byte[] ActionListToByte(){
    byte[] buf = new byte[Server.Action.size() * ActionType.ActionTypeSize]; //One ActionType Size is 28 Bytes
    byte[] temp4 = new byte[4];
    byte[] temp8 = new byte[8];
    int start = 0;
    ActionType action;
    while (true) {
      action = Server.Action.poll();
      if (action == null) {
        break;
      }
      ByteBuffer.wrap(temp4,0,4).putInt(action.ActionID); //write ActionID
      System.arraycopy(buf,start,temp4,0,4);
      ByteBuffer.wrap(temp4,0,4).putInt(action.Player); //write Player
      System.arraycopy(buf,start+4,temp4,0,4);
      ByteBuffer.wrap(temp4,0,4).putInt(action.Target); //write Target
      System.arraycopy(buf,start+8,temp4,0,4);
      ByteBuffer.wrap(temp8,0,8).putDouble(action.Information1); //write Information1
      System.arraycopy(buf,start+12,temp8,0,8);
      ByteBuffer.wrap(temp8,0,8).putDouble(action.Information2); //write Information2
      System.arraycopy(buf,start+20,temp8,0,8);

      start += ActionType.ActionTypeSize;
    }

    return buf;
  }

}
