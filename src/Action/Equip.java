package Action;

import Tools.ByteArrayTransform;
import Tools.ToCSharpTool;
import Type.EquipmentBoxType;
import Type.PlayerInformation;

import java.io.OutputStream;

public class Equip {
  public PlayerInformation Equip(OutputStream out, PlayerInformation playerInformation, byte[] Data){
    int EquipmentOrder = ByteArrayTransform.ToInt(Data,0);
    boolean success = false;

    for (EquipmentBoxType e: playerInformation.equipment){
      if(e.EquipmentBox_ID == EquipmentOrder){
        if(e.Equipping){
          break;
        }
        e.Equipping = true;
        success = true;
      }
    }
    try{
      byte[] buf;
      if(success)
        buf = ToCSharpTool.ToCSharp(1);
      else
        buf = ToCSharpTool.ToCSharp(-1);
      out.write(buf);
    }catch (Exception e){
      System.err.println(e);
    }


    return playerInformation;
  }
  public PlayerInformation DisEquip(OutputStream out, PlayerInformation playerInformation, byte[] Data){
    int EquipmentOrder = ByteArrayTransform.ToInt(Data,0);
    boolean success = false;

    for (EquipmentBoxType e: playerInformation.equipment){
      if(e.EquipmentBox_ID == EquipmentOrder){
        if(!e.Equipping){
          break;
        }
        e.Equipping = false;
        success = true;
      }
    }

    try{
      byte[] buf;
      if(success)
        buf = ToCSharpTool.ToCSharp(1);
      else
        buf = ToCSharpTool.ToCSharp(-1);
      out.write(buf);
    }catch (Exception e){
      System.err.println(e);
    }

    return playerInformation;
  }
}
