import Type.*;
import java.io.OutputStream;

public class Broadcast extends Thread{
  OutputStream out;
  public void run() {
    while (true){
      try {
        for (SocketType socketType : Server.User){
          out = socketType.socket.getOutputStream();

          //write Broadcast message to every Player

        }
        sleep(1);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
