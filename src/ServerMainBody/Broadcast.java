package ServerMainBody;

import Tools.*;
import Type.*;
import java.io.OutputStream;

public class Broadcast extends Thread{
  OutputStream out;
  public void run() {
    while (true){
      try {
        for (SocketType socketType : Server.User){
          //write ServerMainBody.Broadcast message to every Player
          //Action
          if(!Server.Action.isEmpty()){
            out = socketType.ActionSocket.getOutputStream();
            byte[] action = PackageTool.ActionListToByte();
            out.write(action);
          }

          //Map information
          out = socketType.MapSocket.getOutputStream();
          byte[] map = PackageTool.MapTypeToByte();
          out.write(map);

        }
        sleep(1);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
