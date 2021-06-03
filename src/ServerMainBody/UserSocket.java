package ServerMainBody;
import Action.*;
import ID.ProtocolID;
import Tools.DisconnectTool;
import Tools.ProtocolTool;
import Type.PlayerInformation;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
public class UserSocket extends Thread{
  int           SocketID;
  int           PlayerID = -1;
  String        name = null;
  Socket        socket;
  InputStream   in;
  OutputStream  out;
  PlayerInformation playerInformation = new PlayerInformation();
  byte[]        buf = new byte[1000];
  public UserSocket(int ID, Socket sc){
    SocketID  = ID;
    socket    = sc;
  }
  //thread running
  public void run(){
    ProtocolTool data = null;
    try {
      in = socket.getInputStream();
      out = socket.getOutputStream();
      do {
        //read login message
        int len = in.read(buf);
        if(len < 0){
          throw new Exception("Disconnect"); //中斷連線
        }
        data = ProtocolTool.ProtocolTrim(buf);
        //should login First
        //to connect database and get information of player
        if (data.protocol == ProtocolID.LOGIN){
          System.out.println("SID " + SocketID + ": "+data.protocol + " " + new String(data.data));
          //have to return PID to UserSocket
          PlayerID = Login.login(out,data.data);
          Server.online.add(PlayerID);
//          name = Login.get_name(PlayerID);
        } else if (data.protocol == ProtocolID.REGISTER){
          System.out.println("SID " + SocketID + ": "+data.protocol + " " + new String(data.data));
          PlayerID = Login.register(out,data.data);
          Server.online.add(PlayerID);
//          name = Login.get_name(PlayerID);
        } else {
          System.out.println("SID " + SocketID + ": "+data.protocol + " " + new String(data.data));
        }
      }while(PlayerID < 0);
      playerInformation.sc        = socket;
      playerInformation.PID       = PlayerID;
      playerInformation.status    = Login.getStatus(PlayerID);
      playerInformation.item      = Login.getItem(PlayerID);
      playerInformation.equipment = Login.getEquipment(PlayerID);
      Login.Login_Send(out,PlayerID);

      Server.Information.add(playerInformation);

      //read client action
      while (true) {
        buf = new byte[1000];  //clear buffer
        int len = in.read(buf);
        if(len < 0){
          throw new Exception("Disconnect"); //中斷連線
        }
        data = ProtocolTool.ProtocolTrim(buf);
        System.out.println("SID " + SocketID + ": "+data.protocol + " " + new String(data.data));
        switch (data.protocol) {
          case ProtocolID.LOGIN_LOCATION:
            LoginLocation.Login_Location(data.data,playerInformation.status);
            break;
          case ProtocolID.MOVE:
            Move.move(PlayerID, data.data);
            break;
          case ProtocolID.BUY_ITEM:
            Buy.BuyItem(out,playerInformation,data.data);
            break;
          case ProtocolID.BUY_EQUIPMENT:
            Buy.BuyEquipment(out,playerInformation,data.data);
            break;
          case ProtocolID.GET_FRIEND:
            Community.sendFriend(out,PlayerID);
            break;
          case ProtocolID.ADD_FRIEND:
            Community.addFriend(out,PlayerID,data.data);
            break;
          case ProtocolID.DELETE_FRIEND:
            Community.delFriend(out,PlayerID,data.data);
            break;
          case ProtocolID.ACCEPT_FRIEND:
            Community.acceptFriend(out,PlayerID,data.data);
            break;
          case ProtocolID.CREATE_TEAM:
            Community.createTeam(out,PlayerID,data.data);
            break;
          case ProtocolID.JOIN_TEAM:
            Community.joinTeam(out,PlayerID,data.data);
            break;
          case ProtocolID.DELETE_TEAM:
            Community.delTeam(out,PlayerID,data.data);
            break;
          case ProtocolID.LEAVE_TEAM:
            Community.leaveTeam(out,PlayerID);
            break;
          case ProtocolID.GET_TEAM:
            Community.getTeam(out,PlayerID,data.data);
            break;
        }

        for (PlayerInformation p : Server.Information){
          if (p.PID == playerInformation.PID){
            Server.Information.removeIf(Player->Player.PID == playerInformation.PID);
            Server.Information.add(playerInformation);
          }
        }

      }
    }
    catch (Exception e){
      if (Server.debug){
        System.err.println(e);
      }
      //顯示離開ID
      if (PlayerID == -1){
        System.out.printf("Socket ID: %06d out\n", SocketID);
      } else {  //玩家退出連線
        System.out.printf("Socket ID: %06d out\t", SocketID);
        System.out.printf("Player ID: %06d out\n", PlayerID);
        DisconnectTool.PlayerDisconnect(SocketID,PlayerID, playerInformation);
      }
    }
  }
}