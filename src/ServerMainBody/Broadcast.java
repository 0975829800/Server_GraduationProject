package ServerMainBody;

import ServerMainBody.Server;
import Type.*;
import java.io.OutputStream;

public class Broadcast extends Thread{
  OutputStream out;
  public void run() {
    while (true){
      try {
        for (SocketType socketType : Server.User){
          out = socketType.socket.getOutputStream();

          //write ServerMainBody.Broadcast message to every Player
          //Action
          if(!Server.Action.isEmpty()){

          }

          //Map information


        }
        sleep(1);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
