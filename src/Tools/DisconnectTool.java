package Tools;

import Action.Equip;
import DBS.DBConnection;
import ID.TypeID;
import Type.*;
import ServerMainBody.*;

import java.util.ArrayList;

public class DisconnectTool {
  public static void PlayerDisconnect(int SocketID, int PID,PlayerInformation playerInformation) {
    Server.online.removeIf(id -> id == PID);
    Server.User.removeIf(socket -> socket.ID == SocketID);                      //remove socket about player
    Server.Map.removeIf(m -> m.TypeID == TypeID.PLAYER && m.BelongID == PID);   //remove map information about player
    StatusUpdate(PID,playerInformation.status);
    ItemUpdate(PID, playerInformation.item);
    EquipmentUpdate(PID,playerInformation.equipment);
  }

  public static void StatusUpdate(int PID, Status status){
    try {
      DBConnection con = new DBConnection();
      con.updateStatus(status.PlayID,status.HP,status.MAX_HP,status.MP,status.MAX_MP,
              status.STR,status.MG,status.AGI,status.LUC,status.Level,status.Skill_Point,status.State,status.coin);
    }catch (Exception e){
      System.err.println(e);
    }
  }

  public static void ItemUpdate(int PID, ArrayList<ItemType> items){
    try {
      DBConnection con = new DBConnection();
      con.delItem_bag(PID);
      for(ItemType i : items){
        con.addItem_bag(i.PlayerID,i.ItemBox_ID,i.Item_ID,i.Rarity,i.Amount);
      }
    }catch (Exception e){
      System.err.println(e);
    }
  }

  public static void EquipmentUpdate(int PID, ArrayList<EquipmentBoxType> equipments){
    try{
      DBConnection con = new DBConnection();
      con.delEquipment_bag(PID);
      for(EquipmentBoxType e : equipments){
        if(e.Equipping)
          con.updateEquipment_bag(e.PlayerID,e.EquipmentBox_ID,e.Equipment_ID,e.Rarity,e.Part,e.Level,1,e.Skill_ID_1,e.Skill_ID_2);
        else
          con.updateEquipment_bag(e.PlayerID,e.EquipmentBox_ID,e.Equipment_ID,e.Rarity,e.Part,e.Level,0,e.Skill_ID_1,e.Skill_ID_2);
      }
    }catch (Exception e){
      System.err.println(e);
    }
  }
}
