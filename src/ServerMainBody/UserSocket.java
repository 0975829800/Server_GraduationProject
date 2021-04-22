package ServerMainBody;

import Action.Login;
import Action.LoginLocation;
import Action.Move;
import ID.ProtocolID;
import Tools.DisconnectTool;
import Tools.ProtocolTool;
import Type.Status;

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
  Status status;

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
        in.read(buf);
        data = ProtocolTool.ProtocolTrim(buf);

        //should login First
        //to connect database and get information of player
        if (data.protocol == ProtocolID.LOGIN){
          System.out.println("SID " + SocketID + ": "+data.protocol + " " + new String(data.data));
          //have to return PID to UserSocket
          PlayerID = Login.login(out,data.data);
//          name = Login.get_name(PlayerID);
        } else if (data.protocol == ProtocolID.REGISTER){
          System.out.println("SID " + SocketID + ": "+data.protocol + " " + new String(data.data));
          PlayerID = Login.register(out,data.data);
//          name = Login.get_name(PlayerID);
        } else {
          System.out.println("SID " + SocketID + ": "+data.protocol + " " + new String(data.data));
        }
      }while(PlayerID == -1);
      status = Login.getStatus(PlayerID);
      Login.sendStatus(out,PlayerID);


      //read client action
      while (true) {
        buf = new byte[1000];  //clear buffer
        in.read(buf);
        data = ProtocolTool.ProtocolTrim(buf);
        System.out.println("SID " + SocketID + ": "+data.protocol + " " + new String(data.data));

        switch (data.protocol) {
          case ProtocolID.LOGIN_LOCATION:
            LoginLocation.Login_Location(data.data,status);
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
