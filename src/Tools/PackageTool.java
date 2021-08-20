package Tools;

import ServerMainBody.Server;
import Type.ActionType;
import Type.MapType;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Queue;

public class PackageTool {

  //transform Action list to Byte list
  public static byte[] ActionListToByte(Queue<ActionType> Data){
    byte[] buf = new byte[Data.size() * ActionType.ActionTypeSize]; //One ActionType Size is 36 Bytes
    byte[] temp4 = new byte[4]; //save int
    byte[] temp8 = new byte[8]; //save double
    int start = 0;
    for (ActionType action : Data) {
      temp4 = ToCSharpTool.ToCSharp(action.ActionID);                  //write ActionID
      System.arraycopy(temp4,0,buf,start,4);
      temp4 = ToCSharpTool.ToCSharp(action.MoverMapID);                //write MoverMapID
      System.arraycopy(temp4,0,buf,start+4,4);
      temp4 = ToCSharpTool.ToCSharp(action.MoverID);                   //write MoverID
      System.arraycopy(temp4,0,buf,start+8,4);
      temp4 = ToCSharpTool.ToCSharp(action.TargetMapID);               //write Target
      System.arraycopy(temp4,0,buf,start+12,4);
      temp4 = ToCSharpTool.ToCSharp(action.TargetID);                   //write Target
      System.arraycopy(temp4,0,buf,start+16,4);
      temp8 = ToCSharpTool.ToCSharp(action.Information1);               //write Information1
      System.arraycopy(temp8,0,buf,start+20,8);
      temp8 = ToCSharpTool.ToCSharp(action.Information2);               //write Information2
      System.arraycopy(temp8,0,buf,start+28,8);

      start += ActionType.ActionTypeSize;
    }
    return buf;
  }

  public static byte[] MapTypeToByte(){
    byte[] buf = new byte[Server.Map.size() * MapType.MapTypeSize]; //One MapType Size is 64 Bytes
    byte[] temp4; //save int
    byte[] temp8; //save double
    int start = 0;
    for(MapType mapType : Server.Map) {
      temp4 = ToCSharpTool.ToCSharp(mapType.MapObjectID);               //write MapObjectID
      System.arraycopy(temp4,0,buf, start,4);
      temp4 = ToCSharpTool.ToCSharp(mapType.TypeID);                    //write TypeID
      System.arraycopy(temp4,0,buf, start+4,4);
      temp4 = ToCSharpTool.ToCSharp(mapType.BelongID);                  //write BelongID
      System.arraycopy(temp4,0,buf,start+8,4);
      temp4 = ToCSharpTool.ToCSharp(mapType.Level);                     //write Level
      System.arraycopy(temp4,0,buf,start+12,4);
      temp4 = ToCSharpTool.ToCSharp(mapType.HP);                        //write HP
      System.arraycopy(temp4,0,buf,start+16,4);
      temp4 = ToCSharpTool.ToCSharp(mapType.MP);                        //write MP
      System.arraycopy(temp4,0,buf,start+20,4);
      temp4 = ToCSharpTool.ToCSharp(mapType.state);                     //write state
      System.arraycopy(temp4,0,buf,start+24,4);
      temp8 = ToCSharpTool.ToCSharp(mapType.Longitude);                 //write Longitude
      System.arraycopy(temp8,0,buf,start+28,8);
      temp8 = ToCSharpTool.ToCSharp(mapType.Latitude);                  //write Latitude
      System.arraycopy(temp8,0,buf,start+36,8);
      temp4 = mapType.Name.getBytes(Charset.forName("big5"));
      System.arraycopy(temp4,0,buf,start+44,temp4.length); //Name
      start += MapType.MapTypeSize;
  }

    return buf;
  }

}
