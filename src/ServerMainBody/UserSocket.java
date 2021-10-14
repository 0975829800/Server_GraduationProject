package ServerMainBody;
import Action.*;
import ID.ProtocolID;
import Progress.FinishMission;
import Progress.TakeMission;
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
  Socket        MessageSocket;
  InputStream   in;
  OutputStream  out;
  PlayerInformation playerInformation = new PlayerInformation();
  byte[]        buf = new byte[1000];
  public UserSocket(int ID, Socket sc, Socket mss){
    SocketID  = ID;
    socket    = sc;
    MessageSocket = mss;
  }
  //thread running
  public void run(){
    ProtocolTool data;
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
          playerInformation.Name = Login.get_name(PlayerID);
        } else if (data.protocol == ProtocolID.REGISTER){
          System.out.println("SID " + SocketID + ": "+data.protocol + " " + new String(data.data));
          PlayerID = Login.register(out,data.data);
          Server.online.add(PlayerID);
          playerInformation.Name = Login.get_name(PlayerID);
        } else {
          System.out.println("SID " + SocketID + ": "+data.protocol + " " + new String(data.data));
        }
      }while(PlayerID < 0);
      playerInformation.sc        = socket;
      playerInformation.mss       = MessageSocket;
      playerInformation.PID       = PlayerID;
      playerInformation.status    = Login.getStatus(PlayerID);
      playerInformation.item      = Login.getItem(PlayerID);
      playerInformation.equipment = Login.getEquipment(PlayerID);
      playerInformation.progress  = Login.getProgress(PlayerID);

      Login.Login_Send(playerInformation,PlayerID);
      MessageSender.sendName(playerInformation);
      MessageSender.QuestUpdate(playerInformation);
      Equip.EquipmentStatusUpdate(playerInformation);
      MessageSender.EquipmentStatusUpdate(playerInformation);

      Server.Information.add(playerInformation);

      if(playerInformation.status.HP == 0){
        MessageSender.PlayerDead(playerInformation);
      }

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
            playerInformation.MapAddress = LoginLocation.Login_Location(data.data,playerInformation);
            playerInformation.MapID = playerInformation.MapAddress.MapObjectID;
            break;
          case ProtocolID.MOVE:
            Move.move(playerInformation, data.data);
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
          case ProtocolID.GET_TEAMMATE:
            Community.sendTeammate(out,PlayerID);
            break;
          case ProtocolID.ATTACK:
            Attack.attack(playerInformation,data.data);
            break;
          case ProtocolID.USE_ITEM:
            UseItem.useItem(out,playerInformation,data.data);
            break;
          case ProtocolID.EQUIP:
            Equip.Equip(out,playerInformation,data.data);
            break;
          case ProtocolID.DISEQUIP:
            Equip.DisEquip(out,playerInformation,data.data);
            break;
          case ProtocolID.SKILL_POINT_ASSIGN:
            SetSkillPoint.SkillPoint(playerInformation,data.data);
            break;
          case ProtocolID.SELL_ITEM:
            Buy.SellItem(out,playerInformation,data.data);
            break;
          case ProtocolID.SELL_EQUIPMENT:
            Buy.SellEquipment(out,playerInformation,data.data);
            break;
          case ProtocolID.GET_PLAYER_INFORMATION:
            GetOtherPlayerInformation.get(playerInformation,data.data);
            break;
          case ProtocolID.TAKE_QUEST:
            TakeMission.take(playerInformation,data.data);
            break;
          case ProtocolID.FINISH_QUEST:
            FinishMission.finish(playerInformation,data.data);
            break;
          case ProtocolID.CREATE_MONSTER:
            CreateMonster.create(playerInformation,data.data);
            break;
          case ProtocolID.CREATE_NPC:
            CreateNPC.create(playerInformation,data.data);
            break;
        }

      }
    }
    catch (Exception e){
      if (Server.debug){
        e.printStackTrace();
      }
      //顯示離開ID
      if (PlayerID == -1){
        System.out.printf("Socket ID: %06d out\n", SocketID);
        Server.User.removeIf(S -> S.Socket == socket);
      } else {  //玩家退出連線
        System.out.printf("Socket ID: %06d out\t", SocketID);
        System.out.printf("Player ID: %06d out\n", PlayerID);
        DisconnectTool.PlayerDisconnect(SocketID,PlayerID, playerInformation);
      }
    }
  }
}