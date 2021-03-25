package Tools;

import ServerMainBody.Server;
import Type.ActionType;
import Type.MapType;

import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class PackageTool {

  //transform Action list to Byte list
  public static byte[] ActionListToByte(){
    byte[] buf = new byte[Server.Action.size() * ActionType.ActionTypeSize]; //One ActionType Size is 28 Bytes
    byte[] temp4; //save int
    byte[] temp8; //save double
    int start = 0;
    ActionType action;
    while ((action = Server.Action.poll())!=null) {
      temp4 = ToCSharpTool.ToCSharp(action.ActionID);                   //write ActionID
      System.arraycopy(temp4,0,buf,start,4);
      temp4 = ToCSharpTool.ToCSharp(action.PlayerID);                   //write Player
      System.arraycopy(temp4,0,buf,start+4,4);
      temp4 = ToCSharpTool.ToCSharp(action.TargetID);                   //write Target
      System.arraycopy(temp4,0,buf,start+8,4);
      temp8 = ToCSharpTool.ToCSharp(action.Information1);               //write Information1
      System.arraycopy(temp8,0,buf,start+12,8);
      temp8 = ToCSharpTool.ToCSharp(action.Information2);               //write Information2
      System.arraycopy(temp8,0,buf,start+20,8);

      start += ActionType.ActionTypeSize;
    }

    return buf;
  }

  public static byte[] MapTypeToByte(){
    byte[] buf = new byte[Server.Map.size() * MapType.MapTypeSize]; //One MapType Size is 40 Bytes
    byte[] temp4; //save int
    byte[] temp8; //save double
    int start = 0;
    for(MapType mapType : Server.Map) {
      temp4 = ToCSharpTool.ToCSharp(mapType.TypeID);                    //write TypeID
      System.arraycopy(buf,start,temp4,0,4);
      temp4 = ToCSharpTool.ToCSharp(mapType.BelongID);                  //write BelongID
      System.arraycopy(buf,start+4,temp4,0,4);
      temp4 = ToCSharpTool.ToCSharp(mapType.Level);                     //write Level
      System.arraycopy(buf,start+8,temp4,0,4);
      temp4 = ToCSharpTool.ToCSharp(mapType.HP);                        //write HP
      System.arraycopy(buf,start+12,temp4,0,4);
      temp4 = ToCSharpTool.ToCSharp(mapType.MP);                        //write MP
      System.arraycopy(buf,start+16,temp4,0,4);
      temp4 = ToCSharpTool.ToCSharp(mapType.state);                     //write state
      System.arraycopy(buf,start+20,temp4,0,4);
      temp8 = ToCSharpTool.ToCSharp(mapType.Longitude);                 //write Longitude
      System.arraycopy(buf,start+24,temp8,0,8);
      temp8 = ToCSharpTool.ToCSharp(mapType.Latitude);                  //write Latitude
      System.arraycopy(buf,start+32,temp8,0,8);

      start += MapType.MapTypeSize;
    }

    return buf;
  }

}
