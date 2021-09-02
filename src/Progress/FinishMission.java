package Progress;

import Action.MessageSender;
import ID.EquipmentID;
import ID.ItemID;
import ID.MissionID;
import ID.TypeID;
import Tools.ByteArrayTransform;
import Tools.LevelTool;
import Tools.ToCSharpTool;
import Type.*;


public class FinishMission {
  public static void finish(PlayerInformation p, byte[] data){
    int QuestID = ByteArrayTransform.ToInt(data,0);
    byte[] buf;
    int FinishState = 1;
    for(Progress progress: p.progress){
      if(progress.missionID == QuestID){
        if(progress.state == 0){
          FinishState = 2;

          if(MissionID.missions[QuestID].DemandType == TypeID.ITEM) {
            if(GetDemandItem(p,MissionID.missions[QuestID])){
              FinishState = 0;
              progress.state = 2;
              progress.information1 = progress.information2;
              MessageSender.StatusUpdate(p);
              if(MissionID.missions[QuestID].RewardType == TypeID.ITEM){
                MessageSender.ItemBoxUpdate(p);
              }else if(MissionID.missions[QuestID].RewardType == TypeID.EQUIPMENT){
                MessageSender.EquipBoxUpdate(p);
              }
            }
          }

          if(MissionID.missions[QuestID].DemandType == TypeID.EQUIPMENT) {
            if(GetDemandEquipment(p,MissionID.missions[QuestID])){
              FinishState = 0;
              progress.state = 2;
              progress.information1 = progress.information2;
              MessageSender.StatusUpdate(p);
              if(MissionID.missions[QuestID].RewardType == TypeID.ITEM){
                MessageSender.ItemBoxUpdate(p);
              }else if(MissionID.missions[QuestID].RewardType == TypeID.EQUIPMENT){
                MessageSender.EquipBoxUpdate(p);
              }
            }
          }

        }
        else if(progress.state == 2){
          FinishState = 3;
        }
        else { //完成
          FinishState = 0;
          progress.state = 2;
          //發送獎勵
          RewardSend(p,MissionID.missions[QuestID]);
          MessageSender.StatusUpdate(p);
          if(MissionID.missions[QuestID].RewardType == TypeID.ITEM){
            MessageSender.ItemBoxUpdate(p);
          }else if(MissionID.missions[QuestID].RewardType == TypeID.EQUIPMENT){
            MessageSender.EquipBoxUpdate(p);
          }
        }
      }
    }

    buf = ToCSharpTool.ToCSharp(FinishState);

    try {
      p.sc.getOutputStream().write(buf);
    } catch (Exception e) {
      e.printStackTrace();
    }

    MessageSender.QuestUpdate(p);
  }

  public static boolean GetDemandItem(PlayerInformation p, MissionType mission){
    boolean flag = false;
    for(ItemType i : p.item){
      if(i.Item_ID == mission.DemandBelong && mission.CompleteAmount <= i.Amount){
        i.Amount -= mission.CompleteAmount;
        if(i.Amount == 0){
          p.item.removeIf(itemType -> itemType == i);
        }
        flag = true;
        break;
      }
    }
    if(flag){
      RewardSend(p,mission);
    }
    return flag;
  }

  public static boolean GetDemandEquipment(PlayerInformation p, MissionType mission){
    boolean flag = false;
    EquipmentBoxType tmp = null;
    for(EquipmentBoxType e: p.equipment){
      if(e.Equipment_ID == mission.DemandBelong){
        if(e.Equipping){
          tmp = e;
        }else {
          p.equipment.removeIf(equipmentBoxType -> equipmentBoxType == e);
          flag = true;
          break;
        }
      }
    }

    if(!flag && tmp != null){
      EquipmentBoxType finalTmp = tmp;
      p.equipment.removeIf(equipmentBoxType -> equipmentBoxType == finalTmp);
      flag = true;
    }

    if(flag){
      RewardSend(p,mission);
    }
    return flag;
  }

  public static void RewardSend(PlayerInformation p, MissionType mission){
    p.status.coin += mission.Coin;
    LevelTool.expControl(p,mission.EXP);
    int amount = mission.RewardAmount;
    if(mission.RewardType == TypeID.ITEM){
      for(ItemType i : p.item){
        if(i.Item_ID == mission.RewardBelong && i.Amount != 99){
          if(i.Amount + amount <= 99){
            i.Amount += amount;
            amount = 0;
            break;
          }else {
            amount = i.Amount + amount - 99;
            i.Amount = 99;
          }
        }
      }
      if(amount > 0){
        int index = p.getEmptyItemBoxIndex();
        ShopItem si = ItemID.GetItemState(mission.RewardBelong);
        if(si != null){
          p.item.add(new ItemType(p.PID,index,mission.RewardBelong, si.Rarity,amount));
        }
      }
    }else if(mission.RewardType == TypeID.EQUIPMENT){
      int index = p.getEmptyEquipmentBoxIndex();

      EquipmentType eq = EquipmentID.GetEquipmentState(mission.RewardBelong);
      if(eq != null){
        p.equipment.add(new EquipmentBoxType(p.PID,index, eq));
      }
    }
  }
}
