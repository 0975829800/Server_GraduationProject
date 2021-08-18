package Action;

import Tools.ByteArrayTransform;
import Tools.ToCSharpTool;
import Type.EquipmentBoxType;
import Type.PlayerInformation;

import java.io.OutputStream;

public class Equip {
  public static void Equip(OutputStream out, PlayerInformation playerInformation, byte[] Data){
    int EquipmentOrder = ByteArrayTransform.ToInt(Data,0);
    boolean success = false;

    for (EquipmentBoxType e: playerInformation.equipment){
      if(e.EquipmentBox_ID == EquipmentOrder){
        if(e.Equipping){
          break;
        }
        for(EquipmentBoxType f: playerInformation.equipment){
          if(f.Part == e.Part && f.Equipping){
            f.Equipping = false;
          }
        }
        e.Equipping = true;
        EquipmentStatusUpdate(playerInformation);
        MessageSender.EquippedSuccessUpdate(playerInformation,EquipmentOrder);
        MessageSender.EquipBoxUpdate(playerInformation);
        success = true;
        break;
      }
    }
  }

  public static void DisEquip(OutputStream out, PlayerInformation playerInformation, byte[] Data){
    int EquipmentOrder = ByteArrayTransform.ToInt(Data,0);
    boolean success = false;

    for (EquipmentBoxType e: playerInformation.equipment){
      if(e.EquipmentBox_ID == EquipmentOrder){
        if(!e.Equipping){
          break;
        }
        e.Equipping = false;
        EquipmentStatusUpdate(playerInformation);
        MessageSender.DisEquippedSuccessUpdate(playerInformation,EquipmentOrder);
        MessageSender.EquipBoxUpdate(playerInformation);
        success = true;
      }
    }
  }

  public static void EquipmentStatusUpdate(PlayerInformation p){
    p.status.EquipStatusReset();
    for(EquipmentBoxType e: p.equipment){
      if(e.Equipping){
        int[] ES = e.getEquipStatus();
        p.status.EquipUP(ES[0],ES[1],ES[2],ES[3]);
      }
    }
  }
}
