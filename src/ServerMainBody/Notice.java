package ServerMainBody;

import Tools.PackageTool;
import Type.ActionType;
import Type.SocketType;

import java.io.OutputStream;
import java.util.LinkedList;
import java.util.Queue;

public class Notice extends Thread{
  OutputStream out;
  Queue<ActionType> temp = new LinkedList<>();
  public void run() {
    while (true){
      try {
        if(!Server.Action.isEmpty()){
          temp.addAll(Server.Action);
          Server.Action.clear();
        }
        for (SocketType socketType : Server.User){
          //write ServerMainBody.Broadcast message to every Player
          //Action
          if(!temp.isEmpty()){
            out = socketType.ActionSocket.getOutputStream();
            byte[] action = PackageTool.ActionListToByte(temp);
            out.write(action);
          }


          //Map information
          out = socketType.MapSocket.getOutputStream();
          byte[] map = PackageTool.MapTypeToByte();
          out.write(map);
        }
        temp.clear();
        sleep(1000);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
