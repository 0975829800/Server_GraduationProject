package Tools;

import DBS.DBConnection;
import ID.ActionID;
import ID.TypeID;
import ServerMainBody.Server;
import Type.*;

import java.util.ArrayList;

public class DisconnectTool {
  public static void PlayerDisconnect(int SocketID, int PID,PlayerInformation playerInformation) {
    Server.Action.add(new ActionType(ActionID.PLAYER_LEAVE,playerInformation.MapID,playerInformation.PID,0,0,0,0));

    Server.online.removeIf(id -> id == PID);
    Server.User.removeIf(socket -> socket.ID == SocketID);                      //remove socket about player
    Server.Map.removeIf(m -> m.TypeID == TypeID.PLAYER && m.BelongID == PID);   //remove map information about player
    Server.Information.removeIf(p->p.PID == playerInformation.PID);
    StatusUpdate(PID,playerInformation.status);
    ItemUpdate(PID, playerInformation.item);
    EquipmentUpdate(PID,playerInformation.equipment);
    ProgressUpdate(PID,playerInformation.progress);
  }

  public static void StatusUpdate(int PID, Status status){
    try {

      DBConnection.updateStatus(status.PlayID,status.HP,status.MAX_HP,status.MP,status.MAX_MP,
              status.STR,status.MG,status.AGI,status.LUC,status.Level,status.Skill_Point,status.State,status.coin,status.EXP);
    }catch (Exception e){
      e.printStackTrace();
    }
  }

  public static void ItemUpdate(int PID, ArrayList<ItemType> items){
    try {

      DBConnection.delItem_bag(PID);
      for(ItemType i : items){
        DBConnection.addItem_bag(i.PlayerID,i.ItemBox_ID,i.Item_ID,i.Rarity,i.Amount);
      }
    }catch (Exception e){
      e.printStackTrace();
    }
  }

  public static void EquipmentUpdate(int PID, ArrayList<EquipmentBoxType> equipments){
    try{

      DBConnection.delEquipment_bag(PID);
      for(EquipmentBoxType e : equipments){
        if(e.Equipping)
          DBConnection.addEquipment_bag(e.PlayerID,e.EquipmentBox_ID,e.Equipment_ID,e.Rarity,e.Part,e.Level,1,e.Skill_ID_1,e.Skill_ID_2);
        else
          DBConnection.addEquipment_bag(e.PlayerID,e.EquipmentBox_ID,e.Equipment_ID,e.Rarity,e.Part,e.Level,0,e.Skill_ID_1,e.Skill_ID_2);
      }
    }catch (Exception e){
      e.printStackTrace();
    }
  }

  public static void ProgressUpdate(int PID, ArrayList<Progress> progresses){
    try {

      DBConnection.delProgresses(PID);
      for(Progress p: progresses){
        DBConnection.addProgress(p);
      }
    }catch (Exception e){
      e.printStackTrace();
    }
  }

  public static void leaveTeam(int PID){
    try{

//      con.setTeam(PID,0);
    }catch (Exception e){
      e.printStackTrace();
    }
  }
}
