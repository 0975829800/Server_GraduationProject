package ServerMainBody;

import java.net.Socket;
import java.io.InputStream;
import java.io.OutputStream;

import Action.LoginLocation;
import Action.Move;
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

      //should login First
      //to connect database and get information of player
      if (data.protocol == ProtocolID.LOGIN){
        //have to return PID to UserSocket
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
          case ProtocolID.MOVE:
            Move.move(PlayerID, data.data);
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
