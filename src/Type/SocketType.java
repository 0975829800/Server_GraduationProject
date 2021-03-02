package Type;

import java.net.Socket;

public class SocketType {
  int ID;
  Socket socket;
  public SocketType(int ID, Socket socket){
    this.ID = ID;
    this.socket = socket;
  }
}
