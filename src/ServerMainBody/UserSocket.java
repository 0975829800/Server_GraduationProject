package ServerMainBody;

import java.net.Socket;
import java.io.InputStream;
import java.io.OutputStream;

import Tools.*;
import Type.ActionType;

public class UserSocket extends Thread{
  int           SocketID;
  int           PlayerID = -1;
  Socket        socket;
  InputStream   in;
  OutputStream  out;

  byte[]        buf = new byte[1000];

  public UserSocket(int ID, Socket sc){
    SocketID  = ID;
    socket    = sc;
  }

  //thread running
  public void run(){
    try {
      in = socket.getInputStream();

      //read login message
      in.read(buf);
      ProtocolTool data = ProtocolTool.ProtocolTrim(buf);
      if (data.protocol == ProtocolTool.LOGIN){
        System.out.println(1);
      } else if (data.protocol == ProtocolTool.REGISTER){
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
