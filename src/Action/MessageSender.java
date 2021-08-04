package Action;

import Tools.ToCSharpTool;
import Type.EquipmentBoxType;
import Type.EquipmentType;
import Type.PlayerInformation;
import Type.Status;

public class MessageSender { //Message傳送方式在這寫
  static byte[] buf;

  public static void EXP(PlayerInformation p, int MonsterID, int exp){
    buf = new byte[12];
    try {
      byte[] protocol = ToCSharpTool.ToCSharp(1);
      byte[] MID = ToCSharpTool.ToCSharp(MonsterID);
      byte[] Exp = ToCSharpTool.ToCSharp(exp);
      System.arraycopy(protocol,0,buf,0,4);
      System.arraycopy(MID,0,buf,4,4);
      System.arraycopy(Exp,0,buf,8,4);
      p.mss.getOutputStream().write(buf);
      p.mss.getOutputStream().flush();
    }catch (Exception e){
      e.printStackTrace();
    }
  }

  public static void Coin(PlayerInformation p, int MonsterID, int coin){
    buf = new byte[12];
    try {
      byte[] protocol = ToCSharpTool.ToCSharp(2);
      byte[] MID = ToCSharpTool.ToCSharp(MonsterID);
      byte[] Coin = ToCSharpTool.ToCSharp(coin);
      System.arraycopy(protocol,0,buf,0,4);
      System.arraycopy(MID,0,buf,4,4);
      System.arraycopy(Coin,0,buf,8,4);
      p.mss.getOutputStream().write(buf);
      p.mss.getOutputStream().flush();
    }catch (Exception e){
      e.printStackTrace();
    }
  }

  public static void EquipDrop(PlayerInformation p, int MonsterID, EquipmentBoxType equipment){
    buf = new byte[4];
    try {
      byte[] protocol = ToCSharpTool.ToCSharp(3);
      System.arraycopy(protocol,0,buf,0,4);
      System.arraycopy(ToCSharpTool.ToCSharp(MonsterID),0,buf,4,4);
      System.arraycopy(equipment.getByte(),0,buf,8,EquipmentBoxType.SendSize);
      p.mss.getOutputStream().write(buf);
      p.mss.getOutputStream().flush();
    }catch (Exception e){
      e.printStackTrace();
    }
  }

  public static void PlayerDead(PlayerInformation p){
    buf = new byte[4];
    try {
      byte[] protocol = ToCSharpTool.ToCSharp(4);
      System.arraycopy(protocol,0,buf,0,4);
      p.mss.getOutputStream().write(buf);
      p.mss.getOutputStream().flush();
    }catch (Exception e){
      e.printStackTrace();
    }
  }

  public static void EquipBoxUpdate(PlayerInformation p){
    buf = new byte[4 + p.equipment.size() * EquipmentBoxType.SendSize];
    try {
      byte[] protocol = ToCSharpTool.ToCSharp(5);
      System.arraycopy(protocol,0,buf,0,4);
      int start = 4;
      for(EquipmentBoxType e:p.equipment){
        System.arraycopy(e.getByte(),0,buf,start,EquipmentBoxType.SendSize);
        start+=4;
      }
      p.mss.getOutputStream().write(buf);
      p.mss.getOutputStream().flush();
    }catch (Exception e){
      e.printStackTrace();
    }
  }

  public static void LevelUp(PlayerInformation p){
    System.out.println("Level up!");
    buf = new byte[4 + Status.SendSize];
    try {
      byte[] protocol = ToCSharpTool.ToCSharp(6);
      byte[] status = p.status.getByte();
      System.arraycopy(protocol,0,buf,0,4);
      System.arraycopy(status,0,buf,4,Status.SendSize);
      p.mss.getOutputStream().write(buf);
      p.mss.getOutputStream().flush();
    }catch (Exception e){
      e.printStackTrace();
    }
  }

  public static void QuestUpdate(PlayerInformation p){
  }

  public static void StatusUpdate(PlayerInformation p){
    System.out.println("Status Update");
    buf = new byte[4 + Status.SendSize];
    try {
      byte[] protocol = ToCSharpTool.ToCSharp(8);
      byte[] status = p.status.getByte();
      System.arraycopy(protocol,0,buf,0,4);
      System.arraycopy(status,0,buf,4,Status.SendSize);
      p.mss.getOutputStream().write(buf);
      p.mss.getOutputStream().flush();
    }catch (Exception e){
      e.printStackTrace();
    }
  }
}
