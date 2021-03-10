package Type;

import java.net.Socket;

public class SocketType {
  public int ID;
  public Socket socket;
  public SocketType(int ID, Socket socket){
    this.ID = ID;
    this.socket = socket;
  }
}
