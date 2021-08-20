package ServerMainBody;

import Control.MonsterCreate;
import ID.*;
import Type.*;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Server {
  public static BlockingQueue<SocketType>   User        = new LinkedBlockingQueue<>();
  public static BlockingQueue<Integer> 	    online      = new LinkedBlockingQueue<>();
  public static BlockingQueue<MapType>      Map         = new LinkedBlockingQueue<>();
  public static BlockingQueue<ActionType>   Action      = new LinkedBlockingQueue<>();
  public static BlockingQueue<MonsterType>  Monster     = new LinkedBlockingQueue<>();
  public static BlockingQueue<PlayerInformation> Information = new LinkedBlockingQueue<>();

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
    ItemID.SetShopItems();
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
     e.printStackTrace();
    }
  }
}
