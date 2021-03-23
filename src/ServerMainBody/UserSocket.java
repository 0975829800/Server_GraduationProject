package ServerMainBody;

import java.net.Socket;
import java.nio.ByteBuffer;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import Type.*;
import DBS.*;
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
      ProtocolType data = ProtocolType.ProtocolTrim(buf);
      if (data.protocol == ProtocolType.LOGIN){
        System.out.println(1);
      } else if (data.protocol == ProtocolType.REGISTER){
        System.out.println(2);
      } else {
        System.out.println(3);
      }

      //read client action
      while (true) {
        buf = new byte[1000];  //clear buffer
        in.read(buf);
        data = ProtocolType.ProtocolTrim(buf);
        System.out.println(data.protocol + " " + new String(data.data));
        //switch (data.protocol) {
        //}
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
