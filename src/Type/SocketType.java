package Type;

import java.net.Socket;

public class SocketType {
  public int ID;
  public Socket Socket;
  public Socket ActionSocket;
  public Socket MapSocket;
  public Socket MessageSocket;
  public SocketType(int ID, Socket socket, Socket actionSocket, Socket mapSocket, Socket messageSocket){
    this.ID = ID;
    Socket = socket;
    ActionSocket = actionSocket;
    MapSocket = mapSocket;
    MessageSocket = messageSocket;
  }
}
