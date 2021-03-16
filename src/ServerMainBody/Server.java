package ServerMainBody;

import ServerMainBody.UserSocket;
import Type.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

public class Server {
  public static Queue<SocketType> 	User        = new LinkedList<>();
  public static Queue<TeamType> 	Team        = new LinkedList<>();
  public static Queue<MapType>      Map         = new LinkedList<>();
  public static Queue<ActionType>   Action      = new LinkedList<>();

  public final static int ServerPort        = 6666;
  public final static int BroadcastPort     = 6667;
  public static boolean   debug = false; //debug時改成true

  public static void main(String[] args)
  {
    ServerSocket  serverSocket;
    Socket        sc;
    int ID = 0;

    try {
      serverSocket = new ServerSocket(ServerPort);
      System.out.println("ServerMainBody.Server Waiting Request...");
      Thread broadcast = new Thread(new Broadcast());
      while (true){
        sc = serverSocket.accept();
        User.add(new SocketType(ID, sc));
        Thread userSocket = new Thread(new UserSocket(ID, sc));
        userSocket.start();
        System.out.printf("ID: %06d in!\n", ID);
        ID++;
      }
    }
    catch (Exception e){
     System.err.println(e);
    }
  }
}
