package Action;

import ID.ShopID;
import Tools.ByteArrayTransform;
import Tools.ToCSharpTool;
import Type.EquipmentBoxType;
import Type.PlayerInformation;
import Type.ShopEquipment;

import java.io.OutputStream;

public class BuyEquipment {
  public static PlayerInformation BuyEquipment(OutputStream out, PlayerInformation playerInformation, byte[] Data){
    byte[] buf;
    int order = ByteArrayTransform.ToInt(Data,0);
    ShopEquipment shopEquipment = ShopID.shopEquipment.get(order);
    if(playerInformation.status.coin >= shopEquipment.Price){
      playerInformation.status.coin -= shopEquipment.Price;

      int EmptyIndex = playerInformation.getEmptyEquipmentBoxIndex();
      if(EmptyIndex == -1){
        buf = new byte[4];
        buf = ToCSharpTool.ToCSharp(-2);
      }
      else{
        EquipmentBoxType equip = new EquipmentBoxType(playerInformation.PID,EmptyIndex,shopEquipment.EquipmentID,
                shopEquipment.Rarity,shopEquipment.part,shopEquipment.Level,shopEquipment.Skill1,shopEquipment.Skill2);
        playerInformation.equipment.add(equip);
        buf = new byte[4+EquipmentBoxType.SendSize];
        System.arraycopy(ToCSharpTool.ToCSharp(playerInformation.status.coin),0,buf,0,4);
        System.arraycopy(equip.getByte(),0,buf,4,EquipmentBoxType.SendSize);
      }
    }
    else{
      buf = new byte[4];
      buf = ToCSharpTool.ToCSharp(-1);
    }
    try {
      out.write(buf);
    }catch (Exception e){
      System.err.println(e);
    }
    return playerInformation;
  }

}
