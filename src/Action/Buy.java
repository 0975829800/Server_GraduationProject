package Action;

import ID.EquipmentID;
import ID.ItemID;
import ID.ShopID;
import ServerMainBody.Server;
import Tools.ByteArrayTransform;
import Tools.ToCSharpTool;
import Type.*;

import java.io.OutputStream;

public class Buy {
  public static void BuyEquipment(OutputStream out, PlayerInformation playerInformation, byte[] Data){
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
  }

  public static void BuyItem(OutputStream out, PlayerInformation playerInformation, byte[] Data){
    byte[] buf = new byte[4];
    boolean hasSame = false;
    boolean isFull = false;
    int order  = ByteArrayTransform.ToInt(Data,0);
    int amount = ByteArrayTransform.ToInt(Data,4);
    if(playerInformation.status.coin >= ShopID.shopItem.get(order).Price * amount){
      playerInformation.status.coin -= ShopID.shopItem.get(order).Price * amount;

      for (ItemType i : playerInformation.item){
        if(i.Amount == 99){ //道具滿(99個)跳過
          continue;
        }

        if(i.Item_ID == ShopID.shopItem.get(order).ItemID && i.Rarity == ShopID.shopItem.get(order).Rarity){ //找背包是否有一樣的道具
          i.Amount+=amount;
          if(i.Amount/100 > 0){ //是否超過單個道具欄上限
            int EmptyIndex = playerInformation.getEmptyItemBoxIndex();
            if(EmptyIndex != -1) {
              playerInformation.item.add(new ItemType(playerInformation.PID, EmptyIndex, i.Item_ID, i.Rarity, i.Amount % 99));
            }else {
              buf = new byte[4];
              buf = ToCSharpTool.ToCSharp(-2);
              isFull = true;
              break;
            }
            i.Amount = 99;
            hasSame = true;
            break;
          }
          hasSame = true;
        }
      }
      if(!hasSame && !isFull){
        playerInformation.item.add(new ItemType(playerInformation.PID,playerInformation.getEmptyItemBoxIndex(),
                ShopID.shopItem.get(order).ItemID,ShopID.shopItem.get(order).Rarity,amount));
      }
      if(!isFull){
        buf = new byte[4+ItemType.SendSize*playerInformation.item.size()];
        System.arraycopy(ToCSharpTool.ToCSharp(playerInformation.status.coin),0,buf,0,4);
        int start = 4;
        for(ItemType i : playerInformation.item){
          System.arraycopy(i.getByte(),0,buf,start,ItemType.SendSize);
          start += ItemType.SendSize;
        }
      }
    }else {
      buf = new byte[4];
      buf = ToCSharpTool.ToCSharp(-1);
    }

    try {
      out.write(buf);
    }catch (Exception e){
      System.err.println(e);
    }
  }

  public static void SellEquipment(OutputStream out, PlayerInformation playerInformation, byte[] data){
    int order = ByteArrayTransform.ToInt(data,0);
    for(EquipmentBoxType e: playerInformation.equipment){
      if(e.EquipmentBox_ID == order){
        for(EquipmentType f: EquipmentID.EquipmentInformation){
          if(f.EID == e.Equipment_ID){
            playerInformation.status.coin += f.price/10;
            break;
          }
        }
        break;
      }
    }
    playerInformation.equipment.removeIf(e->e.EquipmentBox_ID == order);

    MessageSender.SellSuccess(playerInformation);
    MessageSender.StatusUpdate(playerInformation);
    MessageSender.EquipBoxUpdate(playerInformation);

  }

  public static void SellItem(OutputStream out, PlayerInformation playerInformation, byte[] data){
    int order = ByteArrayTransform.ToInt(data,0);
    int Amount = ByteArrayTransform.ToInt(data,4);
    for(ItemType i: playerInformation.item){
      if(i.ItemBox_ID == order){
        i.Amount-=Amount;
        for(ShopItem s: ItemID.shopItems){
          if(s.ItemID == i.Item_ID){
            playerInformation.status.coin += s.Price/10;
          }
        }
        if(i.Amount <= 0){
          playerInformation.item.removeIf(item->item.ItemBox_ID == order);
        }
        break;
      }
    }

    MessageSender.SellSuccess(playerInformation);
    MessageSender.StatusUpdate(playerInformation);
    MessageSender.ItemBoxUpdate(playerInformation);
  }
}
