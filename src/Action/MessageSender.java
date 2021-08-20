package Action;

import Tools.ToCSharpTool;
import Type.*;

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
    }catch (Exception e){
      e.printStackTrace();
    }
  }

  public static void EquipDrop(PlayerInformation p, int MonsterID, EquipmentBoxType equipment){
    buf = new byte[8 + EquipmentBoxType.SendSize];
    try {
      byte[] protocol = ToCSharpTool.ToCSharp(3);
      System.arraycopy(protocol,0,buf,0,4);
      System.arraycopy(ToCSharpTool.ToCSharp(MonsterID),0,buf,4,4);
      System.arraycopy(equipment.getByte(),0,buf,8,EquipmentBoxType.SendSize);
      p.mss.getOutputStream().write(buf);
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
        start+=EquipmentBoxType.SendSize;
      }
      p.mss.getOutputStream().write(buf);
    }catch (Exception e){
      e.printStackTrace();
    }
  }

  public static void LevelUp(PlayerInformation p){
    buf = new byte[4 + Status.SendSize];
    try {
      byte[] protocol = ToCSharpTool.ToCSharp(6);
      byte[] status = p.status.getByte();
      System.arraycopy(protocol,0,buf,0,4);
      System.arraycopy(status,0,buf,4,Status.SendSize);
      p.mss.getOutputStream().write(buf);
    }catch (Exception e){
      e.printStackTrace();
    }
  }

  public static void QuestUpdate(PlayerInformation p){
  }

  public static void StatusUpdate(PlayerInformation p){
    buf = new byte[4 + Status.SendSize];
    try {
      byte[] protocol = ToCSharpTool.ToCSharp(8);
      byte[] status = p.status.getByte();
      System.arraycopy(protocol,0,buf,0,4);
      System.arraycopy(status,0,buf,4,Status.SendSize);
      p.mss.getOutputStream().write(buf);
    }catch (Exception e){
      e.printStackTrace();
    }
  }

  public static void Heal(PlayerInformation p, int heal){
    buf = new byte[8];
    try {
      byte[] protocol = ToCSharpTool.ToCSharp(9);
      byte[] healb = ToCSharpTool.ToCSharp(heal);
      System.arraycopy(protocol,0,buf,0,4);
      System.arraycopy(healb,0,buf,4,4);
      p.mss.getOutputStream().write(buf);
    }catch (Exception e){
      e.printStackTrace();
    }
  }

  public static void HealMP(PlayerInformation p, int heal){
    buf = new byte[8];
    try {
      byte[] protocol = ToCSharpTool.ToCSharp(10);
      byte[] healb = ToCSharpTool.ToCSharp(heal);
      System.arraycopy(protocol,0,buf,0,4);
      System.arraycopy(healb,0,buf,4,4);
      p.mss.getOutputStream().write(buf);
    }catch (Exception e){
      e.printStackTrace();
    }
  }

  public static void Stronger(PlayerInformation p, int type, int BuffRange){
    byte[] buf = new byte[12];
    try {
      byte[] protocol = ToCSharpTool.ToCSharp(11);
      System.arraycopy(protocol,0,buf,0,4);
      System.arraycopy(ToCSharpTool.ToCSharp(type),0,buf,4,4);
      System.arraycopy(ToCSharpTool.ToCSharp(BuffRange),0,buf,8,4);
      p.mss.getOutputStream().write(buf);
    }catch (Exception e){
      e.printStackTrace();
    }
  }

  public static void EquippedSuccessUpdate(PlayerInformation p, int order){
    byte[] buf = new byte[24];
    try {
      System.arraycopy(ToCSharpTool.ToCSharp(12)      ,0,buf,0,4);
      System.arraycopy(ToCSharpTool.ToCSharp(order)         ,0,buf,4,4);
      System.arraycopy(ToCSharpTool.ToCSharp(p.status.ESTR) ,0,buf,8,4);
      System.arraycopy(ToCSharpTool.ToCSharp(p.status.EMG)  ,0,buf,12,4);
      System.arraycopy(ToCSharpTool.ToCSharp(p.status.EAGI) ,0,buf,16,4);
      System.arraycopy(ToCSharpTool.ToCSharp(p.status.ELUC) ,0,buf,20,4);
      p.mss.getOutputStream().write(buf);
    }catch (Exception e){
      e.printStackTrace();
    }
  }

  public static void DisEquippedSuccessUpdate(PlayerInformation p, int order){
    byte[] buf = new byte[24];
    try {
      System.arraycopy(ToCSharpTool.ToCSharp(13)      ,0,buf,0,4);
      System.arraycopy(ToCSharpTool.ToCSharp(order)         ,0,buf,4,4);
      System.arraycopy(ToCSharpTool.ToCSharp(p.status.ESTR) ,0,buf,8,4);
      System.arraycopy(ToCSharpTool.ToCSharp(p.status.EMG)  ,0,buf,12,4);
      System.arraycopy(ToCSharpTool.ToCSharp(p.status.EAGI) ,0,buf,16,4);
      System.arraycopy(ToCSharpTool.ToCSharp(p.status.ELUC) ,0,buf,20,4);
      p.mss.getOutputStream().write(buf);
    }catch (Exception e){
      e.printStackTrace();
    }
  }

  public static void EquipmentStatusUpdate(PlayerInformation p){
    byte[] buf = new byte[20];
    try {
      System.arraycopy(ToCSharpTool.ToCSharp(14)      ,0,buf,0,4);
      System.arraycopy(ToCSharpTool.ToCSharp(p.status.ESTR) ,0,buf,4,4);
      System.arraycopy(ToCSharpTool.ToCSharp(p.status.EMG)  ,0,buf,8,4);
      System.arraycopy(ToCSharpTool.ToCSharp(p.status.EAGI) ,0,buf,12,4);
      System.arraycopy(ToCSharpTool.ToCSharp(p.status.ELUC) ,0,buf,16,4);
      p.mss.getOutputStream().write(buf);
    }catch (Exception e){
      e.printStackTrace();
    }
  }

  public static void ItemBoxUpdate(PlayerInformation p){
    byte[] buf = new byte[4+p.item.size()* ItemType.SendSize];
    int start = 4;
    try {
      System.arraycopy(ToCSharpTool.ToCSharp(15),0,buf,0,4);
      for (ItemType i: p.item){
        System.arraycopy(i.getByte(),0,buf,start,ItemType.SendSize);
        start += ItemType.SendSize;
      }
      p.mss.getOutputStream().write(buf);
    }catch (Exception e){
      e.printStackTrace();
    }
  }

  public static void JoinTeamNotice(PlayerInformation p, int joinPID){
    buf = new byte[8];
    try {
      byte[] protocol = ToCSharpTool.ToCSharp(16);
      byte[] PID = ToCSharpTool.ToCSharp(joinPID);
      System.arraycopy(protocol,0,buf,0,4);
      System.arraycopy(PID,0,buf,4,4);
      p.mss.getOutputStream().write(buf);
    }catch (Exception e){
      e.printStackTrace();
    }
  }

  public static void LeaveTeamNotice(PlayerInformation p, int leavePID){
    buf = new byte[8];
    try {
      byte[] protocol = ToCSharpTool.ToCSharp(17);
      byte[] PID = ToCSharpTool.ToCSharp(leavePID);
      System.arraycopy(protocol,0,buf,0,4);
      System.arraycopy(PID,0,buf,4,4);
      p.mss.getOutputStream().write(buf);
    }catch (Exception e){
      e.printStackTrace();
    }
  }

  public static void DelTeamNotice(PlayerInformation p){
    buf = new byte[4];
    try {
      byte[] protocol = ToCSharpTool.ToCSharp(18);
      System.arraycopy(protocol,0,buf,0,4);
      p.mss.getOutputStream().write(buf);
    }catch (Exception e){
      e.printStackTrace();
    }
  }

  public static void SetSkillPointSuccess(PlayerInformation p){
    buf = new byte[4];
    try {
      byte[] protocol = ToCSharpTool.ToCSharp(19);
      System.arraycopy(protocol,0,buf,0,4);
      p.mss.getOutputStream().write(buf);
    }catch (Exception e){
      e.printStackTrace();
    }
  }

  public static void SellSuccess(PlayerInformation p){
    buf = new byte[4];
    try {
      byte[] protocol = ToCSharpTool.ToCSharp(20);
      System.arraycopy(protocol,0,buf,0,4);
      p.mss.getOutputStream().write(buf);
    }catch (Exception e){
      e.printStackTrace();
    }
  }
}
