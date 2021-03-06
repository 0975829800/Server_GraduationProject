package Action;

import DBS.DBConnection;
import Tools.ToCSharpTool;
import Type.FriendType;

import java.io.OutputStream;
import java.util.ArrayList;

public class Community {

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
      //state 0: show waiting for their acceptance
      //state 2: show accept/reject button
      if (con.addFriend(PID,FID,0) && con.addFriend(FID,PID,2)){
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

  public static void createTeam(OutputStream out, int PID,byte[] data){
    String teamName = new String(data).trim();
    try {
      byte[] buf;
      DBConnection con = new DBConnection();
      if (con.createTeam(PID,teamName)){
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

  public static void joinTeam(OutputStream out, int PID,byte[] data){
    int teamID = Integer.parseInt(new String(data).trim());
    try {
      byte[] buf;
      DBConnection con = new DBConnection();
      if (con.setTeam(PID,teamID)){
        buf = ToCSharpTool.ToCSharp(con.getTeam(teamID)[1]);
//        buf = ToCSharpTool.ToCSharp(1);
      }
      else{
        buf = ToCSharpTool.ToCSharp(-1);
      }
      out.write(buf);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  public static void delTeam(OutputStream out, int PID,byte[] data){
    int teamID = Integer.parseInt(new String(data).trim());
    try {
      byte[] buf;
      DBConnection con = new DBConnection();
      if (con.delTeam(teamID)){
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

  public static void leaveTeam(OutputStream out, int PID){
    try {
      byte[] buf = new byte[0];
      DBConnection con = new DBConnection();
      String[] teamInfo = con.getTeam(PID); //[0] = teamID ,[1] = teamName
      if (teamInfo != null){
        if (PID == Integer.parseInt(teamInfo[0])){ //leader
          if (con.getTeamNum(Integer.parseInt(teamInfo[0])) > 1){ //not only 1 member
            if (con.setTeam(PID,0) && con.replaceTeamLeader(PID)){
              buf = ToCSharpTool.ToCSharp(1);
            }
            else{
              buf = ToCSharpTool.ToCSharp(-1);
            }
          }
          else{
            if(con.delTeam(PID)){
              buf = ToCSharpTool.ToCSharp(1);
            }
            else{
              buf = ToCSharpTool.ToCSharp(-1);
            }
          }

        }
        else{ //member
          if (con.setTeam(PID,0)){
            buf = ToCSharpTool.ToCSharp(2);
          }
          else {
            buf = ToCSharpTool.ToCSharp(-1);  //leave fail
          }
        }
      }
      else{
        buf = ToCSharpTool.ToCSharp(-2);  //have no team
      }
      out.write(buf);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void getTeam(OutputStream out, int PID,byte[] data){
    try {
      byte[] buf;
      DBConnection con = new DBConnection();
      String[] team = con.getTeam(PID);
      if (team != null){
        buf = ToCSharpTool.ToCSharp(team[0]+' '+team[1]);
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
