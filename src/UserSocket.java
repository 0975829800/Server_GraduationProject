import java.net.Socket;
import java.nio.ByteBuffer;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import Type.*;
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

      } else if (data.protocol == ProtocolType.REGISTER){

      } else {

      }

      //read client action
      in.read(buf);
      data = ProtocolType.ProtocolTrim(buf);
      switch (data.protocol){
      }
    }
    catch (Exception e){
      System.err.println(e);
      if (PlayerID == -1){
        System.out.printf("Socket ID: %06d out\n", SocketID);
      } else {
        System.out.printf("Socket ID: %06d out    ", SocketID);
        System.out.printf("Player ID: %06d out\n", PlayerID);
      }
    }
  }
}
