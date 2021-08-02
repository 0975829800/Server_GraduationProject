package ServerMainBody;

import Control.MonsterCreate;
import ID.*;
import Type.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

public class Server {
  public static Queue<SocketType> 	User        = new LinkedList<>();
  public static Queue<Integer> 	    online      = new LinkedList<>();
  public static Queue<NoticeType> 	Notice      = new LinkedList<>();
  public static Queue<MapType>      Map         = new LinkedList<>();
  public static Queue<ActionType>   Action      = new LinkedList<>();
  public static Queue<MonsterType>  Monster     = new LinkedList<>();
  public static Queue<PlayerInformation> Information = new LinkedList<>();

  public final static int ServerPort = 8001;
  public final static int ActionPort = 8002;
  public final static int MapPort    = 8003;
  public final static int MessagePort = 8004;



  public static boolean   debug = false; //debug時改成true

  public static void main(String[] args)
  {
    ServerSocket  serverSocket;
    ServerSocket  actionSocket;
    ServerSocket  mapSocket;
    ServerSocket  messageSocket;
    Socket        ss,as,ms,mss;
    int ID = 0;

    SkillID.setSkillInformation();
    EquipmentID.setEquipmentInformation();
    LocationID.setLocation();
    ShopID.SetItemShop();
    ShopID.SetEquipmentShop();
    MonsterID.GetMonsterInformation();

    try {
      serverSocket = new ServerSocket(ServerPort);
      actionSocket = new ServerSocket(ActionPort);
      mapSocket = new ServerSocket(MapPort);
      messageSocket = new ServerSocket(MessagePort);
      System.out.println("ServerMainBody.Server Waiting Request...");
      Thread broadcast = new Thread(new Broadcast());
      Thread MonsterCreate = new Thread(new MonsterCreate());
      broadcast.start();
      MonsterCreate.start();
      while (true){
        ss = serverSocket.accept();
        as = actionSocket.accept();
        ms = mapSocket.accept();
        mss = messageSocket.accept();
        User.add(new SocketType(ID, ss, as, ms, mss));
        Thread userSocket = new Thread(new UserSocket(ID, ss, mss));
        userSocket.start();
        System.out.printf("ID: %06d in!\n", ID);
        ID++;
      }
    }
    catch (Exception e){
     System.err.println(e);
    }
  }

  public static void PrintPlayerInformation(){
    for(PlayerInformation p : Information){
      System.out.println(p.toString());
    }
  }
}
