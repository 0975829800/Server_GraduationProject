package ServerMainBody;

import ID.ShopID;
import Type.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

public class Server {
  public static Queue<SocketType> 	User        = new LinkedList<>();
  public static Queue<Integer> 	    online      = new LinkedList<>();
  public static Queue<TeamType> 	Team        = new LinkedList<>();
  public static Queue<MapType>      Map         = new LinkedList<>();
  public static Queue<ActionType>   Action      = new LinkedList<>();
  public static Queue<MonsterType>  Monster     = new LinkedList<>();

  public final static int ServerPort = 8001;
  public final static int ActionPort = 8002;
  public final static int MapPort    = 8003;


  public static boolean   debug = false; //debug時改成true

  public static void main(String[] args)
  {
    ServerSocket  serverSocket;
    ServerSocket  actionSocket;
    ServerSocket  mapSocket;
    Socket        ss,as,ms;
    int ID = 0;

    ShopID.SetItemShop();

    try {
      serverSocket = new ServerSocket(ServerPort);
      actionSocket = new ServerSocket(ActionPort);
      mapSocket = new ServerSocket(MapPort);
      System.out.println("ServerMainBody.Server Waiting Request...");
      Thread broadcast = new Thread(new Broadcast());
      broadcast.start();
      while (true){
        ss = serverSocket.accept();
        as = actionSocket.accept();
        ms = mapSocket.accept();
        User.add(new SocketType(ID, ss, as, ms));
        Thread userSocket = new Thread(new UserSocket(ID, ss));
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
