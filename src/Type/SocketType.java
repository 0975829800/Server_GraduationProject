package Type;

import java.net.Socket;

public class SocketType {
  public int ID;
  public Socket Socket;
  public Socket ActionSocket;
  public Socket MapSocket;
  public SocketType(int ID, Socket socket, Socket actionSocket, Socket mapSocket){
    this.ID = ID;
    Socket = socket;
    ActionSocket = actionSocket;
    MapSocket = mapSocket;
  }
}
