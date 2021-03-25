package Tools;

import ServerMainBody.Server;
import Type.ActionType;
import Type.MapType;

import java.nio.ByteBuffer;

public class PackageTool {

  //transform Action list to Byte list
  public static byte[] ActionListToByte(){
    byte[] buf = new byte[Server.Action.size() * ActionType.ActionTypeSize]; //One ActionType Size is 28 Bytes
    byte[] temp4 = new byte[4]; //save int
    byte[] temp8 = new byte[8]; //save double
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

  public static byte[] MapTypeToByte(){
    byte[] buf = new byte[Server.Map.size() * MapType.MapTypeSize]; //One MapType Size is 40 Bytes
    byte[] temp4 = new byte[4]; //save int
    byte[] temp8 = new byte[8]; //save double
    int start = 0;
    MapType mapType;
    while ((mapType = Server.Map.peek()) != null) {
      temp4 = ToCSharpTool.ToCSharp(mapType.TypeID);                    //write TypeID
      System.arraycopy(buf,start,temp4,0,4);
      temp4 = ToCSharpTool.ToCSharp(mapType.BelongID);                  //write BelongID
      System.arraycopy(buf,start+4,temp4,0,4);
      temp4 = ToCSharpTool.ToCSharp(mapType.Level);                     //write HP
      System.arraycopy(buf,start+8,temp4,0,4);
      temp4 = ToCSharpTool.ToCSharp(mapType.HP);                        //write HP
      System.arraycopy(buf,start+12,temp4,0,4);
      temp4 = ToCSharpTool.ToCSharp(mapType.MP);                        //write MP
      System.arraycopy(buf,start+16,temp4,0,4);
      temp4 = ToCSharpTool.ToCSharp(mapType.state);                     //write state
      System.arraycopy(buf,start+20,temp4,0,4);
      ByteBuffer.wrap(temp8,0,8).putDouble(mapType.Longitude); //write Longitude
      System.arraycopy(buf,start+24,temp8,0,8);
      ByteBuffer.wrap(temp8,0,8).putDouble(mapType.Latitude); //write Latitude
      System.arraycopy(buf,start+32,temp8,0,8);

      start += MapType.MapTypeSize;
    }

    return buf;
  }

}
