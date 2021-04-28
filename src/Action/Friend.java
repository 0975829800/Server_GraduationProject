package Action;

import DBS.DBConnection;
import Tools.ToCSharpTool;
import Type.FriendType;

import java.io.OutputStream;
import java.util.ArrayList;

public class Friend {

  public static ArrayList<FriendType> getFriend(int PID){
    try {
      DBConnection con = new DBConnection();
      return con.getFriend(PID);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return new ArrayList<FriendType>();
  }

  public static void sendFriend(OutputStream out, int PID){
    ArrayList<FriendType> friend = getFriend(PID);
    byte[] send = new byte[FriendType.SendSize * friend.size()];
    if(friend.size() == 0) send = new byte[1];
    for(int i = 0; i < friend.size(); i++){
      byte[] temp = friend.get(i).getByte();
      System.arraycopy(temp,0,send,i*FriendType.SendSize,FriendType.SendSize);
    }
    try {
      out.write(send);
    }catch (Exception e){
      System.err.println(e);
    }
  }

  //success send 1 ,fail send -1
  public static void addFriend(OutputStream out, int PID,byte[] data){
    int FID = Integer.parseInt(new String(data).trim());
    try {
      byte[] buf;
      DBConnection con = new DBConnection();
      if (con.addFriend(PID,FID) && con.addFriend(FID,PID)){
        buf = ToCSharpTool.ToCSharp(1);
      }
      else{
        buf = ToCSharpTool.ToCSharp(-1);
      }

      out.write(buf);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void delFriend(OutputStream out, int PID,byte[] data){
    int FID = Integer.parseInt(new String(data).trim());
    try {
      byte[] buf;
      DBConnection con = new DBConnection();
      if (con.delFriend(PID,FID) && con.delFriend(FID,PID)){
        buf = ToCSharpTool.ToCSharp(1);
      }
      else{
        buf = ToCSharpTool.ToCSharp(-1);
      }

      out.write(buf);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void acceptFriend(OutputStream out, int PID,byte[] data){
    int FID = Integer.parseInt(new String(data).trim());
    try {
      byte[] buf;
      DBConnection con = new DBConnection();
      if (con.acceptFriend(PID,FID) && con.acceptFriend(FID,PID)){
        buf = ToCSharpTool.ToCSharp(1);
      }
      else{
        buf = ToCSharpTool.ToCSharp(-1);
      }

      out.write(buf);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
