package ServerMainBody;

import java.net.Socket;
import java.io.InputStream;
import java.io.OutputStream;

import Action.LoginLocation;
import Tools.*;
import Type.*;
import ID.*;

public class UserSocket extends Thread{
  int           SocketID;
  int           PlayerID = -1;
  Socket        socket;
  InputStream   in;
  OutputStream  out;
  PlayerStatusType Status = new PlayerStatusType();

  byte[]        buf = new byte[1000];

  public UserSocket(int ID, Socket sc){
    SocketID  = ID;
    socket    = sc;
  }

  //thread running
  public void run(){
    try {
      in = socket.getInputStream();
      out = socket.getOutputStream();
      //read login message
      in.read(buf);
      ProtocolTool data = ProtocolTool.ProtocolTrim(buf);
      if (data.protocol == ProtocolID.LOGIN){
        System.out.println(1);
      } else if (data.protocol == ProtocolID.REGISTER){
        System.out.println(2);
      } else {
        System.out.println(3);
      }

      //read client action
      while (true) {
        buf = new byte[1000];  //clear buffer
        in.read(buf);
        data = ProtocolTool.ProtocolTrim(buf);
        System.out.println("SID " + SocketID + ": "+data.protocol + " " + new String(data.data));
        switch (data.protocol) {
          case ProtocolID.LOGIN_LOCATION:
            LoginLocation.Login_Location(data.data,Status);
            break;
          case 3:
            Server.Action.add(new ActionType(1,2,3,12,13));
            break;
        }
      }
    }
    catch (Exception e){
      if (Server.debug){
        System.err.println(e);
      }
      DisconnectTool.PlayerDisconnect(SocketID,PlayerID);
      //顯示離開ID
      if (PlayerID == -1){
        System.out.printf("Socket ID: %06d out\n", SocketID);
      } else {
        System.out.printf("Socket ID: %06d out\t", SocketID);
        System.out.printf("Player ID: %06d out\n", PlayerID);
      }
    }
  }
}
