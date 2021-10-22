package Action;

import DBS.DBConnection;
import ServerMainBody.Server;
import Tools.ToCSharpTool;
import Type.FriendType;
import Type.PlayerInformation;
import Type.TeammateType;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Objects;

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

  public static void createTeam(OutputStream out, int PID,byte[] data) throws UnsupportedEncodingException {
    String teamName = new String(data,"big5").trim();
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
      if(con.hasTeam(teamID)){
        if (con.setTeam(PID,teamID)){
          buf = ToCSharpTool.ToCSharp(con.getTeam(teamID)[1]); //name

          /*
           * send notice to members
           * */
          ArrayList<TeammateType> member = con.getTeamMem(teamID);
          for (TeammateType m : member){
            if(m.TMID != PID){
              for (PlayerInformation p : Server.Information){
                if(p.PID == m.TMID){
                  MessageSender.JoinTeamNotice(p,PID);
                }
              }
            }
          }
        }
        else{
          buf = ToCSharpTool.ToCSharp(-1); //join fail
        }
      }
      else{
        buf = ToCSharpTool.ToCSharp(-2); //team not exist
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
      ArrayList<TeammateType> member = con.getTeamMem(teamID);
      for (TeammateType m : member){
        System.out.println(m.TMID);
      }
      if (con.delTeam(teamID)){
        /*
         * notice
         * */
        for (TeammateType m : member){
          if (m.TMID != PID) {
            for (PlayerInformation p : Server.Information){
              if(p.PID == m.TMID){
                MessageSender.DelTeamNotice(p);
              }
            }
          }
        }
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
        int teamID = Integer.parseInt(teamInfo[0]);
        if (PID == Integer.parseInt(teamInfo[0])){ //leader
          System.out.println(PID + "Leader leave");
          if (con.getTeamNum(Integer.parseInt(teamInfo[0])) > 1){ //not only 1 member
            ArrayList<TeammateType> member = con.getTeamMem(teamID);
            if (con.setTeam(PID,0) && con.replaceTeamLeader(PID)){
              buf = ToCSharpTool.ToCSharp(1);
              /*
               * notice
               * */
              for (TeammateType m : member){
                for (PlayerInformation p : Server.Information){
                  if(p.PID == m.TMID){
                    MessageSender.LeaveTeamNotice(p,PID);
                  }
                }
              }
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
            /*
             * notice
             * */
            ArrayList<TeammateType> member = con.getTeamMem(teamID);
            for (TeammateType m : member){
              for (PlayerInformation p : Server.Information){
                if(p.PID == m.TMID){
                  MessageSender.LeaveTeamNotice(p,PID);
                }
              }
            }
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

  public static ArrayList<TeammateType> getTeammate(int TID){
    try {
      DBConnection con = new DBConnection();
      return con.getTeamMem(TID);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return new ArrayList<TeammateType>();
  }

  public static void sendTeammate(OutputStream out, int PID){
    int TID = Integer.parseInt(Objects.requireNonNull(DBConnection.getTeam(PID))[0]);
    ArrayList<TeammateType> teammate = getTeammate(TID);
    byte[] send = new byte[TeammateType.SendSize * teammate.size()];
    if(teammate.size() == 0) send = new byte[1];
    for(int i = 0; i < teammate.size(); i++){
      byte[] temp = teammate.get(i).getByte();
      System.arraycopy(temp,0,send,i*TeammateType.SendSize,TeammateType.SendSize);
    }
    try {
      out.write(send);
    }catch (Exception e){
      System.err.println(e);
    }
  }
}
